package com.firestore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

public class FirestoreService {

	private static final String PROJECT_ID = "logsaccessproj";
	private static final String ACCESS_LOGS_COLLECTION = "access_logs";

	private static final String ACCESS_LOGS_URL = "https://firestore.googleapis.com/v1/projects/" + PROJECT_ID
			+ "/databases/(default)/documents/" + ACCESS_LOGS_COLLECTION;

	public static void sendAccessLogToFirestore(String ip, String method, String endpoint, String timestamp, int status, long responseTime, JSONArray applicationLogsJson) {

		try {
			String json = "{ \"fields\": {" + "\"ip\": {\"stringValue\": \"" + ip + "\"}," + "\"method\": {\"stringValue\": \"" + method + "\"}," + "\"endpoint\": {\"stringValue\": \"" + endpoint + "\"}," + "\"timestamp\": {\"stringValue\": \"" + timestamp + "\"}," + "\"status\": {\"integerValue\": \"" + status + "\"}," + "\"responseTime\": {\"integerValue\": \"" + responseTime + "\"}," + "\"application_logs\": {\"arrayValue\": {\"values\": " + applicationLogsJson + "}}" + "}}";
			System.out.println(json);

			URL url = new URL(ACCESS_LOGS_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			conn.setDoOutput(true);
			
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			os.close();

			System.out.println("Firestore Response Code: " + conn.getResponseCode());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}