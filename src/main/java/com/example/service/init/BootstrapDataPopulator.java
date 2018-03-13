package com.example.service.init;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Product;
import com.example.domain.Products;
import com.example.domain.Record;
import com.example.domain.Records;
import com.example.service.ProductJSONRepository;
import com.example.service.RecordJSONRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Initialize some sample data (if collection are empty yet).
 *
 * @author Niko Schmuck
 */
@Service
public class BootstrapDataPopulator implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(BootstrapDataPopulator.class);

	@Autowired
	protected ProductJSONRepository productJSONRepository;

	@Autowired
	protected RecordJSONRepository recordJSONRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("~~~ Load bootstrap data");
		if (productJSONRepository.count() == 0) {
			// importJSONProducts();
		}
		// importJSONRecords();
	}

	private void importJSONProducts() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = Products.class.getResourceAsStream("/sampledata/products.json");
		Product[] products = mapper.readValue(inputStream, Product[].class);
		for (Product product : products) {
			productJSONRepository.add(product);
		}
		logger.info("Imported {} products to JSON store", products.length);
	}

	private void importJSONRecords() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = Records.class.getResourceAsStream("/sampledata/recordSeries.json");
		Record[] records = mapper.readValue(inputStream, Record[].class);
		for (Record record : records) {
			recordJSONRepository.add(record);
		}
		logger.info("Imported {} records to JSON store", records.length);
	}
}
