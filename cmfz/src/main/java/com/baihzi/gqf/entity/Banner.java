package com.baihzi.gqf.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baihzi.gqf.controller.BannerConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
  @Id
  @ExcelProperty("ID")
  private String id;
  @ExcelProperty("标题")
  private String title;
  @ExcelProperty(value = "路径",converter = BannerConverter.class)
  private String url;
  @ExcelProperty("超链接")
  private String href;
  @ExcelProperty("创建日期")
  @Column(name = "create_date")
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;
  @ExcelProperty("内容简介")
  private String des;// 描述 des
  @ExcelProperty("状态")
  private String status;
}
