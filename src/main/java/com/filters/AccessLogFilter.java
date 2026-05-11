package com.filters;

import java.io.IOException;
import java.time.Instant;

import org.json.JSONArray;

import com.firestore.FirestoreService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccessLogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		long startTime = System.currentTimeMillis();

		String endpoint = req.getRequestURI();
		String method = req.getMethod();
		String ip = req.getRemoteAddr();

		chain.doFilter(request, response);

		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		int status = res.getStatus();
		String timestamp = Instant.now().toString();
		
		JSONArray applicationLogs = (JSONArray) req.getAttribute("applicationLogs");
		if (applicationLogs == null) {
			applicationLogs = new JSONArray();
		}

		FirestoreService.sendAccessLogToFirestore(ip, method, endpoint, timestamp, status, responseTime, applicationLogs);
	}
}