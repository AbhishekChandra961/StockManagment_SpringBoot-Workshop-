package com.bridgelabz.workshop.service;

import com.bridgelabz.workshop.dto.*;
import com.bridgelabz.workshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseDto addUser(UserDto userDto);
    ResponseDto verify(Verification verification);
    ResponseDto login(Login login);
    ResponseDto logout(Logout logout);
    User updateById(int id,UserDto userDto);
    User getById(int id);
    List<User> getAllUser();
    String deleteByToken(String token);
    String forgotPassword(String email);
    String resetPassword(String email,String password);
    String buyStocks(int stockId,int userId,int sellQuantity);

}
