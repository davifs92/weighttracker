package com.davifs92.weigthtracker.service;


import com.davifs92.weigthtracker.WeigthtrackerApplication;
import com.davifs92.weigthtracker.dto.UserDto;
import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.entities.User;
import com.davifs92.weigthtracker.entities.Weight;
import com.davifs92.weigthtracker.repository.UserRepository;
import com.davifs92.weigthtracker.service.exceptions.DataBaseException;
import com.davifs92.weigthtracker.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(PageRequest pageRequest){
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(this::convertEntityToDto);

    }
    @Transactional(readOnly = true)
    public UserDto findById(String id){
        Optional<User> opt = userRepository.findById(UUID.fromString(id));
        User entity = opt.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return convertEntityToDto(entity);

    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        User entity = opt.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return convertEntityToDto(entity);

    }
    @Transactional
    public UserDto update(UserDto dto){
        User entity = userRepository.findById(UUID.fromString(dto.getId())).orElseThrow(
                () -> new ResourceNotFoundException("User was not found")
        );
        return convertEntityToDto(userRepository.save(entity));

    }
    @Transactional
    public UserDto create(UserDto dto){
        User weight = convertDtoToEntity(dto);
        weight.setPassword(WeigthtrackerApplication.bCryptPasswordEncoder().encode(dto.getPassword()));
        return convertEntityToDto(userRepository.save(weight));
    }

    @Transactional
    public void delete(String id){
        try {
            userRepository.deleteById(UUID.fromString(id));
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
    }

    private User convertDtoToEntity(UserDto dto){
        User entity = new User();
        entity.setAge(dto.getAge());
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setHeight(dto.getHeight());
        entity.setGoal(dto.getGoal());
        entity.setEmail(dto.getEmail());
        copyListDtoToSetEntity(dto.getWeights(), entity.getWeightSet());
        return entity;
    }
    private UserDto convertEntityToDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId().toString());
        dto.setAge(entity.getAge());
        dto.setEmail(entity.getEmail());
        dto.setGoal(entity.getGoal());
        dto.setName(entity.getName());
        copySetEntityToDtoList(dto.getWeights(), entity.getWeightSet());
        return dto;
    }

    private void copyListDtoToSetEntity(List<WeightDto> dtoList, Set<Weight> entityList){
        for(WeightDto weightDto : dtoList){
            entityList.add(WeightService.convertWeightDtoToEntity(weightDto));
        }
    }

    private void copySetEntityToDtoList(List<WeightDto> dtoList, Set<Weight> entityList){
        for(Weight weight : entityList){
            dtoList.add(WeightService.convertWeightEntityToDto(weight));
        }
    }


}
