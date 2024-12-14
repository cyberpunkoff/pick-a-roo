package edu.java.service;

import edu.java.dto.RegisterUserDto;
import edu.java.entity.UserEntity;
import edu.java.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void registerUser(RegisterUserDto requestDto) {
        var user = new UserEntity();
        user.setId(requestDto.getUserId());
        user.setFirstname(requestDto.getFirstname());
        user.setSurname(requestDto.getSurname());

        userRepository.save(user);
    }
}
