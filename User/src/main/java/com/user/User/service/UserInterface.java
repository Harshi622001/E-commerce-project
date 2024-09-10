package com.user.User.service;

import com.user.User.entity.UserEntity;
import com.user.User.entity.UserLoginDTO;
import com.user.User.entity.dto.OrderDto;

import java.util.List;

public interface UserInterface {
    String saveUser(UserEntity userEntity);
    List<OrderDto> getAllOrdersOfUser();
    UserEntity getUser(UserLoginDTO userLoginDTO);
}
