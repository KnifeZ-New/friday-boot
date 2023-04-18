package org.knifez.fridaybootcore.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IMybatisPlusService<T> extends IService<T> {

    @Override
    default List<T> listByIds(Collection<? extends Serializable> idList) {
        if (idList.size() == 0) {
            return new ArrayList<>();
        }
        return IService.super.listByIds(idList);
    }
}