package com.library.essay.services;

import java.util.List;
import com.library.essay.persistence.entities.Essay;


public interface FullTextSearchIndexService {

  boolean buildfullTextSearchIndex();

  List<Essay> searchFullText(String text);

}
