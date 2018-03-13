package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.domain.Record;
import com.example.domain.RecordSearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.io.ValuesHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.RawCombinedQueryDefinition;
import com.marklogic.client.query.RawStructuredQueryDefinition;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryBuilder.FragmentScope;
import com.marklogic.client.query.ValuesDefinition;

/**
 * Sample implementation of the {@link RecordRepository} making use of
 * MarkLogic's {@link JSONDocumentManager}.
 *
 * @author Niko Schmuck
 */
@Component
public class RecordJSONRepository implements RecordRepository {

	private static final Logger logger = LoggerFactory.getLogger(RecordJSONRepository.class);

	public static final String COLLECTION_REF = "/records.json";
	public static final int PAGE_SIZE = 50;

	@Autowired
	protected QueryManager queryManager;

	@Autowired
	protected JSONDocumentManager jsonDocumentManager;

	@PostConstruct
	protected void init() throws IOException {

	}

	@Override
	public void add(Record record) {
		// Add this document to a dedicated collection for later retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JacksonHandle writeHandle = new JacksonHandle();
		JsonNode writeDocument = writeHandle.getMapper().convertValue(record, JsonNode.class);
		writeHandle.set(writeDocument);

		StringHandle stringHandle = new StringHandle(writeDocument.toString());
		jsonDocumentManager.write(getDocId(record.getCode()), metadata, stringHandle);
	}

	@Override
	public Record findByCode(String code) {
		JacksonHandle jacksonHandle = new JacksonHandle();
		logger.info("Search for record with code {} ...", code);
		jsonDocumentManager.read(getDocId(code), jacksonHandle);
		return fetchRecord(jacksonHandle);
	}

	@Override
	public RecordSearchResult findAll() {
		StringQueryDefinition queryDef = queryManager.newStringDefinition();
		queryDef.setCollections(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.setPageLength(PAGE_SIZE);
		queryManager.search(queryDef, resultsHandle, 0);
		getAverageScore();
		return toSearchResult(resultsHandle);
	}

	@Override
	public RecordSearchResult findByName(String name) {
		String options = "<options xmlns=\"http://marklogic.com/appservices/search\">" + "<constraint name='name'>"
				+ "<value>" + "<element name='name' ns=''/>" + "</value>" + "</constraint>" + "</options>";
		StructuredQueryBuilder qb = queryManager.newStructuredQueryBuilder();
		RawStructuredQueryDefinition rsq = qb.build(
				qb.word(qb.element("name"), FragmentScope.DOCUMENTS, new String[] { "case-insensitive" }, 1, name));
		String comboq = "<search xmlns=\"http://marklogic.com/appservices/search\">" + rsq.toString() + options
				+ "</search>";
		RawCombinedQueryDefinition querydef = queryManager.newRawCombinedQueryDefinition(new StringHandle(comboq));
		querydef.setCollections(COLLECTION_REF);
		System.out.println(rsq.toString());
		SearchHandle resultsHandle = new SearchHandle();
		queryManager.setPageLength(PAGE_SIZE);
		queryManager.search(querydef, resultsHandle, 0);
		return toSearchResult(resultsHandle);
	}

	@Override
	public Double getAverageScore() {
		ValuesDefinition valuesDefinition = queryManager.newValuesDefinition("score");
		valuesDefinition.setOptionsName("score");
		valuesDefinition.setAggregate("avg");
		String results = queryManager.values(valuesDefinition, new ValuesHandle()).getAggregates()[0].getValue();
		System.out.println("-------------------------");
		System.out.println(results);
		return Double.parseDouble(results);
	}

	private String getDocId(String code) {
		return String.format("/records/%s.json", code);
	}

	private Record fetchRecord(JacksonHandle jacksonHandle) {
		try {
			JsonNode jsonNode = jacksonHandle.get();
			return jacksonHandle.getMapper().readValue(jsonNode.toString(), Record.class);
		} catch (IOException e) {
			throw new RuntimeException("Unable to cast to record", e);
		}
	}

	private RecordSearchResult toSearchResult(SearchHandle resultsHandle) {
		List<Record> records = new ArrayList<>();
		for (MatchDocumentSummary summary : resultsHandle.getMatchResults()) {
			logger.info("  * found {}", summary.getUri());
			// Assumption: summary URI refers to JSON document
			JacksonHandle jacksonHandle = new JacksonHandle();
			jsonDocumentManager.read(summary.getUri(), jacksonHandle);
			records.add(fetchRecord(jacksonHandle));
		}
		return new RecordSearchResult(records, resultsHandle.getFacetResult("score"));
	}
}
