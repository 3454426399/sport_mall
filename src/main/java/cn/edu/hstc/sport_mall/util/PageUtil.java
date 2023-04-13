package cn.edu.hstc.sport_mall.util;

/*
 * 工具类：分页
 */
public class PageUtil {

    private int start;    //当前页面从第几条记录开始显示
    private int count;    //每页显示个数
    private int total;    //总个数
    private String param;    //参数

    private static final int defaultCount = 50;    //默认每页显示5条

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        int count;

        count = this.count;
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        String param;

        param = this.param;
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public PageUtil(){
        count = defaultCount;
    }

    public int getTotalPage(){
        int totalPage;

        /* 如果 total 可以被 count 整除，则共有 total % count 页
           例如：总数是50，是能够被5整除的，那么就有10页
        */
        if (0 == total % count)
            totalPage = total /count;
        /* 否则，就需要多出一页来显示剩余的记录
           例如：总数时51，是不能被5整除的，那么就有11页
        */
        else
            totalPage = total / count + 1;

        if(0==totalPage)
            totalPage = 1;

        return totalPage;
    }

    public int getLast(){
        int last;

        /* 如果 total 可以被 count 整除，则最后一页的开始记录是 total - count
           例如：总数是50，是能够被5整除的，那么最后一页的开始记录就是45
        */
        if (0 == total % count)
            last = total - count;
        /* 否则，则最后一页的开始记录是 total - total % count
           例如：总数是51，是不能够被5整除的，那么最后一页的开始记录就是 51 - 51 % 5 = 50
         */
        else
            last = total - total % count;

        return last;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }
}