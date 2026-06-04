package com.xihongshi.expert.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xihongshi.expert.common.Constants;
import com.xihongshi.expert.common.Result;
import com.xihongshi.expert.dto.AuditRequest;
import com.xihongshi.expert.entity.Application;
import com.xihongshi.expert.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/applications")
    public Result<?> getApplications(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestAttribute("userId") Long userId,
            @RequestAttribute("role") String role) {
        if (!Constants.ROLE_ADMIN.equals(role) && !Constants.ROLE_COACH.equals(role)) {
            return Result.error(403, "权限不足");
        }
        Page<Application> applications = adminService.getApplications(page, size, keyword, status);
        return Result.success(applications);
    }

    @GetMapping("/application/{id}")
    public Result<?> getApplicationDetail(
            @PathVariable Long id,
            @RequestAttribute("role") String role) {
        if (!Constants.ROLE_ADMIN.equals(role) && !Constants.ROLE_COACH.equals(role)) {
            return Result.error(403, "权限不足");
        }
        Application application = adminService.getApplicationDetail(id);
        if (application == null) {
            return Result.error("报名记录不存在");
        }
        return Result.success(application);
    }

    @PostMapping("/application/{id}/audit")
    public Result<?> auditApplication(
            @PathVariable Long id,
            @Valid @RequestBody AuditRequest request,
            @RequestAttribute("userId") Long userId,
            @RequestAttribute("role") String role) {
        if (!Constants.ROLE_ADMIN.equals(role) && !Constants.ROLE_COACH.equals(role)) {
            return Result.error(403, "权限不足");
        }
        try {
            adminService.auditApplication(id, request.getStatus(), request.getComment(), role, userId);
            return Result.success("审核完成");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/upload/signature")
    public Result<?> uploadSignature(
            @RequestParam("file") MultipartFile file,
            @RequestAttribute("role") String role) {
        if (!Constants.ROLE_ADMIN.equals(role) && !Constants.ROLE_COACH.equals(role)) {
            return Result.error(403, "权限不足");
        }
        try {
            String url = adminService.uploadFile(file, "sig");
            return Result.success(url);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
