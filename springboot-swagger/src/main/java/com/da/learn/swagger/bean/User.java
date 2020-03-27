package com.da.learn.swagger.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User {
    @ApiModelProperty(value = "用户id", required = true)
    private Long id;
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
}
