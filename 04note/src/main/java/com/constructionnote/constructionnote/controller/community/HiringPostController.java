package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostReq;
import com.constructionnote.constructionnote.api.response.community.HiringPostRes;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.dto.community.StateConstants;
import com.constructionnote.constructionnote.service.community.HiringPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "구인구직 게시판 구인 검색", description = "구인 게시글을 조건에 따라 검색")
    @GetMapping("")
    public ResponseEntity<?> searchHiringPostList(@RequestParam(required = false, defaultValue = "0", value = "page")
                                                    @Schema(description = "페이지 번호", example = "0")
                                                    Integer page,
                                                @RequestParam(required = false, defaultValue = "1111010100", value = "fullCode")
                                                    @Schema(description = "법정동 코드", example = "1111010100")
                                                    String fullCode,
                                                @RequestParam(required = false, defaultValue = "2", value = "distance")
                                                    @Schema(description = "범위", example = "2")
                                                      Double distance,
                                                @RequestParam(required = false, defaultValue = "", value = "keyword")
                                                    @Schema(description = "검색어", example = "")
                                                    String keyword,
                                                @RequestParam(required = false, defaultValue = StateConstants.ALL)
                                                     @Schema(description = "모두 보기(ALL)/구인중(TRUE)/구인 완료(FALSE)", example = "ALL")
                                                     String state) {
        try {
            return new ResponseEntity<List<PostDto>>(hiringPostService.viewHiringPostsByFilter(page, fullCode, distance, keyword, state), HttpStatus.OK);
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
