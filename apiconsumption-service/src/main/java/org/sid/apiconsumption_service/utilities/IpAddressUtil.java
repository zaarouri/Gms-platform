package org.sid.apiconsumption_service.utilities;

import jakarta.servlet.http.HttpServletRequest;

public class IpAddressUtil {

    /**
     * Extracts the real client IP address from the HttpServletRequest.
     * It checks for common proxy headers such as "X-Forwarded-For", "X-Real-IP", etc.
     * If none are found, it falls back to the request's remote address.
     *
     * @param request HttpServletRequest object
     * @return the real client IP address, or null if unable to determine.
     */
    public static String getClientIp(HttpServletRequest request) {
        String[] headerCandidates = {
                "X-Forwarded-For",  // Most common
                "X-Real-IP",        // Sometimes used by proxies
                "Proxy-Client-IP",  // Some proxies
                "WL-Proxy-Client-IP", // WebLogic
                "HTTP_CLIENT_IP",   // Used by some proxies
                "HTTP_X_FORWARDED_FOR" // Some proxies with multiple IPs
        };

        String ipAddress = null;

        // Check each header in the list
        for (String header : headerCandidates) {
            ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                break;  // Exit if we found a non-empty, non-"unknown" IP
            }
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();  // Fallback to remote address if no proxy header found
        } else {
            // In case of multiple IPs in the "X-Forwarded-For" header, take the first one
            ipAddress = ipAddress.split(",")[0].trim();
        }

        // Optionally, you can add validation logic to ensure it's a valid IPv4/IPv6 address
        return ipAddress;
    }
}
