package com.library.essay.services;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.essay.persistence.entities.Essay;
import com.library.essay.persistence.repositories.EssayRepository;
import com.library.essay.utils.search.EssaySearchSpecifications;
import com.library.essay.utils.search.beans.EssaySearchCriteria;

@Service(value = "essayService")
@Transactional
public class EssayServiceImp implements EssayService {

	private static Whitelist whitelist;

	static {
		whitelist = Whitelist.relaxed();
		whitelist.addAttributes("p", "style");
		whitelist.addAttributes("span", "style");
		whitelist.addAttributes("div", "style");
		// remove hyperlink
		whitelist.removeTags("a");
	}

	@Autowired
	private EssayRepository essayRepository;

	@Override
	public Essay getEssay(long id) {

		return essayRepository.findOne(id);
	}

	@Override
	public List<Essay> getEssays() {

		return essayRepository.findAll();
	}

	@Override
	public List<Essay> findEssaysByAuthor(String author) {
		return essayRepository.findByAuthor(author);
	}

	@Override
	public List<Essay> findEssaysByTitleContains(String titleSegment) {
		return essayRepository.findEssays(titleSegment);
	}

	@Override
	public List<Essay> searchEssays(EssaySearchCriteria essaySearchCriteria) {
		String title = essaySearchCriteria.getTitle().trim();
		String author = essaySearchCriteria.getAuthor().trim();
		String content = essaySearchCriteria.getContent().trim();

		Specification<Essay> titleSpec = EssaySearchSpecifications.titleContainsIgnoreCase(title);
		Specification<Essay> authorSpec = EssaySearchSpecifications.authorContainsIgnoreCase(author);
		Specification<Essay> contentSpec = EssaySearchSpecifications.contentContainsIgnoreCase(content);

		List<Essay> searchResult = essayRepository
				.findAll(Specifications.where(titleSpec).and(authorSpec).and(contentSpec));

		return searchResult;
	}

	@Override
	public List<Essay> getEssays(String sortProperty, boolean isAsc) {

		Direction direction = null;

		if (isAsc) {
			direction = Sort.Direction.ASC;
		} else {
			direction = Sort.Direction.DESC;
		}

		Sort sort = new Sort(new Sort.Order(direction, sortProperty));

		return essayRepository.findAll(sort);

	}

	@Override
	public List<Essay> getEssays(int pageIndex, int pageSize, String sortProperty, boolean isAsc) {

		Pageable pageSpecification = buildPageSpecification(pageIndex, pageSize, sortProperty, isAsc);

		Page<Essay> page = essayRepository.findAll(pageSpecification);

		return page.getContent();
	}

	// Pagination
	private Pageable buildPageSpecification(int pageIndex, int pageSize, String sortProperty, boolean isAsc) {

		Sort sortSpec = getSort(sortProperty, isAsc);

		return new PageRequest(pageIndex, pageSize, sortSpec);
	}

	private Sort getSort(String sortProperty, boolean isAsc) {
		Direction direction = null;

		if (isAsc) {
			direction = Sort.Direction.ASC;
		} else {
			direction = Sort.Direction.DESC;
		}

		Sort sort = new Sort(new Sort.Order(direction, sortProperty));

		return sort;
	}

	@Override
	public Essay saveOrUpdate(Essay essay) {

		String content = essay.getContent();

		// sans-serif is not found in jasperreports and may cause the crash of
		// report generation. But SansSerif is supported.
		content = this.sanitizeRichText(content.replace("sans-serif;", "SansSerif;"));
		essay.setContent(content);

		return essayRepository.save(essay);
	}

	@Override
	public void delete(Essay essay) {
		essayRepository.delete(essay);
	}

	@Override
	public void deleteAll() {
		essayRepository.deleteAll();
	}

	/**
	 * This setter method should be used only by unit tests.
	 * 
	 * @param essayRepository
	 */
	protected void setEssayRepository(EssayRepository essayRepository) {
		this.essayRepository = essayRepository;
	}

	private String sanitizeRichText(String rawText) {
		String sanitizedText = "";

		if (rawText != null) {

			sanitizedText = Jsoup.clean(rawText, whitelist);
		}

		return sanitizedText;
	}
}
