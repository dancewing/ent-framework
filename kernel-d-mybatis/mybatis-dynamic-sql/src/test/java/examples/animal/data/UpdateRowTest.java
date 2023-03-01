package examples.animal.data;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateRowStatementProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import static examples.animal.data.AnimalDataDynamicSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

class UpdateRowTest {

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
        Configuration config = new Configuration(environment);
        config.addMapper(AnimalDataMapper2.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }

    @Test
    void testUpdateRow() throws Exception {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            AnimalDataMapper2 mapper = sqlSession.getMapper(AnimalDataMapper2.class);
            SelectStatementProvider selectStatement = select(id, animalName, bodyWeight, brainWeight).from(animalData)
                    .where(id, SqlBuilder.isEqualTo(1)).build().render(RenderingStrategies.MYBATIS3);
            AnimalData animal = mapper.selectOne(selectStatement);
            assertAll(() -> assertNotNull(animal), () -> assertThat(animal.getId()).isEqualTo(1),
                    () -> assertThat(animal.getAnimalName()).isEqualTo("Lesser short-tailed shrew"),
                    () -> assertThat(animal.getBrainWeight()).isEqualTo(0.005d));
            animal.setBodyWeight(2d);
            animal.setAnimalName(null);
            UpdateRowStatementProvider<AnimalData> updateRowStatementProvider = SqlBuilder.updateRow(animal)
                    .into(animalData).set(animalName).toProperty("animalName").set(bodyWeight).toProperty("bodyWeight")
                    .set(brainWeight).toValue(5d).where(id).toProperty("id").build()
                    .render(RenderingStrategies.MYBATIS3);
            int count = mapper.updateRow(updateRowStatementProvider);
            assertEquals(1, count);
            AnimalData animal2 = mapper.selectOne(selectStatement);
            assertAll(() -> assertNotNull(animal2), () -> assertThat(animal2.getId()).isEqualTo(1),
                    () -> assertNull(animal2.getAnimalName()), () -> assertThat(animal2.getBodyWeight()).isEqualTo(2d),
                    () -> assertThat(animal2.getBrainWeight()).isEqualTo(5d));
        }
    }

}
