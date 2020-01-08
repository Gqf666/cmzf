package com.baihzi.gqf.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Guru implements Serializable {
  @Id
  private String id;
  private String name;
  private String photo;
  private String status;
  @Column(name = "nick_name")
  private String nickName;

}
