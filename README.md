# kwai-business-extension-framwork
![GitHub License](https://img.shields.io/github/license/kwai/kwai-business-extension-framwork)
![Maven Central Version](https://img.shields.io/maven-central/v/com.kuaishou/kwai-business-extension-framwork)

提供通用业务扩展框架，引入业务身份 + 提供可扩展的隔离架构，帮助业务搭建定制业务流程的架构标准、研发工具和运维体系。通过「能力全在线，定制都可见」的解决策略，帮助业务快速接入、精细化运维和稳定性保障

## Architecture
![Architecture](img/readme/img.png)

## Getting started

以下代码示例来自 [kbf-samples](https://github.com/kwai/kwai-business-extension-framwork/tree/main/kbf-samples).

```bash
git clone https://github.com/kwai/kwai-business-extension-framwork.git
cd kbf-samples/kbf-common-sample
```
运行`SampleApplication.java`,然后分别访问[http://127.0.0.1:8080/kbf?bizCode=trade2](http://127.0.0.1:8080/kbf?bizCode=trade1)和[http://127.0.0.1:8080/kbf?bizCode=trade1](http://127.0.0.1:8080/kbf?bizCode=trade1)，不同的业务身份将返回不同的结果


### Maven dependency

```xml
<dependency>
  <groupId>com.kuaishou</groupId>
  <artifactId>kbf-common-starter</artifactId>
  <version>{{last_version}}</version>
</dependency>

```

### 名词解释
| 名称    | 说明 |
| ---- | ------- |
| 业务 | 业务最终的目的是“售出产品，换取利润”，所以一个业务必定是有一个业务团队进行或处理商业上相关的活动，也是一个最小的经营单位    |
| 业务身份 | 系统唯一标识这个业务的标志就是业务身份     |
| 扩展点   | 相同流程，不同业务执行逻辑不同的节点，就叫扩展点   |


### 配置应用
在 application.properties 中添加以下配置：`spring.kbf.common.enabled=true`


### 定义扩展点
```java
@KExtPoint
public interface OrderPriceExtPoints extends ExtPoint {

  @KExtPointMethod
  Long calculateExpressFee(CreateOrderDTO createOrderDTO);

}
```
1. 定义扩展接口继承`ExtPoint`
2. 在接口添加`@KExtPoint`注解
3. 需要扩展的方法上添加`@KExtPointMethod`注解

### 定义服务执行扩展点

```java
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

  @KExtPointInvoke
  private OrderPriceExtPoints orderPriceExtPoints;

  @Override
  @KSessionAround
  public CreateOrderResponse createOrder(CreateOrderRequest request) {
    CreateOrderDTO createOrder = request.getCreateOrder();
    Long expressFee = orderPriceExtPoints.calculateExpressFee(createOrder);
    CreateOrderResponse response = new CreateOrderResponse();
    response.setExpressFee(expressFee);
    return response;
  }
}
```
1. 定义扩展服务，需要将其定义为Spring Bean
2. 通过`@KExtPointInvoke`注解注入扩展点
3. 在执行扩展点的方法上添加`@KSessionAround`以开启`session`
4. 在方法内调用扩展点执行扩展方法

### 定义业务
```java
@KBusiness(name = Trade1Constants.BIZ_CODE_NAME, code = Trade1Constants.BIZ_CODE)
@Component
public class Trade1Identity implements NormalBizIdentityDefinition {

  @Override
  public String supportedBizCode() {
    return Trade1Constants.BIZ_CODE;
  }

  @Override
  public String scanPath() {
    return "*";
  }

  @Override
  public MatchResult match(Object request) {
    CreateOrderRequest createOrder = (CreateOrderRequest) request;
    if (createOrder.getBizCode().equals("trade1")) {
      return MatchResult.MATCH;
    } else {
      return MatchResult.NOT_MATCH;
    }
  }
}
```
1. 扩展点需要业务去实现，定义业务类实现`NormalBizIdentityDefinition `,并将其定义为 Spring Bean
2. 实现`match`方法，入参为执行扩展点的方法的入参，如果满足生效条件返回MATCH，否则返回NOT_MATCH
3. 在业务类上添加`@KBusiness`注解，`code`为这个业务对应的业务身份


### 实现扩展点

```java
@KBizRealize(bizCode = Trade1Constants.BIZ_CODE)
@Component
public class Trade1Realize implements OrderPriceExtPoints {

  @Override
  public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
    return 1000L;
  }
}
```
1. 实现扩展点接口方法，实现该业务对应的逻辑，并将该实现类定义为 Spring Bean
2. 在实现类上添加`@ KBizRealize `注解，`bizCode `为对应的业务身份

## Built With
• JDK 11

• Spring Framework 5+

• Spring Boot 2.5+

• Maven 3.0

## License

kwai-business-extension-framwork software is licensed under the Apache License Version 2.0. See the [LICENSE](https://github.com/kwai/kwai-business-extension-framwork/blob/main/LICENSE) file for details.
