package com.triton.fintastics.requestpojo;

public class LoginRequest {

    /**
     * user_email : iddineshkumar@gmail.com
     * password : 123456
     */

    private String user_email;
    private String password;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
