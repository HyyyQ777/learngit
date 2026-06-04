package com.xihongshi.expert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("applications")
public class Application {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String applicationNo;
    @TableField("basic_info")
    private String basicInfo;
    @TableField("detailed_info")
    private String detailedInfo;
    @TableField("competition_experiences")
    private String competitionExperiences;
    @TableField("honors")
    private String honors;
    private String signatureUrl;
    private String sealUrl;
    private String status;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
