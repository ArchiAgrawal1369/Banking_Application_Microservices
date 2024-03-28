package com.bank.customermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.customermanagement.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	public Customer findById(int customerId);

	public void deleteById(int customerId);
}
