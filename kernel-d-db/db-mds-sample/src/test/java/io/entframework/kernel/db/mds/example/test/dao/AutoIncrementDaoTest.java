package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.mds.example.entity.AutoIncrement;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AutoIncrementDaoTest extends JUnitDaoWithFraud {

    @Test
    void testInsert() {

        AutoIncrement autoIncrement = new AutoIncrement();
        autoIncrement.setUsername("test");
        generalRepository.insert(autoIncrement);
        assertAll(() -> assertThat(autoIncrement.getId()).isNotNull(),
                () -> assertThat(autoIncrement.getId()).isPositive());
    }

}
