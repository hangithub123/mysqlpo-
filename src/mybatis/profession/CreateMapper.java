package mybatis.profession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mybatis.entity.DataBase;
import mybatis.entity.KeyDataType;
import mybatis.entity.PropertyDetails;

/**
 * @className：CreateMapper.java
 * @Title: CreateMapper
 * @Description: TODO(mapper文件生成)
 * @author: ludaqing
 * @date: 2018年5月9日下午2:06:22
 */
public class CreateMapper {
    
    /**
     * 开始mapper文件生成
     * @param dataBase 用户设置数据实体
     */
    public static void createEntity(DataBase dataBase,List<PropertyDetails> list){
    	FileWriter fw =null;
        try {
            File dir = new File("src\\" + dataBase.getMapperPath().replace(".", "\\"));// 创建一个File对象  
            if (!dir.exists()) {// 判断该目录是否存在，存在则返回true  
                //目录不存在则 创建目标目录  
                dir.mkdirs();
            }
            
            /*
             * FileWriter
             * 字符输出流，构造方法第一个参数为：保存文件的路径+文件名称（包含后缀） 第二个参数为：再次往流中添加数据时之前内容是否保留（true：保留 false：不保留）
             */
             fw = new FileWriter("src\\" + dataBase.getMapperPath().replace(".", "\\") + "\\" + dataBase.getEntityName() + "Mapper.java", false);
            
            /*
             * 开始拼装mapper包导入
             */
            StringBuffer stringBuffer = new StringBuffer("package " + dataBase.getMapperPath() + ";\r\n" + "\r\n");
            stringBuffer.append("import " + dataBase.getEntityPath() + "." + dataBase.getEntityName() + ";\r\n");
            stringBuffer.append("import java.util.List;\r\n");
            
            //类名
            stringBuffer.append("\r\n" + "public interface " + dataBase.getEntityName() + "Mapper {\r\n" + "\r\n");
            
            /*
             * 开始拼装mapper接口
             */
            stringBuffer.append("\tvoid insert(" + dataBase.getEntityName() + " record);\r\n" + "\r\n");
            stringBuffer.append("\tvoid insertSelective(" + dataBase.getEntityName() + " record);\r\n" + "\r\n");
            stringBuffer.append("\tint deleteByPrimaryKey("+KeyDataType.getKeyJavaType(list,true)+" id);\r\n" + "\r\n");
            stringBuffer.append("\t" + dataBase.getEntityName() + " selectByPrimaryKey("+KeyDataType.getKeyJavaType(list,true)+" id);\r\n" + "\r\n");
            stringBuffer.append("\tList<"+dataBase.getEntityName()+"> queryByExists(" + dataBase.getEntityName() + " record);\r\n" + "\r\n");
            stringBuffer.append("\tint updateByPrimaryKeySelective(" + dataBase.getEntityName() + " record);\r\n" + "\r\n");
            stringBuffer.append("\tint updateByPrimaryKey(" + dataBase.getEntityName() + " record);\r\n" + "\r\n");
            
            //结尾大括号
            stringBuffer.append("}");
            
            fw.write(stringBuffer.toString());
            
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
				try {
					if(fw!=null)
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        }
        
    }
}
