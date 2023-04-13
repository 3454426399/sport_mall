package cn.edu.hstc.sport_mall.pojo;

import java.io.Serializable;

/**
 *  实体类-用户
 */
public class User implements Serializable {

    private int id;    //编号
    private String phone;    //手机号码
    private String password;    //密码
    private String salt;    //盐值

    //用户的个人微信信息
    private String nickName;    //微信昵称
    private String headImgUrl;    //微信头像url
    private String openId;    //唯一标识，对同一用户同一应用下唯一
    private String unionId;    //统一标识，对同一用户不同应用唯一

    public User() {
    }

    public User(int id, String phone, String password, String salt) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    /*
     * 将用户的账号做匿名处理，在显示评论时使用
     * 具体做法：11位的手机号码，前3位和后4位正常显示，其余位用 * 代替
     */
    public String getAnonymousName(){
        if (this.phone == null){
            return null;
        }

        return this.phone.substring(0, 3) + "****" + this.phone.substring(7, this.phone.length());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", openId='" + openId + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}