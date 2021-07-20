package com.sjzc.kt.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;





/**
* @ClassName: HttpClientUtils
* @Description: TODO
* @author 
* @date 
*/
public class HttpClientUtils {
	 
	/**
	 * 最大线程池
	 */
	public static final int THREAD_POOL_SIZE = 5;
 
	public interface HttpClientDownLoadProgress {
		public void onProgress(int progress);
	}
 
	private static HttpClientUtils httpClientDownload;
 
	private ExecutorService downloadExcutorService;
 
	private HttpClientUtils() {
		downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
 
	public static HttpClientUtils getInstance() {
		if (httpClientDownload == null) {
			httpClientDownload = new HttpClientUtils();
		}
		return httpClientDownload;
	}
 
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 */
	public void download(final String url, final String filePath) {
		downloadExcutorService.execute(new Runnable() {
 
			@Override
			public void run() {
				httpDownloadFile(url, filePath, null, null);
			}
		});
	}
 
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 * @param progress
	 *            进度回调
	 */
	public void download(final String url, final String filePath,
			final HttpClientDownLoadProgress progress) {
		downloadExcutorService.execute(new Runnable() {
 
			@Override
			public void run() {
				httpDownloadFile(url, filePath, progress, null);
			}
		});
	}
 
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 */
	private void httpDownloadFile(String url, String filePath,
			HttpClientDownLoadProgress progress, Map<String, String> headMap) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			setGetHead(httpGet, headMap);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity httpEntity = response1.getEntity();
				long contentLength = httpEntity.getContentLength();
				InputStream is = httpEntity.getContent();
				// 根据InputStream 下载文件
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int r = 0;
				long totalRead = 0;
				while ((r = is.read(buffer)) > 0) {
					output.write(buffer, 0, r);
					totalRead += r;
					if (progress != null) {// 回调进度
						progress.onProgress((int) (totalRead * 100 / contentLength));
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath);
				output.writeTo(fos);
				output.flush();
				output.close();
				fos.close();
				EntityUtils.consume(httpEntity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public String httpGet(String url) {
		return httpGet(url, null);
	}
 
	/**
	 * http get请求
	 * 
	 * @param url
	 * @return
	 */
	public String httpGet(String url, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			setGetHead(httpGet, headMap);
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity entity = response1.getEntity();
				responseContent = getRespString(entity);
				System.out.println("debug:" + responseContent);
				EntityUtils.consume(entity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
 
	public String httpPost(String url, Map<String, Object> paramsMap) {
		return httpPost(url, paramsMap, null);
	}
 
	
	
	
	
	/**
	 * http的post请求
	 * 
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public String httpPost(String url, Map<String, Object> paramsMap,
			Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			setPostHead(httpPost, headMap);
			setPostParams(httpPost, paramsMap);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("responseContent = " + responseContent);
		return responseContent;
	}
 
	/**
	 * 设置POST的参数
	 * 
	 * @param httpPost
	 * @param paramsMap
	 * @throws Exception
	 */
	private void setPostParams(HttpPost httpPost, Map<String, Object> paramsMap)
			throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = paramsMap.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, paramsMap.get(key).toString()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		}
	}
 
	/**
	 * 设置http的HEAD
	 * 
	 * @param httpPost
	 * @param headMap
	 */
	private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpPost.addHeader(key, headMap.get(key));
			}
		}
	}
 
	/**
	 * 设置http的HEAD
	 * 
	 * @param httpGet
	 * @param headMap
	 */
	private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpGet.addHeader(key, headMap.get(key));
			}
		}
	}
 
 
	
	/**
	 * 将返回结果转化为String
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}
	
	public static JSONObject doPostStr(String url,String outStr) throws ClientProtocolException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response;
		
			response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject = JSONObject.fromObject(result);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		 
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
