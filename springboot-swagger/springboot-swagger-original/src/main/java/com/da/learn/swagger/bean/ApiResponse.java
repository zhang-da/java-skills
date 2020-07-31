package com.da.learn.swagger.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用API接口返回", description = "Common Api Response")
public class ApiResponse<T> {
    @ApiModelProperty(value = "通用返回状态", required = true, position = 0)
    private Integer code;
    @ApiModelProperty(value = "通用返回信息", required = true, position = 1)
    private String message;
    @ApiModelProperty(value = "通用返回数据", required = true, position = 2)
    private T data;
}
