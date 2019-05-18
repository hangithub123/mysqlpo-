package mybatis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className：CaptureName.java
 * @Title: CaptureName
 * @Description: TODO(首字母大写)
 * @author: ludaqing
 * @date: 2018年5月8日下午6:07:20
 */
public class CaptureName {
    
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /**
     * 
     * 功能逻辑  根据 '_' 字符转驼峰，第二个参数true首字母大写，否则小写
     *
     * @方法名称  lineToHump
     * @作者  韩伟其
     * @创建日期  2018年12月27日
     * @返回值  String
     *
     * @修改记录 （修改时间、作者、原因）：
     */
    public static String lineToHump(String str,boolean firstCharUp){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        if(firstCharUp){
        	String sb2=new StringBuilder().append(Character.toUpperCase(sb.charAt(0))).append(sb.substring(1)).toString();
        	return sb2;
        }
        return sb.toString();

    }
    public static void main(String[] args) {
    	System.out.println(CaptureName.lineToHump("Aa_bb_ccc",true));
	}
}
