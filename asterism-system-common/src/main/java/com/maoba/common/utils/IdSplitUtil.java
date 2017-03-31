package com.maoba.common.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Kitty daddy
 *  id分割工具
 */
public class IdSplitUtil {
	
	/**
	 * 字符串类型的数据转换成Long类型的字符串
	 * @param ids
	 * @return
	 */
     public static Set<Long> splitString2Long(String ids){
    	 Set<Long> idSets = null;
    	 if(StringUtils.isNotEmpty(ids)){
    		 idSets = new HashSet<Long>();
    		 String[] strs = ids.split(",");
    		 for(String str : strs){
    			 idSets.add(Long.parseLong(str));
    		 }
    	 }
    	 return idSets;
     }
}
