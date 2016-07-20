package ck_oj;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetId {
	

	ConstantValue constantValue = new ConstantValue();
	
	
	public void ID (String Name, String VolumeStart, String VolumeEnd){
		
		int vstart = Integer.parseInt(VolumeStart);
		int vend = Integer.parseInt(VolumeEnd);
		
		if(Name.equals("HDU")){
			int count = 0;
			HttpURLConnection mg = null;
			
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
					
					//hdu题目格式  p(0,1000,-1,"A + B Problem",120021,379781)
					Pattern pattern = Pattern.compile("p\\(\\d,\\d+,[^;]+;");
					Matcher matcher = pattern.matcher(linehdu);
					while (matcher.find()) {
						String ProblemInfo = matcher.group();
						int index = ProblemInfo.indexOf(',') + 1;
						
						count++;
						sb.append(count).append("\t");
						//获取题目id
						sb.append(ProblemInfo.substring(index, index + 4)).append("\t");
						//获取题目名称
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
					FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/hduid.txt" + VolumeStart + "-" + VolumeEnd );
					fos.write(sb.toString().getBytes());
					sb.setLength(0);
					reader.close();
					url.openStream().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		
		//获取poj的id文件
		else if(Name.equals("POJ")){
			int count = 0;
			HttpURLConnection mg = null;
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
					FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/pojid" + VolumeStart + "-" + VolumeEnd + ".txt");
					fos.write(sb.toString().getBytes());
					sb.setLength(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		//获取uva的id文件
		else if(Name.equals("UVA")){
			int count = 0;
			HttpURLConnection mg = null;
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
					FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/uvaid" + VolumeStart + "-" + VolumeEnd + ".txt");
					fos.write(sb.toString().getBytes());
					sb.setLength(0);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//获取zoj的id文件
		else if(Name.equals("ZOJ")){
			int count = 0;
			HttpURLConnection mg = null;
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
						count++;
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
						
					FileOutputStream fos = new FileOutputStream(constantValue.ROOTDIR + "/id/zojid" + VolumeStart + "-" + VolumeEnd + ".txt" );
					fos.write(sb.toString().getBytes());
					sb.setLength(0);
					
				} catch(Exception e){
					e.printStackTrace();
				}
			}//for
		}//else if
	}
}
