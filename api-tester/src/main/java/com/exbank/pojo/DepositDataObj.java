package com.exbank.pojo;

import com.creditdatamw.zerocell.annotation.Column;
import com.creditdatamw.zerocell.annotation.RowNumber;

public class DepositDataObj {

    @RowNumber
    private int rowNumber;

    @Column(index=0, name="TEST_CASE_ID")
    private String tname;

    @Column(index=1, name="TEST_CASE_DESC")
    private String testDesc;

    @Column(index=2, name="NAME_WITH_INIT")
    private String nameWithinitial;
    @Column(index=8, name="SOURCE")
    private String source;

    @Column(index=5, name="AMOUNT")
    private String amount;

    @Column(index=6, name="REMARKS")
    private String remarks;

    @Column(index=7, name="CONTACT_NUMBER")
    private String contactNu;
}
