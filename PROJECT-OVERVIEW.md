# AccessLogs — Project Overview

---

## Purpose

**AccessLogs** is a demonstration web application (Jakarta EE 10 / Servlet 5) designed to show **centralized HTTP access logging to Google Cloud Firestore**. Every HTTP request is intercepted by a filter that records key data:

- Client IP address
- HTTP method
- Request URI
- Response status code
- Request duration (milliseconds)

Each request results in a single Firestore document written to the `access_logs` collection in the **`logsaccessproj`** GCP project, using the Firestore REST API (`firestore.googleapis.com/v1`). 

Endpoints such as `/hi`, `/hello`, and `/welcome` illustrate attaching structured **application-level log messages** (INFO/ERROR entries with class/source and text) to the same access log entry—collected in an **`application_logs`** array—removing the need for multiple Firestore writes per request.

A sample `index.html` provides buttons to manually test these endpoints via AJAX.
---

## Change History

This section lists tracked changes in order, as reflected by version control for the `AccessLogs` directory.

1. **Initial Project Setup**  
   - Created a servlet-based demo with Firestore integration.
   - Configured `AccessLogFilter` on all paths (`/*`).
   - Added example servlets.

2. **AccessLogFilter Improvements**  
   - Updated to use `doFilter(ServletRequest, ServletResponse, FilterChain)`, the standard Jakarta signature.
   - Casts to `HttpServletRequest` / `HttpServletResponse` for modern servlet compatibility.
   - Collects response timing, status, and `applicationLogs` attribute after `chain.doFilter`.
   - Invokes `FirestoreService.sendAccessLogToFirestore(...)` with these details.

3. **Consistent Firestore Call Signature**  
   - Argument order for `sendAccessLogToFirestore` (and its use in the filter) standardized to:  
     `(ip, method, endpoint, timestamp, status, responseTime, applicationLogs)`

4. **FirestoreService Payload Structure**  
   - Refactored outgoing JSON:  
     - **Before:** Used an invalid format, placing `applicationLogs.toString()` into a field, not following Firestore's expected schema.
     - **After:** Now includes fields like `responseTime` and a properly encoded `application_logs` array following Firestore's REST conventions.
   - Clarified console log messaging for Firestore response codes.

5. **ApplicationLogger Utility**  
   - Added new class `com.logs.ApplicationLogger`.
   - Collects structured log entries per request as JSON objects formatted for Firestore (`mapValue`, `stringValue`, etc.) so they're sent together by the filter.

6. **Servlet Logging Refactor**  
   - Removed direct `FirestoreService.sendApplicationLog` calls from endpoints.
   - Servlets now use `ApplicationLogger.addLog` to record events (e.g., "request received", "response sent", errors), aggregating logs per request.

7. **Eclipse Build Configuration**  
   - Added `json-20220924.jar` and `jakarta.servlet-api-6.1.0.jar` to the Eclipse `.classpath`.
   - Servlet API is marked as non-deployment for local development use only.

---

## Notes

- `WEB-INF/web.xml` declares a **`LogViewerServlet`** mapped to `/logs`, but `com.servlets.LogViewerServlet` does **not** exist in the source tree. Deployment will fail unless this class is created, or the mapping is removed.

---

## Repository Layout

| Area                          | Description                                              |
|-------------------------------|---------------------------------------------------------|
| `com.filters.AccessLogFilter`  | Global filter that logs and writes to Firestore per request. |
| `com.firestore.FirestoreService` | Forms JSON and POSTs directly to Firestore REST API.      |
| `com.logs.ApplicationLogger`   | Collects and formats application-level logs.            |
| `com.servlets.*`              | Example endpoints for demonstration.                    |
| `src/main/webapp`             | Static assets, `index.html`, `WEB-INF/web.xml`, and dependencies. |

---
*This document summarizes the AccessLogs project’s scope, history, and code organization.*
