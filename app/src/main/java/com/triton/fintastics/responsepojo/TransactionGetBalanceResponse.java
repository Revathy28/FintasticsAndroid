package com.triton.fintastics.responsepojo;

public class TransactionGetBalanceResponse {

    /**
     * Status : Success
     * Message : Total Available Balance
     * Data : {"Credit_amount":29000,"Debit_amount":22082,"Balance_amount":6918}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * Credit_amount : 29000
     * Debit_amount : 22082
     * Balance_amount : 6918
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
}
