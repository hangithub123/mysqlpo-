package mybatis.utils;

/**
 * @className：TypeDispose.java
 * @Title: TypeDispose
 * @Description: TODO(类型转换处理)
 * @author: ludaqing
 * @date: 2018年5月9日下午2:09:26
 */
public class TypeDispose {
    /**
     * 导包类型处理，将数据库对应的类型更改为java对应的类型，如果为常见类型则返回null
     * @param type 当前类型
     * @param databaseType 数据库对应类型
     * @return
     */
    public static String importTypeDispose(String type, String databaseType, StringBuffer stringBuffer){
        String result = null;
        if ("DECIMAL".equals(databaseType)) {//BigDecimal类型
            if (!stringBuffer.toString().contains(type)) {
                result = type;
            }
        }else if ("DATETIME".equals(databaseType)) {//时间类型
            if (!stringBuffer.toString().contains("java.util.Date")) {
                result = "java.util.Date";
            }
        }
        return result;
    }
    
    /**
     * 实体类属性类型处理，将数据库对应的类型更改为java对应的类型
     * @param databaseType 数据库对应类型
     * @return
     */
    public static String attributeTypeDispose(String databaseType){
        String result = null;
        if ("BIGINT".equals(databaseType) || "ID".equals(databaseType)) {
            result = "Long";
        }else if ("VARCHAR".equals(databaseType) || "CHAR".equals(databaseType) || "TEXT".equals(databaseType)) {
            result = "String";
        }else if ("BLOB".equals(databaseType)) {
            result = "byte[]";
        }else if ("INTEGER".equals(databaseType) || "SMALLINT".equals(databaseType) || "MEDIUMINT".equals(databaseType) || "TINYINT".equals(databaseType) || "BOOLEAN".equals(databaseType)) {
            result = "Integer";
        }else if ("DECIMAL".equals(databaseType)) {
            result = "BigDecimal";
        }else if ("FLOAT".equals(databaseType)) {
            result = "Float";
        }else if ("DOUBLE".equals(databaseType)) {
            result = "Double";
        }else if ("BIT".equals(databaseType)) {
            result = "Boolean";
        }else if ("DATETIME".equals(databaseType) || "DATE".equals(databaseType) || "TIME".equals(databaseType) || "TIMESTAMP".equals(databaseType) || "YEAR".equals(databaseType)) {
            result = "Date";
        }
        return result;
    }
}
