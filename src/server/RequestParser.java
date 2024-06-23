package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null || line.isEmpty()) {
            throw new IOException("Empty request line");
        }

        // Parse request line
        String[] requestLineParts = line.split(" ");
        if (requestLineParts.length != 3) {
            throw new IOException("Incorrect request form");
        }

        String httpCommand = requestLineParts[0];
        String uri = requestLineParts[1];

        // Parse URI segments and parameters
        String[] uriSegments;
        Map<String, String> parameters = new HashMap<>();
        
        int questionMarkIndex = uri.indexOf("?");
        if (questionMarkIndex != -1) {
            String path = uri.substring(0, questionMarkIndex);
            String queryString = uri.substring(questionMarkIndex + 1);
            uriSegments = path.split("/");
            
            String[] queryParams = queryString.split("&");
            for (String param : queryParams) {
                String[] paramParts = param.split("=");
                if (paramParts.length == 2) {
                    parameters.put(paramParts[0], paramParts[1]);
                } 
            }
        } else {
            uriSegments = uri.split("/");
        }

        // Skip headers
        while ((line = reader.readLine()) != null && !line.isEmpty()) {}
        
        // Parse body
        StringBuilder bodyBuilder = new StringBuilder();
        while (reader.ready()) {
            bodyBuilder.append((char) reader.read());
        }

        byte[] content = bodyBuilder.toString().getBytes();

        return new RequestInfo(httpCommand, uri, uriSegments, parameters, content);
    }

    // RequestInfo given internal class
    public static class RequestInfo {
        private final String httpCommand;
        private final String uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters,
                byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }

        public String getHttpCommand() {
            return httpCommand;
        }

        public String getUri() {
            return uri;
        }

        public String[] getUriSegments() {
            return uriSegments;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public byte[] getContent() {
            return content;
        }
    }
}