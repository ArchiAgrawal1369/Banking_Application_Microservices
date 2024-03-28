# Banking_Application_Microservices
#### Technologies Used : SpringBoot + JPA + Java + MySQL

This repository contains two microservices, Customer Management Service and Account Management Service. Details of these microservices are mentioned below :

### Customer Management Service with below mentioned APIs:
<ol type='a'>
  <li>Add customer</li>
  <li>Get all Customers</li>
  <li>Get single Customer Details</li>
  <li>Update Customer Details</li>
  <li>Delete Customer (Deleting customer would also delete the customer account from account management service)</li>
</ol>

### Account Management service with below mentioned APIs:
<ol type='a'>
  <li>Add Money to account. (Before adding money to account, checks if customer details passed in the request are valid or not)</li>
  <li>Withdraw money from account. (Before adding money to account, checks if customer details passed in the request are valid or not)</li>
  <li>Get Account details. (Details include account details and related customer details)</li>
  <li>Delete Account</li>
</ol>

In addition to these services, some other components have been implemented to facilitate microservices such as:
1.	API Gateway
2.	Eureka server for service registration
3.	Centralized configuration management using Spring Cloud Config Server
