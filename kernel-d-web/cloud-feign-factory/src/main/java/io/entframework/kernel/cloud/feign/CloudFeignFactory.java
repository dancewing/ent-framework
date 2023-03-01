package io.entframework.kernel.cloud.feign;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;

import java.util.List;

public class CloudFeignFactory {

	private final Decoder decoder;

	private final Encoder encoder;

	private final Client client;

	private final Contract contract;

	private final List<RequestInterceptor> interceptors;

	public CloudFeignFactory(Decoder decoder, Encoder encoder, Client client, Contract contract,
			List<RequestInterceptor> interceptors) {
		this.decoder = decoder;
		this.encoder = encoder;
		this.client = client;
		this.contract = contract;
		this.interceptors = interceptors;
	}

	public <T> T build(Class<T> cls, String baseServerUrl) {
		return Feign.builder().decoder(decoder).encoder(encoder).client(client).contract(contract)
				.requestInterceptors(interceptors).target(cls, baseServerUrl);
	}

	public <T> T asyncBuild(Class<T> cls, String baseServerUrl) {
		AsyncClient<Object> asyncClient = new AsyncClient.Pseudo<>(client);
		return AsyncFeign.asyncBuilder().decoder(decoder).encoder(encoder).client(asyncClient).contract(contract)
				.requestInterceptors(interceptors).target(cls, baseServerUrl);
	}

}
