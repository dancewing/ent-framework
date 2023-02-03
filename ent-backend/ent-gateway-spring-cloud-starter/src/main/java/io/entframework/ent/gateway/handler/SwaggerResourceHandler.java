
/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.ent.gateway.handler;

import io.entframework.ent.gateway.provider.SwaggerProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * SwaggerResourceHandler
 * @author jeff_qian
 */
@Slf4j
@Component
@AllArgsConstructor
public class SwaggerResourceHandler implements HandlerFunction<ServerResponse> {
	private final SwaggerProvider swaggerProvider;

	/**
	 * Handle the given request.
	 *
	 * @param request the request to handler
	 * @return the response
	 */
	@Override
	@NotNull
	public Mono<ServerResponse> handle(@NotNull ServerRequest request) {
		return ServerResponse.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(swaggerProvider.get()));
	}
}
