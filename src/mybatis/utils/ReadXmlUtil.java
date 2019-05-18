package mybatis.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mybatis.entity.DataBase;
import mybatis.entity.Table;

/**
 * @className：ReadXmlUtil.java
 * @Title: ReadXmlUtil
 * @Description: TODO(读取xml文件并存储到实体类中)
 * @author: ludaqing
 * @date: 2018年5月8日下午2:33:49
 */
public class ReadXmlUtil {
    
    private static DocumentBuilderFactory dbFactory = null;
    private static DocumentBuilder db = null;
    private static Document document = null;
    private static DataBase dataBase = null;
    private static List<Table> tablelist=null;
    static{
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            db = dbFactory.newDocumentBuilder();
            dataBase = new DataBase();
            tablelist= new ArrayList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用DOM方式读取xml文件
     * 返回获取的参数
     * @return
     */
    public static Map readXml(String fileName){
        //将给定 URI 的内容解析为一个 XML 文档,并返回Document对象
        try {
            document = db.parse(fileName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        readName("jdbcConnection");
        
        readName("javaModelGenerator");
        
        readName("sqlMapGenerator");
        
        readName("javaClientGenerator");
        
        readName("table");
        Map map =new HashMap();
        map.put("dataBase", dataBase);
        map.put("tables", tablelist);
        
        return map;

    }
    
    /**
     * 根据不同的xml的标签名称得到不同标签的属性值并存储到实体类中
     * @param name
     */
    public static void readName(String name){
      //按文档顺序返回包含在文档中且具有给定标记名称的所有 Element 的 NodeList
        NodeList nodeList = document.getElementsByTagName(name);
        //遍历books
        for(int i = 0; i < nodeList.getLength(); i++){
            //获取第i个book结点
            org.w3c.dom.Node node = nodeList.item(i);
            //获取第i个book的所有属性
            NamedNodeMap namedNodeMap = node.getAttributes();
            if ("jdbcConnection".equals(name)) {
                //获取已知名的属性值
                String driver = namedNodeMap.getNamedItem("driverClass").getTextContent();
                String databasePath = namedNodeMap.getNamedItem("connectionURL").getTextContent();
                String account = namedNodeMap.getNamedItem("userId").getTextContent();
                String password = namedNodeMap.getNamedItem("password").getTextContent();
                
                dataBase.setDrive(driver);
                dataBase.setDatabasePath(databasePath);
                dataBase.setAccount(account);
                dataBase.setPassword(password);
            } else if ("javaModelGenerator".equals(name)) {
                //获取已知名的属性值
                String entityPath = namedNodeMap.getNamedItem("targetPackage").getTextContent();
                dataBase.setEntityPath(entityPath);
            } else if ("sqlMapGenerator".equals(name)) {
                //获取已知名的属性值
                String mapperPath = namedNodeMap.getNamedItem("targetPackage").getTextContent();
                dataBase.setMapperPath(mapperPath);
            } else if ("javaClientGenerator".equals(name)) {
                //获取已知名的属性值
                String xmlPath = namedNodeMap.getNamedItem("targetPackage").getTextContent();
                dataBase.setXmlPath(xmlPath);
            } else if ("table".equals(name)) {
            	 Table table = new Table();
                //获取已知名的属性值
                String tableName = namedNodeMap.getNamedItem("tableName").getTextContent();
                String entityName = namedNodeMap.getNamedItem("domainObjectName").getTextContent();
                String annotation = namedNodeMap.getNamedItem("annotation").getTextContent();
                table.setTableName(tableName);
                table.setEntityName(entityName);
                if (annotation.equals("true")) {
                	table.setAnnotation(true);
                }else {
                	table.setAnnotation(false);
                }
                tablelist.add(table);
            }
        }
    }

    
}
