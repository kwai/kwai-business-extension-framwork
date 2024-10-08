package com.kuaishou.business.extension.dimension.identity;

/**
 * @author liuzhuo
 * Created on 2023-03-28 下午9:20
 */
class ComboBizSceneItem extends BizSceneItem {

    private static final String DIMENSION_TYPE = "comboDimension";

    public ComboBizSceneItem(String name, String code,
            BizSceneIdentityDefinition definition, EffectScope effectScope) {
        super(name, code, definition, DIMENSION_TYPE, effectScope);
    }

    @Override
    public boolean isCombo() {
        return true;
    }
}
