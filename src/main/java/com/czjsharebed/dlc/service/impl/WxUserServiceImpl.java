package com.czjsharebed.dlc.service.impl;

import com.czjsharebed.dlc.model.WxUser;
import com.czjsharebed.dlc.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Override
    public WxUser getByOpenId(String openId) {
      return  null;
    }

}
