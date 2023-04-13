package cn.edu.hstc.sport_mall.interceptor;

import cn.edu.hstc.sport_mall.espojo.Log;
import cn.edu.hstc.sport_mall.pojo.Statistics;
import cn.edu.hstc.sport_mall.service.StatisticsService;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拦截器-记录用户、管理员的登录请求、后台登录处理结果至日志文件
 */
public class LoggerAndStatisticsServiceInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    //日志记录器
    Logger logger = LoggerFactory.getLogger(LoggerAndStatisticsServiceInterceptor.class);

    /*
     * 在业务处理器处理请求之前被调用，返回true则进入业务处理
     * 记录用户、管理员的登录请求至日志文件、es服务器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String remoteAddr = request.getRemoteAddr();    //获取远程客户机的ip地址
        StringBuilder logMsg = new StringBuilder();

        if (request.getRequestURI().contains("foreRegister"))    //注册成功，记录至日志文件
            logMsg.append("系统运行时日志: ").append(request.getAttribute("logMsg")).append("，客户端ip地址: ").append(remoteAddr);
        else if (request.getRequestURI().contains("foreResetPassword"))    //重置密码成功，记录至日志文件
            logMsg.append("系统运行时日志: ").append(request.getAttribute("logMsg")).append("，客户端ip地址: ").append(remoteAddr);
        else if (request.getRequestURI().contains("UsernamePasswordLogin.admin"))    //管理员的登录请求路径被访问，记录至日志文件
            logMsg.append("系统运行时日志: ").append(request.getParameter("adminName")).append("发起了登录请求(管理员)").append("，客户端ip地址: ").append(remoteAddr);
        else if (request.getRequestURI().contains("UsernamePasswordLogin.user"))    //消费者用户的账号密码登录请求路径被访问，记录至日志文件
            logMsg.append("系统运行时日志: ").append(request.getParameter("phone")).append("发起了登录请求(账号密码方式)").append("，客户端ip地址: ").append(remoteAddr);
        else if (request.getRequestURI().contains("MobileCheckCodeLogin"))    //消费者用户的短信验证码登录请求被访问，记录至日志文件
            logMsg.append("系统运行时日志: ").append(request.getParameter("phone")).append("发起了登录请求(短信验证码方式)").append("，客户端ip地址: ").append(remoteAddr);

        logger.info(logMsg.toString());    //记录至日志文件
        Date now = new Date();
        Log log = new Log();
        log.setTimeStamp(now.getTime());
        log.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(now));
        log.setIpAddress(remoteAddr);
        log.setLevel("info");
        log.setMessage(logMsg.toString());

        //记录至es服务器
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName("sport_mall-log")
                .withType("sport_mall-log")
                .withId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(now) + RandomUtils.nextInt(10000))
                .withObject(log)
                .build();
        elasticsearchOperations.index(indexQuery);

        return true;
    }

    /*
     * 在业务处理请求执行完成后，返回视图之前被调用
     * 记录后台登录处理结果至日志文件
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String remoteAddr = request.getRemoteAddr();    //获取远程客户机的ip地址
        StringBuilder logMsg = new StringBuilder();

        logMsg.append("系统运行时日志: ").append(request.getAttribute("logMsg")).append("，客户端ip地址: ").append(remoteAddr);

        logger.info(logMsg.toString());    //记录所有注册、登录、重置密码、退出结果至日志文件
        Date now = new Date();
        Log log = new Log();
        log.setTimeStamp(now.getTime());
        log.setLevel("info");
        log.setIpAddress(remoteAddr);
        log.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(now));
        log.setMessage(logMsg.toString());

        //记录所有注册、登录、重置密码、退出结果至es服务器
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName("sport_mall-log")
                .withType("sport_mall-log")
                .withId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(now) + RandomUtils.nextInt(10000))
                .withObject(log)
                .build();
        elasticsearchOperations.index(indexQuery);

        //消费者用户登录成功，系统的当日访问量加1
        if (logMsg.toString().contains("登录成功user")){
            Statistics statistics = statisticsService.select();
            statistics.setVisit(statistics.getVisit() + 1);
            statisticsService.update(statistics);
        }
    }

    //返回视图之后被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ;
    }
}