package com.baihzi.gqf.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Article implements Serializable {
  @Id
  private String id;
  private String title;
  private String img;
  private String content;
  @Column(name = "create_date")
  @JSONField(format = "yyyy-MM-dd")
  private java.util.Date createDate;
  @JSONField(format = "yyyy-MM-dd")
  @Column(name = "publish_date")
  private java.util.Date publishDate;
  private String status;
  @Column(name = "guru_id")
  private String guruId;
}
