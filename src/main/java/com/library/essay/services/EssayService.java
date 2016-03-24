package com.library.essay.services;

import java.util.List;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.utils.search.beans.EssaySearchCriteria;

public interface EssayService {

	Essay getEssay(long id);

	List<Essay> getEssays();

	List<Essay> getEssays(String sortProperty, boolean isAsc);

	List<Essay> getEssays(int pageIndex, int pageSize, String sortProperty, boolean isAsc);

	Essay saveOrUpdate(Essay essay);

	void delete(Essay essay);

	void deleteAll();

	List<Essay> findEssaysByAuthor(String author);

	List<Essay> findEssaysByTitleContains(String titleSegment);

	List<Essay> searchEssays(EssaySearchCriteria essaySearchCriteria);
}
