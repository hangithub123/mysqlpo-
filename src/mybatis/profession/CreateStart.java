package mybatis.profession;

import java.util.List;

import mybatis.entity.DataBase;
import mybatis.entity.PropertyDetails;

/**
 * @className：CreateStart.java
 * @Title: CreateStart
 * @Description: TODO(开始生成文件前的准备)
 * @author: ludaqing
 * @date: 2018年5月8日下午4:36:04
 */
public class CreateStart {
    
    /**
     * 开始生成文件前的准备
     * @param dataBase 用户设置数据实体
     * @param list 属性详情集合
     */
    public static void createStart(DataBase dataBase, List<PropertyDetails> list){
        
        CreateEntity.createEntity(dataBase, list);
        
        CreateMapper.createEntity(dataBase,list);
        
        CreateXml.createXml(dataBase, list);
        
    }
    
}
