package com.library.essay.reports.beans;

public class AuthorEssayCountBean {

  private Long essayCount;
  private String author;

  public AuthorEssayCountBean(String author, long essayCount) {

    this.author = author;
    this.essayCount = essayCount;
  }

  public Long getEssayCount() {
    return essayCount;
  }

  public void setEssayCount(Long essayCount) {
    this.essayCount = essayCount;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

}
