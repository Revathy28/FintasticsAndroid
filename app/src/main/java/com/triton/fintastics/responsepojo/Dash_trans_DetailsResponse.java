package com.triton.fintastics.responsepojo;

import java.util.List;

public class Dash_trans_DetailsResponse {

    private String Status;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
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
        private String transaction_date;
        private String transaction_way;
        private String transaction_amount;
        private String transaction_balance;
        private String createdAt;
        private String transaction_description;

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

        public String getTransaction_way() {
            return transaction_way;
        }

        public void setTransaction_way(String transaction_way) {
            this.transaction_way = transaction_way;
        }

        public String getTransaction_amount() {
            return transaction_amount;
        }

        public void setTransaction_amount(String transaction_amount) {
            this.transaction_amount = transaction_amount;
        }

        public String getTransaction_balance() {
            return transaction_balance;
        }

        public void setTransaction_balance(String transaction_balance) {
            this.transaction_balance = transaction_balance;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getTransaction_description() {
            return transaction_description;
        }

        public void setTransaction_description(String transaction_description) {
            this.transaction_description = transaction_description;
        }
    }
}
