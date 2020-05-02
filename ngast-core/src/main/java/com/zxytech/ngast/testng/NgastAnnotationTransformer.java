package com.zxytech.ngast.testng;

import static com.zxytech.ngast.constant.NgastPropertiesKeyName.NGAST_TEST_TIMEOUT_KEY_NAME;

import com.zxytech.ngast.util.PropertiesUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

@Slf4j
public class NgastAnnotationTransformer implements IAnnotationTransformer {

  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    if (null == annotation.getRetryAnalyzerClass()
        || DisabledRetryAnalyzer.class.equals(annotation.getRetryAnalyzerClass())) {
      annotation.setRetryAnalyzer(NgastRetryAnalyzer.class);
    }
    if (StringUtils.isBlank(annotation.getTestName())) {
      if (testMethod.isAnnotationPresent(Feature.class)) {
        annotation.setTestName(testMethod.getDeclaredAnnotation(Feature.class).value());
      } else {
        annotation.setTestName(testMethod.getName());
      }
    }
    if (StringUtils.isBlank(annotation.getSuiteName())) {
      if (testMethod.isAnnotationPresent(Epic.class)) {
        annotation.setSuiteName(testMethod.getDeclaredAnnotation(Epic.class).value());
      } else if (testClass != null) {
        if (testClass.isAnnotationPresent(Epic.class)) {
          annotation.setSuiteName(((Epic) testClass.getDeclaredAnnotation(Epic.class)).value());
        } else {
          annotation.setSuiteName(testClass.getName());
        }
      }
    }
    Long ngastTimeout = PropertiesUtils.getAsLong(NGAST_TEST_TIMEOUT_KEY_NAME);
    if (annotation.getTimeOut() <= 0 && ngastTimeout != null) {
      annotation.setTimeOut(ngastTimeout);
    }
    if (StringUtils.isBlank(annotation.getDescription())) {
      if (testMethod.isAnnotationPresent(Description.class)) {
        annotation.setDescription(testMethod.getDeclaredAnnotation(Description.class).value());
      } else if (testMethod.isAnnotationPresent(Step.class)) {
        annotation.setDescription(testMethod.getDeclaredAnnotation(Step.class).value());
      }
    }
    if (log.isDebugEnabled()) {
      log.debug(
          "{} {} {} {}",
          annotation.getSuiteName(),
          annotation.getTestName(),
          annotation.getDescription(),
          annotation.getRetryAnalyzerClass().getCanonicalName());
    }
  }
}
