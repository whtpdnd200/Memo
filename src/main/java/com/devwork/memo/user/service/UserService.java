package com.devwork.memo.user.service;

import com.devwork.memo.common.MD5HashingEncoder;
import com.devwork.memo.user.domain.User;
import com.devwork.memo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 클래스내에 생성자가 의존성 주입을 위한 생성자가 유일한 경우 @AutoWired 생각 가능
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(
                String loginId
                , String password
                , String name
                , String email) {

        String encodedPassword = MD5HashingEncoder.encode(password);

        int count = userRepository.insertUser(loginId, encodedPassword, name, email);

        if(count == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String loginId, String password) {

        String encodedPassword = MD5HashingEncoder.encode(password);

        User user = userRepository.selectUser(loginId, encodedPassword);

        return user;
    }
}
