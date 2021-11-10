package com.triton.fintastics.requestpojo;

public class DashboardDataRequest {

    /**
     * transaction_type : Cash
     * transaction_way : Debit
     * user_id : 617a7c37eeb3a520395e2f15
     * start_date : 23-10-2021
     * end_date : 23-10-2021
     */

    private String transaction_type;
    private String transaction_way;
    private String user_id;
    private String start_date;
    private String end_date;

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_way() {
        return transaction_way;
    }

    public void setTransaction_way(String transaction_way) {
        this.transaction_way = transaction_way;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
