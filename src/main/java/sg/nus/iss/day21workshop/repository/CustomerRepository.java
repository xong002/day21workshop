package sg.nus.iss.day21workshop.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.iss.day21workshop.model.Customer;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate template;

    private String sql = "select * from customers limit ? offset ?";

    public List<Customer> getCustomers(int limit, int offset) {
        List<Customer> customerList = new ArrayList<>();

        // Method 1: using queryForRowSet
        SqlRowSet rs = template.queryForRowSet(sql, limit, offset);
        while(rs.next()){
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customerList.add(customer);
        }
        return (Collections.unmodifiableList(customerList));

        // //Method 2: usinsg query with RowMapper
        // customerList = template.query("select * from customers", BeanPropertyRowMapper.newInstance(Customer.class));
        // return customerList;

        // not working?
        // List<Customer> result = template.query(
        // sql,
        // (rs, int) -> {
        // Customer customer = new Customer();
        // customer.setId(rs.getInt("CustomerID"));
        // customer.setName(rs.getString("CustomerName"));
        // },
        // limit, offset);

    }

    public Customer getCustomerById(int id) {
        Customer customer = new Customer();
        customer = template.queryForObject("select * from customers where id = ?",
                BeanPropertyRowMapper.newInstance(Customer.class), id);
        return customer;
    }

    public Integer count() {
        Integer result = 0;
        result = template.queryForObject("select count(*) from customers", Integer.class);
        return result;
    }

    // test this
    public Boolean save(Customer customer){
        Boolean saved = false;
        saved = template.execute("INSERT into customers(CustomerID, CustomerName) values (?, ?)", new PreparedStatementCallback<Boolean>() {
            //set the parameters to be put into sql INSERT statement, will throw error if cannot execute.
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, customer.getId());
                ps.setString(2, customer.getFirstName());
                return ps.execute();
            }
        });
        return saved;
    }

    public int deleteById(int id){
        int deleted = 0;
        deleted = template.update("DELETE from customers where CustomerId = ?", id);
        return deleted;
    }
}
