package com.baihzi.gqf.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
  @Id
  private String id;
  private String phone;
  private String password;
  private String salt;
  private String status;
  private String photo;
  private String name;
  @Column(name = "nick_name")
  private String nickName;
  private String sex;
  private String sign;
  private String location;
  @Column(name = "rigest_date")
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date rigestDate;
  @Column(name = "last_login")
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date lastLogin;

}
