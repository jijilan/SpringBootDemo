package com.czjsharebed.dlc.service;

import com.czjsharebed.dlc.model.SysManager;

public interface SysManagerService {
    SysManager backLogin(String userAcount, String passWord);
}
