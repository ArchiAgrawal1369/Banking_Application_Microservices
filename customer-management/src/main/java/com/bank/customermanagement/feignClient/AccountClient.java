package com.bank.customermanagement.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ACCOUNT-MANAGEMENT")
public interface AccountClient {

	@DeleteMapping("/account/customer/{customerId}")
	public boolean deleteAccountByCustomerId(@PathVariable int customerId);

}
