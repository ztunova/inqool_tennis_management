package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CrudRepository crudRepository;

    @Autowired
    public CustomerService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Customer findCustomer(Customer customer) {
        Optional<Customer> customerOpt = getCustomerByPhoneNumber(customer.getPhoneNumber());

        Customer result;
        if (customerOpt.isEmpty()) {
            result = createCustomer(customer);
        }
        else {
            result = customerOpt.get();
            if (!result.getName().equals(customer.getName())) {
                throw new IllegalArgumentException("Invalid name for given phone number");
            }
        }

        return result;
    }

    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        List<Customer> customers = crudRepository.findByNamedQuery(
                Customer.FIND_BY_PHONE_NUMBER, Customer.class,
                Collections.singletonMap("phoneNumber", phoneNumber)
        );

        Customer result = null;
        if (!customers.isEmpty()) {
            result = customers.get(0);
        }

        return Optional.ofNullable(result);
    }

    private Customer createCustomer(Customer customer) {
        return crudRepository.create(customer);
    }
}
