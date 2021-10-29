package com.triton.fintastics.responsepojo;

public class EmailOTPResponse {

    /**
     * Status : Success
     * Message : Eamil id sent successfully
     * Data : {"email_id":"mohammedimthi2395@gmail.com","otp":"020198"}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * email_id : mohammedimthi2395@gmail.com
     * otp : 020198
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
        private String email_id;
        private String otp;

        public String getEmail_id() {
            return email_id;
        }

        public void setEmail_id(String email_id) {
            this.email_id = email_id;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }
}
