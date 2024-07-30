package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostRes;
import com.constructionnote.constructionnote.service.community.HiringPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "구인 게시글 컨트롤러", description = "구인 게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringPostController {
    private final HiringPostService hiringPostService;

    @Operation(summary = "구인 게시글 등록", description = "구인 게시글을 등록")
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody HiringPostReq hiringPostReq) {
        try {
            Long hiringPostId = hiringPostService.registerHiringPost(hiringPostReq);
            return new ResponseEntity<>(hiringPostId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 게시글 조회", description = "게시글id에 해당하는 구인 게시글을 조회")
    @GetMapping("/{postid}")
    public ResponseEntity<?> view(@PathVariable("postid")
                                      @Schema(description = "게시글id", example = "1")
                                      Long postId) {
        try {
            return new ResponseEntity<HiringPostRes>(hiringPostService.viewHiringPostById(postId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 게시글 수정", description = "게시글id에 해당하는 구인 게시글을 수정")
    @PutMapping("/{postid}")
    public ResponseEntity<?> update(@PathVariable("postid")
                                        @Schema(description = "게시글id", example = "1")
                                        Long postId,
                                    @RequestBody HiringPostReq hiringPostReq) {
        try {
            hiringPostService.updateHiringPost(postId, hiringPostReq);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 게시글 삭제", description = "게시글id에 해당하는 구인 게시글을 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId")
                                        @Schema(description = "게시글id", example = "1")
                                        Long postId) {
        try {
            hiringPostService.deleteHiringPost(postId);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 게시글 모집 상태 변경", description = "게시글id에 해당하는 구인 게시글을 모집 상태를 변경")
    @PutMapping("/{postid}/complete")
    public ResponseEntity<?> updateState(@PathVariable("postid")
                                    @Schema(description = "게시글id", example = "1")
                                    Long postId) {
        try {
            hiringPostService.updateHiringState(postId);
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
