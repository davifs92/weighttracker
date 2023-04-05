package com.davifs92.weigthtracker.controller;

import com.davifs92.weigthtracker.dto.UserDto;
import com.davifs92.weigthtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

       @GetMapping
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        UserDto dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto){
        UserDto saved = userService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);

    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto dto){
        UserDto updated = userService.update(dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping
    public ResponseEntity<UserDto> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
