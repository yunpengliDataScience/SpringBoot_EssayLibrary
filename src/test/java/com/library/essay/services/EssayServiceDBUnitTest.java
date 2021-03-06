package com.library.essay.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.library.essay.configurations.TestConfiguration;
import com.library.essay.configurations.TestContextInitializer;
import com.library.essay.persistence.entities.Essay;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
public class EssayServiceDBUnitTest {

	@Autowired
	private EssayService essayService;

	@Test
	@DatabaseSetup("/sampleEssayData.xml")
	@DatabaseTearDown("/emptyEssayData.xml") // Used to reset database
	public void testGetEssays() {

		System.out.println("------------------------------------");
		System.out.println("testGetEssays() starts");
		System.out.println("------------------------------------");

		List<Essay> savedEssays = essayService.getEssays();

		assertNotNull(savedEssays);
		assertEquals(2, savedEssays.size());

		for (Essay essay : savedEssays) {
			System.out.println(essay);
		}
	}

	@Test
	@DatabaseSetup("/sampleEssayData2.xml")
	@DatabaseTearDown("/emptyEssayData.xml") // Used to reset database
	public void testFindEssays() {

		System.out.println("------------------------------------");
		System.out.println("testFindEssays() starts");
		System.out.println("------------------------------------");

		List<Essay> essays = essayService.findEssaysByAuthor("Yunpeng");

		assertNotNull(essays);
		assertEquals(3, essays.size());

		for (Essay essay : essays) {
			System.out.println(essay);
		}

		essays = essayService.findEssaysByTitleContains("Human");

		for (Essay essay : essays) {
			System.out.println("================");
			System.out.println(essay);
			System.out.println("================");
		}

		assertNotNull(essays);
		assertEquals(2, essays.size());

	}

	@Test
	@DatabaseSetup("/sampleEssayData.xml")
	// @ExpectedDatabase("/expectedEssayData.xml")
	// Expected data after run the test
	@DatabaseTearDown("/emptyEssayData.xml") // Used to reset database
	public void testDeleteEssay() {

		System.out.println("------------------------------------");
		System.out.println("testDeleteEssay() starts");
		System.out.println("------------------------------------");

		Essay essay = essayService.getEssay(1);
		essayService.delete(essay);

		List<Essay> essays = essayService.getEssays();
		assertNotNull(essays);
		assertEquals(1, essays.size());
	}
}
