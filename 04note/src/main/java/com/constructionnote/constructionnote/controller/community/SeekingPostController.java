package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.SeekingPostReq;
import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.dto.community.StateConstants;
import com.constructionnote.constructionnote.service.community.SeekingPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "구인구직 게시판 구직 검색", description = "구직 게시글을 조건에 따라 검색")
    @GetMapping("")
    public ResponseEntity<?> searchSeekingPostList(@RequestParam(required = false, defaultValue = "0", value = "page")
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
                                                  @Schema(description = "모두 보기(ALL)/구직중(TRUE)/구직중 아님(FALSE)", example = "ALL")
                                                  String state) {
        try {
            return new ResponseEntity<List<PostDto>>(seekingPostService.viewSeekingPostsByFilter(page, fullCode, distance, keyword, state), HttpStatus.OK);
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
