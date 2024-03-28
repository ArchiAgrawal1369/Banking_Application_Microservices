package com.bank.customermanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.customermanagement.entity.Customer;
import com.bank.customermanagement.feignClient.AccountClient;
import com.bank.customermanagement.repo.CustomerRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private AccountClient accountClient;

	public Customer addCustomer(Customer customer) {
		return customerRepo.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	public Customer getCustomerById(int customerId) {
		return customerRepo.findById(customerId);
	}

	public Customer updateCustomer(int customerId, Customer updatedCustomer) {
		Customer existingCustomer = customerRepo.findById(customerId);
		if (existingCustomer != null) {
			if (updatedCustomer.getEmail() != null) {
				existingCustomer.setEmail(updatedCustomer.getEmail());
			}
			if (updatedCustomer.getName() != null) {
				existingCustomer.setName(updatedCustomer.getName());
			}
			if (updatedCustomer.getNumber() != null) {
				existingCustomer.setNumber(updatedCustomer.getNumber());
			}
			if (updatedCustomer.getAddress() != null) {
				existingCustomer.setAddress(updatedCustomer.getAddress());
			}
			if (updatedCustomer.getDob() != null) {
				existingCustomer.setDob(updatedCustomer.getDob());
			}
			if (updatedCustomer.getOccupation() != null) {
				existingCustomer.setOccupation(updatedCustomer.getOccupation());
			}
			return customerRepo.save(existingCustomer);
		} else {
			return existingCustomer;
		}
	}

	public boolean deleteCustomer(int customerId) {
		Customer customer = getCustomerById(customerId);
		if (customer != null) {
			customerRepo.deleteById(customerId);
			accountClient.deleteAccountByCustomerId(customerId);
			return true;
		} else {
			return false;
		}
	}
}
