package com.baihzi.gqf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private int records;//总数据
    private int page;//当前页
    private int total;//总页数
    private List<Album> rows;
}
