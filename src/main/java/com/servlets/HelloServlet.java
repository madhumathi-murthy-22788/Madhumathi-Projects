package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.logs.ApplicationLogger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ApplicationLogger.addLog(req, this.getClass().getName(), "INFO", "Request received for HelloServlet.");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("hello amigo");
            ApplicationLogger.addLog(req, this.getClass().getName(), "INFO", "Response sent for HelloServlet.");
        } catch (Exception e) {
            ApplicationLogger.addLog(req, this.getClass().getName(), "ERROR", "Exception in HelloServlet: " + e.getMessage());
            throw e;
        }
   
    }
}