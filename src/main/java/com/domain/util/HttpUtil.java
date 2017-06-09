package com.domain.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

	/**
	 * post请求
	 * @param url
	 * @param map
	 * @return
	 */
	public static String post(String url, Map<String, String> map) {
		CloseableHttpResponse response = null;
		StringBuffer sb = new StringBuffer();
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			//map 转 NameValuePairList
			if (null != map && map.size() != 0) {
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					if(map.get(key) == null){
						continue;
					}
					nvps.add(new BasicNameValuePair(key, map.get(key)));
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String str = "";
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			EntityUtils.consume(entity);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static String get(String url){
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet(url);
	    CloseableHttpResponse response = null;
	    StringBuffer sb = new StringBuffer();
	    try {
	        long cunrrentTime = System.currentTimeMillis();
            response = httpclient.execute(httpGet);
	        HttpEntity entity = response.getEntity();
	        
	        InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            EntityUtils.consume(entity);
	    } 
        catch (ClientProtocolException e) {
	    	e.printStackTrace();
        } catch (IOException e) {
	    	e.printStackTrace();
        }
	    finally {
	        try {
	        	if (response != null){
					response.close();
				}
            } catch (IOException e) {
	        	e.printStackTrace();
            }
	    }
	    return sb.toString();
	}

}
