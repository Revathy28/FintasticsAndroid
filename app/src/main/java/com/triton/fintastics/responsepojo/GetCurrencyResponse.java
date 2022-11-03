package com.triton.fintastics.responsepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetCurrencyResponse {


       /* "Status": "Success",
            "Message": "payment type Details",
            "Data": {
        "payment_types": [
        {
            "_id": "617a9f0e8b392d379a26fafc",
                "payment_type": "Cash"
        },
        {
            "_id": "617a9f158b392d379a26fafe",
                "payment_type": "Card"
        },
        {
            "_id": "617a9f1e8b392d379a26fb00",
                "payment_type": "GPay"
        },
        {
            "_id": "617a9f248b392d379a26fb02",
                "payment_type": "PhonePay"
        },
        {
            "_id": "617a9f2b8b392d379a26fb04",
                "payment_type": "EMI Card"
        },
        {
            "_id": "619c99c09c9c964e0451a9f8",
                "payment_type": "cash"
        },
        {
            "_id": "619ca05e9c9c964e0451aa24",
                "payment_type": "Credit"
        }
        ],
        "desc_types": [
        {
            "_id": "619f82faea15b9691ae3cbb9",
                "desc_type": "CASH"
        },
        {
            "_id": "61b076fbea15b9691ae3d180",
                "desc_type": "LOAN"
        },
        {
            "_id": "61b07759ea15b9691ae3d19d",
                "desc_type": "FOOD"
        },
        {
            "_id": "61b077aeea15b9691ae3d1b2",
                "desc_type": "TOUR"
        },
        {
            "_id": "61b077b7ea15b9691ae3d1b6",
                "desc_type": "CLOTH"
        },
        {
            "_id": "61b077caea15b9691ae3d1bc",
                "desc_type": "DEPT"
        },
        {
            "_id": "61b077d7ea15b9691ae3d1c0",
                "desc_type": "BORROW"
        },
        {
            "_id": "61b077eaea15b9691ae3d1d2",
                "desc_type": "RENT"
        },
        {
            "_id": "61b077f4ea15b9691ae3d1d6",
                "desc_type": "EDUCATION"
        },
        {
            "_id": "61b07802ea15b9691ae3d1da",
                "desc_type": "MEDICAL"
        },
        {
            "_id": "61b07823ea15b9691ae3d1ec",
                "desc_type": "WATER"
        },
        {
            "_id": "61b0782cea15b9691ae3d1f0",
                "desc_type": "GAS"
        },
        {
            "_id": "61b07835ea15b9691ae3d1f4",
                "desc_type": "REPAIR"
        },
        {
            "_id": "61b07845ea15b9691ae3d1f8",
                "desc_type": "SALARY"
        }
        ],
        "currencies": [
        {
            "_id": "6200afd1592eee419d46cf6c",
                "currency": "USD",
                "symbol": "$"
        },
        {
            "_id": "6200f40fe3b0a6679fe11c23",
                "currency": "INR",
                "symbol": "₹"
        },
        {
            "_id": "621318cff6bc9330f6419a43",
                "currency": "EUR",
                "symbol": "€"
        },
        {
            "_id": "621318d8f6bc9330f6419a44",
                "currency": "GBP",
                "symbol": "£"
        },
        {
            "_id": "621318e1f6bc9330f6419a45",
                "currency": "YEN",
                "symbol": "¥"
        }
        ]
    },
        "Code": 200
    }*/


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

    }

}
