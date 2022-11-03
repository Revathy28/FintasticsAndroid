package com.triton.fintastics.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payment_type_listResponse {

    private String Status;
    private String Message;
    private int Code;
    private DataBean Data;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }


    public static class DataBean {

        @SerializedName("currencies")
        @Expose
        private List<CurrenciesDatabeanList> CurrenciesList;

        public List<CurrenciesDatabeanList> getCurrenciesList() {
            return CurrenciesList;
        }

        public void setCurrenciesList(List<CurrenciesDatabeanList> CurrenciesList) {
            this.CurrenciesList = CurrenciesList;
        }

        @SerializedName("payment_types")
        @Expose
        private List<Payment_typesDatabeanList> PaymenttypeList;

        public List<Payment_typesDatabeanList> getPaymenttypeList() {
            return PaymenttypeList;
        }

        public void setPayment_typesDatabeanList(List<Payment_typesDatabeanList> PaymenttypeList) {
            this.PaymenttypeList = PaymenttypeList;
        }

        @SerializedName("desc_types")
        @Expose
        private List<Desc_typesDatabeanList> DesctypeList;

        public List<Desc_typesDatabeanList> getDesctypeList() {
            return DesctypeList;
        }

        public void setDesc_typesDatabeanList(List<Desc_typesDatabeanList> DesctypeList) {
            this.DesctypeList = DesctypeList;
        }


        public class CurrenciesDatabeanList {

            @Expose
            private String currency;
            private String symbol;

            @SerializedName("currency")


            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }
        }


        public class Payment_typesDatabeanList {

            @Expose
            private String payment_type;
            @SerializedName("payment_type")
        //    private String _id;
//            @SerializedName("_id")


            public String getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(String payment_type) {
                this.payment_type = payment_type;
            }

//            public String get_id() {
//                return _id;
//            }
//
//            public void set_id(String _id) {
//                this._id = _id;
//            }
    }



        public class Desc_typesDatabeanList {

            @Expose
            private String desc_type;
            @SerializedName("desc_type")
//            private String _id;
//            @SerializedName("_id")


            public String getDesc_type() {
                return desc_type;
            }

            public void setDesc_type(String desc_type) {
                this.desc_type = desc_type;
            }

//            public String get_id() {
//                return _id;
//            }
//
//            public void set_id(String _id) {
//                this._id = _id;
//            }
        }

    }

}
