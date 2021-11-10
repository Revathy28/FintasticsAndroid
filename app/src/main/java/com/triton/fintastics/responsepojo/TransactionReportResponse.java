package com.triton.fintastics.responsepojo;

import java.util.List;

public class TransactionReportResponse {

    /**
     * Status : Success
     * Message : Transaction Data
     * Data : [{"_id":"2021-10-31T00:00:00.000Z","price":5000,"date":"2021-10-31","credeit_amount":0,"debit_amount":5000}]
     * total_count : {"total_credit_value":0,"total_debit_value":5000,"available_balance":-5000}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * total_credit_value : 0
     * total_debit_value : 5000
     * available_balance : -5000
     */

    private TotalCountBean total_count;
    private int Code;
    /**
     * _id : 2021-10-31T00:00:00.000Z
     * price : 5000
     * date : 2021-10-31
     * credeit_amount : 0
     * debit_amount : 5000
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

    public TotalCountBean getTotal_count() {
        return total_count;
    }

    public void setTotal_count(TotalCountBean total_count) {
        this.total_count = total_count;
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

    public static class TotalCountBean {
        private int total_credit_value;
        private int total_debit_value;
        private int available_balance;

        public int getTotal_credit_value() {
            return total_credit_value;
        }

        public void setTotal_credit_value(int total_credit_value) {
            this.total_credit_value = total_credit_value;
        }

        public int getTotal_debit_value() {
            return total_debit_value;
        }

        public void setTotal_debit_value(int total_debit_value) {
            this.total_debit_value = total_debit_value;
        }

        public int getAvailable_balance() {
            return available_balance;
        }

        public void setAvailable_balance(int available_balance) {
            this.available_balance = available_balance;
        }
    }

    public static class DataBean {
        private String _id;
        private int price;
        private String date;
        private int credeit_amount;
        private int debit_amount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getCredeit_amount() {
            return credeit_amount;
        }

        public void setCredeit_amount(int credeit_amount) {
            this.credeit_amount = credeit_amount;
        }

        public int getDebit_amount() {
            return debit_amount;
        }

        public void setDebit_amount(int debit_amount) {
            this.debit_amount = debit_amount;
        }
    }
}
