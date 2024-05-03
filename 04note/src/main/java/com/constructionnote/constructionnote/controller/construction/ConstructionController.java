package com.constructionnote.constructionnote.controller.construction;

import com.constructionnote.constructionnote.api.request.construction.ConstructionReq;
import com.constructionnote.constructionnote.api.response.construction.ConstructionRes;
import com.constructionnote.constructionnote.service.construction.ConstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/construction")
public class ConstructionController {

    private final ConstructionService constructionService;

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody ConstructionReq constructionReq) {
        try {
            Long constructionId = constructionService.registerConstruction(constructionReq);
            return new ResponseEntity<>(constructionId, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{constructionid}")
    public ResponseEntity<?> view(@PathVariable("constructionid") Long constructionId) {
        try {
            return new ResponseEntity<ConstructionRes>(constructionService.viewConstruction(constructionId), HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/{constructionid}")
    public ResponseEntity<?> update(@PathVariable("constructionid") Long constructionId, @RequestBody ConstructionReq constructionReq) {
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