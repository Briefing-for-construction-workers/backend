package com.constructionnote.constructionnote.controller.user;

import com.constructionnote.constructionnote.api.request.user.UserReq;
import com.constructionnote.constructionnote.api.response.community.ReviewRes;
import com.constructionnote.constructionnote.api.response.user.UserProfileRes;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.service.community.*;
import com.constructionnote.constructionnote.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "유저 컨트롤러", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final HiringPostService hiringPostService;
    private final SeekingPostService seekingPostService;
    private final HiringReviewService hiringReviewService;
    private final SeekingReviewService seekingReviewService;
    private final PostLikeService postLikeService;

    @Operation(summary = "회원 등록", description = "회원의 정보를 등록")
    @PostMapping(value = "/signup",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signup(@RequestPart(value = "userReq") UserReq userReq, @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            userService.signUp(userReq, image);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "회원 확인", description = "해당 회원이 존재하는지 확인")
    @GetMapping("/exist/{userid}")
    public ResponseEntity<?> exist(@PathVariable("userid")
                                       @Schema(description = "유저id(토큰명)", example = "1")
                                       String userId) {
        try {
            return new ResponseEntity<>(userService.exist(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "회원 정보 수정", description = "회원의 정보를 수정")
    @PutMapping(value = "/profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@RequestPart(value = "userReq") UserReq userReq, @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            userService.updateUserProfile(userReq, image);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "회원 프로필 조회", description = "해당 회원의 프로필 조회")
    @GetMapping("/{userid}/profile")
    public ResponseEntity<?> getProfile(@PathVariable("userid")
                                            @Schema(description = "유저id(토큰명)", example = "1")
                                            String userId) {
        try {
            return new ResponseEntity<UserProfileRes>(userService.getUserProfile(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구직 상태 변경", description = "해당 회원의 구직 상태를 변경")
    @PutMapping("/{userid}/state")
    public ResponseEntity<?> updateSeekingState(@PathVariable("userid")
                                        @Schema(description = "유저id(토큰명)", example = "1")
                                        String userId) {
        try {
            userService.updateSeekingState(userId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구인 게시글 조회", description = "내가 작성한 구인 게시글을 조회")
    @GetMapping("/{userid}/hiring")
    public ResponseEntity<?> viewMyHiringPostList(@PathVariable("userid")
                                            @Schema(description = "유저id(토큰명)", example = "1")
                                            String userId) {
        try {
            return new ResponseEntity<List<PostDto>>(hiringPostService.viewHiringPostByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구직 게시글 조회", description = "내가 작성한 구직 게시글을 조회")
    @GetMapping("/{userid}/seeking")
    public ResponseEntity<?> viewMySeekingPostList(@PathVariable("userid")
                                            @Schema(description = "유저id(토큰명)", example = "1")
                                            String userId) {
        try {
            return new ResponseEntity<List<PostDto>>(seekingPostService.viewSeekingPostByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구인 후기 조회", description = "나에게 작성된 구인 후기 조회")
    @GetMapping("/{userid}/hiring/review")
    public ResponseEntity<?> viewHiringReviewList(@PathVariable("userid")
                                  @Schema(description = "유저id(토큰명)", example = "1")
                                  String userId) {
        try {
            return new ResponseEntity<List<ReviewRes>>(hiringReviewService.viewHiringReviewList(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 구직 후기 조회", description = "나에게 작성된 구직 후기 조회")
    @GetMapping("/{userid}/seeking/review")
    public ResponseEntity<?> viewSeekingReviewList(@PathVariable("userid")
                                  @Schema(description = "유저id(토큰명)", example = "1")
                                  String userId) {
        try {
            return new ResponseEntity<List<ReviewRes>>(seekingReviewService.viewSeekingReviewList(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 좋아요 구인 게시글 조회", description = "내가 좋아요한 구인 게시글 조회")
    @GetMapping("/{userid}/hiring/like")
    public ResponseEntity<?> viewHiringLikeList(@PathVariable("userid")
                                                  @Schema(description = "유저id(토큰명)", example = "1")
                                                  String userId) {
        try {
            return new ResponseEntity<List<PostDto>>(postLikeService.viewLikedHiringPostsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "내 좋아요 구직 게시글 조회", description = "내가 좋아요한 구직 게시글 조회")
    @GetMapping("/{userid}/seeking/like")
    public ResponseEntity<?> viewSeekingLikeList(@PathVariable("userid")
                                                 @Schema(description = "유저id(토큰명)", example = "1")
                                                 String userId) {
        try {
            return new ResponseEntity<List<PostDto>>(postLikeService.viewLikedSeekingPostsByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
