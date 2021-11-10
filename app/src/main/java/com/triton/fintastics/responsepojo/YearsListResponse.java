package com.triton.fintastics.responsepojo;

import java.util.List;

public class YearsListResponse {

    /**
     * Status : Success
     * Message : Yearly
     * Data : [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007,2006,2005,2004,2003,2002,2001,2000,1999,1998,1997,1996,1995,1994,1993,1992,1991,1990,1989,1988,1987,1986,1985,1984,1983,1982,1981,1980,1979,1978,1977,1976,1975,1974,1973,1972]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<Integer> Data;

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

    public List<Integer> getData() {
        return Data;
    }

    public void setData(List<Integer> Data) {
        this.Data = Data;
    }
}
