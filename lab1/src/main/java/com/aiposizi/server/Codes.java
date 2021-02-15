package com.aiposizi.server;

public enum Codes {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NOT_FOUND(404, "Not Found"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private Integer code;
    private String description;

    Codes(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
