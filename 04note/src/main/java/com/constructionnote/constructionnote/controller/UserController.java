package com.constructionnote.constructionnote.controller;

import com.constructionnote.constructionnote.api.request.UserProfileReq;
import com.constructionnote.constructionnote.api.request.UserSignupReq;
import com.constructionnote.constructionnote.api.response.UserProfileRes;
import com.constructionnote.constructionnote.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestPart(value = "userSignupReq") UserSignupReq userSignupReq, @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            userService.signUp(userSignupReq, image);
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

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestPart(value = "userProfileReq") UserProfileReq userProfileReq, @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            userService.updateUserProfile(userProfileReq, image);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/profile/{userid}")
    public ResponseEntity<?> getProfile(@PathVariable("userid") String userId) {
        try {
            return new ResponseEntity<UserProfileRes>(userService.getUserProfile(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
