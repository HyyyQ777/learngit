package com.xihongshi.expert.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xihongshi.expert.common.Constants;
import com.xihongshi.expert.dto.SaveDraftRequest;
import com.xihongshi.expert.entity.Application;
import com.xihongshi.expert.mapper.ApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public Application saveDraft(Long userId, SaveDraftRequest request) throws JsonProcessingException {
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId)
                .eq(Application::getStatus, Constants.STATUS_DRAFT);
        Application application = applicationMapper.selectOne(wrapper);

        if (application == null) {
            application = new Application();
            application.setUserId(userId);
            application.setApplicationNo(generateApplicationNo());
            application.setStatus(Constants.STATUS_DRAFT);
        }

        application.setBasicInfo(objectMapper.writeValueAsString(request.getBasicInfo()));
        application.setDetailedInfo(objectMapper.writeValueAsString(request.getDetailedInfo()));
        application.setCompetitionExperiences(objectMapper.writeValueAsString(request.getCompetitionExperiences()));
        application.setHonors(objectMapper.writeValueAsString(request.getHonors()));

        if (application.getId() == null) {
            applicationMapper.insert(application);
        } else {
            applicationMapper.updateById(application);
        }
        return application;
    }

    public Application submitApplication(Long userId) {
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId)
                .eq(Application::getStatus, Constants.STATUS_DRAFT);
        Application application = applicationMapper.selectOne(wrapper);

        if (application == null) {
            throw new RuntimeException("请先保存报名信息");
        }

        application.setStatus(Constants.STATUS_PENDING);
        application.setSubmittedAt(LocalDateTime.now());
        applicationMapper.updateById(application);
        return application;
    }

    public Application getMyApplication(Long userId) {
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getUserId, userId)
                .orderByDesc(Application::getCreatedAt)
                .last("LIMIT 1");
        return applicationMapper.selectOne(wrapper);
    }

    public Application getApplicationDetail(Long id) {
        return applicationMapper.selectById(id);
    }

    private String generateApplicationNo() {
        return "XHSZJ" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
