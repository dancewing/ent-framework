/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test;

import io.entframework.kernel.auth.api.LoginUserApi;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.entity.HistoryScore;
import io.entframework.kernel.db.mds.example.entity.HistoryScore.ExamType;
import io.entframework.kernel.db.mds.example.entity.Student;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import io.entframework.kernel.db.mds.example.pojo.request.ClassGradeRequest;
import io.entframework.kernel.db.mds.example.pojo.request.StudentRequest;
import io.entframework.kernel.db.mds.example.pojo.request.TeacherRequest;
import io.entframework.kernel.db.mds.ext.dto.TeachProperty;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootApp.class)
@ActiveProfiles("h2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class JUnitWithFraud {

    /**
     * Mock LoginUserApi，防止
     */
    @MockBean
    private LoginUserApi loginUserApi;

    public void setUp() throws Exception {
        LoginUser currentUser = new LoginUser();
        currentUser.setUserId(-1L);
        currentUser.setAccount("tester");
        Mockito.when(this.loginUserApi.getLoginUser()).thenReturn(currentUser);
    }

    public ClassGrade fraudClassGrade() {
        ClassGrade grade = new ClassGrade();
        grade.setName(fraudUnique("class"));
        grade.setDescription("this is desc of class");
        grade.setGradeType(fraudRandom(0, 1) == 0 ? ClassGrade.GradeType.ADVANCE : ClassGrade.GradeType.COMMON);
        grade.setStartTime(LocalDateTime.now());
        grade.setStudents(fraudList(this::fraudStudent));
        grade.setRegulator(fraudTeacher());
        return grade;
    }

    public ClassGradeRequest fraudClassGradeRequest() {
        ClassGradeRequest grade = new ClassGradeRequest();
        grade.setName(fraudUnique("class"));
        grade.setDescription("this is desc of class");
        grade.setGradeType(fraudRandom(0, 1) == 0 ? ClassGrade.GradeType.ADVANCE : ClassGrade.GradeType.COMMON);
        grade.setStartTime(LocalDateTime.now());
        grade.setStudents(fraudList(this::fraudStudentRequest));
        grade.setRegulator(fraudTeacherRequest());
        return grade;
    }

    public Student fraudStudent(long gradeId) {
        Student student = new Student();
        student.setGradeId(gradeId);
        student.setName(fraudUnique("student"));
        student.setCardNum(fraudUnique("card_id"));
        student.setGender(fraudRandom(0, 1) == 0 ? Student.Gender.MALE : Student.Gender.FEMALE);
        student.setBirthday(LocalDate.now().plusYears(-18));
        student.setTakeCourses(fraudStringList("course"));
        student.setFromForeign(fraudRandom(0, 1) != 0);
        student.setHometown(fraudUnique("town"));
        student.setDelFlag(YesOrNotEnum.Y);
        student.setHobbies("");

        return student;
    }

    public Student fraudStudent() {
        Student student = new Student();
        student.setGradeId(-1L);
        student.setName(fraudUnique("student"));
        student.setCardNum(fraudUnique("card_id"));
        student.setGender(fraudRandom(0, 1) == 0 ? Student.Gender.MALE : Student.Gender.FEMALE);
        student.setBirthday(LocalDate.now().plusYears(-18));
        student.setTakeCourses(fraudStringList("course"));
        student.setFromForeign(fraudRandom(0, 1) != 0);
        student.setHometown(fraudUnique("town"));
        student.setDelFlag(YesOrNotEnum.Y);
        student.setHobbies("");
        return student;
    }

    public StudentRequest fraudStudentRequest() {
        StudentRequest student = new StudentRequest();
        student.setGradeId(-1L);
        student.setName(fraudUnique("student"));
        student.setCardNum(fraudUnique("card_id"));
        student.setGender(fraudRandom(0, 1) == 0 ? Student.Gender.MALE : Student.Gender.FEMALE);
        student.setBirthday(LocalDate.now().plusYears(-18));
        student.setTakeCourses(fraudStringList("course"));
        student.setFromForeign(fraudRandom(0, 1) != 0);
        student.setHometown(fraudUnique("town"));
        student.setHobbies("");
        return student;
    }

    public Teacher fraudTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName(fraudUnique("teacher"));
        teacher.setCardNum(fraudUnique("card_id"));
        teacher.setGender(fraudRandom(0, 1) == 0 ? Teacher.Gender.MALE : Teacher.Gender.FEMALE);
        teacher.setBirthday(LocalDate.now().plusYears(-11));
        teacher.setWorkSeniority(fraudRandom(0, 5));
        teacher.setStatusFlag(fraudRandom(0, 1) == 0 ? StatusEnum.ENABLE : StatusEnum.DISABLE);
        teacher.setTechCourses(fraudStringList("course"));
        TeachProperty property = new TeachProperty();
        property.setTest("1111");
        teacher.setProperties(property);
        return teacher;
    }

    public TeacherRequest fraudTeacherRequest() {
        TeacherRequest teacher = new TeacherRequest();
        teacher.setName(fraudUnique("teacher"));
        teacher.setCardNum(fraudUnique("card_id"));
        teacher.setGender(fraudRandom(0, 1) == 0 ? Teacher.Gender.MALE : Teacher.Gender.FEMALE);
        teacher.setBirthday(LocalDate.now().plusYears(-11));
        teacher.setWorkSeniority(fraudRandom(0, 5));
        teacher.setStatusFlag(fraudRandom(0, 1) == 0 ? StatusEnum.ENABLE : StatusEnum.DISABLE);
        teacher.setTechCourses(fraudStringList("course"));
        teacher.setProperties(new TeachProperty());
        return teacher;
    }

    public HistoryScore fraudHistoryScore(long studentId) {
        Map<String, Integer> scores = fraudScore();
        int totalScore = scores.values().stream().mapToInt(s -> s).sum();
        HistoryScore historyScore = new HistoryScore();
        historyScore.setStudentId(studentId);
        historyScore.setExamTime(LocalDateTime.now().plusMonths(2));
        historyScore.setExamType(ExamType.MONTHLY);
        historyScore.setTotalScore(totalScore);
        historyScore.setScore(scores);
        return historyScore;
    }

    public HistoryScore fraudHistoryScore() {
        Map<String, Integer> scores = fraudScore();
        int totalScore = scores.values().stream().mapToInt(s -> s).sum();
        HistoryScore historyScore = new HistoryScore();
        historyScore.setStudentId(-1L);
        historyScore.setExamTime(LocalDateTime.now().plusMonths(2));
        historyScore.setExamType(ExamType.MONTHLY);
        historyScore.setTotalScore(totalScore);
        historyScore.setScore(scores);
        return historyScore;
    }

    public Map<String, Integer> fraudScore() {
        Map<String, Integer> scores = new HashMap<>();
        int end = fraudRandom(2, 5);
        for (int i = 0; i < end; i++) {
            scores.put(fraudCourse(), fraudRandom(90, 150));
        }
        return scores;
    }

    public <T> List<T> fraudList(Supplier<T> supplier) {
        List<T> list = new ArrayList<>();
        int end = fraudRandom(2, 5);
        for (int i = 0; i < end; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    public void repeat(Callable<?> callable, int end) {
        for (int i = 0; i < end; i++) {
            try {
                callable.call();
            } catch (Exception e) {
                //
            }

        }
    }

    public <T> List<T> fraudList(Supplier<T> supplier, int end) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    public List<String> fraudStringList(String str) {
        List<String> list = new ArrayList<>();
        int end = fraudRandom(2, 5);
        for (int i = 0; i < end; i++) {
            list.add(fraudUnique(str));
        }
        return list;
    }

    public String fraudCourse() {
        return fraudUnique("course");
    }

    public String fraudUnique(String str) {
        return str + "_" + UUID.randomUUID().toString().replace("-", "");
    }

    public Integer fraudRandom(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
