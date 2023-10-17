package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.controller.dto.UserPasswordDTO;
import com.example.springboot.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    boolean checkPassword(String rawPassword, String hashedPassword);

    String hashPassword(String rawPassword);

    void sendVerificationCode(String email);

    void resetPasswordWithEmail(String email, String verificationCode, String newPassword);

}
