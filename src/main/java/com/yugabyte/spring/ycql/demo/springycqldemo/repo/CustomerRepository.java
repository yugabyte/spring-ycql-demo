package com.yugabyte.spring.ycql.demo.springycqldemo.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.yugabyte.spring.ycql.demo.springycqldemo.domain.Customer;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, String> {

	Customer findByEmailId(final String emailId);
}

