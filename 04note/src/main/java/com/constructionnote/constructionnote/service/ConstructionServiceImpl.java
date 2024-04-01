package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.ConstructionReq;
import com.constructionnote.constructionnote.api.response.ConstructionRes;
import com.constructionnote.constructionnote.dto.construction.*;
import com.constructionnote.constructionnote.entity.Construction;
import com.constructionnote.constructionnote.entity.ConstructionUser;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.ConstructionRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Transactional
@Service
@RequiredArgsConstructor
public class ConstructionServiceImpl implements ConstructionService {
    private final ConstructionRepository constructionRepository;
    private final UserRepository userRepository;

    @Override
    public void registerConstruction(ConstructionReq constructionReq) {
        Construction construction = Construction.builder()
                .kind(constructionReq.getKind())
                .timeBegin(constructionReq.getSchedule().getTimeBegin())
                .timeEnd(constructionReq.getSchedule().getTimeEnd())
                .city(constructionReq.getConstructionSite().getAddress().getCity())
                .district(constructionReq.getConstructionSite().getAddress().getDistrict())
                .dong(constructionReq.getConstructionSite().getAddress().getDong())
                .workSiteDescription(constructionReq.getConstructionSite().getWorkSiteDescription())
                .memo(constructionReq.getMemo())
                .build();

        User user = userRepository.findById(constructionReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        ConstructionUser constructionUser = ConstructionUser.builder()
                .user(user)
                .construction(construction)
                .build();

        construction.addConstructionUser(constructionUser);

        constructionRepository.save(construction);
    }

    @Override
    public ConstructionRes viewConstruction(Long constructionId) {
        Construction construction = constructionRepository.findById(constructionId)
                .orElseThrow(() -> new IllegalArgumentException("construction doesn't exist"));

        //상태 구하기
        Date timeBegin = construction.getTimeBegin();
        Date timeEnd = construction.getTimeEnd();

        StatusType statusType = getStatus(timeBegin, timeEnd);

        return ConstructionRes.builder()
                .statusType(statusType)
                .kind(construction.getKind())
                .schedule(Schedule.createSchedule(timeBegin, timeEnd))
                .constructionSite(ConstructionSite.createConstructionSite(construction.getCity(), construction.getDistrict(), construction.getDong(), construction.getWorkSiteDescription()))
                .memo(construction.getMemo())
                .build();
    }

    private StatusType getStatus(Date timeBegin, Date timeEnd) {
        java.util.Date utilDate = new java.util.Date();
        long currentMilliseconds = utilDate.getTime();
        java.sql.Date curDate = new java.sql.Date(currentMilliseconds);

        if(curDate.before(timeBegin)) {
            return StatusType.SCHEDULED;
        } else if(timeBegin.before(curDate) && timeEnd.after(curDate)){
            return StatusType.IN_PROGRESS;
        } else {
            return StatusType.FINISHED;
        }
    }
}
