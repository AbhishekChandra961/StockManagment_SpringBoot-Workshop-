package com.bridgelabz.workshop.controller;

import com.bridgelabz.workshop.dto.*;
import com.bridgelabz.workshop.model.User;
import com.bridgelabz.workshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseDto addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }
    @PutMapping("/verify")
    public ResponseDto verify(@RequestBody Verification verification){
        return userService.verify(verification);
    }
    @PostMapping("/login")
    public ResponseDto login(@RequestBody Login login){
        return userService.login(login);
    }
    @PutMapping("/logout")
    public ResponseDto logout(@RequestBody Logout logout){
        return  userService.logout(logout);
    }
    @PutMapping("/update/{id}")
    public ResponseDto updateById(@RequestBody UserDto userDto,@PathVariable int id){
        User data = userService.updateById(id,userDto);
        ResponseDto responseDto = new ResponseDto("Data is ",data);
        return responseDto;
    }
    @GetMapping("/{id}")
    public ResponseDto getById(@PathVariable int id){
        User data = userService.getById(id);
        ResponseDto responseDto = new ResponseDto("Data is ",data);
        return responseDto;
    }
    @GetMapping("/all")
    public ResponseDto getAllUser(){
        List<User> data = userService.getAllUser();
        ResponseDto responseDto = new ResponseDto("All Users are : ",data);
        return responseDto;
    }
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDto> deleteByToken(@PathVariable String token){
        String userData = userService.deleteByToken(token);
        ResponseDto responseDto = new ResponseDto("Data is deleted ",userData);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email){
        return userService.forgotPassword(email);
    }
    @PutMapping("/reset")
    public String resetPassword(@RequestParam String email,@RequestParam String password){
        return userService.resetPassword(email,password);
    }
    @PutMapping("/buystock/{userId}")
    public String buyStock(@RequestParam int stockId,@PathVariable int userId,@RequestParam int buyQuantity){
        return userService.buyStocks(stockId,userId,buyQuantity);
    }
}
