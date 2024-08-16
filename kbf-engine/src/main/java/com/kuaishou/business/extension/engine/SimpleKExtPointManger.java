package com.kuaishou.business.extension.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-27 上午11:23
 */
@Slf4j
public class SimpleKExtPointManger {

    public static <Ext extends ExtPoint> List<Ext> getInstance(Collection<KbfRealizeItem> KbfRealizeItemList,
            Class<Ext> clazz, String method) {
        List<Ext> instanceList = KbfRealizeItemList.stream()
                .map(spec -> findExtRealize(spec, clazz))
                .filter(Objects::nonNull)
                .filter(extRealize -> extRealize.getMethods().containsKey(method))
                .sorted(Comparator.comparing(extRealize -> extRealize.getMethods().get(method).getPriority(),
                        Collections.reverseOrder()))
                .map(e -> (Ext) e.getObject())
                .collect(Collectors.toList());
        return instanceList;
    }

    private static <Ext extends ExtPoint> ExtPointRealizeWrap findExtRealize(KbfRealizeItem KBFRealizeItem, Class<Ext> clazz) {
        for (ExtPointRealizeWrap extPointRealize : KBFRealizeItem.getExtPointRealizes()) {
            if (ClassUtils.isAssignable(extPointRealize.getExtPointClass(), clazz)) {
                return extPointRealize;
            }
        }
        return null;
    }
}
