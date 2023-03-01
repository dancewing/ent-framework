package examples.animal.data;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.GenericUpdateRowMapper;

import java.util.List;

public interface AnimalDataMapper2 extends GenericUpdateRowMapper<AnimalData> {

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "AnimalDataResult",
            value = { @Result(column = "id", property = "id", id = true),
                    @Result(column = "animal_name", property = "animalName"),
                    @Result(column = "brain_weight", property = "brainWeight"),
                    @Result(column = "body_weight", property = "bodyWeight") })
    List<AnimalData> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("AnimalDataResult")
    AnimalData selectOne(SelectStatementProvider selectStatement);

}
