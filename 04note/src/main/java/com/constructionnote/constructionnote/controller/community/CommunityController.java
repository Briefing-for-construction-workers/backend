package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.dto.community.PostDto;
import com.constructionnote.constructionnote.service.community.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "커뮤니티 메인 컨트롤러", description = "커뮤니티 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityService communityService;

    @Operation(summary = "구인구직 게시판 전체 검색", description = "구인구직 게시글을 조건에 따라 검색")
    @GetMapping("/post")
    public ResponseEntity<?> searchPostList(@RequestParam(required = false, defaultValue = "0", value = "page")
                                                @Schema(description = "페이지 번호", example = "0")
                                                Integer page,
                                            @RequestParam(required = false, defaultValue = "1111010100", value = "fullCode")
                                                @Schema(description = "법정동 코드", example = "1111010100")
                                                String fullCode,
                                            @RequestParam(required = false, defaultValue = "", value = "keyword")
                                                @Schema(description = "검색어", example = "")
                                                String keyword) {
        try {
            return new ResponseEntity<List<PostDto>>(communityService.viewPostListByFilter(page, fullCode, keyword), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
