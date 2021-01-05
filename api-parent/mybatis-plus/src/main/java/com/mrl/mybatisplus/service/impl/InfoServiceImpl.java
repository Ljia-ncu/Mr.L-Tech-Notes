package com.mrl.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.mybatisplus.entity.Info;
import com.mrl.mybatisplus.mapper.InfoMapper;
import com.mrl.mybatisplus.service.InfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr.L
 * @since 2020-11-28
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

}
