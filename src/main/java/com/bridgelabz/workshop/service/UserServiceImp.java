package com.bridgelabz.workshop.service;

import com.bridgelabz.workshop.dto.*;
import com.bridgelabz.workshop.exception.CustomException;
import com.bridgelabz.workshop.model.Stock;
import com.bridgelabz.workshop.model.User;
import com.bridgelabz.workshop.repo.StockRepo;
import com.bridgelabz.workshop.repo.UserRepo;
import com.bridgelabz.workshop.util.EmailService;
import com.bridgelabz.workshop.util.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StockRepo stockRepo;

    @Override
    public ResponseDto addUser(UserDto userDto) {
        String email=userDto.getEmail();
        User data=userRepo.findByEmail(email);
        System.out.println("Done");
        if(data==null){
            User userData=new User(userDto);
            int generatedOtp= (int) (((Math.random() * 999999) % 899998) + 100001);
            System.out.println(generatedOtp);
            userData.setOtp(generatedOtp);
            System.out.println(data);
            userRepo.save(userData);
            emailService.sendEmail(userData.getEmail(),"Registration Done","Hey.."+userData.getFirstName()+" "+userData.getLastName()+"\n THe OTP is "+generatedOtp);
            return new ResponseDto("Otp sent ....Now Verify",userDto);
        }else{
            return new ResponseDto("Enter Unique Email ID",userDto.getEmail());
        }
    }

    @Override
    public ResponseDto verify(Verification verification) {
        String email=verification.getEmail();
        User userId=userRepo.findByEmail(email);
        if(userId!=null){
            Optional<User> data = userRepo.findById(userId.getUserId());
            if(verification.getOtp() == data.get().getOtp()){
                data.get().setVerifyOtp(true);
                userRepo.save(data.get());
                return new ResponseDto("Verification Successfull,Now Login",email);
            }else {
                return new ResponseDto("Wrong OTP X-X ","Check Data");
            }
        }else{
            return new ResponseDto("Email Not Registered ",email);
        }
    }

    @Override
    public ResponseDto login(Login login) {
        String email=login.getEmail();
        String password=login.getPassword();
        String passwordData=userRepo.getPassword(email);
        User userId=userRepo.findByEmail(email);
//        boolean verifyOtpData=userRepo.getVerifyOtp(email);
        boolean verifyOtpData=userId.isVerifyOtp();
        if(email!=null && password.equals(passwordData) && verifyOtpData==true){
            Optional<User> data=userRepo.findById(userId.getUserId());
            String token=jwtToken.creatToken(userId.getUserId());
            data.get().setToken(token);
            data.get().setLogin(true);
            userRepo.save(data.get());
            return new ResponseDto("Login Successfull :)","Token = "+token);
        }else if(email==null){
            return new ResponseDto("Email not present in DataBase",email);
        } else if (password!=passwordData) {
            return new ResponseDto("Wrong Password X-X ","Recheck");
        }else {
            return new ResponseDto("Login Unsuccessfull ","Verify first");
        }
    }

    @Override
    public ResponseDto logout(Logout logout) {
        String email = logout.getEmail();
        User data = userRepo.findByEmail(email);
        if(data!=null){
            data.setLogin(false);
            userRepo.save(data);
            return new ResponseDto("LogOut SuccessFull","Thank you:)");
        }else {
            return new ResponseDto("Email id not Present :(",email);
        }
    }

    @Override
    public User updateById(int id, UserDto userDto) {
        User data = this.getById(id);
        if(data.isLogin()) {
            data.updateData(userDto);
            return userRepo.save(data);
        }else{
            System.out.println("Login First");
            return null;
        }
    }

    @Override
    public User getById(int id) {
        return userRepo.findById(id).orElseThrow(()-> new CustomException("Employee Not Found with id : "+id));
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public String deleteByToken(String token) {
        int id = jwtToken.decodeToken(token);
        User data = this.getById(id);
        if(data.isLogin()) {
            userRepo.delete(data);
            return "User Deleted ";
        }else{
            return "Can not Delete, Login first";
        }
    }

    @Override
    public String forgotPassword(String email) {
        User id = userRepo.findByEmail(email);
        User userData = userRepo.findById(id.getUserId()).orElseThrow(()-> new CustomException("Employee Not Found with id : "+id));
        Optional<User> data = userRepo.findById(id.getUserId());
        if(id == null){
            return "Mail Id is Not Registered";
        }else{
            int genarateOtp = (int) ((Math.random() * 9999) % 8998) + 1001;
            data.get().setOtp(genarateOtp);
            data.get().setVerifyOtp(false);
            userRepo.save(data.get());
            emailService.sendEmail(userData.getEmail(),"The data added successfully ", "hi...." + userData.getFirstName()+userData.getLastName() + "\n your data added succsessfully " + "\n your otp is  <- " + genarateOtp + " ->");
            return "Otp generated successfully , now verify "+genarateOtp;
        }
    }

    @Override
    public String resetPassword(String email, String password) {
         User data = userRepo.findByEmail(email);
         if(data==null){
             return "Email Not Present";
         }else{
             System.out.println("do");
//             User id = userRepo.findByEmail(email);
//             Optional<User> userData = userRepo.findById(data.getUserId());
//             if(userData.isPresent() && userData.get().isVerifyOtp()==true){
//                 userData.get().setPassword(password);
//                 userRepo.save(userData.get());
//                 return "Password reset done";
             if(data.isVerifyOtp()){
                 data.setPassword(password);
                 userRepo.save(data);
                 return "Password reset done";
             }else{
                 return "password reset FAILED, verify first";
             }
         }
    }
//    @Override
//    public String buyStocks(int stockId,int userId,int buyQuantity) {
//        Optional<Stock> stockData=stockRepo.findById(stockId);
//        Optional<User> data = userRepo.findById(userId);
//        if(stockData.isPresent() && data.get().isLogin()){
//            if(buyQuantity<=stockData.get().getQuantity()){
//                int prevQuantity=stockData.get().getQuantity();
//                int currQuantity=prevQuantity-buyQuantity;
//                System.out.println(currQuantity);
//                stockData.get().setQuantity(currQuantity);
//                stockData.get().setStockSold(buyQuantity+stockData.get().getStockSold());
//                data.get().setStockBought(buyQuantity+data.get().getStockBought());
//                data.get().setStocks(stockData.get());
////                data.get().setStock(stockData);
//                stockRepo.save(stockData.get());
//                userRepo.save(data.get());
//                int totalPrice = buyQuantity*(stockData.get().getPrice());
//                return "Stock sold ,total price is : "+totalPrice;
//            }else{
//                return "Not have enough Quantity";
//            }
//        }else if(!data.get().isLogin()){
//            return "Login First";
//        }else{
//            return "Stock Not Present";
//        }
//    }
    @Override
    public String buyStocks(int stockId,int userId,int buyQuantity) {
        Optional<Stock> stockData=stockRepo.findById(stockId);
        Optional<User> data = userRepo.findById(userId);
        if(stockData.isPresent() && data.get().isLogin()){
            if(buyQuantity<=stockData.get().getQuantity()){
                int prevQuantity=stockData.get().getQuantity();
                int currQuantity=prevQuantity-buyQuantity;
                System.out.println(currQuantity);
                stockData.get().setQuantity(currQuantity);
                stockData.get().setStockSold(buyQuantity+stockData.get().getStockSold());
//                stockData.get().setUsers((List<User>) data.get());
                List<User> usersList = new ArrayList<>();
                usersList.add(data.get());
                stockData.get().setUsers(usersList);
                data.get().setStockBought(buyQuantity+data.get().getStockBought());
//                data.get().setStocks((List<Stock>) stockData.get());
//                data.get().setStock(stockData);
                List<Stock> stocksList = new ArrayList<>();
                stocksList.add(stockData.get());
                data.get().setStocks(stocksList);

                stockRepo.save(stockData.get());
                userRepo.save(data.get());
                int totalPrice = buyQuantity*(stockData.get().getPrice());
                return "Stock sold ,total price is : "+totalPrice;
            }else{
                return "Not have enough Quantity";
            }
        }else if(!data.get().isLogin()){
            return "Login First";
        }else{
            return "Stock Not Present";
        }
    }
}
