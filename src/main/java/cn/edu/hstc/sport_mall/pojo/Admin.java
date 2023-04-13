package cn.edu.hstc.sport_mall.pojo;

/**
 *  实体类-管理员用户
 */
public class Admin {

    private int id;    //管理员编号
    private String adminName;    //管理员名称
    private String adminPassword;    //管理员密码
    private String salt;    //盐值

    public Admin() {
    }

    public Admin(int id, String adminName, String adminPassword, String salt) {
        this.id = id;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
