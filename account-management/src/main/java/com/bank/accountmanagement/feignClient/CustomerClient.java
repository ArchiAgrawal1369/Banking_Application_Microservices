package com.bank.accountmanagement.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bank.accountmanagement.entity.Customer;

@FeignClient("CUSTOMER-MANAGEMENT")
public interface CustomerClient {

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId);

}
