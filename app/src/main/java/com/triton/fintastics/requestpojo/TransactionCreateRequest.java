package com.triton.fintastics.requestpojo;

public class TransactionCreateRequest {

    /**
     * transaction_date : 23-10-2021 11:00 AM
     * transaction_type : Cash
     * transaction_desc : Salary
     * transaction_way : Credit
     * transaction_amount : 2000
     * transaction_balance  : 2000
     * user_id : 617a7c37eeb3a520395e2f15
     * parent_code :
     */

    private String transaction_date;
    private String transaction_type;
    private String transaction_desc;
    private String transaction_way;
    private int transaction_amount;
    private int transaction_balance ;
    private String user_id;
    private String parent_code;

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
}
