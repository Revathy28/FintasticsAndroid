package com.triton.fintastics.requestpojo;

public class BlockUnblockRequest {

    /**
     * user_id : 618230269dcc2a290e5bae9a
     * delete_status : true
     */

    private String user_id;
    private boolean delete_status;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isDelete_status() {
        return delete_status;
    }

    public void setDelete_status(boolean delete_status) {
        this.delete_status = delete_status;
    }
}
