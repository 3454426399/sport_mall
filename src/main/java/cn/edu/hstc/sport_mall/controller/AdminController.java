package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.pojo.Admin;
import cn.edu.hstc.sport_mall.pojo.Statistics;
import cn.edu.hstc.sport_mall.service.AdminService;
import cn.edu.hstc.sport_mall.service.StatisticsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller层：负责管理员的登录验证
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService userService;
    @Autowired
    private StatisticsService statisticsService;

    /*
     * 当用户访问 http://localhost:8080/sport_mall/admin 这个路径时，访问此控制器，跳转至管理员登录页面 admin_login.jsp
     */
    @RequestMapping("admin")
    public String adminLoginPage(){
        return "redirect:/admin_login.jsp";
    }

    /*
     * 管理员退出登录状态
     */
    @RequestMapping("admin_logout")
    public String logout(HttpServletRequest request){
        Subject currentUser = SecurityUtils.getSubject();
        Admin admin = (Admin) currentUser.getSession().getAttribute("admin");

        request.setAttribute("logMsg", admin.getAdminName() + "退出......");
        currentUser.logout();
        return "redirect:admin";
    }

    /*
     * 按日统计
     */
    @RequestMapping("admin_statistics_day_list")
    public String statisticsListByDay(String date){
        String[] timer = null;
        List<Statistics> statisticsList = null;
        String subtext = null;

        if (date == null || date.equals(",")){
            Calendar calendar = new GregorianCalendar();
            statisticsList = statisticsService.selByDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            subtext = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        }else {
            timer = date.substring(0, date.length() - 1).split("-");
            statisticsList = statisticsService.selByDay(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]));
            subtext = date.substring(0, date.length() - 1);
        }
        Subject currentUser = SecurityUtils.getSubject();

        List<Integer> visitList = new ArrayList<>();
        List<Integer> saleList = new ArrayList<>();
        List<Float> incomeList = new ArrayList<>();
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < statisticsList.size(); i++){
            visitList.add(statisticsList.get(i).getVisit());
            saleList.add(statisticsList.get(i).getSale());
            incomeList.add(statisticsList.get(i).getIncome());
            timeList.add(statisticsList.get(i).getDay());
        }

        currentUser.getSession().setAttribute("visitList", visitList);
        currentUser.getSession().setAttribute("saleList", saleList);
        currentUser.getSession().setAttribute("incomeList", incomeList);
        currentUser.getSession().setAttribute("timeList", timeList);
        currentUser.getSession().setAttribute("subtext", subtext);
        currentUser.getSession().setAttribute("max", 12000);
        currentUser.getSession().setAttribute("splitNumber", 20);
        currentUser.getSession().setAttribute("xname", "日");

        return "admin/listStatistics";
    }

    /*
     * 按月统计
     */
    @RequestMapping("admin_statistics_month_list")
    public String statisticsListByMonth(String date){
        List<Statistics> statisticsList = null;
        String subtext = null;

        if (date == null || date.equals("")){
            Calendar calendar = new GregorianCalendar();
            statisticsList = statisticsService.selByMonth(calendar.get(Calendar.YEAR));
            subtext = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        }else {
            statisticsList = statisticsService.selByMonth(Integer.parseInt(date));
            subtext = date;
        }
        Subject currentUser = SecurityUtils.getSubject();

        List<Integer> visitList = new ArrayList<>();
        List<Integer> saleList = new ArrayList<>();
        List<Float> incomeList = new ArrayList<>();
        List<Integer> timeList = new ArrayList<>();
        for (int i = 0; i < statisticsList.size(); i++){
            visitList.add(statisticsList.get(i).getVisit());
            saleList.add(statisticsList.get(i).getSale());
            incomeList.add(statisticsList.get(i).getIncome());
            timeList.add(statisticsList.get(i).getMonth());
        }

        currentUser.getSession().setAttribute("visitList", visitList);
        currentUser.getSession().setAttribute("saleList", saleList);
        currentUser.getSession().setAttribute("incomeList", incomeList);
        currentUser.getSession().setAttribute("timeList", timeList);
        currentUser.getSession().setAttribute("subtext", subtext);
        currentUser.getSession().setAttribute("max", 360000);
        currentUser.getSession().setAttribute("splitNumber", 20);
        currentUser.getSession().setAttribute("xname", "月");

        return "admin/listStatistics";
    }
}