package com.kuaishou.business.extension.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.kuaishou.business.core.KBusinessRequest;
import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.session.KSession;
import com.kuaishou.business.core.session.KSessionFactory;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.spring.annotations.KSessionAround;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Aspect
@Slf4j
public class KSessionAroundAspect {

    private KSessionFactory kSessionFactory;

	public void setkSessionFactory(KSessionFactory kSessionFactory) {
		this.kSessionFactory = kSessionFactory;
	}

	@Pointcut("@annotation(com.kuaishou.business.extension.spring.annotations.KSessionAround)")
    public void pointcut() {
        // Do nothing because of pointcut schema.
    }

    @Around("pointcut()")
    @SneakyThrows
    public Object around(ProceedingJoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
//        checkArgs(args);
        //       多次重入
        if (KSessionScope.init()) {
            throw new UnsupportedOperationException("@KProcess invoke only once");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        KSessionAround kSessionAround = signature.getMethod().getAnnotation(KSessionAround.class);

        final Class<? extends KBizContext> bizContextType = kSessionAround.bizContextType();

        try (final KSession session = kSessionFactory.openSession(args[0], bizContextType)) {
            KSessionScope.initWithSession(session);
            return joinPoint.proceed(args);
        }

    }

    private void checkArgs(Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            throw new BizIdentityException("kprocess illegal param, param is empty");
        }

        final Object arg = args[0];

        if (!ClassUtils.isAssignable(arg.getClass(), KBusinessRequest.class)) {
            throw new BizIdentityException("kprocess illegal param, first args must extending KBusinessRequest");
        }
    }
}
