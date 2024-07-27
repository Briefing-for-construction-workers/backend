package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.PostLikeReq;
import com.constructionnote.constructionnote.service.community.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "구인 게시글 좋아요 컨트롤러", description = "구인 게시글 좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "구인 게시글 좋아요/취소", description = "구인 게시글을 좋아요 하거나 취소")
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody PostLikeReq postLikeReq) {
        try {
            String result = postLikeService.likePost(postLikeReq);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
