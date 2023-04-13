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
 * 登录实现类-账号密码登录
 */
@Controller
public class UsernamePasswordLogin implements LoginInterface {

    /*
     * principals：用户名
     * credentials：密码
     * 校验用户的用户名和密码，实现登录校验逻辑
     */
    @Override
    @RequestMapping("UsernamePasswordLogin.user")
    @ResponseBody
    public String login(@RequestParam("phone") String principals, @RequestParam("password") String credentials, HttpServletRequest request) {

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()){    //还未登录，就进行登录验证
            UsernamePasswordToken token = new UsernamePasswordToken(principals, credentials);    //封装用户名和密码
            token.setRememberMe(true);

            try {
                currentUser.login(token);    //Shiro登录校验
            }catch (AuthenticationException ae){
                request.setAttribute("logMsg", principals + "因账号或密码登录失败");
                return ae.getMessage();
            }
        }

        request.setAttribute("logMsg", principals + "登录成功user");
        return "success";//登录成功
    }

    /*
     * principals：用户名
     * credentials：密码
     * checkCode：系统随机生成的图形验证码
     * 校验用户的用户名、密码以及图形验证码，实现登录校验逻辑
     */
    @Override
    @RequestMapping("UsernamePasswordLogin.admin")
    @ResponseBody
    public String login(@RequestParam("adminName") String principals, @RequestParam("adminPassword") String credentials, @RequestParam("checkCode") String checkCode, HttpServletRequest request) {

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()){    //如果还未认证，则把认证工作交给Shiro
            //从session中获取图形验证码
            String graphicsCode = (String) currentUser.getSession().getAttribute("graphicsCode");
            if (!checkCode.equals(graphicsCode)){    //校验图形验证码，若不通过，则认证失败，且验证码失效
                currentUser.getSession().removeAttribute("graphicsCode");
                request.setAttribute("logMsg", principals + "因验证码错误，登录失败");
                return "验证码错误";
            }

            currentUser.getSession().removeAttribute("graphicsCode");    //即便验证码校验成功，也只能使用一次

            UsernamePasswordToken token = new UsernamePasswordToken(principals,credentials);    //封装用户名和密码
            token.setRememberMe(true);

            try {

                currentUser.login(token);    //Shiro登录校验

            }catch (AuthenticationException ae){
                request.setAttribute("logMsg",  principals + "因用户名或密码错误，登录失败");
                return "用户名或密码错误";
            }
        }
        request.setAttribute("logMsg", principals + "登录成功admin");

        return "success";
    }
}
