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

    @GetMapping("/{hiringpostid}")
    public ResponseEntity<?> view(@PathVariable("hiringpostid") Long hiringPostId) {
        try {
            return new ResponseEntity<HiringPostDetailRes>(hiringPostService.viewHiringPostById(hiringPostId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/{hiringpostid}")
    public ResponseEntity<?> update(@PathVariable("hiringpostid") Long hiringPostId, @RequestBody HiringPostReq hiringPostReq) {
        try {
            hiringPostService.updateHiringPost(hiringPostId, hiringPostReq);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
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

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
