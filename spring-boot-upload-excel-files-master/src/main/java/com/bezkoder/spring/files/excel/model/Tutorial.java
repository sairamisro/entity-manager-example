package com.bezkoder.spring.files.excel.model;

import java.util.Date;

public class Tutorial {

    private long id;

    private String title;

    private Date exp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}


}
