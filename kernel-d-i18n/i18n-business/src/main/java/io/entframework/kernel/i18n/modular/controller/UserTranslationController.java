/*
 * ******************************************************************************
 *  * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */
package io.entframework.kernel.i18n.modular.controller;

import io.entframework.kernel.auth.api.SessionManagerApi;
import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.dict.api.DictApi;
import io.entframework.kernel.dict.api.constants.DictConstants;
import io.entframework.kernel.i18n.api.context.TranslationContext;
import io.entframework.kernel.i18n.api.pojo.request.TranslationRequest;
import io.entframework.kernel.rule.pojo.dict.SimpleDict;
import io.entframework.kernel.rule.pojo.response.ResponseData;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 用户多语言信息控制器
 *
 * @date 2021/1/27 21:59
 */
@RestController
@ApiResource(name = "用户多语言信息控制器")
public class UserTranslationController {

    @Resource
    private DictApi dictApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 获取所有的多语言类型编码
     *
     * @date 2021/1/24 19:20
     */
    @GetResource(name = "获取所有的多语言类型编码", path = "/i18n/get-all-languages", requiredPermission = false)
    public ResponseData<List<SimpleDict>> getAllLanguages() {
        List<SimpleDict> dictDetailsByDictTypeCode = dictApi.getDictDetailsByDictTypeCode(DictConstants.LANGUAGES_DICT_TYPE_CODE);
        return ResponseData.ok(dictDetailsByDictTypeCode);
    }

    /**
     * 获取当前用户的多语言字典
     *
     * @date 2021/1/27 22:00
     */
    @GetResource(name = "获取当前用户的多语言字典", path = "/i18n/get-user-translation", requiredPermission = false)
    public ResponseData<Map<String, String>> getUserTranslation() {
        String tranLanguageCode = LoginContext.me().getLoginUser().getTranLanguageCode();
        Map<String, String> translationDictByLanguage = TranslationContext.me().getTranslationDictByLanguage(tranLanguageCode);
        return ResponseData.ok(translationDictByLanguage);
    }

    /**
     * 修改当前用户的多语言配置
     *
     * @date 2021/1/27 22:04
     */
    @PostResource(name = "修改当前用户的多语言配置", path = "/i18n/change-user-translation", requiredPermission = false)
    public ResponseData<Void> changeUserTranslation(@RequestBody @Validated(TranslationRequest.changeUserLanguage.class) TranslationRequest translationRequest) {

        String token = LoginContext.me().getToken();
        LoginUser loginUser = LoginContext.me().getLoginUser();

        // 更新当前用户的多语言配置
        loginUser.setTranLanguageCode(translationRequest.getTranLanguageCode());
        sessionManagerApi.updateSession(token, loginUser);

        return ResponseData.OK_VOID;
    }

}


