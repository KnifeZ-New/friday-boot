package org.knifez.fridaybootapi.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        var userAccount = JwtTokenUtils.getCurrentUser();
        this.strictInsertFill(metaObject, "createBy", String.class, userAccount);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateBy", String.class, userAccount);
        this.strictUpdateFill(metaObject, "updateTime", (Supplier<LocalDateTime>) null, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "updateBy", String.class, JwtTokenUtils.getCurrentUser());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}
