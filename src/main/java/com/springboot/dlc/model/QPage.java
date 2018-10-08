package com.springboot.dlc.model;

import lombok.Data;

@Data
public class QPage {
    /**
     * 偏移量
     */
    Integer offset=1;
    /**
     * 限制，限量
     */
    Integer limit=10;
}
