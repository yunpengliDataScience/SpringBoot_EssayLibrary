package com.library.essay.utils.search.beans;

import java.io.Serializable;

public class EssaySearchCriteria implements Serializable {

  private String title;
  private String author;
  private String content;
  private String keywords;

  public EssaySearchCriteria() {
    title = "";
    author = "";
    content = "";
    keywords = "";
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

}
