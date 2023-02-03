package io.entframework.kernel.db.mds.example.repository.impl;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.api.exception.enums.DaoExceptionEnum;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.mapper.StudentDynamicSqlSupport;
import io.entframework.kernel.db.mds.example.repository.ClassGradeRepository;
import io.entframework.kernel.db.mds.repository.BaseRepositoryImpl;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.rule.exception.base.ServiceException;
import io.entframework.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import io.entframework.kernel.rule.util.ReflectionKit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mybatis.dynamic.sql.SqlBuilder;

public class ClassGradeRepositoryImpl extends BaseRepositoryImpl<ClassGrade> implements ClassGradeRepository {
    public ClassGradeRepositoryImpl() {
        super(ClassGrade.class);
    }

    public ClassGradeRepositoryImpl(Class<? extends ClassGrade> entityClass) {
        super(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassGrade insert(ClassGrade row) {
        Teacher regulator = row.getRegulator();
        if (regulator != null && row.getRegulatorId() == null) {
            getMapper(Teacher.class).insert(regulator);
            row.setRegulatorId(regulator.getId());
        }
        int count = getMapper().insert(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.INSERT_RECORD_ERROR);
        }
        List<Student> students = row.getStudents();
        if (students != null && !students.isEmpty()) {
            students.forEach(r -> r.setGradeId(row.getId()));
            getMapper(Student.class).insertMultiple(students);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassGrade> insertMultiple(List<ClassGrade> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<Teacher> regulators = new ArrayList<>();
        for (ClassGrade record : records) {
            Teacher regulator = record.getRegulator();
            Optional.ofNullable(regulator).ifPresent(regulators::add);
        }
        getMapper(Teacher.class).insertMultiple(regulators);
        records.forEach(record -> record.setRegulatorId(record.getRegulator().getId()));
        getMapper().insertMultiple(records);
        List<Student> allStudents = new ArrayList<>();
        for (ClassGrade row : records) {
            List<Student> students = row.getStudents();
            if (students != null && !students.isEmpty()) {
                students.forEach(r -> r.setGradeId(row.getId()));
                allStudents.addAll(students);
            }
        }
        getMapper(Student.class).insertMultiple(allStudents);
        return records;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassGrade update(ClassGrade row) {
        Teacher regulator = row.getRegulator();
        if (regulator != null) {
            if (regulator.getId() != null) {
                getMapper(Teacher.class).updateByPrimaryKey(regulator);
            } else {
                getMapper(Teacher.class).insert(regulator);
            }
            row.setRegulatorId(regulator.getId());
        }
        int count = getMapper().updateByPrimaryKey(row);
        if (count == 0) {
            throw new DaoException(DaoExceptionEnum.UPDATE_RECORD_ERROR, row.getId());
        }
        List<Student> students = row.getStudents();
        if (students != null && !students.isEmpty()) {
            students.forEach(r -> r.setGradeId(row.getId()));
            // 学生启用了逻辑筛选
            getMapper(Student.class).update(c -> c.set(StudentDynamicSqlSupport.delFlag).equalTo(YesOrNotEnum.Y)
                    .where(StudentDynamicSqlSupport.gradeId, SqlBuilder.isEqualTo(row.getId())));
            getMapper(Student.class).insertMultiple(students);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(ClassGrade row) {
        if (row == null || row.getId() == null) {
            throw new ServiceException(DefaultBusinessExceptionEnum.WRONG_ARGS_ERROR);
        }
        return getMapper().deleteByPrimaryKey(row.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassGrade get(Object id) {
        ClassGrade classGradeQuery = new ClassGrade();
        ReflectionKit.setFieldValue(classGradeQuery, "id", id);
        Optional<ClassGrade> row = this.selectOne(classGradeQuery);
        return row.<DaoException>orElseThrow(() -> {
            throw new DaoException(DaoExceptionEnum.GET_RECORD_ERROR, id);
        });
    }
}