package com.github.sozinhos.ecommerce.users.controllers;

import com.github.sozinhos.ecommerce.users.entities.User;
import com.github.sozinhos.ecommerce.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable String id) {
        return this.userService.findById(id);
    }

    @PostMapping
    public Mono<User> save(@Valid @RequestBody User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return this.userService.deleteById(id);
    }
}
