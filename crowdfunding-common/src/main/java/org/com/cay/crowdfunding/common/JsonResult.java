package org.com.cay.crowdfunding.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.common.JsonResult
 * Date:         2018/11/29
 * Version:      v1.0
 * Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {

	private Object data;

	private String message;

	private int code;

	public static JsonResult ok(){
		return new JsonResult(null, "", 0);
	}

	public static JsonResult ok(Object data){
		return new JsonResult(data, "", 0);
	}

	public static JsonResult ok(Object data, String message){
		return new JsonResult(data, message, 0);
	}

	public static JsonResult ok(Object data, String message, int code){
		return new JsonResult(data, message, code);
	}

	public static JsonResult ok(String message){
		return new JsonResult(null, message, 0);
	}

	public static JsonResult error(){
		return new JsonResult(null, "", 0);
	}

	public static JsonResult error(String message, int code){
		return new JsonResult(null, message, code);
	}

	public static JsonResult error(String message){
		return new JsonResult(null, message, 1);
	}

}
