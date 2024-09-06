package org.sid.apiconsumption_service.utilities;


import jakarta.servlet.http.HttpServletRequest;


public class IpAddressUtil {

    /**
     * Extracts the real client IP address from the HttpServletRequest.
     * It checks for the "X-Forwarded-For" header first (which is typically added by proxies/load balancers),
     * and if it's absent, it falls back to the request's remote address.
     *
     * @param request HttpServletRequest object
     * @return the real client IP address
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        } else {
            // In case of multiple IPs in X-Forwarded-For, take the first one
            ipAddress = ipAddress.split(",")[0].trim();
        }
        return ipAddress;
    }
}
