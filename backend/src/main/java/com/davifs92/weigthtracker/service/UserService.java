package com.davifs92.weigthtracker.service;


import com.davifs92.weigthtracker.dto.UserDto;
import com.davifs92.weigthtracker.dto.WeightDto;
import com.davifs92.weigthtracker.entities.User;
import com.davifs92.weigthtracker.entities.Weight;
import com.davifs92.weigthtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(PageRequest pageRequest){
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(weight -> convertEntityToDto(weight));

    }
    @Transactional(readOnly = true)
    public UserDto findById(Long id){
        Optional<User> opt = userRepository.findById(id);
        User entity = opt.orElseThrow(() -> new RuntimeException("Not found"));
        return convertEntityToDto(entity);

    }
    @Transactional
    public UserDto update(UserDto dto){
        User entity = userRepository.findById(dto.getId()).get();
        return convertEntityToDto(userRepository.save(entity));

    }
    @Transactional
    public UserDto save(UserDto dto){
        User weight = convertDtoToEntity(dto);
        return convertEntityToDto(userRepository.save(weight));
    }

    @Transactional
    public void delete(Long id){
        Optional<User> weight = userRepository.findById(id);
        userRepository.delete(weight.get());
    }

    private User convertDtoToEntity(UserDto dto){
        User entity = new User();
        entity.setAge(dto.getAge());
        entity.setName(dto.getName());
        entity.setHeight(dto.getHeight());
        entity.setGoal(dto.getGoal());
        entity.setEmail(dto.getEmail());
        copyListDtoToSetEntity(dto.getWeights(), entity.getWeightSet());
        return entity;
    }
    private UserDto convertEntityToDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
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
