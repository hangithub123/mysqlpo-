package mybatis.entity;

import java.util.List;

public class KeyDataType {
/**
 * 
 * 功能逻辑获取主键java类型 例如java.lang.String  
 *	布尔值，true 代表去除路径。
 * @方法名称  getKeyJavaType
 * @作者  韩伟其
 * @创建日期  2018年12月27日
 * @返回值  String
 *
 * @修改记录 （修改时间、作者、原因）：
 */
	public static String getKeyJavaType(List<PropertyDetails> list,boolean noPath){
		String javatype=null;
		for (PropertyDetails propertyDetails : list) {
			if(propertyDetails.getIsKey()){
				javatype=propertyDetails.getDataType();
			}
		}
		if(noPath&&javatype!=null){
			String[] typearr=javatype.split("\\.");
			javatype=typearr[typearr.length-1];
		}
		return javatype;
	}
	/**
	 * 
	 * 功能逻辑获取主键  索引位置
	 *
	 * @方法名称  getDBKey
	 * @作者  韩伟其
	 * @创建日期  2019年1月1日
	 * @返回值  String
	 *
	 * @修改记录 （修改时间、作者、原因）：
	 */
	public static int getDBKeyIndex(List<PropertyDetails> list){
		int key=0;
		for (PropertyDetails propertyDetails : list) {
			if(propertyDetails.getIsKey()){
				key=list.indexOf(propertyDetails);
			}
		}
		System.out.println("主键索引位置="+key);
		return key;
	}
}
