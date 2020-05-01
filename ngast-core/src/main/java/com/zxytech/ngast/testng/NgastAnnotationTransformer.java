package com.zxytech.ngast.testng;

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
    log.debug(
        "{} {} {}",
        annotation.getSuiteName(),
        annotation.getTestName(),
        annotation.getRetryAnalyzerClass().getCanonicalName());
  }
}
