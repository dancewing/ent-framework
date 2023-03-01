/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.security.captcha;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import io.entframework.kernel.cache.api.CacheOperatorApi;
import io.entframework.kernel.security.api.ImageCaptchaApi;
import io.entframework.kernel.security.api.pojo.ImageCaptcha;
import com.wf.captcha.SpecCaptcha;

/**
 * 图形验证码实现
 *
 * @date 2021/1/15 13:44
 */
public class ImageCaptchaService implements ImageCaptchaApi {

    private final CacheOperatorApi<String> cacheOperatorApi;

    public ImageCaptchaService(CacheOperatorApi<String> cacheOperatorApi) {
        this.cacheOperatorApi = cacheOperatorApi;
    }

    @Override
    public ImageCaptcha captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String verKey = IdUtil.simpleUUID();
        cacheOperatorApi.put(verKey, verCode);
        return ImageCaptcha.builder().verImage(specCaptcha.toBase64()).verKey(verKey).build();
    }

    @Override
    public boolean validateCaptcha(String verKey, String verCode) {
        if (CharSequenceUtil.isAllEmpty(verKey, verCode)) {
            return false;
        }

        if (!verCode.trim().toLowerCase().equals(cacheOperatorApi.get(verKey))) {
            return false;
        }

        // 删除缓存中验证码
        cacheOperatorApi.remove(verKey);

        return true;
    }

    @Override
    public String getVerCode(String verKey) {
        return cacheOperatorApi.get(verKey);
    }

}
