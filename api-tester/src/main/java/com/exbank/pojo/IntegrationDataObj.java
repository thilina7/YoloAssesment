package com.exbank.pojo;

import com.creditdatamw.zerocell.annotation.Column;
import com.creditdatamw.zerocell.annotation.RowNumber;
import lombok.Data;

@Data
public class IntegrationDataObj {

    @RowNumber
    private int rowNumber;

    @Column(index=0, name="TEST_CASE_NAME")
    private String testCaseName;
    @Column(index=1, name="TEST_CASE_ID")
    private String testcaseId;

    @Column(index=2, name="CREATE_REQ")
    private String createReq;

    @Column(index=3, name="CREATE_RES")
    private String createRes;
    @Column(index=4, name="DEBIT_REQ")
    private String debitReq;

    @Column(index=5, name="DEBIT_RES")
    private String debitRes;
    @Column(index=6, name="WITHDRAW_REQ")
    private String withdraw_req;

    @Column(index=7, name="WITHDRAW_RES")
    private String withdraw_res;

    @Column(index=8, name="SEND_REQ")
    private String sedReq;
    @Column(index=9, name="SEND_RES")
    private String sendRes;
}
