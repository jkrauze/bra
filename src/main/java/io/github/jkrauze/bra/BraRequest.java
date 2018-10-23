package io.github.jkrauze.bra;

import com.fasterxml.jackson.databind.JsonNode;

public class BraRequest {

    private String method;
    private String path;
    private JsonNode body;

    @java.beans.ConstructorProperties({"method", "path", "body"})
    BraRequest(String method, String path, JsonNode body) {
        this.method = method;
        this.path = path;
        this.body = body;
    }

    public static BraRequestBuilder builder() {
        return new BraRequestBuilder();
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public JsonNode getBody() {
        return this.body;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BraRequest)) return false;
        final BraRequest other = (BraRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$method = this.getMethod();
        final Object other$method = other.getMethod();
        if (this$method == null ? other$method != null : !this$method.equals(other$method)) return false;
        final Object this$path = this.getPath();
        final Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $method = this.getMethod();
        result = result * PRIME + ($method == null ? 43 : $method.hashCode());
        final Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof BraRequest;
    }

    public String toString() {
        return "BraRequest(method=" + this.getMethod() + ", path=" + this.getPath() + ", body=" + this.getBody() + ")";
    }

    public static class BraRequestBuilder {
        private String method;
        private String path;
        private JsonNode body;

        BraRequestBuilder() {
        }

        public BraRequest.BraRequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        public BraRequest.BraRequestBuilder path(String path) {
            this.path = path;
            return this;
        }

        public BraRequest.BraRequestBuilder body(JsonNode body) {
            this.body = body;
            return this;
        }

        public BraRequest build() {
            return new BraRequest(method, path, body);
        }

        public String toString() {
            return "BraRequest.BraRequestBuilder(method=" + this.method + ", path=" + this.path + ", body=" + this.body + ")";
        }
    }
}
