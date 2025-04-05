package com.fixable.fixable.dto;

public class ChangePasswordRequest {
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
