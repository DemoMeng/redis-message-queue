package com.rmq.web.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @title 
 * @author xulz
 * @date 2019年1月25日上午11:17:02
 */
@Data
@ApiModel(value = "ServiceResult",description = "ServiceResult")
public class ServiceResult {

    @ApiModelProperty(value = "SUCCESS/FAIL")
	private String resultCode;
    @ApiModelProperty(value = "错误码")
	private String errorCode;
    @ApiModelProperty(value = "错误信300息")
	private String msg;
	/**扩展*/
	@ApiModelProperty(value = "返回的业务码")
	private String returnCode;
    @ApiModelProperty(value = "返回的对象")
	private Object data;
    
	/**
	 * ServiceResult
	 * @param resultCode
	 * @param errorCode
	 * @param errorMsg 是否要输出错误日志
	 */
	public ServiceResult(String resultCode, String errorCode, boolean errorMsg){
		this.resultCode = resultCode;
		if(StringUtils.isNotBlank(errorCode)){
			this.errorCode = errorCode;
		}
		if(errorMsg){
			this.msg = HttpConstants.MSG_MAP.get(errorCode);
		}
	}
	
	public ServiceResult(String resultCode, String msg){
		this.resultCode = resultCode;
		if(StringUtils.isNotBlank(msg)){
			this.msg = msg;
		}
	}
	
	public boolean isSuccess(){
		if(HttpConstants.RESUTL_OK.equals(this.resultCode)){
			return true;
		}else{
			return false;
		}
	}

}
