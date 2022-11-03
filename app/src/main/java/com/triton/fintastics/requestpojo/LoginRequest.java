package com.triton.fintastics.requestpojo;

public class LoginRequest {

    /**
     * user_email : iddineshkumar@gmail.com
     * password : 123456
     */

    private String user_email;
    private String password;
    private String account_type;

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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
}
