package com.kuaishou.business.samples.sdk;

import lombok.Data;

/**
 * @author liuzhuo
 * Created on 2023-04-02 下午9:46
 */
@Data
public class CreateOrderDTO {

    private Long oid;

    private Long itemId;

    private Long categoryId;

    private Long expressFee;

	private String activityType;

	private Boolean consolidate = false;

	private String payWay;
}
