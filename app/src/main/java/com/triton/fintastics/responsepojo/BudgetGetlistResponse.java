package com.triton.fintastics.responsepojo;

import java.util.List;

public class BudgetGetlistResponse {


    /**
     * Status : Success
     * Message : transaction List
     * Data : {"expensive_data":[{"_id":"GPay","price":300,"percentage":0.15},{"_id":"Card","price":50250,"percentage":25.125},{"_id":"EMI Card","price":420,"percentage":0.21},{"_id":"PhonePay","price":360,"percentage":0.18},{"_id":"Cash","price":200,"percentage":0.1}],"income_data":[],"expend_value":51530,"total_income":200000}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * expensive_data : [{"_id":"GPay","price":300,"percentage":0.15},{"_id":"Card","price":50250,"percentage":25.125},{"_id":"EMI Card","price":420,"percentage":0.21},{"_id":"PhonePay","price":360,"percentage":0.18},{"_id":"Cash","price":200,"percentage":0.1}]
     * income_data : []
     * expend_value : 51530
     * total_income : 200000
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
        private int expend_value;
        private int total_income;
        /**
         * _id : GPay
         * price : 300
         * percentage : 0.15
         */

        private List<ExpensiveDataBean> expensive_data;
        private List<?> income_data;

        public int getExpend_value() {
            return expend_value;
        }

        public void setExpend_value(int expend_value) {
            this.expend_value = expend_value;
        }

        public int getTotal_income() {
            return total_income;
        }

        public void setTotal_income(int total_income) {
            this.total_income = total_income;
        }

        public List<ExpensiveDataBean> getExpensive_data() {
            return expensive_data;
        }

        public void setExpensive_data(List<ExpensiveDataBean> expensive_data) {
            this.expensive_data = expensive_data;
        }

        public List<?> getIncome_data() {
            return income_data;
        }

        public void setIncome_data(List<?> income_data) {
            this.income_data = income_data;
        }

        public static class ExpensiveDataBean {
            private String _id;
            private int price;
            private double percentage;

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

            public double getPercentage() {
                return percentage;
            }

            public void setPercentage(double percentage) {
                this.percentage = percentage;
            }
        }
    }
}
