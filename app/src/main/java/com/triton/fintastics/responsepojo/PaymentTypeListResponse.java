package com.triton.fintastics.responsepojo;

import java.util.List;

public class PaymentTypeListResponse {

    /**
     * Status : Success
     * Message : payment type Details
     * Data : [{"_id":"617a9f0e8b392d379a26fafc","payment_type":"Cash","delete_status":false,"updatedAt":"2021-10-28T13:01:02.409Z","createdAt":"2021-10-28T13:01:02.409Z","__v":0},{"_id":"617a9f158b392d379a26fafe","payment_type":"Card","delete_status":false,"updatedAt":"2021-10-28T13:01:09.552Z","createdAt":"2021-10-28T13:01:09.552Z","__v":0},{"_id":"617a9f1e8b392d379a26fb00","payment_type":"GPay","delete_status":false,"updatedAt":"2021-10-28T13:01:18.171Z","createdAt":"2021-10-28T13:01:18.171Z","__v":0},{"_id":"617a9f248b392d379a26fb02","payment_type":"PhonePay","delete_status":false,"updatedAt":"2021-10-28T13:01:24.219Z","createdAt":"2021-10-28T13:01:24.219Z","__v":0},{"_id":"617a9f2b8b392d379a26fb04","payment_type":"EMI Card","delete_status":false,"updatedAt":"2021-10-28T13:01:31.894Z","createdAt":"2021-10-28T13:01:31.894Z","__v":0}]
     * desc_type : [{"_id":"618a36842ad9d959d8848471","desc_type":"Cash Desk","date_and_time":"23-10-2021 11:00 AM","delete_status":false,"updatedAt":"2021-11-09T08:51:16.152Z","createdAt":"2021-11-09T08:51:16.152Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 617a9f0e8b392d379a26fafc
     * payment_type : Cash
     * delete_status : false
     * updatedAt : 2021-10-28T13:01:02.409Z
     * createdAt : 2021-10-28T13:01:02.409Z
     * __v : 0
     */

    private List<DataBean> Data;
    /**
     * _id : 618a36842ad9d959d8848471
     * desc_type : Cash Desk
     * date_and_time : 23-10-2021 11:00 AM
     * delete_status : false
     * updatedAt : 2021-11-09T08:51:16.152Z
     * createdAt : 2021-11-09T08:51:16.152Z
     * __v : 0
     */

    private List<DescTypeBean> desc_type;

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

    public List<DescTypeBean> getDesc_type() {
        return desc_type;
    }

    public void setDesc_type(List<DescTypeBean> desc_type) {
        this.desc_type = desc_type;
    }

    public static class DataBean {
        private String _id;
        private String payment_type;
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

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
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

    public static class DescTypeBean {
        private String _id;
        private String desc_type;
        private String date_and_time;
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

        public String getDesc_type() {
            return desc_type;
        }

        public void setDesc_type(String desc_type) {
            this.desc_type = desc_type;
        }

        public String getDate_and_time() {
            return date_and_time;
        }

        public void setDate_and_time(String date_and_time) {
            this.date_and_time = date_and_time;
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
