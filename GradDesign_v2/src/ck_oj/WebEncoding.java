/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ck_oj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebEncoding {

	public String getCharset(String strurl) throws IOException {

		URL url = new URL(strurl);

		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    System.setProperty("sun.net.client.defaultConnectTimeout",
	    		String.valueOf(30000));
	    System.setProperty("sun.net.client.defaultReadTimeout",
	    		String.valueOf(30000));
	    urlConnection.setConnectTimeout(30000);
	    urlConnection.setReadTimeout(30000);
	    urlConnection.connect();
	    if(HttpURLConnection.HTTP_OK != urlConnection.getResponseCode()){
	    	return "error";
	    }

		String strencoding = null;

		StringBuffer sb = new StringBuffer();
		String line;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) { // Report any errors that arise
			System.err.println(e);
			System.err.println("Usage:   java   HttpClient   <URL>   [<filename>]");
			System.err.println(strurl);
		}
		String htmlcode = sb.toString();

		String strbegin = "<meta";
		String strend = ">";
		String strtmp;

		int begin = htmlcode.indexOf("charset");
		int end;
		if (begin != -1 && htmlcode.substring(begin+8,begin+9).contains("\""))
			end = htmlcode.indexOf("\"",begin+9);
		else
			end=htmlcode.indexOf("\"",begin);
		if(begin!=-1 && end!=-1)
		{
			strencoding = htmlcode.substring(begin + 7, end).replace(
					"=", "").replace("/", "").replace("\"", "")
					.replace("\'", "").replace(" ", "");
			return strencoding;
		}

		Map<String, List<String>> map = urlConnection.getHeaderFields();
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();


		String key = null;
		String tmp = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			tmp = map.get(key).toString().toLowerCase();

			if (key != null && key.equals("Content-Type")) {
				int m = tmp.indexOf("charset=");
				if (m != -1) {
					strencoding = tmp.substring(m + 8).replace("]", "");
					return strencoding;
				}
			}
		}


		if (strencoding == null) {
			strencoding = "GBK";
		}

		return strencoding;
	}
}
