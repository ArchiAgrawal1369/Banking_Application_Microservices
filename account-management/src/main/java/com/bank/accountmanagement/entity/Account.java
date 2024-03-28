package com.bank.accountmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "account_number")
	private int accountNumber;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "account_type")
	private String accountType = "Saving";

	@Column(name = "branch")
	private String branch = "NIT";

	@Column(name = "IFSCcode")
	private String IFSCcode = "NIT001";

	@Column(name = "customer_id")
	private int customerId;

	public Account() {
	}

	public Account(int accountNumber, Double balance, String accountType, String branch, String iFSCcode,
			int customerId) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.accountType = accountType;
		this.branch = branch;
		IFSCcode = iFSCcode;
		this.customerId = customerId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIFSCcode() {
		return IFSCcode;
	}

	public void setIFSCcode(String iFSCcode) {
		IFSCcode = iFSCcode;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

}
