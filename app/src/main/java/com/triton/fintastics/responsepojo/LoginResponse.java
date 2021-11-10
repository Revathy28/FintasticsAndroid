package com.triton.fintastics.responsepojo;

public class LoginResponse {

    /**
     * Status : Success
     * Message : Logged in successfully
     * Data : {"_id":"618230269dcc2a290e5bae9a","username":"sriram","password":"654321","user_email":"sriramchanr@gmail.com","first_name":"","last_name":"","dob":"","contact_number":"","fb_token":"","mobile_type":"IOS","delete_status":false,"account_type":"Family","roll_type":"Admin","parent_code":"185779","parent_of":"","profile_img":"","updatedAt":"2021-11-03T06:45:58.980Z","createdAt":"2021-11-03T06:45:58.980Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * _id : 618230269dcc2a290e5bae9a
     * username : sriram
     * password : 654321
     * user_email : sriramchanr@gmail.com
     * first_name :
     * last_name :
     * dob :
     * contact_number :
     * fb_token :
     * mobile_type : IOS
     * delete_status : false
     * account_type : Family
     * roll_type : Admin
     * parent_code : 185779
     * parent_of :
     * profile_img :
     * updatedAt : 2021-11-03T06:45:58.980Z
     * createdAt : 2021-11-03T06:45:58.980Z
     * __v : 0
     */

    private DataBean Data;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class DataBean {
        private String _id;
        private String username;
        private String password;
        private String user_email;
        private String first_name;
        private String last_name;
        private String dob;
        private String contact_number;
        private String fb_token;
        private String mobile_type;
        private boolean delete_status;
        private String account_type;
        private String roll_type;
        private String parent_code;
        private String parent_of;
        private String profile_img;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getFb_token() {
            return fb_token;
        }

        public void setFb_token(String fb_token) {
            this.fb_token = fb_token;
        }

        public String getMobile_type() {
            return mobile_type;
        }

        public void setMobile_type(String mobile_type) {
            this.mobile_type = mobile_type;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }

        public String getRoll_type() {
            return roll_type;
        }

        public void setRoll_type(String roll_type) {
            this.roll_type = roll_type;
        }

        public String getParent_code() {
            return parent_code;
        }

        public void setParent_code(String parent_code) {
            this.parent_code = parent_code;
        }

        public String getParent_of() {
            return parent_of;
        }

        public void setParent_of(String parent_of) {
            this.parent_of = parent_of;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
