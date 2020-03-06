package com.yugabyte.spring.ycql.demo.springycqldemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yugabyte.spring.ycql.demo.springycqldemo.domain.Customer;
import com.yugabyte.spring.ycql.demo.springycqldemo.repo.CustomerRepository;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customers")
	public List<Customer> getProducts() {
		return customerRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/save", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer createProductUsingSaveAPI(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@GetMapping("/customer/{email}")
	public Customer findProductById(@PathVariable String email) {
		return customerRepository.findByEmailId(email);
	}

	@GetMapping("/customer/delete/{customerId}")
	public String deleteProductById(@PathVariable String customerId) {
		 customerRepository.deleteById(customerId);
		 return "Delete Success";
	}

}
