/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.pay.alipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 阿里支付配置类
 *
 * @date 2021/04/20 20:43
 */
@Component
public class AlipayConfig implements ApplicationRunner {

    /**
     * 应用id
     */
    @Value("${alipay.appId}")
    private String appId;

    /**
     * 私钥
     */
    @Value("${alipay.merchantPrivateKey}")
    private String merchantPrivateKey;

    /**
     * 公钥
     */
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    /**
     * 网关
     */
    @Value("${alipay.gatewayHost}")
    private String gatewayHost;

    /**
     * 支付成功后的接口回调地址 （可选）
     */
    @Value("${alipay.notifyUrl:#{null}}")
    private String notifyUrl;

    /**
     * AES密钥 （可选）
     */
    @Value("${alipay.encryptKey:#{null}}")
    private String encryptKey;

    /**
     * 应用公钥证书文件路径
     */
    @Value("${alipay.merchantCertPath:#{null}}")
    private String merchantCertPath;

    /**
     * 支付宝公钥证书文件路径
     */
    @Value("${alipay.alipayCertPath:#{null}}")
    private String alipayCertPath;

    /**
     * 支付宝根证书文件路径
     */
    @Value("${alipay.alipayRootCertPath:#{null}}")
    private String alipayRootCertPath;

    /**
     * 初始化
     *
     * @date 2021/04/20 20:43
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Factory.setOptions(getOptions());
    }

    /**
     * 获取支付配置对象
     *
     * @return 支付配置对象
     * @date 2021/04/20 20:43
     */
    private Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gatewayHost;
        config.signType = "RSA2";
        config.appId = this.appId;
        config.merchantPrivateKey = this.merchantPrivateKey;
        // 证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        config.merchantCertPath = this.merchantCertPath;
        config.alipayCertPath = this.alipayCertPath;
        config.alipayRootCertPath = this.alipayRootCertPath;
        // 如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = this.alipayPublicKey;
        // 可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyUrl;
        // 可设置AES密钥，调用AES加解密相关接口时需要（可选）
        config.encryptKey = this.encryptKey;
        return config;
    }

}
