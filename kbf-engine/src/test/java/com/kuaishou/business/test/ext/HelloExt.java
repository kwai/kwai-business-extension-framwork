package com.kuaishou.business.test.ext;

import com.kuaishou.business.core.annotations.KExtPoint;

//@KExtPoint(belong = Object.class, displayName = "")
@KExtPoint(belong = "a", displayName = "")
public interface HelloExt {
    String name();
}
