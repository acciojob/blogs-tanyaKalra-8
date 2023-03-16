package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(String username, String password){

        User user =  new User();
        if (username != null && password != null){
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstName("test");
            user.setLastName("test");
        }

        userRepository.save(user);
        return user;
    }

    public void deleteUser(int userId){

        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).get();
            List<Blog> blogList = user.getBlogList();
            blogList.clear();;
            user.setBlogList(blogList);

            userRepository.deleteById(userId);
        }
    }

    public User updateUser(Integer id, String password){

        if (userRepository.existsById(id)){
            User user = userRepository.findById(id).get();
            if (password!= null){
                user.setPassword(password);
            }
            userRepository.save(user);
            return user;
        }
        return null;
    }
}
