package com.sicred.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paltas")
public class PaltaResource {

    @PostMapping(produces = "application/vnd.sicred.app-v1+json")
    public ResponseEntity<?> create() {
        return ResponseEntity.ok().build();
    }
}
