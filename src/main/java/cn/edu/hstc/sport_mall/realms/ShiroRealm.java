package cn.edu.hstc.sport_mall.realms;

import cn.edu.hstc.sport_mall.pojo.Admin;
import cn.edu.hstc.sport_mall.pojo.Statistics;
import cn.edu.hstc.sport_mall.pojo.User;
import cn.edu.hstc.sport_mall.service.AdminService;
import cn.edu.hstc.sport_mall.service.StatisticsService;
import cn.edu.hstc.sport_mall.service.UserService;
import cn.edu.hstc.sport_mall.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Shiro认证和授权
 */
public class ShiroRealm extends AuthenticatingRealm {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatisticsService statisticsService;

    /*
     * Shiro认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String name = token.getUsername();    //获取用户名
        String pwd = new String(token.getPassword());    //获取密码
        Admin admin = null;
        User user = null;

        //判断用户身份，非管理员用户的用户名是手机号，不包含字母
        if (name.matches(".*[a-zA-Z]+.*")){
            admin = adminService.selLogin(name);

            //若用户不存在 | 密码错误，校验失败，抛出异常
            if (admin == null || !ShiroUtil.encrypt(pwd, admin.getSalt()).equals(admin.getAdminPassword())){
                throw new AuthenticationException("用户名或密码错误");
            }else {
                SecurityUtils.getSubject().getSession().setAttribute("admin", admin);
            }
        }else {
            if (pwd.length() > 6){    //判断用户登录方式，密码长度大于6位，短信验证码只有6位
                user = userService.selExist(name);

                //若用户不存在 | 密码错误，校验失败，抛出异常
                if (user == null || !ShiroUtil.encrypt(pwd, user.getSalt()).equals(user.getPassword())){
                    throw new AuthenticationException("用户名或密码错误");
                }
            }else {    //短信验证码登录方式
                user = userService.selExist(name);
                String currentMobilCheckCode = (String) SecurityUtils.getSubject().getSession().getAttribute("mobilCheckCode");

                if (!currentMobilCheckCode.equals(pwd)){
                    throw new AuthenticationException("短信验证码错误");
                }
            }

            Statistics statistics = statisticsService.select();
            statistics.setVisit(statistics.getVisit() + 1);
            statisticsService.update(statistics);
        }

        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        AuthenticationInfo info = new SimpleAuthenticationInfo(name, pwd, getName());    //登录校验通过，返回验证信息
        return info;
    }
}
