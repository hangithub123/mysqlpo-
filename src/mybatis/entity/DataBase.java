package mybatis.entity;

import java.io.Serializable;

/**
 * @className：Data.java
 * @Title: Data
 * @Description: TODO(保存数据库信息和生成文件的保存路径)
 * @author: ludaqing
 * @date: 2018年5月8日下午2:12:20
 */
public class DataBase implements Serializable {

    private static final long serialVersionUID = -3531003652239507935L;
    
    /**
     * 数据库驱动
     */
    private String drive;
    
    /**
     * 数据库路径
     */
    private String databasePath;
    
    /**
     * 数据库账号
     */
    private String account;
    
    /**
     * 数据库密码
     */
    private String password;
    
    /**
     * 实体类存放路径
     */
    private String entityPath;
    
    /**
     * mapper文件存放路径
     */
    private String mapperPath;
    
    /**
     * xml文件存放路径
     */
    private String xmlPath;
    
    /**
     * 是否需要注释
     */
    private Boolean annotation;
    
    /**
     * 表名称
     */
    private String tableName;
    
    /**
     * 实体类名称
     */
    private String entityName;

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Boolean getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Boolean annotation) {
        this.annotation = annotation;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    
}
