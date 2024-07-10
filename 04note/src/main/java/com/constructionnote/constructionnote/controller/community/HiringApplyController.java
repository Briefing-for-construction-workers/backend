package com.constructionnote.constructionnote.controller.community;

import com.constructionnote.constructionnote.api.request.community.HiringPostApplyReq;
import com.constructionnote.constructionnote.service.community.HiringPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "구인 지원 컨트롤러", description = "구인 지원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hiring")
public class HiringApplyController {
    private final HiringPostService hiringPostService;

    @Operation(summary = "구인 게시글 지원", description = "구인 게시글에 지원")
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody HiringPostApplyReq hiringPostApplyReq) {
        try {
            Long hiringPostApplyId = hiringPostService.applyHiringPost(hiringPostApplyReq);
            return new ResponseEntity<>(hiringPostApplyId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "구인 성사 등록", description = "구인 성사를 등록")
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
