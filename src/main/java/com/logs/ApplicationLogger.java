package com.logs;

import java.time.Instant;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;

public class ApplicationLogger {

    public static void addLog(HttpServletRequest req,String className,String level,String msg) {

        JSONArray applicationLogs = (JSONArray) req.getAttribute("applicationLogs");

        if (applicationLogs == null) {
            applicationLogs = new JSONArray();
        }

        JSONObject logFields = new JSONObject();

        logFields.put("className", new JSONObject().put("stringValue", className));
        logFields.put("level", new JSONObject().put("stringValue", level));
        logFields.put("timestamp", new JSONObject().put("stringValue", Instant.now().toString()));
        logFields.put("msg", new JSONObject().put("stringValue", msg));

        JSONObject logObjectValue = new JSONObject().put("mapValue", new JSONObject().put("fields", logFields));

        applicationLogs.put(logObjectValue);

        req.setAttribute("applicationLogs", applicationLogs);
    }
}