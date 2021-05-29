package com.kj.springsecurity.user;

public enum Role {
    ROLE_USER ("uzytkownik"),
    ROLE_ADMIN ("admin");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
