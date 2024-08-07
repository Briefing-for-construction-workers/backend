package com.constructionnote.constructionnote.service.user;

import com.constructionnote.constructionnote.api.request.user.UserReq;
import com.constructionnote.constructionnote.api.response.user.UserProfileRes;
import com.constructionnote.constructionnote.component.S3FileStore;
import com.constructionnote.constructionnote.dto.user.FileDto;
import com.constructionnote.constructionnote.entity.*;
import com.constructionnote.constructionnote.repository.AddressRepository;
import com.constructionnote.constructionnote.repository.SkillRepository;
import com.constructionnote.constructionnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final AddressRepository addressRepository;

    private final S3FileStore s3FileStore;

    @Override
    public void signUp(UserReq userReq, MultipartFile image) throws Exception {
        User user = User.builder()
                .id(userReq.getUserId())
                .level(userReq.getLevel())
                .build();

        String imageUrl = null;
        String fileName = null;

        if(!image.isEmpty()) {
            FileDto fileDto = s3FileStore.storeFile(image);
            imageUrl = fileDto.getImageUrl();
            fileName = fileDto.getFileName();
        }

        Profile profile = Profile.builder()
                .nickname(userReq.getNickname())
                .imageUrl(imageUrl)
                .fileName(fileName)
                .build();

        user.putProfile(profile)    ;

        if(userReq.getSkills() != null) {
            for(String skillName : userReq.getSkills()) {
                Skill skill = skillRepository.findByName(skillName).orElse(null);

                if(skill == null) { //Skill 테이블에 없으면 해당 스킬 저장
                    Skill skillTmp = Skill.builder()
                            .name(skillName)
                            .build();

                    skill = skillRepository.save(skillTmp);
                }

                UserSkill userSkill = UserSkill.builder()
                        .user(user)
                        .skill(skill)
                        .build();

                user.addUserSkill(userSkill);
            }
        }

        Address address = addressRepository.findById(userReq.getFullCode())
                .orElseThrow(() -> new IllegalArgumentException("addressCode doesn't exist"));

        user.putAddress(address);

        userRepository.save(user);
    }

    @Override
    public boolean exist(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null;
    }

    @Override
    public void updateUserProfile(UserReq userReq, MultipartFile image) throws Exception {
        String userId = userReq.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        user.updateUser(userReq.getLevel());

        if(user.getProfile().getFileName() != null) {
            s3FileStore.deleteFile(user.getProfile().getFileName());
        }

        String imageUrl = null;
        String fileName = null;

        if(!image.isEmpty()) {
            FileDto fileDto = s3FileStore.storeFile(image);
            imageUrl = fileDto.getImageUrl();
            fileName = fileDto.getFileName();
        }

        Profile profile = user.getProfile();
        profile.updateProfile(userReq.getNickname(), imageUrl, fileName);

        if(userReq.getSkills() != null) {
            for(String skillName : userReq.getSkills()) {
                Skill skill = skillRepository.findByName(skillName).orElse(null);

                if(skill == null) { //Skill 테이블에 없으면 해당 스킬 저장
                    Skill skillTmp = Skill.builder()
                            .name(skillName)
                            .build();

                    skill = skillRepository.save(skillTmp);
                }

                UserSkill userSkill = UserSkill.builder()
                        .user(user)
                        .skill(skill)
                        .build();

                user.addUserSkill(userSkill);
            }
        }

        Address address = addressRepository.findById(userReq.getFullCode())
                .orElseThrow(() -> new IllegalArgumentException("addressCode doesn't exist"));

        user.putAddress(address);

        userRepository.save(user);
    }

    @Override
    public UserProfileRes getUserProfile(String userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user doesn't exist"));

        String imageUrl = user.getProfile().getImageUrl();

        return UserProfileRes.builder()
                .nickname(user.getProfile().getNickname())
                .imageUrl(imageUrl)
                .build();
    }
}
