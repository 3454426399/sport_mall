package cn.edu.hstc.sport_mall.espojo;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * es实体类-日志
 */
@Document(indexName = "sport_mall-log", type = "sport_mall-log", shards = 5, replicas = 0)
public class Log implements Serializable {
    @Field(type = FieldType.Long)
    private long timeStamp;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private  String level;
    @Field(type = FieldType.Keyword, index = false)
    private String dateTime;
    @Field(type = FieldType.Keyword, index = false)
    private String ipAddress;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String message;

    public Log() {
    }

    public Log(long timeStamp, String level, String dateTime, String ipAddress, String message) {
        this.timeStamp = timeStamp;
        this.level = level;
        this.dateTime = dateTime;
        this.ipAddress = ipAddress;
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Log{" +
                "timeStamp=" + timeStamp +
                ", level='" + level + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
