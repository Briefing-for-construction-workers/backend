package com.constructionnote.constructionnote.controller;

import com.constructionnote.constructionnote.api.request.HiringPostReq;
import com.constructionnote.constructionnote.api.response.HiringPostDetailRes;
import com.constructionnote.constructionnote.service.HiringPostService;
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

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
