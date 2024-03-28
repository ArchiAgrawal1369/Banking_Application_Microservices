package com.bank.accountmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.accountmanagement.entity.Account;
import com.bank.accountmanagement.entity.AccountCustomerResponse;
import com.bank.accountmanagement.entity.Transaction;
import com.bank.accountmanagement.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping()
	public ResponseEntity<Account> addAccount(@RequestBody Account account) {
		Account createdCustomer = accountService.addAccount(account);
		return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<AccountCustomerResponse> getAccountById(@PathVariable int accountId) {
		return accountService.getDetails(accountId);

	}

	@PutMapping("/add-money")
	public ResponseEntity<String> addMoneyToAccount(@RequestBody Transaction transaction) {
		return accountService.addMoneyToAccount(transaction);
	}

	@PutMapping("/withdraw-money")
	public ResponseEntity<String> withdrawMoneyFromAccount(@RequestBody Transaction transaction) {
		return accountService.withdrawMoneyFromAccount(transaction);
	}

	@DeleteMapping("/{accountId}")
	public HttpStatus deleteAccount(@PathVariable int accountId) {
		Boolean isDeleted = accountService.deleteAccount(accountId);
		if (isDeleted) {
			return HttpStatus.NO_CONTENT;
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}

	// For use by Customer-Service
	@DeleteMapping("/customer/{customerId}")
	public boolean deleteAccountByCustomerId(@PathVariable int customerId) {
		return accountService.deleteAccountByCustomerId(customerId);
	}

}
