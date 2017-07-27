package by.intexsoft.test.repository;

import java.util.Set;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import by.intexsoft.test.model.CallRecord;

@Repository
public interface CouchBaseRepository extends CrudRepository<CallRecord, String> {
	@View(designDocument = "callRecord", viewName = "all")
	Set<CallRecord> findAll();
}
