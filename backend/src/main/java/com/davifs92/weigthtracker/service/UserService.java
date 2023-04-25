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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        Optional<User> opt = userRepository.findById(Long.valueOf(id));
        User entity = opt.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return convertEntityToDto(entity);

    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        User entity = opt.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return convertEntityToDto(entity);

    }

    @Transactional(readOnly = true)
    public User findByUsernameAndReturnEntity(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        User entity = opt.orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return entity;

    }
    @Transactional
    public UserDto update(String id, UserDto dto){
        User entity = userRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("User was not found")
        );
        return convertEntityToDto(userRepository.save(entity));

    }

    @Transactional
    public void delete(String id){
        try {
            userRepository.deleteById(Long.valueOf(id));
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation");
        }
    }

    public User convertDtoToEntity(UserDto dto){
        User entity = new User();
        Optional.of(dto.getId()).ifPresent(id -> entity.setId(Long.getLong(id)));
        entity.setAge(dto.getAge());
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setHeight(dto.getHeight());
        entity.setGoal(dto.getGoal());
        entity.setEmail(dto.getEmail());
        if(!dto.getWeights().isEmpty()){
            copyListDtoToListEntity(dto.getWeights(), entity.getWeights());
        }
        return entity;
    }
    public UserDto convertEntityToDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId().toString());
        dto.setUsername(entity.getUsername());
        dto.setAge(entity.getAge());
        dto.setEmail(entity.getEmail());
        dto.setGoal(entity.getGoal());
        dto.setName(entity.getName());
        dto.setHeight(entity.getHeight());
        if(!entity.getWeights().isEmpty()){
            copyListEntityToDtoList(dto.getWeights(), entity.getWeights());
        }
        return dto;
    }

    private void copyListDtoToListEntity(List<WeightDto> dtoList, List<Weight> entityList){
        for(WeightDto weightDto : dtoList){
            entityList.add(WeightService.convertWeightDtoToEntity(weightDto));
        }
    }

    private void copyListEntityToDtoList(List<WeightDto> dtoList, List<Weight> entityList){
        for(Weight weight : entityList){
            dtoList.add(WeightService.convertWeightEntityToDto(weight));
        }
    }


}
