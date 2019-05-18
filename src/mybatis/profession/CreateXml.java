package mybatis.profession;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mybatis.entity.DataBase;
import mybatis.entity.KeyDataType;
import mybatis.entity.PropertyDetails;
import mybatis.utils.CaptureName;

/**
 * @className：CreateXml.java
 * @Title: CreateXml
 * @Description: TODO(xml生成)
 * @author: ludaqing
 * @date: 2018年5月9日下午2:49:48
 */
public class CreateXml {
    /**
     * 开始xml文件生成
     * @param dataBase 用户设置数据实体
     * @param list 属性详情集合
     */
    public static void createXml(DataBase dataBase, List<PropertyDetails> list){
    	FileWriter fw=null;
        try {
            File dir = new File("src\\" + dataBase.getXmlPath().replace(".", "\\"));// 创建一个File对象  
            if (!dir.exists()) {// 判断该目录是否存在，存在则返回true  
                //目录不存在则 创建目标目录  
                dir.mkdirs();
            }
            
            /*
             * FileWriter
             * 字符输出流，构造方法第一个参数为：保存文件的路径+文件名称（包含后缀） 第二个参数为：再次往流中添加数据时之前内容是否保留（true：保留 false：不保留）
             */
             fw = new FileWriter("src\\" + dataBase.getXmlPath().replace(".", "\\") + "\\" + dataBase.getEntityName() + "Mapper.xml", false);
            
            /*
             * 开始拼装xml头
             */
            StringBuffer stringBuffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
            stringBuffer.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n");
            
            //xml mapper标签开头
            stringBuffer.append("<mapper namespace=\"" + dataBase.getMapperPath() + "." + dataBase.getEntityName() + "Mapper\" >" + "\r\n");
            
            /*
             * 开始拼装数据库字段
             */
            //获得主键的信息
            PropertyDetails keyDetails = null;
            stringBuffer.append("  <resultMap id=\"BaseResultMap\" type=\"" + dataBase.getEntityPath() + "." + dataBase.getEntityName() + "\" >" + "\r\n");
            for (PropertyDetails propertyDetails:list) {
                if (propertyDetails.getIsKey()) {
                    keyDetails = propertyDetails;
                    stringBuffer.append("    <id column=\"" + propertyDetails.getName() + "\" property=\"" + CaptureName.lineToHump(propertyDetails.getName(),false) + "\" jdbcType=\"" + propertyDetails.getDatabaseType() + "\" />" + "\r\n");
                }else {
                    stringBuffer.append("    <result column=\"" + propertyDetails.getName() + "\" property=\"" + CaptureName.lineToHump(propertyDetails.getName(),false) + "\" jdbcType=\"" + propertyDetails.getDatabaseType() + "\" />" + "\r\n");
                }
            }
            stringBuffer.append("  </resultMap>" + "\r\n");
            
            /*
             * 开始拼装字段集合Base_Column_List
             */
            stringBuffer.append("  <sql id=\"Base_Column_List\" >" + "\r\n");
            //用于存储字段，方便超过长度限制做换行操作
            StringBuffer baseColumnList = new StringBuffer();
            //如果做过第一次换行后不能再继续用100的长度进行判断是否换行，所以需要一个变量实时增加需要换行时的长度判断
            int index = 0;
            baseColumnList.append("    ");
            for (int i = 0; i < list.size(); i++) {
                //最后一个参数后不需要逗号
                if (i == (list.size() - 1)) {
                    baseColumnList.append(list.get(i).getName() + "\r\n");
                    break;
                }else {
                    baseColumnList.append(list.get(i).getName() + ", ");
                }
                //超过长度限制就换行
                if (baseColumnList.length() > (100 + index)) {
                    baseColumnList.append("\r\n    ");
                    index += 100;
                }
            }
            //放到主StringBuffer中
            stringBuffer.append(baseColumnList);
            stringBuffer.append("  </sql>" + "\r\n");
            //wheresql
            stringBuffer.append(whereSql(list));
           
      
            
            /*
             * 开始拼装新增方法
             */
            stringBuffer.append(insert(dataBase,  list));
            stringBuffer.append("  \r\n");
            //insertSelective
            stringBuffer.append(insertSelective(dataBase,  list));
            stringBuffer.append("  \r\n");
            /*
             * 开始拼装删除方法
             */
            stringBuffer.append("  <delete id=\"deleteByPrimaryKey\" parameterType=\""+KeyDataType.getKeyJavaType(list, false)+"\" >" + "\r\n");
            stringBuffer.append("    delete from " + dataBase.getTableName() + "\r\n");
            stringBuffer.append("    where " + keyDetails.getName() + " = #{" + CaptureName.lineToHump(keyDetails.getName(), false) + ",jdbcType=" + keyDetails.getDatabaseType() + "}" + "\r\n");
            stringBuffer.append("  </delete>" + "\r\n");
            /*
             * 开始拼装查询方法
             */
            stringBuffer.append("  <select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\""+KeyDataType.getKeyJavaType(list, false)+"\" >" + "\r\n");
            stringBuffer.append("    select" + "\r\n");
            stringBuffer.append("    <include refid=\"Base_Column_List\" />" + "\r\n");
            stringBuffer.append("    from " + dataBase.getTableName() + "\r\n");
            stringBuffer.append("    where " + keyDetails.getName() + " = #{" + CaptureName.lineToHump(keyDetails.getName(), false) + ",jdbcType=" + keyDetails.getDatabaseType() + "}" + "\r\n");
            stringBuffer.append("  </select>" + "\r\n");
            
            //queryByExists
            stringBuffer.append(queryByExists( dataBase,  list));
            stringBuffer.append(" \r\n");
            
            //updateByPrimaryKeySelective
            stringBuffer.append( updateByPrimaryKeySelective(dataBase,  list));
            stringBuffer.append(" \r\n");
            //updateByPrimaryKey
            stringBuffer.append( updateByPrimaryKey(dataBase,  list));
            stringBuffer.append(" \r\n");
            stringBuffer.append("");
            //xml mapper标签结尾
            stringBuffer.append("</mapper>");
            
            fw.write(stringBuffer.toString());
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			if(fw!=null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
    }
    /**
     * 
     * 功能逻辑  获取根据不为空字段查询
     *
     * @方法名称  queryByExists
     * @作者  韩伟其
     * @创建日期  2018年12月27日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String queryByExists(DataBase dataBase, List<PropertyDetails> list){
    	String str="  <select id=\"queryByExists\" parameterType=\"" + dataBase.getEntityPath() + "." + dataBase.getEntityName() + "\" resultMap=\"BaseResultMap\" >\r\n";
    	str+="    select \r\n";
    	str+="    <include refid=\"Base_Column_List\" /> \r\n";
    	str+="    from "+ dataBase.getEntityName()+"\r\n";
    	str+="    <include refid=\"queryWhere\"/> \r\n";
    	str+="  </select> \r\n";
    	return str;
    }
    /**
     * 
     * 功能逻辑 where条件sql片段  
     *
     * @方法名称  whereSql
     * @作者  韩伟其
     * @创建日期  2018年12月27日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String whereSql(List<PropertyDetails> list){
    	String str="  <sql id=\"queryWhere\">\r\n    <where>\r\n";
    	for (PropertyDetails propertyDetails : list) {
			String NAME=propertyDetails.getName();//数据库字段
			String name=CaptureName.lineToHump(NAME, false);//po字段
			int index=list.indexOf(propertyDetails);
			str+="    <if test=\""+name+" != null\"> \r\n";
			if(index>0){str+="      and ";}
			else{str+="      ";}
			str+=NAME+" = #{"+name+",jdbcType="+propertyDetails.getDatabaseType()+"}\r\n";
			str+="    </if>\r\n";
		}
    	str+="    </where>\r\n  </sql> \r\n";
    	return str;
    }
    /**
     * 
     * 功能逻辑  不为空值插入
     *
     * @方法名称  insertSelective
     * @作者  韩伟其
     * @创建日期  2018年12月27日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String insertSelective(DataBase dataBase,List<PropertyDetails> list){
    	String str="  <insert id=\"insertSelective\" parameterType=\"" + dataBase.getEntityPath() + "." + dataBase.getEntityName() + "\" >\r\n";
    	str+="    insert into " + dataBase.getTableName() +"\r\n";
    	str+="    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
    	for (PropertyDetails propertyDetails : list) {
    		String NAME=propertyDetails.getName();//数据库字段
			String name=CaptureName.lineToHump(NAME, false);//po字段
			str+="        <if test = \""+name+" != null\">\r\n";
			if(list.indexOf(propertyDetails)>0){str+="          ,";}else{str+="          ";}
			str+=NAME+"\r\n";
			str+="        </if>\r\n";
		}
    	str+="    </trim>\r\n";
    	str+="    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n";
    	for (PropertyDetails propertyDetails : list) {
    		String dbtype=propertyDetails.getDatabaseType();
    		String NAME=propertyDetails.getName();//数据库字段
			String name=CaptureName.lineToHump(NAME, false);//po字段
			str+="        <if test = \""+name+" != null\">\r\n";
			if(list.indexOf(propertyDetails)>0){str+="          ,";}else{str+="          ";}
			str+="#{"+CaptureName.lineToHump(NAME, false)+",jdbcType="+dbtype+"}\r\n";
			str+="        </if>\r\n";
		}
    	str+="     </trim>\r\n";
    	str+="	   <selectKey resultType=\"java.lang.String\" order=\"AFTER\" keyProperty=\"id\">SELECT LAST_INSERT_ID()</selectKey>\r\n";
    	str+="  </insert>\r\n";
    	return str;
    }
    public static String insert(DataBase dataBase,List<PropertyDetails> list){
    	String str="  <insert id=\"insert\" parameterType=\"" + dataBase.getEntityPath() + "." + dataBase.getEntityName() + "\" >\r\n";
    	str+="    insert into " + dataBase.getTableName() + "(<include refid=\"Base_Column_List\" />)\r\n";
    	str+="    values(\r\n";
    	for (PropertyDetails propertyDetails : list) {
    		String dbtype=propertyDetails.getDatabaseType();
    		String NAME=propertyDetails.getName();//数据库字段
    		if(list.indexOf(propertyDetails)>0){str+="        ,";}
    		else{str+="        ";}
    		str+="#{"+CaptureName.lineToHump(NAME, false)+",jdbcType="+dbtype+"}\r\n";
    	}
    	str+="        )\r\n";
    	str+="    <selectKey resultType=\"java.lang.String\" order=\"AFTER\" keyProperty=\"id\">SELECT LAST_INSERT_ID()</selectKey>\r\n";
    	str+="  </insert>\r\n";
    	return str;
    }
    /**
     * 
     * 功能逻辑 根据主键更新  ，空属性不更新
     *
     * @方法名称  updateByPrimaryKey
     * @作者  韩伟其
     * @创建日期  2019年1月1日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String updateByPrimaryKeySelective(DataBase dataBase,List<PropertyDetails> list){
    	String str="  <update id=\"updateByPrimaryKeySelective\" parameterType=\""+dataBase.getEntityPath()+"."+dataBase.getEntityName()+"\"> \r\n";
    	str+="    update "+dataBase.getEntityName()+"\r\n     set\r\n";
    	boolean isFirst=true;
    	for (PropertyDetails propertyDetails : list) {
    		String dbtype=propertyDetails.getDatabaseType();
    		String NAME=propertyDetails.getName();//数据库字段
			String name=CaptureName.lineToHump(NAME, false);//po字段
			//去掉主键
			if(!propertyDetails.getIsKey()){
				str+="        <if test = \""+name+" != null\">\r\n";
				if(!isFirst){str+="        ,";}
				else{str+="        ";isFirst=false;}
				str+=NAME+" = #{"+CaptureName.lineToHump(NAME, false)+",jdbcType="+dbtype+"}\r\n";
				str+="        </if>\r\n";
			}
		}
    	PropertyDetails key=list.get(KeyDataType.getDBKeyIndex(list));
    	str+="    where "+key.getName()+" = #{"+CaptureName.lineToHump(key.getName(), false)+",jdbcType="+key.getDatabaseType()+"}\r\n";
    	str+="  </update>";
    	return str;
    }
    /**
     * 
     * 功能逻辑 根据主键全字段更新，空的属性值为默认值  
     *
     * @方法名称  updateByPrimaryKey
     * @作者  韩伟其
     * @创建日期  2019年1月1日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String updateByPrimaryKey(DataBase dataBase,List<PropertyDetails> list){
    	String str="  <update id=\"updateByPrimaryKey\" parameterType=\""+dataBase.getEntityPath()+"."+dataBase.getEntityName()+"\"> \r\n";
    	str+="    update "+dataBase.getEntityName()+"\r\n     set\r\n";
    	boolean isFirst=true;
    	for (PropertyDetails propertyDetails : list) {
    		String dbtype=propertyDetails.getDatabaseType();
    		String NAME=propertyDetails.getName();//数据库字段
    		if(!propertyDetails.getIsKey()){
    			if(!isFirst){str+="        ,";}
        		else{str+="        ";isFirst=false;}
    			str+="      "+NAME+" = #{"+CaptureName.lineToHump(NAME, false)+",jdbcType="+dbtype+"}\r\n";
    			
    		}
    	}
    	PropertyDetails key=list.get(KeyDataType.getDBKeyIndex(list));
    	str+="    where "+key.getName()+" = #{"+CaptureName.lineToHump(key.getName(), false)+",jdbcType="+key.getDatabaseType()+"}\r\n";
    	str+="  </update>";
    	return str;
    }
    
}
