package com.user.User.controller;


import com.user.User.entity.UserEntity;
import com.user.User.entity.UserLoginDTO;
import com.user.User.entity.dto.OrderDto;
import com.user.User.entity.dto.UserDto;
import com.user.User.service.UserInterface;
import com.user.User.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(userInterface.saveUser(userEntity), HttpStatus.CREATED);

    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> getAllOrdersOfUser() {
        return ResponseEntity.ok(userInterface.getAllOrdersOfUser());
    }

    @PostMapping("/login")
    public UserEntity userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        return userInterface.getUser(userLoginDTO);
    }
}