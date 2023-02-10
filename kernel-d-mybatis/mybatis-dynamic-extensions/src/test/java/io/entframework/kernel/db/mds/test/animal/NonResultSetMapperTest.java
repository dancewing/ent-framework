package io.entframework.kernel.db.mds.test.animal;

import io.entframework.kernel.db.mybatis.KernelMybatisConfiguration;
import io.entframework.kernel.db.mybatis.mapper.GeneralMapperSupport;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

import static io.entframework.kernel.db.mds.test.animal.AnimalDataDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

public class NonResultSetMapperTest {
    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    void setup() throws Exception {
        Class.forName(JDBC_DRIVER);
        InputStream is = getClass().getResourceAsStream("/examples/animal/data/CreateAnimalData.sql");
        assert is != null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, "sa", "")) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.setLogWriter(null);
            sr.runScript(new InputStreamReader(is));
        }

        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        KernelMybatisConfiguration config = new KernelMybatisConfiguration(environment);
        config.addMapper(AnimalDataMapper.class);
        config.addMapper(NonResultSetAnimalDataMapper.class);
        config.addMapper(GeneralMapperSupport.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }

    @Test
    void testSelectAllRows() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            NonResultSetAnimalDataMapper mapper = sqlSession.getMapper(NonResultSetAnimalDataMapper.class);
            SelectStatementProvider selectStatement = select(id, animalName, bodyWeight, brainWeight)
                    .from(AnimalData.class)
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            List<AnimalData> animals = mapper.selectMany(selectStatement);
            assertAll(
                    () -> assertThat(animals).hasSize(65),
                    () -> assertThat(animals.get(0).getId()).isEqualTo(1)
            );
        }
    }

    @Test
    void testSelectOneRow() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            NonResultSetAnimalDataMapper mapper = sqlSession.getMapper(NonResultSetAnimalDataMapper.class);
            SelectStatementProvider selectStatement = select(id, animalName, bodyWeight, brainWeight)
                    .from(AnimalDataDynamicSqlSupport.animalData)
                    .where(id, SqlBuilder.isEqualTo(1))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            AnimalData animal = mapper.selectOne(selectStatement);
            assertAll(
                    () -> assertThat(animal.getId()).isEqualTo(1)
            );
        }
    }

    @Test
    void testSelectOptionalOneRow() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            NonResultSetAnimalDataMapper mapper = sqlSession.getMapper(NonResultSetAnimalDataMapper.class);
            SelectStatementProvider selectStatement = select(id, animalName, bodyWeight, brainWeight)
                    .from(AnimalDataDynamicSqlSupport.animalData)
                    .where(id, SqlBuilder.isEqualTo(1))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            Optional<AnimalData> animal = mapper.selectOptionalOne(selectStatement);
            assertAll(
                    () -> assertTrue(animal.isPresent()),
                    () -> assertThat(animal.get().getId()).isEqualTo(1)
            );
        }
    }
}
