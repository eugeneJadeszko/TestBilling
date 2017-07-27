package by.intexsoft.test.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.intexsoft.test.model.CallRecord;
import by.intexsoft.test.repository.CouchBaseRepository;

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
