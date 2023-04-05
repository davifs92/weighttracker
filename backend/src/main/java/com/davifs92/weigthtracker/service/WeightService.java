package com.davifs92.weigthtracker.service;

import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.entities.Weight;
import com.davifs92.weigthtracker.repository.WeightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeightService {
    @Autowired
    private WeightRepository weightRepository;


    @Transactional(readOnly = true)
    public Page<WeightDto> findAll(PageRequest pageRequest){
        Page<Weight> list = weightRepository.findAll(pageRequest);
        return list.map(weight -> convertWeightEntityToDto(weight));

    }
    @Transactional(readOnly = true)
    public WeightDto findById(Long id){
        Optional<Weight> opt = weightRepository.findById(id);
        Weight entity = opt.orElseThrow(() -> new RuntimeException("Not found"));
        return convertWeightEntityToDto(entity);

    }
    @Transactional
    public WeightDto update(WeightDto dto){
        Weight entity = weightRepository.findById(dto.getId()).get();
        entity.setWeight(dto.getWeight());
        return convertWeightEntityToDto(weightRepository.save(entity));

    }
    @Transactional
    public WeightDto save(WeightDto dto){
        Weight weight = convertWeightDtoToEntity(dto);
        return convertWeightEntityToDto(weightRepository.save(weight));
    }

    @Transactional
    public void delete(Long id){
       Optional<Weight> weight = weightRepository.findById(id);
       weightRepository.delete(weight.get());
    }

    public static Weight convertWeightDtoToEntity(WeightDto dto){
        Weight entity = new Weight();
        entity.setWeight(dto.getWeight());
        entity.setUser(dto.getUser());
        return entity;
    }
    public static WeightDto convertWeightEntityToDto(Weight entity){
        WeightDto dto = new WeightDto();
        dto.setId(entity.getId());
        dto.setWeight(entity.getWeight());
        dto.setUser(entity.getUser());
        return dto;
    }
}
