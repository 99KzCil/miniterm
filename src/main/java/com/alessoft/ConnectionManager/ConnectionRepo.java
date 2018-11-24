package com.alessoft.ConnectionManager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepo extends CrudRepository<Connection, String> {


}
