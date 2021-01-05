package com.mrl.es.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SearchResponseAttr
 * @Description 用于表示聚合结果
 * @Author Mr.L
 * @Date 2020/12/7 17:14
 * @Version 1.0
 */
@Data
public class AggregationAttr implements Serializable {
    private Long id;
    private String name;
    private List<String> values = new ArrayList<>();
}
