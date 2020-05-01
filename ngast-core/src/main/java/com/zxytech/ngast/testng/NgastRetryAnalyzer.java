package com.zxytech.ngast.testng;

import static com.zxytech.ngast.constant.NgastPropertiesKeyName.NGAST_RETRY_TIMES_KEY_NAME;

import com.zxytech.ngast.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class NgastRetryAnalyzer implements IRetryAnalyzer {

  private static final int RETRY_TIMES;

  static {
    Integer retryTimes = PropertiesUtils.getAsInteger(NGAST_RETRY_TIMES_KEY_NAME);
    RETRY_TIMES = retryTimes == null ? 1 : retryTimes;
  }

  private int executedCount = 1;

  @Override
  public boolean retry(ITestResult result) {
    log.debug(
        "method {} for case {} {} had executed {} times",
        result.getMethod(),
        result.getTestName(),
        result.getName(),
        executedCount);
    if (!result.isSuccess() && executedCount < RETRY_TIMES) {
      executedCount++;
      return true;
    }
    return false;
  }
}
