package com.rmq.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * @author mqz
 * @description
 * @since 2020/6/16
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "topic列表实体")
public class TopicsVO implements Serializable {
    private static final long serialVersionUID = -7123767930892488049L;

    @ApiModelProperty(value = "topic名称")
    private String topicName;

    @ApiModelProperty(value = "topic所在的group")
    private String groups;

}
