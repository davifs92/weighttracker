package com.davifs92.weigthtracker.controller;

import com.davifs92.weigthtracker.dto.UserDto;
import com.davifs92.weigthtracker.service.UserService;
import jakarta.validation.Valid;
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

    public ResponseEntity<Page<UserDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                   @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                   @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<UserDto> list = userService.findAll(pageRequest);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping
    public ResponseEntity<UserDto> findById(@PathVariable String id){
        UserDto dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto){
        UserDto saved = userService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);

    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto){
        UserDto updated = userService.update(dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping
    public ResponseEntity<UserDto> delete(@PathVariable String id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
