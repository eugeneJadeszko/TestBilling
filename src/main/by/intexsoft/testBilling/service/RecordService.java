package main.by.intexsoft.testBilling.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.by.intexsoft.testBilling.model.CallRecord;
import main.by.intexsoft.testBilling.repository.CouchBaseRepository;

@Service
public class RecordService {
	@Autowired
	CouchBaseRepository repository;

	public Set<CallRecord> findAll() {
		return (Set<CallRecord>) repository.findAll();
	}

	public void deleteAll() {
		repository.deleteAll();
	}
}
