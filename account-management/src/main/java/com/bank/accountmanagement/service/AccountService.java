package com.bank.accountmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.accountmanagement.entity.Account;
import com.bank.accountmanagement.entity.AccountCustomerResponse;
import com.bank.accountmanagement.entity.Customer;
import com.bank.accountmanagement.entity.Transaction;
import com.bank.accountmanagement.feignClient.CustomerClient;
import com.bank.accountmanagement.repo.AccountRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AccountService {

	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private CustomerClient customerClient;

	public Account addAccount(Account account) {
		return accountRepo.save(account);
	}

	@HystrixCommand(fallbackMethod = "fallbackGetDetails")
	public ResponseEntity<AccountCustomerResponse> getDetails(int accountId) {
		Account account = accountRepo.findByAccountNumber(accountId);
		Customer customer = customerClient.getCustomerById(account.getCustomerId()).getBody();
		if (customer != null && account != null) {
			AccountCustomerResponse response = new AccountCustomerResponse();
			response.setAccount(account);
			response.setCustomer(customer);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@HystrixCommand(fallbackMethod = "fallbackMoneyTransfer")
	public ResponseEntity<String> addMoneyToAccount(Transaction transaction) {
		Account account = accountRepo.findByAccountNumber(transaction.getAccountId());
		if (account != null && transaction!=null) {
			Customer customer = customerClient.getCustomerById(account.getCustomerId()).getBody();
			if (transaction.getEmail().equals(customer.getEmail()) && transaction.getNumber().equals(customer.getNumber())
					&& transaction.getName().equals(customer.getName())) {
				account.setBalance(account.getBalance() + transaction.getAmount());
				accountRepo.save(account);
				return new ResponseEntity<>("Success!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Given details do not match!"+customer.getEmail()+"  "+transaction.getEmail()+"\n"+customer.getName()+"  "+transaction.getName(), HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<>("Account with ID " + transaction.getAccountId() + " not found.",
					HttpStatus.NOT_FOUND);
		}
	}

	@HystrixCommand(fallbackMethod = "fallbackMoneyTransfer")
	public ResponseEntity<String> withdrawMoneyFromAccount(Transaction transaction) {
		Account account = accountRepo.findByAccountNumber(transaction.getAccountId());
		if (account != null) {
			Customer customer = customerClient.getCustomerById(account.getCustomerId()).getBody();
			if (transaction.getEmail().equals(customer.getEmail()) && transaction.getNumber().equals(customer.getNumber())
					&& transaction.getName().equals(customer.getName()) && account.getBalance() >= transaction.getAmount()) {
				account.setBalance(account.getBalance() - transaction.getAmount());
				accountRepo.save(account);
				return new ResponseEntity<>("Success!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Given details do not match or insufficient balance!", HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<>("Account with ID " + transaction.getAccountId() + " not found.",
					HttpStatus.NOT_FOUND);
		}
	}

	
	//Hystrix Fallback functions
	
	public ResponseEntity<AccountCustomerResponse> fallbackGetDetails(int accountId) {
		return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
	}

	public ResponseEntity<String> fallbackMoneyTransfer(Transaction transaction) {
		return new ResponseEntity<>("The server is busy.", HttpStatus.REQUEST_TIMEOUT);
	}
	
	//Deletion functions
	
	public Account getAccountById(int accountId) {
		return accountRepo.findByAccountNumber(accountId);
	}
	
	@Transactional
	public boolean deleteAccount(int accountId) {
		Account account = getAccountById(accountId);
		if (account != null) {
			accountRepo.deleteByAccountNumber(accountId);
			return true;
		} else {
			return false;
		}
	}

	public List<Account> getAccountsByCustomerId(int customerId) {
		return accountRepo.findAllByCustomerId(customerId);
	}

	@Transactional
	public boolean deleteAccountByCustomerId(int customerId) {
		List<Account> accounts = getAccountsByCustomerId(customerId);
		if (!accounts.isEmpty()) {
			for (Account account : accounts) {
				accountRepo.delete(account);
			}
			return true;
		} else {
			return false;
		}
	}

}
