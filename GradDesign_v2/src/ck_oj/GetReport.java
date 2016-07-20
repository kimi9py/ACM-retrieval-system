package ck_oj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class GetReport {

	ConstantValue constantValue = new ConstantValue();
	
	
//	public String myProcessString(String HtmlStr) {
//		String NewStr = "";
//		int pos0 = 0;
//		int pos1 = -1;
//		while ((pos1 = HtmlStr.indexOf("\r\n", pos0 + 2)) != -1) {
//			if (!HtmlStr.substring(pos0, pos1).trim().equals("")) {
//				NewStr = NewStr + HtmlStr.substring(pos0, pos1);
//			}
//			pos0 = pos1;
//		}
//		return NewStr;
//	}

//	public String getNoHTMLString(String HtmlStr) {
//
//		if (HtmlStr == null)
//			return "";
//		java.util.regex.Pattern p_script;
//		java.util.regex.Matcher m_script;
//		java.util.regex.Pattern p_style;
//		java.util.regex.Matcher m_style;
//		java.util.regex.Pattern p_html;
//		java.util.regex.Matcher m_html;
//
//		try {
//			HtmlStr = HtmlStr.replace("<br />", "\r\n");
//			HtmlStr = HtmlStr.replace("<br>", "\r\n");
//			HtmlStr = HtmlStr.replace("<br >", "\r\n");
//			HtmlStr = HtmlStr.replace("<BR />", "\r\n");
//			HtmlStr = HtmlStr.replace("<BR>", "\r\n");
//			HtmlStr = HtmlStr.replace("<BR >", "\r\n");
//			HtmlStr = HtmlStr.replace("<p>", "\r\n");
//			HtmlStr = HtmlStr.replace("</p>", "\r\n");
//			HtmlStr = HtmlStr.replace("<P>", "\r\n");
//			HtmlStr = HtmlStr.replace("</P>", "\r\n");
//			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
//		
//			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
//			
//			String regEx_html = "<[^>]+>"; 
//
//			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//			m_script = p_script.matcher(HtmlStr);
//			HtmlStr = m_script.replaceAll(""); 
//
//			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//			m_style = p_style.matcher(HtmlStr);
//			HtmlStr = m_style.replaceAll(""); // ����style��ǩ
//			
//
//			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
//			m_html = p_html.matcher(HtmlStr);
//
//			HtmlStr = m_html.replaceAll("").replace(" ", " "); 
//			//HtmlStr = HtmlStr.replaceAll("<([^<>]+)>", "");
//			HtmlStr = HtmlStr.replace("&nbsp;", " ");
//			HtmlStr = HtmlStr.replace("&#160;", " ");
//			HtmlStr = HtmlStr.replace("&lt;", "<");
//			HtmlStr = HtmlStr.replace("&gt;", ">");
//			HtmlStr = HtmlStr.replace("&quot;", "\"");
//			HtmlStr = HtmlStr.replace("&amp;", "&");
//			//HtmlStr = HtmlStr.replaceAll("<([^<>]+)>", "");
//
//			HtmlStr = myProcessString(HtmlStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e);
//		}
//		return HtmlStr;
//	}
	
	public void report(String website, String Name, String ID, int cnt){
		
		/*
		 * Name = POJ
		 * ID = poj1001
		 */
		final String[] Skipurl = { "http://acm.hdu.edu.cn","http://acm.zju.edu.cn",
				"http://poj.org", "http://acm.pku.edu.cn",
				"http://poj.grids.cn", "http://wenku.baidu.com",
				"http://www.iteye.com/wiki/blog", "http://www.elanso.com",
				"http://groups.google.com", "http://hongliang.72pines.com",
				"http://en.pudn.com", "http://www.pudn.com","http://icpc.njust.edu.cn",
				"http://www.baidu.com/gaoji/preferences.html","http://uva.onlinejudge.org"};	
		
		boolean isurlvalid = true;
		
		if (ID.contains("pku"))
			ID = "poj" + ID.substring(3);
		
		WebEncoding web = new WebEncoding();
		HttpURLConnection mg = null;
		String[] SkipStr = { "题目分类", "算法分类", "算法总结", "题目列表", "题目归类", "题目推荐" };
		
		try{
			File fr = new File(constantValue.ROOTDIR + "/report/" + Name + "/" + ID + "/" + "report" + cnt + ".txt");
			if(!fr.exists()){
				URL myurl = new URL(website);
				mg = (HttpURLConnection) myurl.openConnection();
				
				mg.setRequestMethod("GET");
				
				mg.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				System.setProperty("sun.net.client.defaultConnectTimeout",
						String.valueOf(5000));
				System.setProperty("sun.net.client.defaultReadTimeout",
						String.valueOf(10000));
				mg.setConnectTimeout(5000);
				mg.setReadTimeout(10000);

				String encodeStr = web.getCharset(website);
	System.out.println("encodeStr = " + encodeStr);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						mg.getInputStream(), encodeStr));
				String validurl = mg.getURL().toString();
				if (validurl.startsWith("/s")|| 
						validurl.endsWith("txt")||
						validurl.endsWith("pdf")||
						validurl.endsWith("doc")||
						validurl.endsWith("xls")||
						validurl.endsWith("TXT")||
						validurl.endsWith("PDF")||
						validurl.endsWith("DOC")||
						validurl.endsWith("XLS")||
						validurl.endsWith("ps")||
						validurl.endsWith("jpg")||
						validurl.endsWith("zip")
							){
					isurlvalid = false;
				}
				for (int j = 0; j < Skipurl.length; j++) {
					if (validurl.startsWith(Skipurl[j])){
						isurlvalid = false;
						break;
					}
				}
				if(!isurlvalid){
					System.out.println(validurl + " is not valid! ");
				}
				else{
					System.out.println(validurl);
					String line = null;
					StringBuffer sb = new StringBuffer();
					sb.append(website).append("\r\n");
					while ((line = in.readLine()) != null) {
						sb.append(line).append("\r\n");
					}
					
					Document doc = Jsoup.parse(sb.toString()); 
					String title = doc.title(); 
					String ss = sb.toString();
//					String ss = (getNoHTMLString(sb.toString()));
					
//					if (ss.length() > 10 *  1024) {
//						System.out.println("Too large\n");			
//					}
					
					boolean isvalid = true;
					for (int k = 0; k < SkipStr.length; k++) {
						if (ss.contains(SkipStr[k])) {
							isvalid = false;
							break;
						}
					}
					
					if(ss.indexOf("main(") != -1 && isvalid){
						FileOutputStream fos = new FileOutputStream(
								constantValue.ROOTDIR + "/report/" + Name + "/" + ID + "/" + "report" + cnt + ".txt");
						fos.write((title+"\r\n").getBytes());
						fos.write(ss.getBytes());
						fos.close();
					}
				}
				in.close();
				mg.disconnect();	
			}//if	
			else
				System.out.println(constantValue.ROOTDIR + "/report/" + Name + "/" + ID + "/" + "report" + cnt + ".txt already exists");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
