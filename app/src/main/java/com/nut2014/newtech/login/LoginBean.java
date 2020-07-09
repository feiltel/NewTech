package com.nut2014.newtech.login;


/**
 * @author feiltel 2020/4/21 0021
 */
public class LoginBean extends BaseResponseBean {
    public LoginBean() {
    }

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public LoginBean setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }
}
