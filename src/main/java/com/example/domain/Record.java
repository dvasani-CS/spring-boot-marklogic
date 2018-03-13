package com.example.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
public class Record {

	private List<String> wikiTokens;
	private String confidence;
	private String code;
	private List<String> terms;
	private String name;
	private double score;
	private String description;

	// Default constructor to keep JAXB happy
	public Record() {
	}

	public Record(List<String> wikiTokens, String confidence, String code, List<String> terms, String name,
			double score, String description) {
		super();
		this.wikiTokens = wikiTokens;
		this.confidence = confidence;
		this.code = code;
		this.terms = terms;
		this.name = name;
		this.score = score;
		this.description = description;
	}

	public List<String> getWikiTokens() {
		return wikiTokens;
	}

	public void setWikiTokens(List<String> wikiTokens) {
		this.wikiTokens = wikiTokens;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Record [wikiTokens=" + wikiTokens + ", confidence=" + confidence + ", code=" + code + ", terms=" + terms
				+ ", name=" + name + ", score=" + score + ", description=" + description + "]";
	}

}
