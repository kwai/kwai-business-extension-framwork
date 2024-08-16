package com.kuaishou.business.core.extpoint;

import java.lang.reflect.Method;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuzhuo
 * Created on 2023-03-29 下午9:07
 * 扩展点实例方法
 */
@Data
@Accessors(chain = true)
public class ExtPointMethodRealizeWrap {

    /**
     * 扩展点实例方法优先级
     */
    private int priority;

    /**
     * 扩展点实例方法
     */
    private final Method invokeMethod;

    public ExtPointMethodRealizeWrap(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    @Override
    public String toString() {
        return "ExtPointMethodRealizeWrap{" +
                "invokeMethod=" + invokeMethod.getName() +
                ", priority=" + priority +
                '}';
    }
}
