package com.zxytech.ngast.testng;

import static com.zxytech.ngast.constant.NgastPropertiesKeyName.NGAST_TEST_TIMEOUT_KEY_NAME;

import com.zxytech.ngast.util.PropertiesUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

@Slf4j
public class NgastAnnotationTransformer implements IAnnotationTransformer {

  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    if (null == annotation.getRetryAnalyzerClass()) {
      annotation.setRetryAnalyzer(NgastRetryAnalyzer.class);
    }
    if (StringUtils.isBlank(annotation.getTestName())) {
      annotation.setTestName(testMethod.getName());
    }
    if (StringUtils.isBlank(annotation.getSuiteName()) && testClass != null) {
      annotation.setSuiteName(testClass.getName());
    }
    Long ngastTimeout = PropertiesUtils.getAsLong(NGAST_TEST_TIMEOUT_KEY_NAME);
    if (annotation.getTimeOut() <= 0 && ngastTimeout != null) {
      annotation.setTimeOut(ngastTimeout);
    }
    log.debug(
        "{} {} {}",
        annotation.getSuiteName(),
        annotation.getTestName(),
        annotation.getRetryAnalyzerClass().getCanonicalName());
  }
}
