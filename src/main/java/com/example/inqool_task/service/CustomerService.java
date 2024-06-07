package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {

    private final CrudRepository crudRepository;

    @Autowired
    public CustomerService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Customer findCustomer(Customer customer) {
        List<Customer> customers = crudRepository.findByNamedQuery(
                Customer.FIND_BY_PHONE_NUMBER, Customer.class,
                Collections.singletonMap("phoneNumber", customer.getPhoneNumber())
        );

        Customer result;
        if (customers.isEmpty()) {
            result = createCustomer(customer);
        }
        else {
            result = customers.get(0);
            if (!result.getName().equals(customer.getName())) {
                throw new IllegalArgumentException("Invalid name for given phone number");
            }
        }

        return result;
    }

    private Customer createCustomer(Customer customer) {
        Customer createdCustomer = crudRepository.create(customer);

        if (createdCustomer == null) {
            System.out.println("FAIL CREATE Customer");
        }

        return createdCustomer;
    }
}
