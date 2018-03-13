package com.example.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class RecordSearchResult {

	private List<Record> records;

	private FacetValue[] scoreFacets;

	// Default constructor to keep JAXB happy
	public RecordSearchResult() {
	}

	public RecordSearchResult(List<Record> records, FacetResult scores) {
		this.records = records;
		this.scoreFacets = scores != null ? scores.getFacetValues() : null;
	}

	@XmlElementWrapper(name = "records")
	@XmlElement(name = "record")
	public List<Record> getRecords() {
		return records;
	}

	public FacetValue[] getScoreFacets() {
		return scoreFacets;
	}
}
