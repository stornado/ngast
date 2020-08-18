package com.zxytech.ngast.constant;

public final class NgastPropertiesKeyName {

  /** 配置失败重试次数 */
  public static final String NGAST_RETRY_TIMES_KEY_NAME = "ngast.testng.retry.times";
  /** 配置测试超时时间 */
  public static final String NGAST_TEST_TIMEOUT_KEY_NAME = "ngast.testng.timeout";
  /** 配置接口请求超时时间 */
  public static final String NGAST_API_TIMEOUT_KEY_NAME = "ngast.api.timeout";
  /** 配置Allure报告日志打印级别 ERROR： 只在发生错误时打印 ALL/DEBUG：详细打印请求响应报文 */
  public static final String NGAST_REPORT_LEVEL_KEY_NAME = "ngast.rest.assured.log.level";
}
