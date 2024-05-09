package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostApplyReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostLikeReq;
import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostDetailRes;
import com.constructionnote.constructionnote.service.community.HiringPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringPostController {
    private final HiringPostService hiringPostService;

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody HiringPostReq hiringPostReq) {
        try {
            Long hiringPostId = hiringPostService.registerHiringPost(hiringPostReq);
            return new ResponseEntity<>(hiringPostId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{postid}")
    public ResponseEntity<?> view(@PathVariable("postid") Long postId) {
        try {
            return new ResponseEntity<HiringPostDetailRes>(hiringPostService.viewHiringPostById(postId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/{postid}")
    public ResponseEntity<?> update(@PathVariable("postid") Long postId, @RequestBody HiringPostReq hiringPostReq) {
        try {
            hiringPostService.updateHiringPost(postId, hiringPostReq);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId) {
        try {
            hiringPostService.deleteHiringPost(postId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody HiringPostLikeReq hiringPostLikeReq) {
        try {
            Long hiringLikeId = hiringPostService.likeHiringPost(hiringPostLikeReq);
            return new ResponseEntity<>(hiringLikeId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody HiringPostApplyReq hiringPostApplyReq) {
        try {
            Long hiringPostApplyId = hiringPostService.applyHiringPost(hiringPostApplyReq);
            return new ResponseEntity<>(hiringPostApplyId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/pick")
    public ResponseEntity<?> pick(@RequestBody HiringPostApplyReq hiringPostApplyReq) {
        try {
            hiringPostService.pickApplicant(hiringPostApplyReq);
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
