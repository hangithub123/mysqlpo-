package mybatis;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mybatis.entity.DataBase;
import mybatis.entity.PropertyDetails;
import mybatis.entity.Table;
import mybatis.profession.CreateStart;
import mybatis.utils.LinkDatabaseUtil;
import mybatis.utils.ReadXmlUtil;

public class Produce {
    
    public static void main(String[] args) {
        //存储数据库中各个字段的属性名称及类型
       
        //xml文件路径
        String fileName = "src/mybatis/generatorConfig.xml";
        //获取xml中的属性set到实体类中
        Map map = ReadXmlUtil.readXml(fileName);
        DataBase  dataBase = (DataBase) map.get("dataBase");
        List<Table> listtable = (List<Table>) map.get("tables");
        //获取数据库连接
        Connection conn = LinkDatabaseUtil.getConnection(dataBase);
        PreparedStatement stmt;
        for(Table table:listtable) {
        	List<PropertyDetails> list = new ArrayList<PropertyDetails>();
        	System.out.println(table.getTableName());
        	dataBase.setTableName(table.getTableName());
        	dataBase.setEntityName(table.getEntityName());
        	dataBase.setAnnotation(table.isAnnotation());
        	String sql = "select * from " + dataBase.getTableName();
        try {
            //获取DatabaseMetaData用于获取当前主键
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet resultSet = databaseMetaData.getPrimaryKeys(null, null,  dataBase.getTableName());//获取当前主键
            String keyName = null;
            int isKey = 0;//用于判断当前主键是否已经保存，如果保存后则置为1
            while (resultSet.next()) keyName=resultSet.getString(4);
            
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                PropertyDetails propertyDetails = new PropertyDetails();
                // 获得指定列的列名
                String columnName = data.getColumnName(i);
                // 获得指定列的数据类型名
                String columnTypeName = data.getColumnTypeName(i);
                // 对应数据类型的类
                String columnClassName = data.getColumnClassName(i);
                if("INT".equals(columnTypeName)){columnTypeName="INTEGER";}
                propertyDetails.setName(columnName);
                propertyDetails.setDatabaseType(columnTypeName);
                propertyDetails.setDataType(columnClassName);
                //判断当前列名和获取的主键名称是否相同
                if (isKey == 0 && columnName.equals(keyName)) {
                    propertyDetails.setIsKey(true);
                    isKey = 1;
                }else {
                    propertyDetails.setIsKey(false);
                }
                list.add(propertyDetails);
            }
            
            CreateStart.createStart(dataBase, list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }
}
