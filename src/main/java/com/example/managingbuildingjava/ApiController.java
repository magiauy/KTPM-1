package com.example.managingbuildingjava;

import BUS.ServiceBUS;
import DTO.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/Service")
    public ResponseEntity<List<Service>> getService() {
        ServiceBUS serviceBUS = new ServiceBUS();
        List<Service> services = serviceBUS.getAll();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping("/Service")
    public ResponseEntity<Boolean> addService(@Validated @RequestBody Service service) {
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean result = serviceBUS.add(service);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/Service")
    public ResponseEntity<Boolean> updateService(@Validated @RequestBody Service service) {
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean result = serviceBUS.update(service);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/Service/{id}")
    public ResponseEntity<Boolean> deleteService(@PathVariable String id) {
        ServiceBUS serviceBUS = new ServiceBUS();
        Service service = serviceBUS.getService(id);
        if (service == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean result = serviceBUS.delete(service);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
