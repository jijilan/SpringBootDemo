package com.springboot.dlc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.dlc.entity.WxAgent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujiebang
 * @since 2018-09-22
 */
public interface WxAgentMapper extends BaseMapper<WxAgent> {

    List<WxAgent> findAgentAndRole(IPage iPage);
}
