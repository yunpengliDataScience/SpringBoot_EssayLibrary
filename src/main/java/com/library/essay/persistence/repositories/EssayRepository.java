package com.library.essay.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.essay.persistence.entities.Essay;

public interface EssayRepository extends JpaRepository<Essay, Long>, JpaSpecificationExecutor<Essay> {

	List<Essay> findByAuthor(String author);

	@Query("select e from Essay e where e.title like %:titleSegment%")
	List<Essay> findEssays(@Param("titleSegment") String titleSegment);

}
