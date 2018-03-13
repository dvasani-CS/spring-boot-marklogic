package com.example.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.marklogic.client.query.FacetResult;
import com.marklogic.client.query.FacetValue;

/**
 * @author Niko Schmuck
 */
@XmlRootElement(name = "searchresult")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductSearchResult {

	private List<Product> products;

	private FacetValue[] yearFacets;
	private FacetValue[] priceFacets;

	// Default constructor to keep JAXB happy
	public ProductSearchResult() {
	}

	public ProductSearchResult(List<Product> products, FacetResult prices, FacetResult years) {
		this.products = products;
		this.priceFacets = prices != null ? prices.getFacetValues() : null;
		this.yearFacets = years != null ? years.getFacetValues() : null;
	}

	@XmlElementWrapper(name = "products")
	@XmlElement(name = "product")
	public List<Product> getProducts() {
		return products;
	}

	public FacetValue[] getYearFacets() {
		return yearFacets;
	}

	public FacetValue[] getPriceFacets() {
		return priceFacets;
	}
}
