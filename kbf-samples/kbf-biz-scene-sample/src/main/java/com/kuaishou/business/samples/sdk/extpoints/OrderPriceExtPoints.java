package com.kuaishou.business.samples.sdk.extpoints;


import com.kuaishou.business.core.annotations.KExtPoint;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.extension.dimension.BizSceneExtPointMethod;
import com.kuaishou.business.samples.app.bizscene.constants.CommonConstants;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;

@KExtPoint(belong = "TradeBuyStep", displayName = "计算金额扩展点")
public interface OrderPriceExtPoints extends ExtPoint {

	@BizSceneExtPointMethod(belong = "TradeBuyStep", name = "计算邮费金额",
		scenesTypes = {CommonConstants.ACTIVITY, CommonConstants.PAY})
	Long calculateExpressFee(CreateOrderDTO createOrderDTO);

}
