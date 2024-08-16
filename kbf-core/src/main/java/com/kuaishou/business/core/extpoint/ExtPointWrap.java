package com.kuaishou.business.core.extpoint;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExtPointWrap {

    private String extPointCode;

    private Class<?> extPointClass;

    private Object instance;
}
