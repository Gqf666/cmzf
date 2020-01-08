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
public class Album implements Serializable {
  @Id
  private String id;
  private String title; //标题
  private String score; //评分
  private String author; //作者
  private String broadcast; //播音
  private Integer count; //章节数量
  @Column(name = "`desc`")
  private String desc; //内容简介
  private String status; // 状态
  @Column(name = "create_date")
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate; //发布日期
  private String cover; //封面
}
