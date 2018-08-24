package com.czjsharebed.dlc.service;

import com.czjsharebed.dlc.model.WxUser;
import com.czjsharebed.dlc.result.ResultView;

public interface WxUserService {

    WxUser getByOpenId(String openId);

}
