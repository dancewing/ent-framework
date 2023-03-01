package io.entframework.kernel.db.mds.example.config;

import io.entframework.kernel.db.mds.example.service.AutoIncrementService;
import io.entframework.kernel.db.mds.example.service.ClassGradeService;
import io.entframework.kernel.db.mds.example.service.HistoryScoreService;
import io.entframework.kernel.db.mds.example.service.StudentService;
import io.entframework.kernel.db.mds.example.service.TeacherService;
import io.entframework.kernel.db.mds.example.service.impl.AutoIncrementServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.ClassGradeServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.HistoryScoreServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.StudentServiceImpl;
import io.entframework.kernel.db.mds.example.service.impl.TeacherServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "io.entframework.kernel.db.mds.example.controller",
		"io.entframework.kernel.db.mds.example.converter", "io.entframework.kernel.db.mds.example.service" })
@EntityScan("io.entframework.kernel.db.mds.example.entity")
public class SampleSpringAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(ClassGradeService.class)
	public ClassGradeService classGradeService() {
		return new ClassGradeServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(HistoryScoreService.class)
	public HistoryScoreService historyScoreService() {
		return new HistoryScoreServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(TeacherService.class)
	public TeacherService teacherService() {
		return new TeacherServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(StudentService.class)
	public StudentService studentService() {
		return new StudentServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean(AutoIncrementService.class)
	public AutoIncrementService autoIncrementService() {
		return new AutoIncrementServiceImpl();
	}

}