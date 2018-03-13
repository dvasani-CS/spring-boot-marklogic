package com.example.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.annotation.JsonValue;

@XmlRootElement(name = "category")
public class Category {

	private String text;

	// Default constructor to keep JAXB happy
	public Category() {
	}

	public Category(String text) {
		this.text = text;
	}

	@JsonValue
	@XmlValue
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
