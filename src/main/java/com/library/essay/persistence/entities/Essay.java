package com.library.essay.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "Essay")
@Indexed
public class Essay implements Serializable {

  @Id
  @Column(name = "ESSAY_ID")
  @GeneratedValue
  @DocumentId
  private Long id;

  @Column(name = "TITLE")
  @Field
  private String title;

  @Column(name = "AUTHOR")
  @Field
  private String author;

  @Lob
  // Solve issue for Derby: Derby “A truncation error was encountered trying to shrink CLOB
  // '<stream-value>' to length 255”
  @Column(name = "CONTENT", columnDefinition = "clob")
  @Field
  private String content;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "Id: " + id + ", Title: " + title + ", Author: " + author + ", Content: " + content;

  }
}
