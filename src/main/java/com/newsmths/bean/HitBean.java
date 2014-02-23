package com.newsmths.bean;

import java.util.ArrayList;

import com.newsmths.lucene.SearchHit;

public class HitBean {
	private SearchHit hit;
	private ArrayList<TagBean> tags;

	public SearchHit getHit() {
		return hit;
	}

	public void setHit(SearchHit hit) {
		this.hit = hit;
	}

	public ArrayList<TagBean> getTags() {
		return tags;
	}

	public void setTags(ArrayList<TagBean> tags) {
		this.tags = tags;
	}

}
