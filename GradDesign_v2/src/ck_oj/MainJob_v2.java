package ck_oj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class MainJob_v2 extends Configured implements Tool{
	
	enum Counter{
		LINESKIP,
	}

	public static String VolumeStart;
	public static String VolumeEnd;
	public static ConstantValue constantValue = new ConstantValue();
	public static HashMap<String, String> hsMapWords_Path;//{冒泡排序=/home/ck/CK_ACM/排序/冒泡排序, 排序=/home/ck/CK_ACM/排序}
	public static HashMap<String, String> hsMapWords_Alias;//{bubble-sort=冒泡排序, bubble sort=冒泡排序, 冒泡排序=冒泡排序, sorting=排序, 排序=排序}
	public static HashMap<String, String> hsMapWords_Prev;//{冒泡排序=排序, 插入排序=排序，选择排序=排序}
	public static HashSet<String> hsSetRootWords;//[计算几何, 数论, 组合数学, 图论, 字符串处理, 数据结构, 搜索, 基本算法, 动态规划, 博弈论, 概率论, 线性代数, 网络流, 排序]
	public static HashMap<String, Integer> hsMapRootNodesCount;//[排序]
	public static HashMap hsMapWords2Numb;
	
	//Map Class of getting id file
	public static class MapInfo extends Mapper<LongWritable, Text, NullWritable, Text>{
		
		private MultipleOutputs<NullWritable,Text> mos;
		
		protected void setup(Context context) throws IOException,InterruptedException {
			mos = new MultipleOutputs<NullWritable,Text>(context);
			super.setup(context);
		}
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{

			String line = value.toString();
			String[] linestr = line.split("\t");
			String Name = linestr[0];
			VolumeStart = linestr[1];
			VolumeEnd= linestr[2];
			int vstart = Integer.parseInt(VolumeStart);
			int vend = Integer.parseInt(VolumeEnd);
			String outpath = null;
			File Proid = new File(constantValue.ROOTDIR + "/id/");
			Proid.mkdirs();
			if(Name.equals("HDU")){
				int count = 0;
				HttpURLConnection mg = null;
				outpath = "hduid" + "(" + VolumeStart + "-" + VolumeEnd + ")";
				for (int VolumeNumber = vstart; VolumeNumber <= vend; VolumeNumber++){		
					try {
						URL url = new URL(constantValue.HDULink + VolumeNumber);
						mg = (HttpURLConnection) url.openConnection();
	    			    mg.setRequestMethod("GET");
	    			    mg.setRequestProperty("User-Agent",
	    						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    			    System.setProperty("sun.net.client.defaultConnectTimeout",
	    			    		String.valueOf(5000));
	    			    System.setProperty("sun.net.client.defaultReadTimeout",
	    			    		String.valueOf(100000));
	    			    mg.setConnectTimeout(5000);
	    			    mg.setReadTimeout(100000);
						BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "GB2312"));
						String linehdu = null;
						StringBuffer sb = new StringBuffer();
						while (true) {
							linehdu = reader.readLine();
							if (linehdu == null
									|| linehdu
											.indexOf("Ratio(Accepted/Submissions)</td></tr><script language=\"javascript\">") != -1) {
								break;
							}
						}
						
						//hdu题目信息的格式为  p(0,1000,-1,"A + B Problem",120021,379781)
						Pattern pattern = Pattern.compile("p\\(\\d,\\d+,[^;]+;");
						Matcher matcher = pattern.matcher(linehdu);
						while (matcher.find()) {
							String ProblemInfo = matcher.group();
							int index = ProblemInfo.indexOf(',') + 1;
							
							count++;
							sb.append(count).append("\t");
							sb.append(ProblemInfo.substring(index, index + 4)).append("\t");
							sb.append(
									ProblemInfo.substring(ProblemInfo.indexOf('\"') + 1,
											ProblemInfo.lastIndexOf('\"'))).append("\t");
							sb.append(
									ProblemInfo.substring(
											ProblemInfo.lastIndexOf('\"') + 2, ProblemInfo
													.lastIndexOf(','))).append("\t");
							sb.append(
									ProblemInfo.substring(ProblemInfo.lastIndexOf(',') + 1,
											ProblemInfo.lastIndexOf(')'))).append("\r\n");
						}
						String ss = sb.substring(0, sb.length() - 2);
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/hduid.txt" + VolumeStart + "-" + VolumeEnd , true);
						fos.write(ss.getBytes());
						fos.write("\r\n".getBytes());
						mos.write(NullWritable.get(), new Text(ss), outpath);
						sb.setLength(0);
						reader.close();
						url.openStream().close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			}
			
			else if(Name.equals("POJ")){
				int count = 0;
				HttpURLConnection mg = null;
				outpath = "pojid" + "(" + VolumeStart + "-" + VolumeEnd + ")";
				for (int VolumeNumber = vstart; VolumeNumber <= vend; VolumeNumber++){
					try {
						URL url = new URL(constantValue.POJLink + VolumeNumber);
						mg = (HttpURLConnection) url.openConnection();
	    			    mg.setRequestMethod("GET");
	    			    mg.setRequestProperty("User-Agent",
	    						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    			    System.setProperty("sun.net.client.defaultConnectTimeout",
	    			    		String.valueOf(5000));
	    			    System.setProperty("sun.net.client.defaultReadTimeout",
	    			    		String.valueOf(100000));
	    			    mg.setConnectTimeout(5000);
	    			    mg.setReadTimeout(100000);
						BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
						String linepoj = null;
						StringBuffer sb = new StringBuffer();
						while (true) {
							linepoj = reader.readLine();
							if (linepoj == null
									|| linepoj.indexOf("<td width=12%>Date</td></tr>") != -1) {
								break;
							}
						}
						while (true) {
							linepoj = reader.readLine();
							if (linepoj == null || linepoj.equals("</table>")) {
								break;
							}
							while (!linepoj.endsWith("</td></tr>")) {
								linepoj = linepoj + reader.readLine();
							}
							count++;
							sb.append(count).append("\t");
							sb.append(linepoj.substring(21, 25)).append("\t");
							sb.append(linepoj.substring(82, linepoj.indexOf("</a></td><td>"))).append("\t");
							int begin = linepoj
									.lastIndexOf("(<a href=problemstatus?problem_id=") + 39;
							int end = linepoj.lastIndexOf("</a>/<a href=status?problem_id=");
							// ProblemAccept.add(line.substring(begin, end));
							sb.append(linepoj.substring(begin, end)).append("\t");
							begin = linepoj.lastIndexOf("</a>/<a href=status?problem_id=") + 36;
							end = linepoj.lastIndexOf("</a>)</td><td>");
							// ProblemSubmit.add(line.substring(begin, end));
							sb.append(linepoj.substring(begin, end)).append("\r\n");
						}
						String ss = sb.substring(0, sb.length() - 2);
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/pojid" + VolumeStart + "-" + VolumeEnd + ".txt", true);
						fos.write(ss.getBytes());
						fos.write("\r\n".getBytes());
						mos.write(NullWritable.get(), new Text(ss), outpath);
						sb.setLength(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
			else if(Name.equals("UVA")){
				int count = 0;
				HttpURLConnection mg = null;
				outpath = "uvaid" + "(" + VolumeStart + "-" + VolumeEnd + ")";
				for (int VolumeNumber = vstart; VolumeNumber <= vend; VolumeNumber++){
					String UVALink = constantValue.UVALink;
					try {
						URL url = null;		
						if (VolumeNumber < 2) {
							int temp = VolumeNumber + 2;
							url = new URL(UVALink + temp);}
						if (VolumeNumber < 10) {
							int temp = VolumeNumber + 2;
							url = new URL(UVALink + temp);
						} else if (VolumeNumber < 13) {
							int temp = VolumeNumber + 235;
							url = new URL(UVALink + temp);
							   } else if (VolumeNumber < 16) {
							     int temp = VolumeNumber + 433;
						         url = new URL(UVALink + temp);
					                 } else if (VolumeNumber < 32) {
								            int temp = VolumeNumber - 4;
							                url = new URL(UVALink + temp);
						                    } else if (VolumeNumber < 33) {
									                int temp = VolumeNumber + 46;
								                   url = new URL(UVALink + temp);
							                       } else if (VolumeNumber < 34) {
										                   int temp = VolumeNumber + 84;
										                   url = new URL(UVALink + temp);
									                       } else if (VolumeNumber < 35) {
												                   int temp = VolumeNumber + 192;
												                   url = new URL(UVALink + temp);
											                       } else if (VolumeNumber < 36) {
														                int temp = VolumeNumber + 194;
														                   url = new URL(UVALink + temp);
													                       } else if (VolumeNumber < 39) {
																                int temp = VolumeNumber + 206;
																                   url = new URL(UVALink + temp);
															                       } else if (VolumeNumber < 41) {
																		                int temp = VolumeNumber + 239;
																		                   url = new URL(UVALink + temp);
																	                       }else if (VolumeNumber < 42) {
																				                int temp = VolumeNumber + 400;
																				                   url = new URL(UVALink + temp);
																			                       }else if (VolumeNumber < 43) {
																						                int temp = VolumeNumber + 560;
																						                   url = new URL(UVALink + temp);
																					                       }else if(VolumeNumber < 44) {
																					                    	   int temp = VolumeNumber + 780;
																					                    	   url = new URL(UVALink + temp);
																					                       }
		
		    			    mg = (HttpURLConnection) url.openConnection();
		    			    mg.setRequestMethod("GET");
		    			    mg.setRequestProperty("User-Agent",
		    						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		    			    System.setProperty("sun.net.client.defaultConnectTimeout",
		    			    		String.valueOf(5000));
		    			    System.setProperty("sun.net.client.defaultReadTimeout",
		    			    		String.valueOf(100000));
		    			    mg.setConnectTimeout(5000);
		    			    mg.setReadTimeout(100000);
						BufferedReader reader = new BufferedReader(new InputStreamReader(
								url.openStream(), "utf-8"));
						StringBuffer sb = new StringBuffer();
						String lineuva = null;
						while (true) {
							lineuva = reader.readLine();
							if (lineuva
									.contains("<select name=\"limit\" class=\"inputbox\" size=\"1\" onchange=")) {
								break;
							}
							// System.out.println(line);
							if (lineuva
									.contains("<td><a href=\"index.php?option=com_onlinejudge&")) {
								count++;
								sb.append(count).append("\t");
								int begin = lineuva.indexOf("\">") + "\">".length();
								int end = lineuva.indexOf("&nbsp;");
								// System.out.println(begin + " to " + end);
								int ProblemNumber = Integer.valueOf(
										lineuva.substring(begin, end)).intValue();
								// ProblemID.add(OnlineJudgeName + ' ' + ProblemNumber);
								sb.append(ProblemNumber).append("\t");
								begin = end + "&nbsp;-&nbsp;".length();
								end = lineuva.indexOf("</a></td>");
								// ProblemName.add(line.substring(begin, end));
								sb.append(lineuva.substring(begin, end)).append("\t");
								/*
								 * if (VolumeNumber < 10) { ProblemNumber -= 64;
								 * ProblemURL.add(VolumeLink + VolumeNumber +
								 * "&page=show_problem&problem=" + ProblemNumber); } else if
								 * (VolumeNumber < 13) { ProblemNumber += 2441;
								 * ProblemURL.add(VolumeLink + VolumeNumber +
								 * "&page=show_problem&problem=" + ProblemNumber); }
								 */
								while (!lineuva.contains("<td align=\"right\">")) {
									lineuva = reader.readLine();
								}
								begin = lineuva.indexOf("<td align=\"right\">")
										+ "<td align=\"right\">".length();
								end = lineuva.indexOf("</td>");
								// ProblemSubmit.add(line.substring(begin, end));
								sb.append(lineuva.substring(begin, end)).append("\t");
								int submit = Integer.valueOf(lineuva.substring(begin, end))
										.intValue();
								while (!lineuva.contains("<div style=\"width:100%;")) {
									lineuva = reader.readLine();
								}
								begin = lineuva.indexOf("\">") + "\">".length();
								end = lineuva.indexOf("%</div>");
								float f = Float.valueOf(lineuva.substring(begin, end));
								int accept = (int) (submit * f / 100);
								// ProblemAccept.add(String.valueOf(accept));
								sb.append(String.valueOf(accept)).append("\r\n");
							}
						}
						String ss = sb.substring(0, sb.length() - 2);
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/uvaid" + VolumeStart + "-" + VolumeEnd + ".txt", true);
						fos.write(ss.getBytes());
						fos.write("\r\n".getBytes());
						mos.write(NullWritable.get(), new Text(ss), outpath);
						sb.setLength(0);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			else if(Name.equals("ZOJ")){
				int count = 0;
				HttpURLConnection mg = null;
				outpath = "zojid" + "(" + VolumeStart + "-" + VolumeEnd + ")";
				for (int VolumeNumber = vstart; VolumeNumber <= vend; VolumeNumber++){
					try {
						URL url = new URL(constantValue.ZOJLink + VolumeNumber);
						mg = (HttpURLConnection) url.openConnection();
	    			    mg.setRequestMethod("GET");
	    			    mg.setRequestProperty("User-Agent",
	    						"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    			    System.setProperty("sun.net.client.defaultConnectTimeout",
	    			    		String.valueOf(5000));
	    			    System.setProperty("sun.net.client.defaultReadTimeout",
	    			    		String.valueOf(100000));
	    			    mg.setConnectTimeout(5000);
	    			    mg.setReadTimeout(100000);
						BufferedReader reader = new BufferedReader(new InputStreamReader(
								url.openStream(), "utf-8"));
						String linezoj = null;
						StringBuffer sb = new StringBuffer();
						while (true) {
							linezoj = reader.readLine();
							if (linezoj == null) {
								break;
							}
							if (linezoj
									.indexOf("<td class=\"problemId\"><a href=\"/onlinejudge/showProblem.do?problemCode=") == -1) {
								continue;
							}
							
							count++;
							sb.append(count).append("\t");
							//id
							int index = linezoj.lastIndexOf("</font></a></td>");
							sb.append(linezoj.substring(index - 4, index)).append("\t");


							//name
							linezoj = reader.readLine();
							index = linezoj.lastIndexOf("</font></a></td>");
							sb.append(
									linezoj.substring(
											linezoj.indexOf("<font color=\"blue\">") + 19,
											index)).append("\t");
							int begin, end;
							do {
								linezoj = reader.readLine();
								begin = linezoj.indexOf("<td class=\"problemStatus\">");
							} while (begin == -1);
							if (linezoj.substring(begin + 26, begin + 31).equals("0.00%")) {
								// ProblemAccept.add("0");
								sb.append("0").append("\t");
							} else {
								begin = linezoj.indexOf("&judgeReplyIds=5'>") + 18;
								end = linezoj.lastIndexOf("</a>/<a href=");
								// ProblemAccept.add(line.substring(begin, end));
								sb.append(linezoj.substring(begin, end)).append("\t");
							}

							begin = linezoj
									.lastIndexOf("/<a href='/onlinejudge/showRuns.do?contestId=1&problemCode=") + 65;
							end = linezoj.lastIndexOf("</a>)</td>");
							// ProblemSubmit.add(line.substring(begin, end));
							sb.append(linezoj.substring(begin, end)).append("\r\n");
						}
						String ss = sb.substring(0, sb.length() - 2);
						FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/zojid" + VolumeStart + "-" + VolumeEnd + ".txt" ,true);
						fos.write(ss.getBytes());
						fos.write("\r\n".getBytes());
						mos.write(NullWritable.get(), new Text(ss), outpath);
						sb.setLength(0);
						
					} catch(Exception e){
						e.printStackTrace();
					}
				}//for
			}//else if
		}//map
		
		protected void cleanup(Context context) throws IOException, InterruptedException {
			mos.close();
			super.cleanup(context);
		}	
	}//MapInfo
	
	//Map Class of downloading url
	public static class MapUrl extends Mapper<LongWritable, Text, NullWritable, Text>{
		
		//返回输入文件的文件名	
		public String getFilePath(Context context) throws IOException {
			// FileSplit fileSplit = (FileSplit) context.getInputSplit();
			InputSplit split = context.getInputSplit();
			Class<? extends InputSplit> splitClass = split.getClass();
			FileSplit fileSplit = null;
			if (splitClass.equals(FileSplit.class)) {
				fileSplit = (FileSplit) split;
			} 
			else if (splitClass.getName().equals("org.apache.hadoop.mapreduce.lib.input.TaggedInputSplit")) {
				try {
					Method getInputSplitMethod = splitClass.getDeclaredMethod("getInputSplit");
					getInputSplitMethod.setAccessible(true);
					fileSplit = (FileSplit) getInputSplitMethod.invoke(split);
					} catch (Exception e) {
						throw new IOException(e);
						}
				}
			return fileSplit.getPath().getName().toString();
		}
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			String lineProb = value.toString();
			String[] sa = lineProb.split("\t");
			String Number = sa[0];//1
			String ID = sa[1];//1000
			String filename = getFilePath(context);//pojid(1-1)-m-0000
			String Volume = null;
			int VolumeNumber;
			if(filename.contains("hdu")){
				String Name = "HDU";
				VolumeNumber = Integer.parseInt(ID)/100 - 9;
				Volume = "Volume" + VolumeNumber;
				File Prourl = new File(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume);
				Prourl.mkdirs();
				GetUrl geturl = new GetUrl();
				geturl.url(filename, Name, Number, ID, Volume);
			}
			else if(filename.contains("poj")){
				String Name = "POJ";
				VolumeNumber = Integer.parseInt(ID)/100 - 9;
				Volume = "Volume" + VolumeNumber;
				File Prourl = new File(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume);
				Prourl.mkdirs();
				GetUrl geturl = new GetUrl();
				geturl.url(filename, Name, Number, ID, Volume);
			}
			else if(filename.contains("uva")){
				String Name = "UVA";
				VolumeNumber = Integer.parseInt(ID)/100;
				Volume = "Volume" + VolumeNumber;
				File Prourl = new File(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume);
				Prourl.mkdirs();
				GetUrl geturl = new GetUrl();
				geturl.url(filename, Name, Number, ID, Volume);
			}
			else if(filename.contains("zoj")){
				String Name = "ZOJ";
				VolumeNumber = (Integer.parseInt(ID) - 1)/100 - 9;
				Volume = "Volume" + VolumeNumber;
				File Prourl = new File(constantValue.ROOTDIR + "/url/" + Name + "/" + Volume);
				Prourl.mkdirs();
				GetUrl geturl = new GetUrl();
				geturl.url(filename, Name, Number, ID, Volume);
			}//else if
			context.write(NullWritable.get(), new Text());
		}//map
	}//MapUrl

	//Map Class of downloading report
	public static class MapReport extends Mapper<LongWritable, Text, NullWritable, Text>{
		
		public String getFilePath(Context context) throws IOException {
			// FileSplit fileSplit = (FileSplit) context.getInputSplit();
			InputSplit split = context.getInputSplit();
			Class<? extends InputSplit> splitClass = split.getClass();
			FileSplit fileSplit = null;
			if (splitClass.equals(FileSplit.class)) {
				fileSplit = (FileSplit) split;
			} 
			else if (splitClass.getName().equals("org.apache.hadoop.mapreduce.lib.input.TaggedInputSplit")) {
				try {
					Method getInputSplitMethod = splitClass.getDeclaredMethod("getInputSplit");
					getInputSplitMethod.setAccessible(true);
					fileSplit = (FileSplit) getInputSplitMethod.invoke(split);
					} catch (Exception e) {
						throw new IOException(e);
						}
				}
			return fileSplit.getPath().toString();
		}
		int cnt = 1;
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			String lineProb = value.toString();
			if (lineProb.isEmpty()){
				cnt = 1;
			}
			String[] sa = lineProb.split("\t");
			String ID = sa[0];//poj1001
			String website = sa[1];
			String path = getFilePath(context);// hdfs://localhost:9000/url/POJ/poj1000_website.txt;
			System.out.println("path = " + path);
			if (path.contains("HDU")){
				String Name = "HDU";
				File Proreport = new File(constantValue.ROOTDIR + "/report/" + Name + "/" + ID);
				Proreport.mkdirs();
				GetReport getreport = new GetReport();
				getreport.report(website, Name, ID, cnt++);
			}
			else if (path.contains("POJ")){
				if (ID.contains("pku"))
					ID = "poj" + ID.substring(3);
				String Name = "POJ";
				File Proreport = new File(constantValue.ROOTDIR + "/report/" + Name + "/" + ID);
				Proreport.mkdirs();
				GetReport getreport = new GetReport();
				getreport.report(website, Name, ID, cnt++);
			}
			else if (path.contains("UVA")){
				String Name = "UVA";
				File Proreport = new File(constantValue.ROOTDIR + "/report/" + Name + "/" + ID);
				Proreport.mkdirs();
				GetReport getreport = new GetReport();
				getreport.report(website, Name, ID, cnt++);
			}
			else if (path.contains("ZOJ")){
				String Name = "ZOJ";
				File Proreport = new File(constantValue.ROOTDIR + "/report/" + Name + "/" + ID);
				Proreport.mkdirs();
				GetReport getreport = new GetReport();
				getreport.report(website, Name, ID, cnt++);
			}
			
			context.write(NullWritable.get(), new Text());		
		}//map
	}//MapReport
	
	//Map Class of analyzing report
	public static int index = 0;
	public static class MapAnalyze extends Mapper<LongWritable, Text, NullWritable, Text>{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			
			String address = value.toString();
			String result = "";
			AnalyzeReport ap = new AnalyzeReport();
		
			result = ap.analyze(hsMapWords_Path, hsMapWords_Alias, hsMapWords_Prev, hsSetRootWords, address);
			if(!result.isEmpty())
				result = result.substring(0, result.length() - 2);
			context.write(NullWritable.get(), new Text(result));
		}
	}
	public static class ReduceAnalyze extends Reducer<NullWritable, Text, NullWritable, Text>{
		public void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
			for (Text value : values){
    			String valueString = value.toString();
    			if (!valueString.isEmpty()){
    				context.write(NullWritable.get(), new Text(valueString));
    			}	
    		}  			
		}
	}

	public static class MapCheck extends Mapper<LongWritable, Text, NullWritable, Text>{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			
			String address = value.toString();
			File file = new File(address);
			if (file.length() == 0)
				context.write(NullWritable.get(), new Text(address));
		}
	}
	
	public void parseExpertFile(String ROOTDIR){
		File fexp = new File(ROOTDIR+"/expert.txt");// fexp = /home/ck/CK_ACM/expert.txt
		hsMapWords_Path = new HashMap<String, String>();
		hsMapWords_Alias= new HashMap<String, String>();
		hsMapWords_Prev = new HashMap<String, String>();
		hsSetRootWords = new HashSet<String>();
		 
		 try {
			 BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(fexp), "gbk"));
			 String line = null;
			 String path = ROOTDIR; // /home/ck/CK_ACM
			 int lastdepth = -1;
			 while ((line = br.readLine()) != null) {
				 if(line.equals("")) {//遇到空行往后读
					 path = ROOTDIR;
					 lastdepth = -1;
					 continue;
				 }
				 int pos = 0;
				 
				 while (pos < line.length() && line.charAt(pos) == '\t') {
						pos++;//遇到tab向后读
					}
				int depth = pos;
				
				String seqs = "";//存放"01", "01.01"
				while (pos < line.length() && line.charAt(pos) != '\t') {
					seqs += line.charAt(pos);//把tab之间的内容存放到seqs
					pos++;
				}
				
				String[] numStr = seqs.split(".");
				int[] numDim = { 0, 0, 0 };
				for (int i = 0; i < numStr.length; i++) {//把带有.的编号存放到数组当中
					numDim[i] = Integer.parseInt(numStr[i]);
				}
				
				while (pos < line.length() && line.charAt(pos) == '\t') {
					pos++;//遇到tab向后读
				}
				
				String word = "";//存放"冒泡排序"， “插入排序”
				while (pos < line.length() && line.charAt(pos) != '\t') {
					word += line.charAt(pos);//把tab之间的内容存放到word
					pos++;
				}
				
				for (int i = 0; i <= lastdepth - depth; i++) {
					path = path.substring(0, path.lastIndexOf("/"));
				}
				path = path.concat("/" + word);
				hsMapWords_Path.put(word, path); //冒泡排序 = /home/ck/CK_ACM/排序/冒泡排序
				
				if (depth == 0) {
					hsSetRootWords.add(word);
				}
				
				String curr = word;
				String temp = path.substring(0, path.lastIndexOf("/"));
				while (!temp.equals(ROOTDIR)) {
					String prev = temp.substring(temp.lastIndexOf("/") + 1);
					if (!hsMapWords_Prev.containsKey(curr)) {
						hsMapWords_Prev.put(curr, prev);//冒泡排序 = 排序
						System.out.println(curr + "->" + prev);
					}
					curr = prev;
					temp = temp.substring(0, temp.lastIndexOf("/"));
				}
				lastdepth = depth;

				while (pos < line.length() && line.charAt(pos) != '(') {
					pos++;//找到左括号的位置
				}
				String[] aliasStr = line.substring(pos + 1, line.length() - 1)
						.split(",");
				for (int i = 0; i < aliasStr.length; i++) {
					hsMapWords_Alias.put(aliasStr[i], word);//把别名存放到map中
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//		Get topic id
//		Configuration conf1 = new Configuration();
//		Job jobinfo = new Job(conf1, "MainJob");
//		jobinfo.setJarByClass(MainJob_v2.class);
//		jobinfo.setOutputKeyClass(NullWritable.class);
//		jobinfo.setOutputValueClass(Text.class);
//		jobinfo.setMapperClass(MapInfo.class);
//		FileInputFormat.addInputPath(jobinfo, new Path("hdfs://localhost:9000/info/info.txt"));
//		String idpath = "hdfs://localhost:9000/id";
//		FileOutputFormat.setOutputPath(jobinfo, new Path(idpath));
//		jobinfo.waitForCompletion(true);
//		
//		//Dowdload url
//    	Configuration conf2 = new Configuration();
//    	conf2.set("mapred.textoutputformat.separator","\r\n");
//		Job joburl = new Job(conf2, "MainJob");
//		joburl.setJarByClass(MainJob_v2.class);
//		joburl.setMapperClass(MapUrl.class);
//		joburl.setOutputKeyClass(NullWritable.class);
//		joburl.setOutputValueClass(Text.class);
//		String hduid = "hdfs://localhost:9000/id";
//		MultipleInputs.addInputPath(joburl, new Path(hduid), TextInputFormat.class, MapUrl.class);
//		FileOutputFormat.setOutputPath(joburl, new Path("hdfs://localhost:9000/successurl"));
//		joburl.waitForCompletion(true);
//		
//		//Upload local url folder to the hdfs
//		Configuration conf3 = new Configuration();
//		try{
//			conf3.set("fs.default.name", "hdfs://localhost:9000");
//			FileSystem hdfs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf3);
//			FileSystem localfs = FileSystem.getLocal(conf3);
//			String localstr = "/home/ck/CK_ACM/url";
//			String dst = "/url";
//			hdfs.mkdirs(new Path(dst));
//			if (!localfs.exists(new Path(localstr)) || !hdfs.exists(new Path(dst))){
//				System.out.println("Error: local folder does not exist OR target folder does not exist! ");
//				return 0;
//			}
//			if(!hdfs.getFileStatus(new Path(dst)).isDir()){
//				System.out.println("Error: Destination is not a folder");
//				return 0;
//			}
//			boolean dir = localfs.getFileStatus(new Path(localstr)).isDir();
//			
//			if(dir){
//				FileStatus[] status = localfs.listStatus(new Path(localstr));
//				for (FileStatus sta : status){// sta = /home/ck/CK_ACM/url/HDU		
//					Path path = sta.getPath();
//					hdfs.copyFromLocalFile(false, true, path, new Path(dst));
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		//Download report from Internet
//		Configuration conf4 = new Configuration();
//		conf4.set("mapred.textoutputformat.separator","\r\n");
//		Job jobreport = new Job(conf4, "MainJob");
//		jobreport.setJarByClass(MainJob_v2.class);
//		jobreport.setMapperClass(MapReport.class);
//		jobreport.setOutputKeyClass(NullWritable.class);
//		jobreport.setOutputValueClass(Text.class);
//		MultipleInputs.addInputPath(jobreport, new Path("hdfs://localhost:9000/url/POJ/Volume1"), TextInputFormat.class, MapReport.class);
//
//		FileOutputFormat.setOutputPath(jobreport, new Path("hdfs://localhost:9000/successreport"));
//		jobreport.waitForCompletion(true);
		
		//Upload local reports' paths to the hdfs
//		Configuration conf5 = new Configuration();
//		try{
//			conf5.set("fs.default.name", "hdfs://localhost:9000");
//			FileSystem hdfs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf5);
//			FileSystem localfs = FileSystem.getLocal(conf5);
//			hdfs.mkdirs(new Path("hdfs://localhost:9000/report"));
//			String localstr = "/home/ck/CK_ACM/report";
//			String dst = "/report";
//			if (!localfs.exists(new Path(localstr)) || !hdfs.exists(new Path(dst))){
//				System.out.println("Error: local folder does not exist OR target folder does not exist! ");
//				return 0;
//			}
//			if(!hdfs.getFileStatus(new Path(dst)).isDir()){
//				System.out.println("Error: Destination is not a folder");
//				return 0;
//			}
//			boolean dir = localfs.getFileStatus(new Path(localstr)).isDir();
//			
//			if(dir){
//				FileStatus[] NameFolder = localfs.listStatus(new Path(localstr));
//				for (FileStatus namefolder : NameFolder){//namefolder = /home/ck/CK_ACM/report/POJ
//					Path path = namefolder.getPath();
//					String pa = namefolder.getPath().toString().replace("/home/ck/CK_ACM/report", "hdfs://localhost:9000/report").replace("file:", "");
//					FSDataOutputStream out = hdfs.create(new Path(pa));
//					FileStatus[] IDFolder = localfs.listStatus(path);
//					for (FileStatus idfolder : IDFolder){//idfolder = /home/ck/CK_ACM/report/POJ/POJ1000
//						String content = idfolder.getPath().toString().replace("file:", "");
//						content = content + "\r\n";
//						out.write(content.getBytes());	
//					}
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		//Analyze the knowledgepoint in the report
		parseExpertFile(constantValue.ROOTDIR);
		Configuration conf6 = new Configuration();
		Job jobanalyze = new Job(conf6, "MainJob");
		jobanalyze.setJarByClass(MainJob_v2.class);
		jobanalyze.setMapperClass(MapAnalyze.class);
		jobanalyze.setReducerClass(ReduceAnalyze.class);
		jobanalyze.setOutputKeyClass(NullWritable.class);
		jobanalyze.setOutputValueClass(Text.class);
		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report/HDU"), TextInputFormat.class, MapAnalyze.class);	
		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report/POJ"), TextInputFormat.class, MapAnalyze.class);	
		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report/UVA"), TextInputFormat.class, MapAnalyze.class);	
		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report/ZOJ"), TextInputFormat.class, MapAnalyze.class);	

		FileOutputFormat.setOutputPath(jobanalyze, new Path("hdfs://localhost:9000/knowledgepoint"));
		jobanalyze.waitForCompletion(true);
		
		
		
//		Configuration conf7 = new Configuration();
//		try{
//			conf7.set("fs.default.name", "hdfs://localhost:9000");
//			FileSystem hdfs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf7);
//			FileSystem localfs = FileSystem.getLocal(conf7);
//			hdfs.mkdirs(new Path("hdfs://localhost:9000/report_check"));
//			String localstr = "/home/ck/CK_ACM/report";
//			String dst = "/report";
//			if (!localfs.exists(new Path(localstr)) || !hdfs.exists(new Path(dst))){
//				System.out.println("Error: local folder does not exist OR target folder does not exist! ");
//				return 0;
//			}
//			if(!hdfs.getFileStatus(new Path(dst)).isDir()){
//				System.out.println("Error: Destination is not a folder");
//				return 0;
//			}
//			boolean dir = localfs.getFileStatus(new Path(localstr)).isDir();
//			
//			if(dir){
//				FileStatus[] NameFolder = localfs.listStatus(new Path(localstr));
//				for (FileStatus namefolder : NameFolder){//namefolder = /home/ck/CK_ACM/report/POJ
//					Path path = namefolder.getPath();
//					String pa = namefolder.getPath().toString().replace("/home/ck/CK_ACM/report", "hdfs://localhost:9000/report_check").replace("file:", "");
//					FSDataOutputStream out = hdfs.create(new Path(pa));
//					FileStatus[] IDFolder = localfs.listStatus(path);
//					for (FileStatus idfolder : IDFolder){//idfolder = /home/ck/CK_ACM/report/POJ/POJ1000
//					    path = idfolder.getPath();
//					    FileStatus[] File = localfs.listStatus(path);
//					    for (FileStatus file : File){
//					    	String content = file.getPath().toString().replace("file:", "");
//							content = content + "\r\n";
//							out.write(content.getBytes());	
//					    }		
//					}
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		Configuration conf8 = new Configuration();
//		Job jobanalyze = new Job(conf8, "MainJob");
//		jobanalyze.setJarByClass(MainJob_v2.class);
//		jobanalyze.setMapperClass(MapCheck.class);
//		jobanalyze.setOutputKeyClass(NullWritable.class);
//		jobanalyze.setOutputValueClass(Text.class);
//		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report_check/HDU"), TextInputFormat.class, MapCheck.class);	
//		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report_check/POJ"), TextInputFormat.class, MapCheck.class);	
//		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report_check/UVA"), TextInputFormat.class, MapCheck.class);	
//		MultipleInputs.addInputPath(jobanalyze, new Path("hdfs://localhost:9000/report_check/ZOJ"), TextInputFormat.class, MapCheck.class);	
//
//		FileOutputFormat.setOutputPath(jobanalyze, new Path("hdfs://localhost:9000/check"));
//		jobanalyze.waitForCompletion(true);
		
		return 1;
	}
	
	
	
	public static void main(String[] args) throws Exception{
		
		long startTime =  System.currentTimeMillis();
		int res = ToolRunner.run(new Configuration(), new MainJob_v2(), args);
		long endTime = System.currentTimeMillis();
		System.out.print("Running Time：" + (endTime - startTime) + "ms");
		System.exit(res);
	}
}
