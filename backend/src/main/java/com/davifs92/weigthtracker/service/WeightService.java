package com.davifs92.weigthtracker.service;

import com.davifs92.weigthtracker.dto.UserDto;
import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.entities.User;
import com.davifs92.weigthtracker.entities.Weight;
import com.davifs92.weigthtracker.repository.UserRepository;
import com.davifs92.weigthtracker.repository.WeightRepository;
import com.davifs92.weigthtracker.service.exceptions.DataBaseException;
import com.davifs92.weigthtracker.service.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public Page<WeightDto> findAll(PageRequest pageRequest){
        Page<Weight> list = weightRepository.findAll(pageRequest);
        return list.map(WeightService::convertWeightEntityToDto);

    }
    @Transactional(readOnly = true)
    public WeightDto findById(Long id){
        Optional<Weight> opt = weightRepository.findById(id);
        Weight entity = opt.orElseThrow(() -> new ResourceNotFoundException("Weight not found"));
        return convertWeightEntityToDto(entity);

    }
    @Transactional
    public WeightDto update(String id, WeightDto dto){
        Weight entity = weightRepository.findById(Long. valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Weight not found")
        );
        entity.setWeight(dto.getWeight());
        return convertWeightEntityToDto(weightRepository.save(entity));

    }
    @Transactional
    public WeightDto save(WeightDto dto, String username){
        User user =userService.findByUsernameAndReturnEntity(username);
        dto.setUser(user);
        Weight weight = convertWeightDtoToEntity(dto);
        return convertWeightEntityToDto(weightRepository.save(weight));
    }

    @Transactional
    public void delete(Long id){
        try {
            weightRepository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("id not found");
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
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
        dto.setDate(entity.getCreatedAt());
        return dto;
    }
}
