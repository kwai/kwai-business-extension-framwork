package com.kuaishou.business.core;

import com.kuaishou.business.core.annotations.KExtPoint;

//@KExtPoint(belong = Object.class, displayName = "测试扩展点1")
@KExtPoint(belong = "a", displayName = "a测试扩展点1")
interface TestExtPoint {
    String name();
}

class Test1 implements TestExtPoint {

    @Override
    public String name() {
        return "a";
    }
}
