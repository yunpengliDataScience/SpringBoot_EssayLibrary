package com.library.essay.utils.search.beans;

import java.io.Serializable;

public class EssaySearchCriteria implements Serializable {

	private String title;
	private String author;
	private String content;

	public EssaySearchCriteria() {
		title = "";
		author = "";
		content = "";
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

}
