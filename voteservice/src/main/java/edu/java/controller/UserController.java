package edu.java.controller;

import edu.java.dto.RegisterUserDto;
import edu.java.model.RegisterUserRequest;
import edu.java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody RegisterUserRequest request, @RequestHeader("Tg-Chat-Id") Long userId) {
        var requestDto = RegisterUserDto.builder()
            .userId(userId)
            .firstname(request.getFirstname())
            .surname(request.getSurname())
            .build();

        userService.registerUser(requestDto);
    }
}
