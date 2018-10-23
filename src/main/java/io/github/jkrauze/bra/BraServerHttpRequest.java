package io.github.jkrauze.bra;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

public class BraServerHttpRequest implements ServerHttpRequest {

    private final static Jackson2JsonEncoder JSON_ENCODER = new Jackson2JsonEncoder();
    private final static DataBufferFactory BUFFER_FACTORY = new DefaultDataBufferFactory();
    private final static ResolvableType RESOLVABLE_TYPE = ResolvableType.forClass(JsonNode.class);

    private RequestPath path;
    private MultiValueMap<String, String> queryParams;
    private MultiValueMap<String, HttpCookie> cookies;
    private String methodValue;
    private Flux<DataBuffer> body;
    private HttpHeaders headers;
    private URI uri;

    public static BraServerHttpRequest of(ServerHttpRequest origRequest, BraRequest braRequest) {
        URI uri = origRequest.getURI().resolve(braRequest.getPath());
        RequestPath path = RequestPath.parse(uri, origRequest.getPath().contextPath().value());
        Flux<DataBuffer> body = Optional.ofNullable(braRequest.getBody())
                .map(jn -> JSON_ENCODER.encode(Mono.just(jn), BUFFER_FACTORY, RESOLVABLE_TYPE, null, null))
                .orElse(Flux.empty());
        return new BraServerHttpRequest(path, new LinkedMultiValueMap<>(), new LinkedMultiValueMap<>(), braRequest.getMethod(), body, origRequest.getHeaders(), uri);
    }

    public BraServerHttpRequest(RequestPath path, MultiValueMap<String, String> queryParams, MultiValueMap<String, HttpCookie> cookies, String methodValue, Flux<DataBuffer> body, HttpHeaders headers, URI uri) {
        this.path = path;
        this.queryParams = queryParams;
        this.cookies = cookies;
        this.methodValue = methodValue;
        this.body = body;
        this.headers = headers;
        this.uri = uri;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public RequestPath getPath() {
        return path;
    }

    @Override
    public MultiValueMap<String, String> getQueryParams() {
        return queryParams;
    }

    @Override
    public MultiValueMap<String, HttpCookie> getCookies() {
        return cookies;
    }

    @Override
    public String getMethodValue() {
        return methodValue;
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public URI getURI() {
        return uri;
    }

}
