package com.constructionnote.constructionnote.controller;

import com.constructionnote.constructionnote.api.response.ConstructionRes;
import com.constructionnote.constructionnote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/{userid}")
    public ResponseEntity<?> signup(@PathVariable("userid") String userId) {
        try {
            userService.signUp(userId);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/exist/{userid}")
    public ResponseEntity<?> exist(@PathVariable("userid") String userId) {
        try {
            return new ResponseEntity<>(userService.exist(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
