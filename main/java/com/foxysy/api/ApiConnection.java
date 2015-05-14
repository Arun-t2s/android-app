package com.foxysy.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApiConnection {

	public static final String GETALL_DETAILS = "http://foxy.sy2.com/app_api_config.php?id=2";

	public String getAllDetails() {

		String result = "";
		BufferedReader bufferedReader = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(GETALL_DETAILS);

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		/*postParameters.add(new BasicNameValuePair("device_id", device_id));
		postParameters.add(new BasicNameValuePair("start_date", start_date));
		postParameters.add(new BasicNameValuePair("end_date", end_date));*/

		try {

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
			request.setEntity(entity);
			HttpResponse response = httpClient.execute(request);
			
			bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LineSeparator = System.getProperty("line.separator");
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LineSeparator);
			}

			bufferedReader.close();
			result = stringBuffer.toString();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}

}
