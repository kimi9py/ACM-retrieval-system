package ck_oj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetUrl {
	
	ConstantValue constantValue = new ConstantValue();
	final String URLBaidu = "http://www.baidu.com/s?wd=";
	final String URLGoogle = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&hl=zh-CN&rsz=large&q=";
	String keyword = null;
	
	public void url(String filename, String Name, String Number, String ID, String Volume){
		/*
		 * filename = pojid(1-1)-m-00000
		 * Name = POJ
		 * Number = 1
		 * ID = 1001
		 * Volume = Volume1
		 */
		String result = null;
		try {
				String folder = Name.toLowerCase() + ID;
				
				File fr = new File(constantValue.ROOTDIR + "/url/"  + Name + "/" + Volume + "/" + folder + "_website.txt");
				if(!fr.exists()){
					//第一轮搜索
					result = url_firstround(filename, ID, Number);
					if(result.toString().isEmpty()){
						//第二轮搜索
						result = url_secondround(filename, ID, Number);
						//第三轮搜索
						if(result.toString().isEmpty()){
							result = url_thirdround(filename, ID, Number);
							if(result.toString().isEmpty())
								result = keyword;
						}
					}
					//将一道题的url写入本地
					if (!result.equals(keyword)){
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume + "/" + folder + "_website.txt");
						fos.write(result.getBytes());
						fos.close();
					}
					else{
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/url/" + "Empty.txt", true);
						fos.write(result.getBytes());
						fos.write("\r\n".getBytes());
						fos.close();
					}
				}	
				else{
					System.out.println(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume + "/" + folder + "_website.txt already exits");
				}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	public String url_firstround(String filename, String ID, String Number){
		StringBuffer sb = new StringBuffer();
		try{
			ArrayList<String> SearchKey = new ArrayList<String>();
			if (filename.contains("hdu")) {
				SearchKey.add("intitle:" + constantValue.HDUOJ + ID);
			}
			else if (filename.contains("poj")) {
				SearchKey.add("intitle:" + constantValue.POJOJ + ID);
				SearchKey.add("intitle:" + "pku" + ID);
			}
			else if (filename.contains("uva")) {
				SearchKey.add("intitle:" + constantValue.UVAOJ + ID);
			}
			else if (filename.contains("zoj")) {
				SearchKey.add("intitle:" + constantValue.ZOJOJ + ID);
			}
			HashMap<String, String> hs = new HashMap<String, String>();
			
			for (int i = 0; i < SearchKey.size(); i++) {
				
				System.out.println(SearchKey.get(i) + " ");
				
				keyword = SearchKey.get(i).toString();
				keyword = keyword.substring(8);

				
				String ss = URLEncoder.encode(SearchKey.get(i), "UTF-8");
				getURLBaidu urlthread = new getURLBaidu(URLBaidu + ss, Number, keyword, hs);
				urlthread.run();
			}
			
			Iterator<Entry<String, String>> it = hs.entrySet().iterator();
		
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String resulturl = entry.getKey().toString();
				String resultkey = entry.getValue().toString();
				sb.append(resultkey).append("\t").append(resulturl).append("\t").append("From Baidu").append("\r\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public String url_secondround(String filename, String ID, String Number){
		StringBuffer sb = new StringBuffer();
		try{
			ArrayList<String> SearchKey = new ArrayList<String>();
			if (filename.contains("hdu")) {
				SearchKey.add(constantValue.HDUOJ + ID);
			}
			else if (filename.contains("poj")) {
				SearchKey.add(constantValue.POJOJ + ID);
				SearchKey.add("pku" + ID);
			}
			else if (filename.contains("uva")) {
				SearchKey.add(constantValue.UVAOJ + ID);
			}
			else if (filename.contains("zoj")) {
				SearchKey.add(constantValue.ZOJOJ + ID);
			}
			HashMap<String, String> hs = new HashMap<String, String>();
			
			for (int i = 0; i < SearchKey.size(); i++) {
				
				System.out.println(SearchKey.get(i) + " ");
				
				keyword = SearchKey.get(i).toString();
				String ss = URLEncoder.encode(SearchKey.get(i), "UTF-8");
				getURLBaidu urlthread = new getURLBaidu(URLBaidu + ss, Number, keyword, hs);
				urlthread.run();
			}
			
			Iterator<Entry<String, String>> it = hs.entrySet().iterator();
		
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String resulturl = entry.getKey().toString();
				String resultkey = entry.getValue().toString();
				sb.append(resultkey).append("\t").append(resulturl).append("\t").append("From Baidu").append("\r\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	public String url_thirdround(String filename, String ID, String Number){
		StringBuffer sb = new StringBuffer();
		try{
			ArrayList<String> SearchKey = new ArrayList<String>();
			if (filename.contains("hdu")) {
				SearchKey.add("intitle:" + constantValue.HDUOJ + ID);
				SearchKey.add(constantValue.HDUOJ + ID);
			}
			else if (filename.contains("poj")) {
				SearchKey.add("intitle:" + constantValue.POJOJ + ID);
				SearchKey.add("intitle:" + "pku" + ID);
				SearchKey.add(constantValue.POJOJ + ID);
				SearchKey.add("pku" + ID);
			}
			else if (filename.contains("uva")) {
				SearchKey.add("intitle:" + constantValue.UVAOJ + ID);
				SearchKey.add(constantValue.UVAOJ + ID);
			}
			else if (filename.contains("zoj")) {
				SearchKey.add("intitle:" + constantValue.ZOJOJ + ID);
				SearchKey.add(constantValue.ZOJOJ + ID);
			}
			HashMap<String, String> hs = new HashMap<String, String>();
		
			for (int i = 0; i < SearchKey.size(); i++) {
				
				System.out.println(SearchKey.get(i) + " ");
				
				keyword = SearchKey.get(i).toString();
				String ss = SearchKey.get(i);
				getURLGoogle urlthread = new getURLGoogle(URLGoogle + ss, Number, keyword, hs);
				urlthread.run();
			}
			
			Iterator<Entry<String, String>> it = hs.entrySet().iterator();
		
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String resulturl = entry.getKey().toString();
				String resultkey = entry.getValue().toString();
				sb.append(resultkey).append("\t").append(resulturl).append("\t").append("From Google").append("\r\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
