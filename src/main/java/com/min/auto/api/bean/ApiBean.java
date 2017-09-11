package com.min.auto.api.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by minyangcheng on 2017/7/27.
 */
@Entity
public class ApiBean {

    @Id
    @GeneratedValue
    private Integer id;
    private String path;
    @Column(length = 5000)
    private String reqData;
    @Column(length = 5000)
    private String respData;
    private Date time;
    private int flag;  //1有效 0无效

    public ApiBean() {
    }

    public ApiBean(String path, String reqData, String respData) {
        this.path = path;
        this.reqData = reqData;
        this.respData = respData;
        this.time = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getRespData() {
        return respData;
    }

    public void setRespData(String respData) {
        this.respData = respData;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
