package com.knifez.fridaybootadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knifez.fridaybootadmin.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

}
