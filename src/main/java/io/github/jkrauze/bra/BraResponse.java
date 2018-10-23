package io.github.jkrauze.bra;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.Map;

public class BraResponse {

    private long timestamp;
    private String method;
    private String path;
    @JsonRawValue
    private String body;
    private int status;
    private Map<String, String> headers;

    @java.beans.ConstructorProperties({"timestamp", "method", "path", "body", "status", "headers"})
    BraResponse(long timestamp, String method, String path, String body, int status, Map<String, String> headers) {
        this.timestamp = timestamp;
        this.method = method;
        this.path = path;
        this.body = body;
        this.status = status;
        this.headers = headers;
    }

    public static BraResponseBuilder builder() {
        return new BraResponseBuilder();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getBody() {
        return this.body;
    }

    public int getStatus() {
        return this.status;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BraResponse)) return false;
        final BraResponse other = (BraResponse) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getTimestamp() != other.getTimestamp()) return false;
        final Object this$method = this.getMethod();
        final Object other$method = other.getMethod();
        if (this$method == null ? other$method != null : !this$method.equals(other$method)) return false;
        final Object this$path = this.getPath();
        final Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        if (this.getStatus() != other.getStatus()) return false;
        final Object this$headers = this.getHeaders();
        final Object other$headers = other.getHeaders();
        if (this$headers == null ? other$headers != null : !this$headers.equals(other$headers)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $timestamp = this.getTimestamp();
        result = result * PRIME + (int) ($timestamp >>> 32 ^ $timestamp);
        final Object $method = this.getMethod();
        result = result * PRIME + ($method == null ? 43 : $method.hashCode());
        final Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        result = result * PRIME + this.getStatus();
        final Object $headers = this.getHeaders();
        result = result * PRIME + ($headers == null ? 43 : $headers.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof BraResponse;
    }

    public String toString() {
        return "BraResponse(timestamp=" + this.getTimestamp() + ", method=" + this.getMethod() + ", path=" + this.getPath() + ", body=" + this.getBody() + ", status=" + this.getStatus() + ", headers=" + this.getHeaders() + ")";
    }

    public static class BraResponseBuilder {
        private long timestamp;
        private String method;
        private String path;
        private String body;
        private int status;
        private Map<String, String> headers;

        BraResponseBuilder() {
        }

        public BraResponse.BraResponseBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public BraResponse.BraResponseBuilder method(String method) {
            this.method = method;
            return this;
        }

        public BraResponse.BraResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public BraResponse.BraResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public BraResponse.BraResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public BraResponse.BraResponseBuilder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public BraResponse build() {
            return new BraResponse(timestamp, method, path, body, status, headers);
        }

        public String toString() {
            return "BraResponse.BraResponseBuilder(timestamp=" + this.timestamp + ", method=" + this.method + ", path=" + this.path + ", body=" + this.body + ", status=" + this.status + ", headers=" + this.headers + ")";
        }
    }
}
