package com.example.inqool_task.service;

import com.example.inqool_task.data.model.Customer;
import com.example.inqool_task.repository.CrudRepository;
import com.example.inqool_task.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findCustomer_createCustomer_returnsCustomer() {
        Customer customer = TestDataFactory.customerEntity;
        Map<String, Object> params = Collections.singletonMap("phoneNumber", customer.getPhoneNumber());

        Mockito.when(crudRepository.findByNamedQuery(Customer.FIND_BY_PHONE_NUMBER, Customer.class, params))
                .thenReturn(new ArrayList<>());
        Mockito.when(crudRepository.create(customer)).thenReturn(customer);

        Customer customerReturned = customerService.findCustomer(customer);

        assertThat(customerReturned).isEqualTo(customer);
    }

    @Test
    void findCustomer_customerExists_returnsCustomer() {
        Customer customer = TestDataFactory.customerEntity;
        Map<String, Object> params = Collections.singletonMap("phoneNumber", customer.getPhoneNumber());

        Mockito.when(crudRepository.findByNamedQuery(Customer.FIND_BY_PHONE_NUMBER, Customer.class, params))
                .thenReturn(List.of(customer));

        Customer customerReturned = customerService.findCustomer(customer);

        assertThat(customerReturned).isEqualTo(customer);
    }

}
