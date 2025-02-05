package dataObject;

import java.util.HashMap;
import java.util.Map;

public class HeadersConfiguration {
    public Map<String, String> header;

    public HeadersConfiguration() {
        header = new HashMap<>();
        header.put("access-control-allow-headers", "Content-Type,api_key,Authorization ");
        header.put("access-control-allow-methods", "GET,POST,DELETE,PUT");
        header.put("content-type", "application/json");
        header.put("accept", "application/json");
    }

    public Map<String, String> getHeaders() {
        return header;
    }
}
