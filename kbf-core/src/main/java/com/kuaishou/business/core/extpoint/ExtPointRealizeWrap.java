package com.kuaishou.business.core.extpoint;

import java.util.HashMap;
import java.util.Map;

import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 扩展点实例
 *
 * {@link KbfRealizeItem 标准能力}
 * {@link BusinessItem 垂直业务标准能力}
 * {@link NormalProductItem 水平产品标注能力}
 */
@Data
@Accessors(chain = true)
public class ExtPointRealizeWrap {

    /**
     * 扩展点实例
     */
    private Object object;

    /**
     * 扩展点实例类优先级
     */
    private Integer priority;

    /**
     * 扩展点实例Class
     */
    private Class<?> extPointClass;

    /**
     * 扩展点实例实现的方法
     */
    private Map<String, ExtPointMethodRealizeWrap> methods = new HashMap<>();

    @Override
    public String toString() {
        return "ExtPointRealizeWrap{" +
                "extPointClass=" + extPointClass +
                ", priority=" + priority +
                ", methods=" + methods +
                '}';
    }
}
