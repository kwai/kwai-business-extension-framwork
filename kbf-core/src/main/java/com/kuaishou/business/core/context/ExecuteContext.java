package com.kuaishou.business.core.context;

import java.util.Set;

import com.kuaishou.business.core.identity.manage.NormalProductItem;

import lombok.Data;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
@Data
public class ExecuteContext {

	private Object request;

	private Class extClz;

	private String methodName;

	private Set<NormalProductItem> effectProducts;
}
