package com.baihzi.gqf.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapter implements Serializable {
  @Id
  private String id;
  private String title;
  private String url;
  private Double size;
  private String time;
  @Column(name = "create_time")
  @JSONField(format = "yyyy-MM-dd")
  private Date createTime;
  @Column(name = "album_id")
  private String albumId;

}
