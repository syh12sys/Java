package com.example.demo.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.Date;


// user_info 和0 user_info_detail 垂直分表，一个数据页能存储更多的数据，减少磁盘i/o的次数，提供性能
// 垂直分表一般安装使用频次分表
@TableName(value = "user_info")//指定表名
public class UserEntity {
    @TableId(value = "id",type = IdType.AUTO)//指定自增策略
    private int id;

    private String username;
    private String password;
    private int status;
    private Date createAt;
    private Date updateAt;
    // 用户唯一标识
    private String token;
    // 用户登录时间
    private Date loginDatetime;
    // 用户手机号
    private String phoneNumber;

    // 乐观锁技术
    private int testOptimisticLockCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getToken() { return token; }

    public void  setToken(String token) { this.token = token; }

    public Date getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(Date loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTestOptimisticLockCount() {
        return testOptimisticLockCount;
    }

    public void setTestOptimisticLockCount(int testOptimisticLockCount) {
        this.testOptimisticLockCount = testOptimisticLockCount;
    }
}
