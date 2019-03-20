package com.jiangwei.entity;

import java.io.Serializable;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/16
 */
public class Result implements Serializable {

    private  int resultCode ;

    private  String message ;

    private MyPage myPage ;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyPage getMyPage() {
        return myPage;
    }

    public void setMyPage(MyPage myPage) {
        this.myPage = myPage;
        if(myPage!=null&&!myPage.getPageData().isEmpty()){
            this.resultCode = SUCCESS ;
            this.message    = "SUCCESS";
        }
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public Result(MyPage myPage) {
        this.myPage = myPage;
        if(myPage!=null&&!myPage.getPageData().isEmpty()){
            this.resultCode = SUCCESS ;
            this.message    = "SUCCESS";
        }
    }

    private static final int SUCCESS = 1 ;
}
