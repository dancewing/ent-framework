package io.entframework.kernel.db.mds.example.config;

import io.entframework.kernel.db.mds.example.repository.ClassGradeRepository;
import io.entframework.kernel.db.mds.example.repository.HistoryScoreRepository;
import io.entframework.kernel.db.mds.example.repository.StudentRepository;
import io.entframework.kernel.db.mds.example.repository.TeacherRepository;
import io.entframework.kernel.db.mds.example.repository.impl.ClassGradeRepositoryImpl;
import io.entframework.kernel.db.mds.example.repository.impl.HistoryScoreRepositoryImpl;
import io.entframework.kernel.db.mds.example.repository.impl.StudentRepositoryImpl;
import io.entframework.kernel.db.mds.example.repository.impl.TeacherRepositoryImpl;
import io.entframework.kernel.db.mds.example.service.ClassGradeService;
import io.entframework.kernel.db.mds.example.service.HistoryScoreService;
import io.entframework.kernel.db.mds.example.service.StudentService;
import io.entframework.kernel.db.mds.example.service.TeacherService;
import io.entframework.kernel.db.mds.example.service.impl.ClassGradeServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.HistoryScoreServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.StudentServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.TeacherServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.entframework.kernel.db.mds.example.controller", "io.entframework.kernel.db.mds.example.converter", "io.entframework.kernel.db.mds.example.service"})
@MapperScan(basePackages = "io.entframework.kernel.db.mds.example.mapper")
public class SampleSpringAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(HistoryScoreRepository.class)
    public HistoryScoreRepository historyScoreRepository() {
        return new HistoryScoreRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(HistoryScoreService.class)
    public HistoryScoreService historyScoreService(HistoryScoreRepository historyScoreRepository) {
        return new HistoryScoreServiceImpl(historyScoreRepository);
    }

    @Bean
    @ConditionalOnMissingBean(ClassGradeRepository.class)
    public ClassGradeRepository classGradeRepository() {
        return new ClassGradeRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ClassGradeService.class)
    public ClassGradeService classGradeService(ClassGradeRepository classGradeRepository) {
        return new ClassGradeServiceImpl(classGradeRepository);
    }

    @Bean
    @ConditionalOnMissingBean(TeacherRepository.class)
    public TeacherRepository teacherRepository() {
        return new TeacherRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TeacherService.class)
    public TeacherService teacherService(TeacherRepository teacherRepository) {
        return new TeacherServiceImpl(teacherRepository);
    }

    @Bean
    @ConditionalOnMissingBean(StudentRepository.class)
    public StudentRepository studentRepository() {
        return new StudentRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(StudentService.class)
    public StudentService studentService(StudentRepository studentRepository) {
        return new StudentServiceImpl(studentRepository);
    }
}