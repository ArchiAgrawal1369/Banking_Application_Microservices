package com.bank.accountmanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.accountmanagement.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
	public Account findByAccountNumber(int accountNumber);

	public List<Account> findAllByCustomerId(int customerId);

	public void deleteByAccountNumber(int accountNumber);

	public void deleteByCustomerId(int customerId);
}
