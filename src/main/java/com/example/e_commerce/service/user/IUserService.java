package com.example.e_commerce.service.user;

import com.example.e_commerce.dto.user.UserDto;
import com.example.e_commerce.model.User;
import com.example.e_commerce.request.user.CreateUserRequest;
import com.example.e_commerce.request.user.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request ,Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
