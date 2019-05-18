package mybatis.entity;

/**
 * @className：ParameterDetails.java
 * @Title: ParameterDetails
 * @Description: TODO(属性详情实体)
 * @author: ludaqing
 * @date: 2018年5月8日下午3:48:04
 */
public class PropertyDetails {
    
    /**
     * 属性名称
     */
    private String name;
    
    /**
     * 数据库类型
     */
    private String databaseType;
    
    /**
     * 对应的数据类型
     */
    private String dataType;
    
    /**
     * 是否是主键
     */
    private Boolean isKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Boolean getIsKey() {
        return isKey;
    }

    public void setIsKey(Boolean isKey) {
        this.isKey = isKey;
    }
    
}
