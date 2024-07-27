package com.constructionnote.constructionnote.controller.construction;

import com.constructionnote.constructionnote.api.request.construction.ConstructionReq;
import com.constructionnote.constructionnote.api.response.construction.ConstructionRes;
import com.constructionnote.constructionnote.service.construction.ConstructionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공사 기록 컨트롤러", description = "공사 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/construction")
public class ConstructionController {

    private final ConstructionService constructionService;

    @Operation(summary = "공사 기록 등록", description = "공사 기록을 새로 등록")
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody ConstructionReq constructionReq) {
        try {
            Long constructionId = constructionService.registerConstruction(constructionReq);
            return new ResponseEntity<>(constructionId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "공사 기록 조회", description = "공사id에 해당하는 공사 기록 조회")
    @GetMapping("/{constructionid}")
    public ResponseEntity<?> view(@PathVariable("constructionid")
                                      @Schema(description = "공사id", example = "1")
                                      Long constructionId) {
        try {
            return new ResponseEntity<ConstructionRes>(constructionService.viewConstruction(constructionId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @Operation(summary = "공사 기록 수정", description = "공사id에 해당하는 공사 기록을 수정")
    @PutMapping("/{constructionid}")
    public ResponseEntity<?> update(@PathVariable("constructionid")
                                        @Schema(description = "공사id", example = "1")
                                        Long constructionId,
                                    @RequestBody ConstructionReq constructionReq) {
        try {
            Long id = constructionService.updateConstruction(constructionId, constructionReq);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
