package com.library.essay.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.essay.persistence.entities.Essay;
import com.library.essay.reports.beans.AuthorEssayCountBean;

public interface EssayRepository
    extends JpaRepository<Essay, Long>, JpaSpecificationExecutor<Essay> {

  List<Essay> findByAuthor(String author);

  @Query("select e from Essay e where e.title like %:titleSegment%")
  List<Essay> findEssays(@Param("titleSegment") String titleSegment);

  // Use "group by"
  @Query("select new com.library.essay.reports.beans.AuthorEssayCountBean(e.author, count(e)) from Essay e group by e.author")
  List<AuthorEssayCountBean> countEssayByAuthor();

}
