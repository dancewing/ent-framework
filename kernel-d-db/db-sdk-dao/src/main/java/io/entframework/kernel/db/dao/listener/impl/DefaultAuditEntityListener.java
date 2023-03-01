package io.entframework.kernel.db.dao.listener.impl;

import io.entframework.kernel.auth.api.context.LoginContext;
import io.entframework.kernel.auth.api.pojo.login.LoginUser;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.db.dao.listener.EntityListener;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Audit 监听器
 */
@Slf4j
public class DefaultAuditEntityListener implements EntityListener {

    private LoginUser getLoginUser() {
        return LoginContext.me().getLoginUser();
    }

    @Override
    public void beforeInsert(Object object) {
        if (object instanceof BaseEntity entity) {
            LoginUser loginUser = this.getLoginUser();
            entity.setCreateUser(loginUser != null ? loginUser.getUserId() : -1L);
            entity.setCreateUserName(loginUser != null ? loginUser.getAccount() : "");
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
        }
    }

    @Override
    public void beforeUpdate(Object object) {
        if (object instanceof BaseEntity entity) {
            LoginUser loginUser = this.getLoginUser();
            entity.setUpdateTime(LocalDateTime.now());
            entity.setUpdateUser(loginUser != null ? loginUser.getUserId() : -1L);
            entity.setUpdateUserName(loginUser != null ? loginUser.getAccount() : "");
        }
    }

}
