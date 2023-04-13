package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 *  实体类-评价
 */
public class Review implements Serializable {

    private int id;    //编号
    private int uid;    //用户编号
    private int pid;    //产品编号
    private String content;    //评价
    private Date createDate;    //评价时间

    /* 非数据库字段，评价的用户的信息 */
    private User user;

    public Review() {
    }

    public Review(int id, int uid, int pid, String content, Date createDate, User user) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
        this.content = content;
        this.createDate = createDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
