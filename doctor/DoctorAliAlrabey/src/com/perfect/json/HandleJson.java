/*
 * author mohamed hamada 
 * this class used to connect to server and convert bytes to string 
 * use this class from every object want get data from server
 */

package com.perfect.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.main.activities.TwitterLoginActivity;

/*
 * 
 * this class use to read JSON from WEBSERVICE and back in two ArrayList
 */
public class HandleJson {
	// stream which back from server
	public static InputStream is;
	// string which back after convert from byte to string
	public static String resultfroConFromStreamToStr;

	public static String responseBody;

	public static String token;

	// # convert stream [byte] to string
	/*
	 * convert stream to string
	 */
	public static void convertStreamToString() throws IOException {

		if (is.available() != -1) { // must check is avaliable before convert
			// BufferedReader reader = new BufferedReader(new InputStreamReader(
			// is, "utf-8"), 8);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close(); // close input stream
			resultfroConFromStreamToStr = sb.toString();

		}

	}

	// End#

	// # fetch data from server
	/*
	 * fetch webservice to urlString and receive data in json form
	 */

	@SuppressWarnings("deprecation")
	public static void fetchJSON(String urlString, String method)
			throws ClientProtocolException, IOException, JSONException {

		List<NameValuePair> pairs = new ArrayList<NameValuePair>();

		HttpClient httpclient = new DefaultHttpClient();

		if (method == "get") {
			HttpGet httppget = new HttpGet(urlString);

			httppget.setHeader("Accept", "*/*");
			httppget.setHeader("Connection", "close");
			httppget.setHeader("User-Agent", "OAuth gem v0.4.4");
			httppget.setHeader("Content-type",
					"application/x-www-form-urlencoded");

			long x=System.currentTimeMillis();
			// nonce,oauth signature,time stamp changed
			httppget.setHeader(
					"Authorization",
					"OAuth oauth_consumer_key=iu0F3yw8m8wPsaDsix6SrJyx1"
							+ ", oauth_nonce=9931d274c6e4cae5ef79bbd3d4e1f1e3"
							+ ", oauth_signature="
							+ "q4hhZjShaxpKvpebxJw1z2ar8ZI%3D"
							+ ", "
							+ "oauth_signature_method="
							+ "HMAC-SHA1"
							+ ", oauth_timestamp="
							+ "1440060153"
							+ ", oauth_token="
							// +
							// "360856741-XB2DyifTIylYIQe2JYTtUuW4vNe2yl39m6aR5O4a"
							+ "360856741-XB2DyifTIylYIQe2JYTtUuW4vNe2yl39m6aR5O4a"
							+ ", oauth_version=" + "1.0");

			//
			//
			// httppget.setHeader("Content-Length", "76");
			httppget.setHeader("Host", "api.twitter.com");

			HttpResponse response = httpclient.execute(httppget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}

	}

	// End#

	// # fetch data and convert stream back to String
	/*
	 * any where obj want to get data from server will call this method path
	 * only URL receive String in json Form
	 */
	public static String getDataFromServer(String urlWebService, String method) {
		try {
			fetchJSON(urlWebService, method);
		} catch (Exception e) {
			// check if error happen
			// this error will back and show it
			return "can't connect to server " + e;
		}
		try {
			convertStreamToString();
		} catch (Exception e) {
			// check if error happen
			return "can't read stream back from server " + e;
		}
		return resultfroConFromStreamToStr;
	}
	// End#
}
