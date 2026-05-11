package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.logs.ApplicationLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HiServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String className = this.getClass().getName();
		try {
			ApplicationLogger.addLog(req, className, "INFO", "Request received for HiServlet.");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain;charset=UTF-8");

			PrintWriter out = resp.getWriter();
			out.print("hi amigo");

			ApplicationLogger.addLog(req, className, "INFO", "Response sent for HiServlet.");
		} catch (Exception e) {
			ApplicationLogger.addLog(req, className, "ERROR", "Exception in HiServlet: " + e.getMessage());
			throw e;
		}
	}
}