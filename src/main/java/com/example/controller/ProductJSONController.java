package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.Product;
import com.example.domain.ProductSearchResult;
import com.example.domain.Record;
import com.example.domain.RecordSearchResult;
import com.example.service.ProductJSONRepository;
import com.example.service.RecordJSONRepository;
import com.marklogic.client.ResourceNotFoundException;

@RestController
public class ProductJSONController {

	private static final Logger logger = LoggerFactory.getLogger(ProductJSONController.class);

	@Autowired
	protected ProductJSONRepository productJSONRepository;

	@Autowired
	protected RecordJSONRepository recordJSONRepository;

	@RequestMapping(value = "/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct(@RequestBody Product product, UriComponentsBuilder builder) {
		productJSONRepository.add(product);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/products/{id}.json").buildAndExpand(product.getSku()).toUri());

		return new ResponseEntity<>("", headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{sku}.json", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable("sku") Long sku) {
		productJSONRepository.remove(sku);
	}

	@RequestMapping(value = "/products/{sku}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product readProduct(@PathVariable("sku") Long sku) {
		return productJSONRepository.findBySku(sku);
	}

	@RequestMapping(value = "/products.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductSearchResult searchProducts(@RequestParam(required = false, value = "name") String name) {
		if (StringUtils.isEmpty(name)) {
			logger.info("Lookup all {} products...", productJSONRepository.count());
			return productJSONRepository.findAll();
		} else {
			logger.info("Lookup products by name: {}", name);
			return productJSONRepository.findByName(name);
		}
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
	@ExceptionHandler(ResourceNotFoundException.class)
	public void handleMarkLogicResourceNotFoundException() {
	}

	@RequestMapping(value = "/records/{code}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Record readRecord(@PathVariable("code") String code) {
		return recordJSONRepository.findByCode(code);
	}

	@RequestMapping(value = "/records.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RecordSearchResult searchRecords(@RequestParam(required = false, value = "name") String name) {
		if (StringUtils.isEmpty(name)) {
			logger.info("Lookup all records...");
			return recordJSONRepository.findAll();
		} else {
			logger.info("Lookup records by name: {}", name);
			return recordJSONRepository.findByName(name);
		}
	}
}
