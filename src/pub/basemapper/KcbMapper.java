package pub.basemapper;

import pub.po.Kcb;
import java.util.List;

public interface KcbMapper {

	void insert(Kcb record);

	void insertSelective(Kcb record);

	int deleteByPrimaryKey(String id);

	Kcb selectByPrimaryKey(String id);

	List<Kcb> queryByExists(Kcb record);

	int updateByPrimaryKeySelective(Kcb record);

	int updateByPrimaryKey(Kcb record);

}