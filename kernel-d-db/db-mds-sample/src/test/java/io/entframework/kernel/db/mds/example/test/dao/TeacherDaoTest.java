/*
 * ******************************************************************************
 *  * Copyright (c) 2022-2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.api.exception.DaoException;
import io.entframework.kernel.db.mds.example.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDaoTest extends JUnitDaoWithFraud {

	@Test
	void create() {
		Teacher ttc = fraudTeacher();
		Teacher ttcd = generalRepository.insert(ttc);
		assertNotNull(ttcd.getId());
	}

	@Test
	void batchCreate() {
		List<Teacher> list = fraudList(this::fraudTeacher);
		List<Teacher> listd = generalRepository.insertMultiple(list);
		assertNotNull(listd);
		listd.forEach(teacher -> {
			assertNotNull(teacher);
			assertNotNull(teacher.getId());
		});
	}

	@Test
	void update() {
		Teacher teacher = generalRepository.insert(fraudTeacher());
		LocalDate newBirth = LocalDate.now().plusYears(-30);
		teacher.setBirthday(newBirth);
		Teacher tcu = generalRepository.update(teacher);
		assertEquals(tcu.getBirthday(), newBirth);
	}

	@Test
	void delete() {
		Teacher teacher = generalRepository.insert(fraudTeacher());
		generalRepository.delete(teacher);
		assertThrows(DaoException.class, () -> generalRepository.get(Teacher.class, teacher.getId()));
	}

	@Test
	void get() {
		Teacher teacher = generalRepository.insert(fraudTeacher());
		Teacher tcg = generalRepository.get(Teacher.class, teacher.getId());
		assertNotNull(tcg);
		assertEquals(tcg.getId(), teacher.getId());
	}

	@Test
	void getAll() {
		Teacher teacher = generalRepository.insert(fraudTeacher());
		List<Teacher> teachers = generalRepository.selectBy(new Teacher());
		assertTrue(teachers.size() >= 1);
	}

}