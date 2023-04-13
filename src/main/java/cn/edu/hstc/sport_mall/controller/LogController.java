package cn.edu.hstc.sport_mall.controller;

import cn.edu.hstc.sport_mall.espojo.Log;
import cn.edu.hstc.sport_mall.util.StringUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Controller层: 记录、搜索、删除系统日志
 */
@Controller
public class LogController {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /*
     * 根据关键字，搜索日志
     */
    @RequestMapping("admin_log_system_search")
    public String search(Model model, String date, @RequestParam(defaultValue = "all") String keyword, int pageNo){
        if (keyword.equals("all"))    //默认搜索全部
            searchAll(model, date, keyword, pageNo);
        //如果搜索的是这几个关键字，则在es服务器中sport_mall-log索引的level字段上进行匹配
        else if (keyword.equals("info") || keyword.equals("debug") || keyword.equals("warm") || keyword.equals("error"))
            searchByLevel(model, date, keyword, pageNo);
        else     //否则，则在es服务器中sport_mall-log索引的message字段上进行匹配
            searchByMessage(model, date, keyword, pageNo);

        return "admin/log";
    }

    /*
     * 搜索全部
     */
    private void searchAll(Model model, String date, String keyword, int pageNo){
        long startTime = 0;    //起始时间戳
        long endTime = 0;    //终止时间戳

        if (date == null || date.equals(",")){    //没有选择日期，则默认搜索当天的日志记录
            Calendar calendar = new GregorianCalendar();
            Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            startTime = calendar1.getTimeInMillis();
            endTime = startTime + 86400000;
        }else {    //否则，搜索指定日期的日志记录
            String[] timer = date.substring(0, date.length() - 1).split("-");
            Calendar calendar = new GregorianCalendar(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]) - 1,
                    Integer.parseInt(timer[2]));

            startTime = calendar.getTimeInMillis();
            endTime = startTime + 86400000;
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()    //构造搜索条件
                .withIndices("sport_mall-log")
                .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                .withSort(SortBuilders.fieldSort("timeStamp").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pageNo, 60))
                .build();

        long total = elasticsearchOperations.count(    //计数
                new NativeSearchQueryBuilder()
                        .withIndices("sport_mall-log")
                        .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                        .build()
        );
        long totalPage = (total % 60 == 0)?(total / 60):(total / 60 + 1);    //总页数
        if (totalPage == 0)    //避免浏览器解析jsp页面时出错
            totalPage = 1;

        List<Log> logList = elasticsearchOperations.queryForList(searchQuery, Log.class);    //搜索

        model.addAttribute("logs", logList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("total", total);
    }

    /*
     * 按level字段进行反向索引的匹配
     */
    private void searchByLevel(Model model, String date, String keyword, int pageNo){
        long startTime = 0;    //起始时间戳
        long endTime = 0;    //终止时间戳

        if (date == null || date.equals(",")){    //没有选择日期，则默认搜索当天的日志记录
            Calendar calendar = new GregorianCalendar();
            Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            startTime = calendar1.getTimeInMillis();
            endTime = startTime + 86400000;
        }else {    //否则，搜索指定日期的日志记录
            String[] timer = date.substring(0, date.length() - 1).split("-");
            Calendar calendar = new GregorianCalendar(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]) - 1,
                    Integer.parseInt(timer[2]));

            startTime = calendar.getTimeInMillis();
            endTime = startTime + 86400000;
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()    //构造搜索条件
                .withIndices("sport_mall-log")
                .withQuery(QueryBuilders.termQuery("level", keyword))
                .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                .withSort(SortBuilders.fieldSort("timeStamp").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pageNo, 60))
                .build();

        long total = elasticsearchOperations.count(    //计数
                new NativeSearchQueryBuilder()
                        .withIndices("sport_mall-log")
                        .withQuery(QueryBuilders.termQuery("level", keyword))
                        .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                        .build()
        );
        long totalPage = (total % 60 == 0)?(total / 60):(total / 60 + 1);    //总页数
        if (totalPage == 0)    //避免浏览器解析jsp页面时出错
            totalPage = 1;

        List<Log> logList = elasticsearchOperations.queryForList(searchQuery, Log.class);    //搜索
        for (Log log : logList) {    //渲染，关键字高亮显示
            log.setLevel(StringUtil.decorate(log.getLevel(), keyword, "green"));
        }

        model.addAttribute("logs", logList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("total", total);
    }

    /*
     * 按message字段进行反向索引的匹配
     */
    private void searchByMessage(Model model, String date, String keyword, int pageNo){
        long startTime = 0;    //起始时间戳
        long endTime = 0;    //终止时间戳

        if (date == null || date.equals(",")){    //没有选择日期，则默认搜索当天的日志记录
            Calendar calendar = new GregorianCalendar();
            Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            startTime = calendar1.getTimeInMillis();
            endTime = startTime + 86400000;
        }else {    //否则，搜索指定日期的日志记录
            String[] timer = date.substring(0, date.length() - 1).split("-");
            Calendar calendar = new GregorianCalendar(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]) - 1,
                    Integer.parseInt(timer[2]));

            startTime = calendar.getTimeInMillis();
            endTime = startTime + 86400000;
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()    //构造搜索条件
                .withIndices("sport_mall-log")
                .withQuery(QueryBuilders.termQuery("message", keyword))
                .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                .withSort(SortBuilders.fieldSort("timeStamp").order(SortOrder.DESC))
                .withPageable(PageRequest.of(pageNo, 60))
                .build();

        long total = elasticsearchOperations.count(    //计数
                new NativeSearchQueryBuilder()
                        .withIndices("sport_mall-log")
                        .withQuery(QueryBuilders.termQuery("message", keyword))
                        .withQuery(QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime))
                        .build()
        );
        long totalPage = (total % 60 == 0)?(total / 60):(total / 60 + 1);    //总页数
        if (totalPage == 0)    //避免浏览器解析jsp页面时出错
            totalPage = 1;

        List<Log> logList = elasticsearchOperations.queryForList(searchQuery, Log.class);    //搜索
        for (Log log : logList) {    //渲染，关键字高亮显示
            log.setMessage(StringUtil.decorate(log.getMessage(), keyword, "green"));
        }

        model.addAttribute("logs", logList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("total", total);
    }

    /*
     * 删除指定日期的系统日志
     */
    @RequestMapping("admin_log_system_delete")
    public String delete(Model model, String date){
        long startTime = 0;    //起始时间戳
        long endTime = 0;    //终止时间戳

        if (date == null || date.equals(",")){    //没有选择日期，则默认删除当天的日志记录
            Calendar calendar = new GregorianCalendar();
            Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            startTime = calendar1.getTimeInMillis();
            endTime = startTime + 86400000;
        }else {    //否则，删除指定日期的日志记录
            String[] timer = date.substring(0, date.length() - 1).split("-");
            Calendar calendar = new GregorianCalendar(Integer.parseInt(timer[0]), Integer.parseInt(timer[1]) - 1,
                    Integer.parseInt(timer[2]));

            startTime = calendar.getTimeInMillis();
            endTime = startTime + 86400000;
        }

        DeleteQuery deleteQuery = new DeleteQuery();    //构造删除条件
        deleteQuery.setIndex("sport_mall-log");
        deleteQuery.setType("sport_mall-log");
        deleteQuery.setQuery(
            QueryBuilders.rangeQuery("timeStamp").gte(startTime).lte(endTime)
        );
        elasticsearchOperations.delete(deleteQuery);    //删除

        return "redirect:admin_log_system_search?pageNo=" + 0;
    }
}
