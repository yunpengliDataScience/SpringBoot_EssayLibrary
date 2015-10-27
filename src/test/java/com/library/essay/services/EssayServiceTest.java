package com.library.essay.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.library.essay.configurations.TestConfiguration;
import com.library.essay.configurations.TestContextInitializer;
import com.library.essay.persistence.entities.Essay;
import com.library.essay.persistence.repositories.EssayRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class, initializers = TestContextInitializer.class)
public class EssayServiceTest {

	@Autowired
	private EssayService essayService;

	private EssayServiceImp essayServiceNoDB;
	private EssayRepository mockedEssayRepository;

	private final int NUM_ESSAYS = 5;

	@Before
	public void setUp() {

		essayServiceNoDB = new EssayServiceImp();

		mockedEssayRepository = mock(EssayRepository.class);
		essayServiceNoDB.setEssayRepository(mockedEssayRepository);
	}

	@After
	public void tearDown() {
		essayService.deleteAll();
	}

	/**
	 * It uses actual injected EssayService to interact with the database.
	 */
	@Test
	public void testSaveOrUpdateEssay() {

		System.out.println("------------------------------------");
		System.out.println("testSaveOrUpdateEssay() starts");
		System.out.println("------------------------------------");

		Essay myEssay = new Essay();
		myEssay.setAuthor("Yunpeng Li");
		myEssay.setTitle("My Essay");
		myEssay.setContent("This is Yunpeng's Essay!");

		Essay savedEssay = essayService.saveOrUpdate(myEssay);

		System.out.println(savedEssay);

		assertEquals(savedEssay, myEssay);

		essayService.deleteAll();
	}

	/**
	 * Use Mockito to mock an EssayRepository. This will not access the
	 * database, but only for testing the behaviors of EssayService.
	 */
	@Test
	public void testSaveOrUpdateEssayMocked() {

		System.out.println("------------------------------------");
		System.out.println("testSaveOrUpdateEssayMocked() starts");
		System.out.println("------------------------------------");

		Essay myEssay = new Essay();
		myEssay.setAuthor("Yunpeng Li");
		myEssay.setTitle("My Essay");
		myEssay.setContent("This is Yunpeng's Essay!");

		when(mockedEssayRepository.save(any(Essay.class))).thenReturn(myEssay);

		Essay savedEssay = essayServiceNoDB.saveOrUpdate(myEssay);

		System.out.println(savedEssay);

		ArgumentCaptor<Essay> essayArgument = ArgumentCaptor.forClass(Essay.class);
		verify(mockedEssayRepository, times(1)).save(essayArgument.capture());
		verifyNoMoreInteractions(mockedEssayRepository);

		assertEquals(savedEssay, myEssay);

	}

	@Test
	public void testGetEssays() {

		System.out.println("------------------------------------");
		System.out.println("testGetEssays() starts");
		System.out.println("------------------------------------");

		List<Essay> essayList = new ArrayList<Essay>();
		for (int i = 0; i < NUM_ESSAYS; i++) {
			Essay myEssay = new Essay();
			myEssay.setAuthor("Yunpeng Li");
			myEssay.setTitle("My Essay " + i);
			myEssay.setContent("This is Yunpeng's Essay " + i + "!");

			essayList.add(myEssay);
		}

		for (Essay essay : essayList) {
			essayService.saveOrUpdate(essay);
		}

		List<Essay> savedEssays = essayService.getEssays();

		assertNotNull(savedEssays);
		assertEquals(NUM_ESSAYS, savedEssays.size());

		List<Essay> savedEssays2 = essayService.getEssays("title", false);
		for (Essay essay : savedEssays2) {
			System.out.println(essay);
		}

		essayService.deleteAll();
	}
}
