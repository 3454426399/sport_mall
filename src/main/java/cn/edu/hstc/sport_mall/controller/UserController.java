package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.User;
import cn.edu.hstc.sport_mall.service.UserService;
import cn.edu.hstc.sport_mall.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller层-用户管理
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /*
     * 查询所有用户信息，并分页显示
     */
    @RequestMapping("admin_user_list")
    public String list(PageUtil pageUtil){
        Subject currentUser = SecurityUtils.getSubject();

        //使用PageHelper分页插件实现分页
        PageHelper.offsetPage(pageUtil.getStart(), pageUtil.getCount());    //设置当前页的起始记录 & 记录数
        List<User> userList = userService.selList();    //返回当前页面的用户集合
        int total = (int) new PageInfo<>(userList).getTotal();    //查询用户的总数

        pageUtil.setTotal(total);

        //把分页信息 & 当前页面的用户合存放于session中，以便前端页面分页显示
        currentUser.getSession().setAttribute("us", userList);
        currentUser.getSession().setAttribute("page", pageUtil);

        return "admin/listUser";
    }

    /*
     * 根据手机号码，判断用户是否已经注册过了
     */
    @RequestMapping("foreExist")
    @ResponseBody
    public String exist(String phone){
        User user = userService.selExist(phone);

        return (user == null)?"false":"true";
    }
}