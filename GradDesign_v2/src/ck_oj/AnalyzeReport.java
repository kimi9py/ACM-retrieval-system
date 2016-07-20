package ck_oj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyzeReport {
	
	HashMap<String, String> hsfromid = new HashMap<String, String>();
	
	public void InfoFromId(String filename){
		
		ConstantValue constantValue = new ConstantValue();
		
		if(filename.contains("hdu")){
			try{
				String number = filename.substring(3);
				File f = new File(constantValue.ROOTDIR + "/id/hduid.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine()) != null){
					String[] str = line.split("\t");
					String id = str[1];
					if (number.equals(id)){
						hsfromid.put("problemname", str[2]);
						hsfromid.put("accept", str[3]);
						break;
					}
				}
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else if(filename.contains("poj")){
			try{
				String number = filename.substring(3);
				File f = new File(constantValue.ROOTDIR + "/id/pojid.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine()) != null){
					String[] str = line.split("\t");
					String id = str[1];
					if (number.equals(id)){
						hsfromid.put("problemname", str[2]);
						hsfromid.put("accept", str[3]);
						break;
					}
				}
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else if(filename.contains("uva")){
			try{
				String number = filename.substring(3);
				File f = new File(constantValue.ROOTDIR + "/id/uvaid.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine()) != null){
					String[] str = line.split("\t");
					String id = str[1];
					if (number.equals(id)){
						hsfromid.put("problemname", str[2]);
						hsfromid.put("accept", str[3]);
						break;
					}
				}
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else if(filename.contains("zoj")){
			try{
				String number = filename.substring(3);
				File f = new File(constantValue.ROOTDIR + "/id/zojid.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine()) != null){
					String[] str = line.split("\t");
					String id = str[1];
					if (number.equals(id)){
						hsfromid.put("problemname", str[2]);
						hsfromid.put("accept", str[3]);
						break;
					}
				}
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public String myProcessString(String HtmlStr) {
		String NewStr = "";
		int pos0 = 0;
		int pos1 = -1;
		while ((pos1 = HtmlStr.indexOf("\r\n", pos0 + 2)) != -1) {
			if (!HtmlStr.substring(pos0, pos1).trim().equals("")) {
				NewStr = NewStr + HtmlStr.substring(pos0, pos1);
			}
			pos0 = pos1;
		}
		return NewStr;
	}

	public String getNoHTMLString(String HtmlStr) {

		if (HtmlStr == null)
			return "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			HtmlStr = HtmlStr.replace("<br />", "\r\n");
			HtmlStr = HtmlStr.replace("<br>", "\r\n");
			HtmlStr = HtmlStr.replace("<br >", "\r\n");
			HtmlStr = HtmlStr.replace("<BR />", "\r\n");
			HtmlStr = HtmlStr.replace("<BR>", "\r\n");
			HtmlStr = HtmlStr.replace("<BR >", "\r\n");
			HtmlStr = HtmlStr.replace("<p>", "\r\n");
			HtmlStr = HtmlStr.replace("</p>", "\r\n");
			HtmlStr = HtmlStr.replace("<P>", "\r\n");
			HtmlStr = HtmlStr.replace("</P>", "\r\n");
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			
			String regEx_html = "<[^>]+>"; 

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(HtmlStr);
			HtmlStr = m_script.replaceAll(""); 

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(HtmlStr);
			HtmlStr = m_style.replaceAll(""); // ����style��ǩ
			

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(HtmlStr);

			HtmlStr = m_html.replaceAll("").replace(" ", " "); 
			//HtmlStr = HtmlStr.replaceAll("<([^<>]+)>", "");
			HtmlStr = HtmlStr.replace("&nbsp;", " ");
			HtmlStr = HtmlStr.replace("&#160;", " ");
			HtmlStr = HtmlStr.replace("&lt;", "<");
			HtmlStr = HtmlStr.replace("&gt;", ">");
			HtmlStr = HtmlStr.replace("&quot;", "\"");
			HtmlStr = HtmlStr.replace("&amp;", "&");
			//HtmlStr = HtmlStr.replaceAll("<([^<>]+)>", "");

			HtmlStr = myProcessString(HtmlStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return HtmlStr;
	}
	
	public String analyze(HashMap<String, String> hsMapWords_Path, HashMap<String, String> hsMapWords_Alias, 
			HashMap<String, String> hsMapWords_Prev, HashSet<String> hsSetRootWords, 
			String address){
		
		
		HashMap<String, Integer> PKcount = new HashMap<String, Integer>();
		ArrayList<String> PKvalid = new ArrayList<String>();
		StringBuffer sbreport = new StringBuffer();
		int i = 0;
		String filename = null;//hdu1000
		String problemname = null;
		String level = null;
		String title = null;
		String priority = "";
		String url = null;
		String temp = null;
		int MaxExpertPoint = 10;
		
		String[] reportweb = {"CSDN","新浪博客","网易博客","博客园","百度空间","ITeye技术网站","红黑联盟","ChinaUnix博客","推酷"};
		try{
			System.out.println(address);
			File DIR = new File(address);//address = /home/ck/CK_ACM/report/HDU/hdu1000
			filename = DIR.getName();
			File[] REP = DIR.listFiles();

			for(File rep : REP){// REP = rep = /home/ck/CK_ACM/report/HDU/hdu1000/report1;
				
				HashMap<String, Integer> hsMapCount = new HashMap<String, Integer>();
				HashSet<String> hsSetKnowledge = new HashSet<String>();
				
				FileReader fr = new FileReader(rep);
				BufferedReader br = new BufferedReader(fr);
				String line =null;
				title = br.readLine().trim().replaceAll("\t", " ");
				while(!(temp = br.readLine()).contains("http://")){
					title = title + temp;
					title.trim().replaceAll("\t", " ");
				}
				title = title.replaceAll(" +", " ").replaceAll("&nbsp", " ");
				url = temp;
				
				temp = title.toLowerCase();
				int n = temp.length() - temp.replaceAll("hdu", "").replaceAll("poj", "").replaceAll("uva", "")
						.replaceAll("zoj", "").length();
				n = n/3;
				if(n > 1){
					continue;
				}
				if(title.contains("百度知道") || title.contains("搜狗问问") || title.contains("中国广告知道网"))
					continue;
				
				if(filename.equals("uva10181")){
					System.out.println();
				}
				
				
				InfoFromId(filename);
				problemname = hsfromid.get("problemname");
				
				for(i = 0; i < reportweb.length; i++){
					if(title.contains(reportweb[i])){
						priority = "high";
						break;
					}
				}
				if(!priority.equals("high")){
					priority = "low";
				}
				
				StringBuffer sb = new StringBuffer();
				String ss = null;
				int Chinese_length = 0;
				while ((line = br.readLine()) != null){
					sb.append(line).append("\r\n");
				}
				
				if (title.contains("CSDN")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.article_content");
					ss = content.text();	
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("新浪博客")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.articalContent");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("网易博客")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.bct").select("div.fc05").select("div.fc11").select("div.nbw-blog").select("div.ztag");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("博客园")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("#cnblogs_post_body");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("百度空间")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("#content");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("ITeye")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.blog_content");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("红黑联盟")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("#Article");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("ChinaUnix博客")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.Blog_wz1");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}
				}
				else if (title.contains("推酷")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.article_body");
					ss = content.text();
					Chinese_length = ss.getBytes("GBK").length - ss.length();
					if (Chinese_length >= 50){
						priority = "overhigh";
					}							
					
				}
				else if (title.contains("豆豆技术网")){
					Document doc = Jsoup.parse(sb.toString());
					Elements content = doc.select("div.techcontent");
					ss = content.text();
					
				}
				else {
					StringBuffer st = new StringBuffer();
					
					Document doc = Jsoup.parse(sb.toString());
					Elements article = doc.getElementsByTag("article");
					if(!article.isEmpty())
						st.append(article.text());
					else{
						doc.getElementsByTag("a").remove();
						doc.getElementsByTag("link").remove();
						doc.select("form[method]").remove();
						doc.getElementsByTag("script").remove();
						doc.getElementsByTag("head").remove();
						doc.getElementsByTag("table").remove();
						doc.getElementsByTag("ul").remove();
						doc.getElementsByTag("nav").remove();
						Elements e = doc.getAllElements();
						for (Element ee : e){
							if(ee.ownText().length() < 10)
								continue;
							else
								st.append(ee.ownText()).append("\r\n");
						}
					}
					
					ss = st.toString();
				}
				
				
				ss = ss.toLowerCase();
				title = title.toLowerCase();
				Iterator<String> it = hsMapWords_Alias.keySet().iterator();
				String rex = "[\u4e00-\u9fa5]+";
				while (it.hasNext()){
					String word = (String) it.next();
					if(ss.contains(word) && !word.matches(rex) 
							&& ss.indexOf(word) + word.length() <= ss.length()){
						char first = 0;
						char last = 0;
						if(ss.indexOf(word) != 0 )
							first = ss.charAt(ss.indexOf(word) - 1);
						if(ss.indexOf(word) + word.length() != ss.length())
							last = ss.charAt(ss.indexOf(word) + word.length());
						
						if( (first >= 'a' && first <= 'z')
							|| (first >= 'A' && first <= 'Z')
							|| (last  >= 'a' && last  <= 'z')
							|| (last  >= 'A' && last  <= 'Z')
							|| (ss.contains("<" + word + ">"))
							|| (ss.contains(word + "("))
							|| (ss.contains(word + "["))
							|| (ss.contains(word + "{")) ){
								continue;
						}
						else{
							String curr = (String) hsMapWords_Alias.get(word);
							if(!hsSetKnowledge.contains(curr))
								hsSetKnowledge.add(curr);
						}
					}
					else if(ss.contains(word) && word.matches(rex)){
						String curr = (String) hsMapWords_Alias.get(word);					
						if(!hsSetKnowledge.contains(curr))
							hsSetKnowledge.add(curr);
					}
					
					if(title.contains(word) && !word.matches(rex) && title.indexOf(word) + word.length() <= title.length()){
						char first = 0;
						char last = 0;
						if(title.indexOf(word) != 0)
							first = title.charAt(title.indexOf(word) - 1);
						if(title.indexOf(word) + word.length() != title.length())
							last = title.charAt(title.indexOf(word) + word.length());
						
						if( (first >= 'a' && first <= 'z')
							|| (first >= 'A' && first <= 'Z')
							|| (last  >= 'a' && last  <= 'z')
							|| (last  >= 'A' && last  <= 'Z')
							|| (title.contains("<" + word + ">"))
							|| (title.contains(word + "("))
							|| (title.contains(word + "["))
							|| (title.contains(word + "{")) ){
								continue;
						}
						else{
							String curr = (String) hsMapWords_Alias.get(word);
							if(!hsSetKnowledge.contains(curr))
								hsSetKnowledge.add(curr);
						}
					}
					else if(title.contains(word) && word.matches(rex)){
						String curr = (String) hsMapWords_Alias.get(word);
						if(!hsSetKnowledge.contains(curr))
							hsSetKnowledge.add(curr);
					}
					
				}//while
				
				if (hsSetKnowledge.size() > MaxExpertPoint){
					sbreport.append(filename).append("\t");
					sbreport.append(problemname).append("\t");
					sbreport.append(title).append("\t");
					sbreport.append(priority).append("\t");
					sbreport.append(url).append("\t").append("\r\n");
					br.close();
					continue;
				}
				
				if (hsSetKnowledge.size() == 0){
					sbreport.append(filename).append("\t");
					sbreport.append(problemname).append("\t");
					sbreport.append(title).append("\t");
					sbreport.append(priority).append("\t");
					sbreport.append(url).append("\t").append("\r\n");
					br.close();
					continue;
				}
				
				
				Iterator<String> itCount = hsSetKnowledge.iterator();
				while(itCount.hasNext()){
					String word = (String) itCount.next();
					if(hsMapCount.get(word) == null)
						hsMapCount.put(word, 1);
					else
						hsMapCount.put(word, hsMapCount.get(word).intValue() + 1);	
				}
				br.close();
				fr.close();
				
				int maxFreq = 0;
				Iterator<String> itMax = hsMapCount.keySet().iterator();
				while(itMax.hasNext()){
					String word = (String) itMax.next();
					int freq = hsMapCount.get(word);
					maxFreq = Math.max(maxFreq, freq);
				}
				
				sbreport.append(filename).append("\t");
				sbreport.append(problemname).append("\t");
				sbreport.append(title).append("\t");
				sbreport.append(priority).append("\t");
				sbreport.append(url).append("\t");
				
				
				itMax = hsMapCount.keySet().iterator();
				while(itMax.hasNext()){
					String word = (String) itMax.next();
					int freq = hsMapCount.get(word);
					if (maxFreq <= freq * 5 / 4){
						if(PKcount.get(word) == null)
							PKcount.put(word, 1);
						else
							PKcount.put(word, PKcount.get(word).intValue() + 1);
						sbreport.append(word).append("\t");
					}
				}
				sbreport.append("\r\n");
			}//for

			Iterator<String> PKmax = PKcount.keySet().iterator();
			int max = 0;
			while(PKmax.hasNext()){
				String word = (String) PKmax.next();
				int freq = PKcount.get(word);
				max = Math.max(max, freq);
			}
			if(max < 2 && PKcount.size() < 3){
				PKmax = PKcount.keySet().iterator();
				while(PKmax.hasNext()){
					String word = (String) PKmax.next();
					if(!PKvalid.contains(word))
						PKvalid.add(word);
				}
			}
			else if(max == 2){
				PKmax  = PKcount.keySet().iterator();
				while(PKmax.hasNext()){
					String word = (String) PKmax.next();
					int freq = PKcount.get(word);
					if (freq == max && !PKvalid.contains(word)){
						PKvalid.add(word);
					}
				}	
			}
			else if(max > 2){
				PKmax  = PKcount.keySet().iterator();
				while(PKmax.hasNext()){
					String word = (String) PKmax.next();
					int freq = PKcount.get(word);
					if ( (freq == max || freq == max - 1) && (!PKvalid.contains(word)) ){
						PKvalid.add(word);
					}
				}
			}
			
			Iterator itcheck = PKvalid.iterator();
			ArrayList<String> removelist = new ArrayList<String>();
			while(itcheck.hasNext()){
				String word = (String) itcheck.next();
				Iterator traverse = PKvalid.iterator();
				while(traverse.hasNext()){
					String text = (String) traverse.next();
					if(text.contains(word) && !text.equals(word)){
						removelist.add(word);
						break;
					}
				}
			}
			for(String removeword : removelist){
				PKvalid.remove(removeword);
			}
			

			
			
			Iterator<String> itvalid = PKvalid.iterator();
			StringBuffer sk = new StringBuffer();
			sk.append(filename).append("\t");
			FileOutputStream out = new FileOutputStream("/home/ck/knowledgepoint.txt", true);
			while(itvalid.hasNext()){
				String knowledgepoint = itvalid.next();
				sk.append(knowledgepoint).append("\t");
			}
			out.write(sk.toString().getBytes());
			out.write("\r\n".getBytes());
			return sbreport.toString();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sbreport.toString();
	}

	

}