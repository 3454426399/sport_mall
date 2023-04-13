package cn.edu.hstc.sport_mall.interceptor;

import cn.edu.hstc.sport_mall.pojo.User;
import cn.edu.hstc.sport_mall.service.OrderItemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器-拦截所有以form开头的请求，期间计算用户购物车中产品数量
 */
public class CartInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private OrderItemService orderItemService;

    //在业务处理器处理请求之前被调用，返回true则进入业务处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    //在业务处理请求执行完成后，返回视图之前被调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        int  cartTotalItemNumber = 0;

        //这里是获取购物车中一共有多少数量
        if(currentUser.isAuthenticated()){
            User user = (User) currentUser.getSession().getAttribute("user");

            cartTotalItemNumber = orderItemService.selCount(user.getId());
            SecurityUtils.getSubject().getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        }else {
            SecurityUtils.getSubject().getSession().setAttribute("cartTotalItemNumber", null);
        }

    }

    //返回视图之后被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ;
    }
}