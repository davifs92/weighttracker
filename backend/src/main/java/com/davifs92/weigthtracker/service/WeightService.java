package com.davifs92.weigthtracker.service;

import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.entities.Weight;
import com.davifs92.weigthtracker.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeightService {
    @Autowired
    private WeightRepository weightRepository;

    public WeightDto save(WeightDto dto){
        Weight weight = convertDtoToEntity(dto);
        return convertEntityToDto(weightRepository.save(weight));
    }

    public void delete(Long id){
       Optional<Weight> weight = weightRepository.findById(id);
       weightRepository.delete(weight.get());
    }

    private Weight convertDtoToEntity(WeightDto dto){
        Weight entity = new Weight();
        entity.setWeight(dto.getWeight());
        entity.setUser(dto.getUser());
        return entity;
    }
    private WeightDto convertEntityToDto(Weight entity){
        WeightDto dto = new WeightDto();
        dto.setId(entity.getId());
        dto.setWeight(entity.getWeight());
        dto.setUser(entity.getUser());
        return dto;
    }
}
