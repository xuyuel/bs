package com.sjzc.kt.util;

import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.sjzc.kt.exception.RRException;




/**
 * 
 * @Description: 非空验证
 * @author 
 * @date 
 */
public class AssertUtil {

	private final static Log log = LogFactory.getLog(AssertUtil.class);

	/**
	 * @Title: dataNotEmpty
	 * @Description: TODO(判断需要的参数不能为空)
	 * @author 
	 * @param:object
	 * @return_type:抛出异常参数存在空值
	 * @date 
	 */
	public static void dataNotEmpty(Object... objects) {

		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null || objects[i] == "") {
				// 日志打印出第几个参数为空
				log.info("第" + (i + 1) + "个参数为空");
				throw new RRException("参数为空");
			}
		}
	}
	

	/**
	 * @Title: dataNotEmpty
	 * @Description: TODO(判断需要的参数不能为空)
	 * @author 
	 * @param:List
	 * @return_type:抛出异常参数存在空值
	 * @date 
	 */
	public static void dataNotEmpty(List<?>... lists) {
		System.err.println(lists.length);
		for (int i = 0; i < lists.length; i++) {
			if (lists[i].size() == 0) {
				log.info("第" + (i + 1) + "个参数为空");
				throw new RRException("参数为空");
			}
		}
	}

	

}
