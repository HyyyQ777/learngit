package com.xihongshi.expert.common;

public class Constants {
    public static final String ROLE_EXPERT = "expert";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_COACH = "coach";

    public static final String STATUS_DRAFT = "draft";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_REVIEWING = "reviewing";
    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_REJECTED = "rejected";

    public static final String AUDIT_PRIMARY = "primary";
    public static final String AUDIT_SECONDARY = "secondary";
    public static final String AUDIT_FINAL = "final";

    public static final String[] ALLOWED_IMAGE_TYPES = {"jpg", "jpeg", "png", "gif"};
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
}
