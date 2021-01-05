package com.mrl.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.mybatisplus.entity.Info;
import com.mrl.mybatisplus.vo.InfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Mr.L
 * @since 2020-11-28
 */
public interface InfoMapper extends BaseMapper<Info> {

    List<InfoVo> selectByAge(int age);

    @Select("select * from info where name = #{name}")
    List<InfoVo> selectByName(String name);
}
