package com.davifs92.weigthtracker.controller;

import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.service.WeightService;
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


    public ResponseEntity<Page<WeightDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                    @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<WeightDto> list = weightService.findAll(pageRequest);

        return ResponseEntity.ok().body(list);
    }
    @GetMapping
    public ResponseEntity<WeightDto> findById(@PathVariable Long id){
        WeightDto dto = weightService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<WeightDto> create(@RequestBody WeightDto dto){
        WeightDto saved = weightService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);

    }

    @PutMapping
    public ResponseEntity<WeightDto> update(@RequestBody WeightDto dto){
        WeightDto updated = weightService.update(dto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping
    public ResponseEntity<WeightDto> delete(@PathVariable Long id){
        weightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
