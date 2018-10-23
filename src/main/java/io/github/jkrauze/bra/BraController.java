package io.github.jkrauze.bra;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BraController {

    private WebHandler webHandler;

    private ErrorWebExceptionHandler exceptionHandler;

    public BraController(WebHandler webHandler, ErrorWebExceptionHandler exceptionHandler) {
        this.webHandler = webHandler;
        this.exceptionHandler = exceptionHandler;
    }

    @PostMapping(value = "bra-v0", produces = "application/json")
    public Flux<BraResponse> batchRequest(@RequestBody Flux<BraRequest> braRequestFlux, ServerWebExchange exchange) {
        return braRequestFlux.flatMap(braRequest -> {
            BraServerHttpRequest request = BraServerHttpRequest.of(exchange.getRequest(), braRequest);
            BraServerHttpResponse response = new BraServerHttpResponse();

            ServerWebExchange braExchange = exchange.mutate().request(request).response(response).build();

            return webHandler.handle(braExchange)
                    .onErrorResume(e -> exceptionHandler.handle(braExchange, e))
                    .then(Mono.fromRunnable(() -> exchange.getAttributes().remove(BraConfiguration.ERROR_ATTRIBUTE)))
                    .then(Mono.fromSupplier(System::currentTimeMillis))
                    .map(time -> BraResponse.builder()
                            .timestamp(time)
                            .method(braRequest.getMethod())
                            .path(braRequest.getPath())
                            .headers(response.getHeaders().toSingleValueMap())
                            .body(response.getBody())
                            .status(response.getStatusCode().value())
                            .build());
        });
    }


}
