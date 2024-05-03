package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.response.community.HiringReviewRes;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.service.community.CommunityService;
import com.constructionnote.constructionnote.service.community.HiringReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityService communityService;
    private final HiringReviewService hiringReviewService;

    @GetMapping("/post")
    public ResponseEntity<?> previewPostList() {
        try {
            return new ResponseEntity<List<PostDto>>(communityService.viewPostList(), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/review/{userid}")
    public ResponseEntity<?> previewReviewList(@PathVariable("userid") String userId) {
        try {
            return new ResponseEntity<List<HiringReviewRes>>(hiringReviewService.viewLimitedReviewList(userId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
