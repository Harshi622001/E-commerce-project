package com.user.User.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.user.User.entity.UserEntity;
import com.user.User.entity.UserLoginDTO;
import com.user.User.entity.dto.OrderDto;
import com.user.User.entity.dto.UserDto;
import com.user.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInterface{
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    private static String BASE_URL = "http://localhost:8081/orders/getAllOrders";

    public String saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        return "User created successfully";
    }

    public List<OrderDto> getAllOrdersOfUser() {
        HttpEntity<String> entity = new HttpEntity<>(null);
        return restTemplate.exchange(
                URI.create(BASE_URL),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<OrderDto>>() {
        }).getBody();
    }

    public UserEntity getUser(UserLoginDTO userLoginDTO) {
        UserEntity userEntity = userRepository.findByUserName(userLoginDTO.getUserName());

        return userEntity.getPassword().equals(userLoginDTO.getPassword()) ? userEntity : null;

    }
}
