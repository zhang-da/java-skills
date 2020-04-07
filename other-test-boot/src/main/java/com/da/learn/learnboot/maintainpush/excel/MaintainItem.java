package com.da.learn.learnboot.maintainpush.excel;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "maintain_item")
@Data
public class MaintainItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model_code", columnDefinition = "VARCHAR(32) COMMENT '车型码'", nullable = false)
    private String modelCode;
    @Column(name = "item_code", columnDefinition = "INT COMMENT '保养项编码'", nullable = false)
    private Integer itemCode;
    @Column(name = "item_name", columnDefinition = "VARCHAR(64) COMMENT '保养项'", nullable = false)
    private String itemName;
    @Column(name = "type_code", columnDefinition = "INT COMMENT '保养项编码'", nullable = false)
    private Integer typeCode;
    @Column(name = "type_name", columnDefinition = "VARCHAR(10) COMMENT '保养类别'", nullable = false)
    private String typeName;

    @Column(name = "first_mileage", columnDefinition = "INT COMMENT '首保里程（km）'")
    private Integer firstMileage;
    @Column(name = "regular_mileage", columnDefinition = "INT COMMENT '定保间隔里程（km）'")
    private Integer regularMileage;

    @Column(name = "first_day", columnDefinition = "INT COMMENT '首保天数（天）'")
    private Integer firstDay;
    @Column(name = "regular_day", columnDefinition = "INT COMMENT '定保间隔天数（天）'")
    private Integer regularDay;

}
