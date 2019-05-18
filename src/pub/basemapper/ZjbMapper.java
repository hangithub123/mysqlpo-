package pub.basemapper;

import pub.po.Zjb;
import java.util.List;

public interface ZjbMapper {

	void insert(Zjb record);

	void insertSelective(Zjb record);

	int deleteByPrimaryKey(String id);

	Zjb selectByPrimaryKey(String id);

	List<Zjb> queryByExists(Zjb record);

	int updateByPrimaryKeySelective(Zjb record);

	int updateByPrimaryKey(Zjb record);

}