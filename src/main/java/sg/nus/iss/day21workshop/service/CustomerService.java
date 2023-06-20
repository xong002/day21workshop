package sg.nus.iss.day21workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import sg.nus.iss.day21workshop.model.Customer;
import sg.nus.iss.day21workshop.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;

    public Optional<List<Customer>> getCustomers(int limit, int offset) {
        try {
            return Optional.of(repo.getCustomers(limit, offset));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Customer> getCustomerById(int id) {
        try {
            return Optional.of(repo.getCustomerById(id));
        } catch (EmptyResultDataAccessException ee) {
            ee.printStackTrace();
            return Optional.empty();
        }
    }
}
