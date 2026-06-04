package com.xihongshi.expert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("audit_records")
public class AuditRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long applicationId;
    private String auditorName;
    private String auditorRole;
    private String status;
    private String comment;
    private LocalDateTime createdAt;
}
