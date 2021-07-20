package com.sjzc.kt.util;

public class NickNameUtil {

	/**
	 * @Description: 微信昵称解析
	 * @author 
	 * @date 
	 */
		


		public static String byteStringArray2String(String[] byteArr) {
			byte[] bis = new byte[byteArr.length];
			for (int i = 0; i < byteArr.length; i++) {
				int ii = Integer.parseInt(byteArr[i]);
				bis[i] = (byte) ii;
			}
			String string = new String(bis);
			return string;
		}
		
		
		public static String byteArray2StringArray(byte[]data){
			  StringBuffer sb=new StringBuffer();
			  for(int i=0;i<data.length;i++){
			   byte b=data[i];
			   //System.out.println(b);
			   if(i==0){
			    sb.append(b+"");
			   }else{
			    sb.append(",").append(b+"");
			   }

			  }
			  String str=sb.toString();
			  return str;
			 }
	


}
