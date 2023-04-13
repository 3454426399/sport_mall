package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-统计
 */
public class Statistics {

    private int id;    //编号
    private int year;    //年
    private int month;    //月
    private int day;    //日
    private int visit;    //日访问量
    private int sale;    //日销售量
    private float income;    //日收入

    public Statistics() {
    }

    public Statistics(int id, int year, int month, int day, int visit, int sale, float income) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.visit = visit;
        this.sale = sale;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", visit=" + visit +
                ", sale=" + sale +
                ", income=" + income +
                '}';
    }
}
