package ck_oj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class getURLBaidu implements Runnable {
	
	private String keyword;
	private String number;
	private String url;
	private HashMap<String,String> hs = new HashMap<String,String>();
	private static String Host = null;
	private static String Path = null;
	private static int Port = 80;
	
	public getURLBaidu(String url, String number, String keyword, HashMap<String,String> hs){
		this.keyword = keyword;
		this.number = number;
		this.url = url;
		this.hs = hs;
	}	

	public static void SyncHostInfo(String uri){
		uri = uri.toLowerCase().replace("http://", "").replace("https://","");
		
		Host = uri.split("/")[0];
		if(Host.indexOf(":")> -1){
			String[] ServerInfo = Host.split(":");
			Host = ServerInfo[0];
			try{
				Port = Integer.parseInt(ServerInfo[1]);
			} catch(Exception ex){
				Port = 80;
			}
		}
		
		Path = uri.replace(Host, "");
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
		StringBuffer page = new StringBuffer();
		
		SyncHostInfo(url); 
		
		try {
			myurl = new URL(url);
System.out.println(myurl);
//			if(myurl!=null){
//				constantValue.lable.setText(myurl.toString());
//			}
			mg = (HttpURLConnection) myurl.openConnection();
			mg.setRequestMethod("GET");
			mg.setRequestProperty("Host", Host);
			mg.setRequestProperty("Referer","Http://" + Host);
			mg.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
//			mg.setRequestProperty("User-Agent",
//					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			if(appendheader != null){
//				for(Iterator it = appendheader.keySet().iterator();it.hasNext();){
//					String skey = (String) it.next();
//					String[] svalue = appendheader.get(skey).toArray(new String[appendheader.get(skey).size()]);
//					String headervalue = null;
//					for (String s : svalue){
//						headervalue = headervalue + s;
//					}
//					mg.setRequestProperty(skey,headervalue);
//				}
//			}
			
		    System.setProperty("sun.net.client.defaultConnectTimeout",
		    		String.valueOf(30000));
		    System.setProperty("sun.net.client.defaultReadTimeout",
		    		String.valueOf(30000));
			mg.setConnectTimeout(30000);
			mg.setReadTimeout(30000);
			mg.connect();
//			appendheader = mg.getHeaderFields();
			if(HttpURLConnection.HTTP_OK != mg.getResponseCode()){
			    	System.out.println("连接错误！！！");
			}
			in = new BufferedReader(new InputStreamReader(mg.getInputStream(), "UTF-8"));
			while ((line = in.readLine()) != null) {
				if (line.length() > 10*1024)
					continue;
				page.append(line);
			}	
			in.close();
			mg.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  catch (IOException e) {
			e.printStackTrace();
		}

		int index = -1;
		int counti = 0;
		
		//keyword = hdu1000
		//keyword2 = hdu 1000
		String tempkeyA = keyword.substring(0, 3);
		String tempkeyB = keyword.substring(3);
		String keyword2 = tempkeyA + " " +  tempkeyB;
		
		while (counti <= 12) {
			counti++;
			index = page.indexOf("class=\"result c-container \"",index + 1);
			if (index == -1) {
				break;
			}
			int begin = page.indexOf("href = \"", index)
					+ "href = \"".length();
			int end = page.indexOf("\"", begin);
			index = end;
			boolean isvalid = true;
			String report_url = page.substring(begin, end);
			
			//isvalid
			if (report_url.startsWith("/s")|| 
					report_url.endsWith("txt")||
					report_url.endsWith("pdf")||
					report_url.endsWith("doc")||
					report_url.endsWith("xls")||
					report_url.endsWith("TXT")||
					report_url.endsWith("PDF")||
					report_url.endsWith("DOC")||
					report_url.endsWith("XLS")
						){
				isvalid = false;
			}
			for (int j = 0; j < SkipStr.length; j++) {
				if (report_url.startsWith(SkipStr[j])){
					isvalid = false;
					break;
				}
			}
			if (!isvalid) {
				continue;
			}	

			int titleA = page.indexOf("data-tools=\'{\"title\":\"", end) + "data-tools=\'{\"title\":\"".length();
			if (titleA == -1)
				break;
			int titleB = page.indexOf("\",\"url", titleA);
			if (titleB == -1)
				break;
			String title = page.substring(titleA, titleB);
			title = title.replaceAll("<[^>]>", "").replaceAll("<>", "").trim();
			if (title.toLowerCase().contains(keyword)||title.toLowerCase().contains(keyword2)) {
				System.out.println(report_url);
				hs.put(report_url, keyword);
			}
		}//while
	}
}
