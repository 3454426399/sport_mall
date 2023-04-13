package cn.edu.hstc.sport_mall.interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口
 */
public interface LoginInterface {

    /*
     * principals：身份
     * credentials：凭证
     * 校验用户的身份和凭证，实现登录认证逻辑
     */
    default public String login(String principals, String credentials, HttpServletRequest request) {
        return null;
    }

    /*
     * principals：身份
     * credentials：凭证
     * checkCode：校验码
     * 校验用户的身份、凭证以及校验码，实现登录认证逻辑
     */
    default public String login(String principals, String credentials, String checkCode, HttpServletRequest request) {
        return null;
    }
}
