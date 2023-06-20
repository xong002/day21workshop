package sg.nus.iss.day21workshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.nus.iss.day21workshop.model.Customer;
import sg.nus.iss.day21workshop.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    CustomerService svc;

    //change to service
    @GetMapping(path = "/customers", consumes = "application/json")
    public ResponseEntity<String> getAllCustomers(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit) {
        Optional<List<Customer>> opt = svc.getCustomers(limit, offset);

        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("message", "No list found").build().toString());
        }

        return ResponseEntity.ok(opt.get().toString());
    }

    @GetMapping(path = "/customers/{id}", consumes = "application/json")
    public ResponseEntity<String> getCustomerById(@PathVariable int id) {
        Optional<Customer> opt = svc.getCustomerById(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("message", "Customer " + id + " not found").build().toString());
        }

        return ResponseEntity.ok(opt.get().toString());
    }
}
