package ck_oj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getURLGoogle implements Runnable{
	private String keyword;
	private String number;
	private String url;
	private HashMap<String,String> hs = new HashMap<String,String>();
	
	public getURLGoogle(String url, String number, String keyword, HashMap<String,String> hs){
		this.keyword = keyword;
		this.number = number;
		this.url = url;
		this.hs = hs;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		final String[] SkipStr = { "http://acm.hdu.edu.cn","http://acm.zju.edu.cn",
				"http://poj.org", "http://acm.pku.edu.cn",
				"http://poj.grids.cn", "http://wenku.baidu.com",
				"http://www.iteye.com/wiki/blog", "http://www.elanso.com",
				"http://groups.google.com", "http://hongliang.72pines.com",
				"http://en.pudn.com", "http://www.pudn.com","http://icpc.njust.edu.cn",
				"http://www.baidu.com/gaoji/preferences.html"};	
		
		URL myurl = null;
		HttpURLConnection mg = null;
		BufferedReader in = null;
		String line = null;
		StringBuilder builder = new StringBuilder();
		String builderStr = "";
		BufferedReader reader = null;
		
		try {
			myurl = new URL(url);
			System.out.println(myurl);
			mg = (HttpURLConnection) myurl.openConnection();
			mg.setRequestMethod("GET");
			mg.setRequestProperty("Referer", "http://www.mysite.com/index.html");
			System.setProperty("sun.net.client.defaultConnectTimeout",
		    		String.valueOf(30000));
		    System.setProperty("sun.net.client.defaultReadTimeout",
		    		String.valueOf(30000));
			mg.setConnectTimeout(30000);
			mg.setReadTimeout(30000);
			mg.connect();
			if(HttpURLConnection.HTTP_OK != mg.getResponseCode()){
		    	System.out.println("连接错误！！！");
			}
			reader = new BufferedReader(new InputStreamReader(mg.getInputStream(),"utf-8"));

			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
			builderStr = builder.toString();
			String tempkeyA = keyword.substring(0, 3);
			String tempkeyB = keyword.substring(3);
			String keyword2 = tempkeyA + " " +  tempkeyB;
			
			try {
				JSONObject json = new JSONObject(builderStr);
				
				JSONObject responseData = (JSONObject) json.get("responseData");
				JSONArray results = responseData.getJSONArray("results");
				boolean isvalid = true;
				for(int i=0;i< results.length();i++)
				{
					JSONObject temp = (JSONObject) results.get(i);
					String tempUrl = temp.getString("url");
					if (tempUrl.startsWith("/s")|| 
							tempUrl.endsWith("txt")||
							tempUrl.endsWith("pdf")||
							tempUrl.endsWith("doc")||
							tempUrl.endsWith("xls")||
							tempUrl.endsWith("TXT")||
							tempUrl.endsWith("PDF")||
							tempUrl.endsWith("DOC")||
							tempUrl.endsWith("XLS")
								){
						isvalid = false;
					}
					for (int j = 0; j < SkipStr.length; j++) {
						if (tempUrl.startsWith(SkipStr[j])){
							isvalid = false;
							break;
						}
					}
					if (!isvalid) {
						continue;
					}	
					
					String tempTitle = temp.getString("titleNoFormatting");
					tempTitle = tempTitle.replaceAll(" ", "");
					tempTitle = tempTitle.replaceAll("\\+", "");
					if (tempTitle.toLowerCase().contains(keyword)||tempTitle.toLowerCase().contains(keyword2)) {
						System.out.println(tempUrl + "From Google");
						hs.put(tempUrl, keyword);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}
			reader.close();
			mg.disconnect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}	
}
