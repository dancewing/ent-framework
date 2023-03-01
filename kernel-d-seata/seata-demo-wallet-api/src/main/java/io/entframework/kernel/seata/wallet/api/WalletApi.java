/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.seata.wallet.api;

/**
 * 用户钱包 api
 *
 * @date 2021/04/10 16:42
 */
public interface WalletApi {

    /**
     * 从用户账户中扣除余额
     * @param userId 用户ID
     * @param money 消费金额
     * @date 2021/4/21 9:44
     */
    void debit(String userId, Integer money);

}
