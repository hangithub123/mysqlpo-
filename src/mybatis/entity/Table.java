package mybatis.entity;

public class Table {

	private String tableName=null;
	private String entityName=null;
	private boolean annotation;
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
	public boolean isAnnotation() {
		return annotation;
	}
	public void setAnnotation(boolean annotation) {
		this.annotation = annotation;
	}

	
}
