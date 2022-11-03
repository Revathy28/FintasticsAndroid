package com.triton.fintastics.requestpojo;

public class SignupRequest {

    /**
     * username : 6172b83d8dd3e15b142de045
     * password : 20-20-2021
     * user_email : mohammedimthi2395@gmail.com
     * mobile_type : Android
     * parent_of : 133we
     */



    private String username;
    private String password;
    private String user_email;
    private String mobile_type;
    private String parent_of;
    private String account_type ;
    private String currency;
    private String symbol;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getMobile_type() {
        return mobile_type;
    }

    public void setMobile_type(String mobile_type) {
        this.mobile_type = mobile_type;
    }

    public String getParent_of() {
        return parent_of;
    }

    public void setParent_of(String parent_of) {
        this.parent_of = parent_of;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSymbol(){return symbol;}

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
