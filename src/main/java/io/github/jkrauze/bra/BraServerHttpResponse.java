package io.github.jkrauze.bra;

import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class BraServerHttpResponse implements ServerHttpResponse {

    private final static StringDecoder STRING_DECODER = StringDecoder.allMimeTypes();
    private static final DataBufferFactory BUFFER_FACTORY = new DefaultDataBufferFactory();
    private final static ResolvableType RESOLVABLE_TYPE = ResolvableType.forClass(String.class);

    private String body = null;

    private HttpStatus statusCode = HttpStatus.OK;
    private HttpHeaders headers = new HttpHeaders();
    private MultiValueMap<String, ResponseCookie> cookies = new LinkedMultiValueMap<>();

    private AtomicBoolean committed = new AtomicBoolean();

    @Override
    public boolean setStatusCode(HttpStatus httpStatus) {
        this.statusCode = httpStatus;
        return true;
    }

    @Override
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public MultiValueMap<String, ResponseCookie> getCookies() {
        return cookies;
    }

    @Override
    public void addCookie(ResponseCookie responseCookie) {
        cookies.add(responseCookie.getName(), responseCookie);
    }

    @Override
    public DataBufferFactory bufferFactory() {
        return BUFFER_FACTORY;
    }

    @Override
    public void beforeCommit(Supplier<? extends Mono<Void>> supplier) {
        supplier.get().block();
    }

    @Override
    public boolean isCommitted() {
        return committed.get();
    }


    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> publisher) {
        return STRING_DECODER.decodeToMono(Flux.from(publisher).doOnComplete(() -> committed.set(true)).cast(DataBuffer.class), RESOLVABLE_TYPE, null, null)
                .cast(String.class).doOnSuccess(s -> body = s).then();
    }

    @Override
    public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> publisher) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> setComplete() {
        return Mono.fromRunnable(() -> committed.set(true));
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
