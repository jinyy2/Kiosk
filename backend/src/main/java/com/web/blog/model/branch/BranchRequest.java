package com.web.blog.model.branch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Valid
@Data
public class BranchRequest {
    @ApiModelProperty(required = true)
    private Integer sid;

    @ApiModelProperty(required = true)
    private Integer menuid;
    
    @ApiModelProperty(required = true)
    private String name;
    
    @ApiModelProperty(required = true)
    private String description;
    
    @ApiModelProperty(required = true)
    private Integer price;
    
    @ApiModelProperty(required = true)
    private String image;
    
    @ApiModelProperty(required = true)
    private Integer kcal;

    @ApiModelProperty(required = true)
    private Integer category1;

    @ApiModelProperty(required = true)
    private Integer category2;

}
