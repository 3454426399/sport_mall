package cn.edu.hstc.sport_mall.interfaces.impl;

import cn.edu.hstc.sport_mall.interfaces.LoginInterface;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录实现类-短信验证码登录
 */
@Controller
public class MobileCheckCodeLogin implements LoginInterface {

    /*
     * credentials：手机号码
     * credentials：短信验证码
     * 根据用户的手机号码和短信验证码，实现登录校验逻辑
     */
    @Override
    @RequestMapping("MobileCheckCodeLogin")
    @ResponseBody
    public String login(@RequestParam("phone") String principals, @RequestParam("phoneCode") String credentials, HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()){    //还未登录，就进行登录验证
            UsernamePasswordToken token = new UsernamePasswordToken(principals, credentials);    //封装手机号码和短信验证码
            token.setRememberMe(true);

            try {
                currentUser.login(token);    //Shiro登录校验
            }catch (AuthenticationException ae){
                request.setAttribute("logMsg", principals + "因" + ae.getMessage() + "，登录失败");
                return ae.getMessage();
            }
        }

        request.setAttribute("logMsg", principals + "登录成功user");
        return "success";
    }
}
