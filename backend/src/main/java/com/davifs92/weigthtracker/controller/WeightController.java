package com.davifs92.weigthtracker.controller;

import com.davifs92.weigthtracker.config.JWTService;
import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.service.WeightService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/weight")
public class WeightController {
    @Autowired
    private WeightService weightService;
    @Autowired
    private JWTService  jwtService;

    @GetMapping
    public ResponseEntity<Page<WeightDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                    @RequestParam(value = "orderBy", defaultValue = "weight") String orderBy,
                                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<WeightDto> list = weightService.findAll(pageRequest);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<WeightDto> findById(@PathVariable Long id){
        WeightDto dto = weightService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<WeightDto> create(@RequestBody WeightDto dto, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        WeightDto saved = weightService.save(dto, username);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<WeightDto> update(@PathVariable String id, @RequestBody WeightDto dto){
        WeightDto updated = weightService.update(id, dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<WeightDto> delete(@PathVariable Long id){
        weightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
