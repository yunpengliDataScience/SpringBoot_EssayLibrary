package com.library.essay.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.essay.persistence.entities.Essay;


@Service(value = "fullTextSearchIndexService")
@Transactional
public class FullTextSearchIndexServiceImp implements FullTextSearchIndexService {

  // Entity Manager
  @PersistenceContext
  private EntityManager entityManager;

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public boolean buildfullTextSearchIndex() {

    boolean success = true;
    // get the full text entity manager
    FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

    try {
      fullTextEntityManager.createIndexer().startAndWait();
    } catch (InterruptedException e) {

      success = false;
      logger.error(e.getMessage());
      e.printStackTrace();
    }

    return success;
  }


  public List<Essay> searchFullTextBasic(String text) {

    List<Essay> results = new ArrayList<Essay>();

    if (text != null && StringUtils.trimToNull(text) != null) {

      // get the full text entity manager
      FullTextEntityManager fullTextEntityManager =
          org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);


      // create the query using Hibernate Search query DSL
      QueryBuilder queryBuilder =
          fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Essay.class).get();

      // a very basic query by keywords
      Query query = queryBuilder.keyword().onFields("title", "author", "content").matching(text)
          .createQuery();


      // wrap Lucene query in an Hibernate Query object
      FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Essay.class);

      // execute search and return results (sorted by relevance as default)
      results = jpaQuery.getResultList();
    }

    return results;
  }

  @Override
  public List<Essay> searchFullText(String text) {

    List<Essay> results = new ArrayList<Essay>();

    if (text != null && StringUtils.trimToNull(text) != null) {


      text = "\"" + text + "\"";

      // get the full text entity manager
      FullTextEntityManager fullTextEntityManager =
          org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

      String[] fields = {"title", "author", "content"};

      BooleanClause.Occur[] flags =
          {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};

      org.apache.lucene.search.Query searchQuery;
      try {
        searchQuery = MultiFieldQueryParser.parse(text, fields, flags, new StandardAnalyzer());
        // wrap Lucene query in an Hibernate Query object
        FullTextQuery jpaQuery =
            fullTextEntityManager.createFullTextQuery(searchQuery, Essay.class);

        // execute search and return results (sorted by relevance as default)
        results = jpaQuery.getResultList();
      } catch (ParseException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
      }


    }

    return results;
  }

}
