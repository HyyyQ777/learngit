package com.xihongshi.expert.dto;

import lombok.Data;
import java.util.Map;

@Data
public class SaveDraftRequest {
    private Map<String, Object> basicInfo;
    private Map<String, Object> detailedInfo;
    private Object competitionExperiences;
    private Object honors;
}
