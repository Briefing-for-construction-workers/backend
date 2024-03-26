package com.constructionnote.constructionnote.service;

import com.constructionnote.constructionnote.api.request.ConstructionReq;
import com.constructionnote.constructionnote.entity.Construction;
import com.constructionnote.constructionnote.entity.ConstructionUser;
import com.constructionnote.constructionnote.entity.User;
import com.constructionnote.constructionnote.repository.ConstructionRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .clientName(constructionReq.getClient().getClientName())
                .phoneNumber(constructionReq.getClient().getPhoneNumber())
                .income(constructionReq.getAccount().getIncome())
                .cost(constructionReq.getAccount().getCost())
                .moneyGiven(constructionReq.getAccount().isMoneyGiven())
                .moneyReceived(constructionReq.getAccount().isMoneyReceived())
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
}
