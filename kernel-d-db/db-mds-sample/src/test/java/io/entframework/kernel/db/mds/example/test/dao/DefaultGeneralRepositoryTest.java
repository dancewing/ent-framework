package io.entframework.kernel.db.mds.example.test.dao;

import io.entframework.kernel.db.dao.repository.GeneralRepository;
import io.entframework.kernel.db.mds.example.entity.ClassGrade;
import io.entframework.kernel.db.mds.example.test.JUnitWithFraud;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultGeneralRepositoryTest extends JUnitWithFraud {

	@Autowired
	private GeneralRepository generalRepository;

	@Test
	void testInsert() {
		ClassGrade classGrade = fraudClassGrade();
		generalRepository.insert(classGrade.getRegulator());
		classGrade.setRegulatorId(classGrade.getRegulator().getId());
		generalRepository.insert(classGrade);
		ClassGrade dbResult = generalRepository.get(ClassGrade.class, classGrade.getId());
	}

}
