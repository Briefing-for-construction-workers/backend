package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.service.community.SeekingPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "구직 게시글 컨트롤러", description = "구직 게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeking")
public class SeekingPostController {
    private final SeekingPostService seekingPostService;

    @Operation(summary = "구직 게시글 등록", description = "구직 게시글을 등록")
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody SeekingPostReq seekingPostReq) {
        try {
            Long seekingPostId = seekingPostService.registerSeekingPost(seekingPostReq);
            return new ResponseEntity<>(seekingPostId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 게시글 수정", description = "게시글id에 해당하는 구인 게시글을 수정")
    @PutMapping("/{postid}")
    public ResponseEntity<?> update(@PathVariable("postid")
                                        @Schema(description = "게시글id", example = "1")
                                        Long postId,
                                    @RequestBody SeekingPostReq seekingPostReq) {
        try {
            seekingPostService.updateSeekingPost(postId, seekingPostReq);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구직 게시글 삭제", description = "게시글id에 해당하는 구직 게시글을 삭제")
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
