package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.service.community.SeekingPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeking")
public class SeekingPostController {
    private final SeekingPostService seekingPostService;

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody SeekingPostReq seekingPostReq) {
        try {
            Long seekingPostId = seekingPostService.registerSeekingPost(seekingPostReq);
            return new ResponseEntity<>(seekingPostId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{postid}")
    public ResponseEntity<?> delete(@PathVariable("postid") Long postId) {
        try {
            seekingPostService.deleteSeekingPost(postId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
