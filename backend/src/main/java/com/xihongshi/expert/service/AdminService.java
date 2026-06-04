package com.xihongshi.expert.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xihongshi.expert.common.Constants;
import com.xihongshi.expert.entity.Application;
import com.xihongshi.expert.entity.AuditRecord;
import com.xihongshi.expert.entity.User;
import com.xihongshi.expert.mapper.ApplicationMapper;
import com.xihongshi.expert.mapper.AuditRecordMapper;
import com.xihongshi.expert.mapper.UserMapper;
import com.xihongshi.expert.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private AuditRecordMapper auditRecordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileUtil fileUtil;

    public Page<Application> getApplications(int page, int size, String keyword, String status) {
        Page<Application> pageObj = new Page<>(page, size);
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();

        if (status != null && !status.isEmpty() && !status.equals("all")) {
            wrapper.eq(Application::getStatus, status);
        }
        wrapper.orderByDesc(Application::getCreatedAt);
        return applicationMapper.selectPage(pageObj, wrapper);
    }

    public Application getApplicationDetail(Long id) {
        return applicationMapper.selectById(id);
    }

    public void auditApplication(Long id, String status, String comment, String auditorRole, Long auditorId) {
        Application application = applicationMapper.selectById(id);
        if (application == null) {
            throw new RuntimeException("报名记录不存在");
        }

        User auditor = userMapper.selectById(auditorId);
        application.setStatus(status);
        applicationMapper.updateById(application);

        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setApplicationId(id);
        auditRecord.setAuditorName(auditor != null ? auditor.getName() : "未知");
        auditRecord.setAuditorRole(auditorRole);
        auditRecord.setStatus(status);
        auditRecord.setComment(comment);
        auditRecordMapper.insert(auditRecord);
    }

    public String uploadFile(MultipartFile file, String prefix) throws IOException {
        return fileUtil.uploadFile(file, prefix);
    }
}
