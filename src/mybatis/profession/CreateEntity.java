package mybatis.profession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mybatis.entity.DataBase;
import mybatis.entity.PropertyDetails;
import mybatis.utils.CaptureName;
import mybatis.utils.TypeDispose;

/**
 * @className：CreateEntity.java
 * @Title: CreateEntity
 * @Description: TODO(实体类生成)
 * @author: ludaqing
 * @date: 2018年5月9日下午2:05:55
 */
public class CreateEntity {
    
    /**
     * 开始生成实体类
     * @param dataBase 用户设置数据实体
     * @param list 属性详情集合
     */
    public static void createEntity(DataBase dataBase, List<PropertyDetails> list){
    	FileWriter fw=null;
        try {
            File dir = new File("src\\" + dataBase.getEntityPath().replace(".", "\\"));// 创建一个File对象  
            if (!dir.exists()) {// 判断该目录是否存在，存在则返回true  
                //目录不存在则 创建目标目录  
                dir.mkdirs();
            }
            
            /*
             * FileWriter
             * 字符输出流，构造方法第一个参数为：保存文件的路径+文件名称（包含后缀） 第二个参数为：再次往流中添加数据时之前内容是否保留（true：保留 false：不保留）
             */
             fw = new FileWriter("src\\" + dataBase.getEntityPath().replace(".", "\\") + "\\" + dataBase.getEntityName() + ".java", false);
            
            /*
             * 开始拼装实体类包导入
             */
            StringBuffer stringBuffer = new StringBuffer("package " + dataBase.getEntityPath() + ";\r\n" + "\r\n");
            for (PropertyDetails propertyDetails:list) {
                String str = TypeDispose.importTypeDispose(propertyDetails.getDataType(), propertyDetails.getDatabaseType(), stringBuffer);
                if(str != null){
                    stringBuffer.append("import " + str + ";\r\n");
                }
            }
            //类名
            stringBuffer.append("\r\n" + "public class " + dataBase.getEntityName() + " {\r\n" + "\r\n");
            
            /*
             * 开始拼装实体类属性
             */
            for (PropertyDetails propertyDetails:list) {
                //String str = TypeDispose.attributeTypeDispose(propertyDetails.getDatabaseType());
            	String[] arr=propertyDetails.getDataType().split("\\.");
            	String type = arr[arr.length-1];
            	String str="\tprivate " + type + " " + CaptureName.lineToHump(propertyDetails.getName(),false);
                if(propertyDetails.getDataType().equals("java.lang.String")){
                	str+=" = null";
                }
                stringBuffer.append(str + ";\r\n" + "\r\n");
            }
            
            /*
             * 开始拼装set/get方法
             */
            for (PropertyDetails propertyDetails:list) {
                //String type = TypeDispose.attributeTypeDispose(propertyDetails.getDatabaseType());
            	String[] arr=propertyDetails.getDataType().split("\\.");
            	String type = arr[arr.length-1];
            	String Name=CaptureName.lineToHump(propertyDetails.getName(),true);//首字母大写
            	String name=CaptureName.lineToHump(propertyDetails.getName(),false);//首字母小写
            	
                //set方法拼装
                stringBuffer.append("\tpublic void set" + Name + "(" + type + " " + name + ") {\r\n");
                stringBuffer.append("\t\tthis." + name + " = " + name + ";\r\n");
                stringBuffer.append("\t}\r\n" + "\r\n");
                
                //get方法拼装
                stringBuffer.append("\tpublic " + type + " get" + Name + "() {\r\n");
                stringBuffer.append("\t\treturn this." + name + ";\r\n");
                stringBuffer.append("\t}\r\n" + "\r\n");
            }
            
            //结尾大括号
            stringBuffer.append("}");
            
            fw.write(stringBuffer.toString());
            
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(fw!=null)
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        
    }
    
}
