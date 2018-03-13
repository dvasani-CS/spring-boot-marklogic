package com.example.service;

import com.example.domain.Record;
import com.example.domain.RecordSearchResult;

public interface RecordRepository {

	void add(Record record);

	Record findByCode(String sku);

	RecordSearchResult findAll();

	RecordSearchResult findByName(String name);

	Double getAverageScore();
}
