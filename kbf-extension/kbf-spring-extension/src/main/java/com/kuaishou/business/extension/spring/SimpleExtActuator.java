package com.kuaishou.business.extension.spring;

import java.util.List;

import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.engine.SimpleProductIdentityRecognizer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 扩展点识别（全局扫描）
 */
@Slf4j
public class SimpleExtActuator extends AbstractExtActuator {

    @Override
    public List<NormalProductItem> recognize(Object request) {
		SimpleProductIdentityRecognizer recognizer = new SimpleProductIdentityRecognizer(KSessionScope.getProducts());
        return recognizer.recognize(request);
    }


}
