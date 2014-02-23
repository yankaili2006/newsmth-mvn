package com.newsmths.bean;

public class TagBean {
	private Integer docId;
	private String word;
	private Double tfidf;

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Double getTfidf() {
		return tfidf;
	}

	public void setTfidf(Double tfidf) {
		this.tfidf = tfidf;
	}
}
