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
public class Counter implements Serializable {
  @Id
  private String id;
  private String title;
  private Integer count;
  @Column(name = "create_date")
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createDate;
  @Column(name = "user_id")
  private String userId;
  @Column(name = "course_id")
  private String courseId;

}
