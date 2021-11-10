package com.triton.fintastics.responsepojo;

import java.util.List;

public class DashboardDataResponse {

    /**
     * Status : Success
     * Message : Filter dashboard
     * Data : [{"_id":"618234a0ecb5dd2ce6542c40","transaction_date":"02-11-2021 12:35 PM","transaction_type":"Card","transaction_desc":"card","transaction_way":"Credit","transaction_amount":500,"transaction_balance":500,"system_date":"2021-11-02T00:00:00.000Z","user_id":"618230269dcc2a290e5bae9a","parent_code":"185779","delete_status":false,"updatedAt":"2021-11-03T07:05:04.622Z","createdAt":"2021-11-03T07:05:04.622Z","__v":0}]
     * balance : {"Credit_amount":500,"Debit_amount":0,"Balance_amount":500}
     * user_count : {"child_count":1,"admin_count":1,"total_count":2}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * Credit_amount : 500
     * Debit_amount : 0
     * Balance_amount : 500
     */

    private BalanceBean balance;
    /**
     * child_count : 1
     * admin_count : 1
     * total_count : 2
     */

    private UserCountBean user_count;
    private int Code;
    /**
     * _id : 618234a0ecb5dd2ce6542c40
     * transaction_date : 02-11-2021 12:35 PM
     * transaction_type : Card
     * transaction_desc : card
     * transaction_way : Credit
     * transaction_amount : 500
     * transaction_balance : 500
     * system_date : 2021-11-02T00:00:00.000Z
     * user_id : 618230269dcc2a290e5bae9a
     * parent_code : 185779
     * delete_status : false
     * updatedAt : 2021-11-03T07:05:04.622Z
     * createdAt : 2021-11-03T07:05:04.622Z
     * __v : 0
     */

    private List<DataBean> Data;

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

    public BalanceBean getBalance() {
        return balance;
    }

    public void setBalance(BalanceBean balance) {
        this.balance = balance;
    }

    public UserCountBean getUser_count() {
        return user_count;
    }

    public void setUser_count(UserCountBean user_count) {
        this.user_count = user_count;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class BalanceBean {
        private int Credit_amount;
        private int Debit_amount;
        private int Balance_amount;

        public int getCredit_amount() {
            return Credit_amount;
        }

        public void setCredit_amount(int Credit_amount) {
            this.Credit_amount = Credit_amount;
        }

        public int getDebit_amount() {
            return Debit_amount;
        }

        public void setDebit_amount(int Debit_amount) {
            this.Debit_amount = Debit_amount;
        }

        public int getBalance_amount() {
            return Balance_amount;
        }

        public void setBalance_amount(int Balance_amount) {
            this.Balance_amount = Balance_amount;
        }
    }

    public static class UserCountBean {
        private int child_count;
        private int admin_count;
        private int total_count;

        public int getChild_count() {
            return child_count;
        }

        public void setChild_count(int child_count) {
            this.child_count = child_count;
        }

        public int getAdmin_count() {
            return admin_count;
        }

        public void setAdmin_count(int admin_count) {
            this.admin_count = admin_count;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
    }

    public static class DataBean {
        private String _id;
        private String transaction_date;
        private String transaction_type;
        private String transaction_desc;
        private String transaction_way;
        private int transaction_amount;
        private int transaction_balance;
        private String system_date;
        private String user_id;
        private String parent_code;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTransaction_date() {
            return transaction_date;
        }

        public void setTransaction_date(String transaction_date) {
            this.transaction_date = transaction_date;
        }

        public String getTransaction_type() {
            return transaction_type;
        }

        public void setTransaction_type(String transaction_type) {
            this.transaction_type = transaction_type;
        }

        public String getTransaction_desc() {
            return transaction_desc;
        }

        public void setTransaction_desc(String transaction_desc) {
            this.transaction_desc = transaction_desc;
        }

        public String getTransaction_way() {
            return transaction_way;
        }

        public void setTransaction_way(String transaction_way) {
            this.transaction_way = transaction_way;
        }

        public int getTransaction_amount() {
            return transaction_amount;
        }

        public void setTransaction_amount(int transaction_amount) {
            this.transaction_amount = transaction_amount;
        }

        public int getTransaction_balance() {
            return transaction_balance;
        }

        public void setTransaction_balance(int transaction_balance) {
            this.transaction_balance = transaction_balance;
        }

        public String getSystem_date() {
            return system_date;
        }

        public void setSystem_date(String system_date) {
            this.system_date = system_date;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getParent_code() {
            return parent_code;
        }

        public void setParent_code(String parent_code) {
            this.parent_code = parent_code;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
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
