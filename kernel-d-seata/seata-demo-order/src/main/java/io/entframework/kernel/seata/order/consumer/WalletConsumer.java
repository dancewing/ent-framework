/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.seata.order.consumer;

import io.entframework.kernel.seata.wallet.api.WalletApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户钱包 api
 *
 * @date 2021/04/10 16:42
 */
@FeignClient(name = "seata-demo-wallet")
public interface WalletConsumer extends WalletApi {
}
