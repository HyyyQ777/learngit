package com.xihongshi.expert.controller;

import com.xihongshi.expert.common.Result;
import com.xihongshi.expert.dto.SaveDraftRequest;
import com.xihongshi.expert.entity.Application;
import com.xihongshi.expert.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/save-draft")
    public Result<?> saveDraft(@RequestBody SaveDraftRequest request, @RequestAttribute("userId") Long userId) {
        try {
            Application application = applicationService.saveDraft(userId, request);
            return Result.success(application);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/submit")
    public Result<?> submit(@RequestAttribute("userId") Long userId) {
        try {
            Application application = applicationService.submitApplication(userId);
            return Result.success(application);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my")
    public Result<?> getMyApplication(@RequestAttribute("userId") Long userId) {
        Application application = applicationService.getMyApplication(userId);
        return Result.success(application);
    }

    @GetMapping("/detail/{id}")
    public Result<?> getDetail(@PathVariable Long id) {
        Application application = applicationService.getApplicationDetail(id);
        if (application == null) {
            return Result.error("报名记录不存在");
        }
        return Result.success(application);
    }
}
