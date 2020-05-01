package com.zxytech.ngast.restassured;

import static com.zxytech.ngast.constant.NgastPropertiesKeyName.NGAST_API_TIMEOUT_KEY_NAME;
import static com.zxytech.ngast.constant.NgastPropertiesKeyName.NGAST_REPORT_LEVEL_KEY_NAME;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_MOVED_TEMPORARILY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import com.zxytech.ngast.util.PropertiesUtils;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public abstract class BaseNgastTestCase {

  public BaseNgastTestCase() {
    initRequestSpec();
    initResponseSpec();
  }

  public void initRequestSpec() {
    RequestSpecBuilder reqSpecBuilder =
        new RequestSpecBuilder()
            .addFilter(new ErrorLoggingFilter())
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL);

    String logLevel = PropertiesUtils.getAsString(NGAST_REPORT_LEVEL_KEY_NAME);
    logLevel = StringUtils.isBlank(logLevel) ? "ERROR" : StringUtils.trim(logLevel);
    if (StringUtils.equalsAnyIgnoreCase(logLevel, "ALL", "DEBUG")) {
      reqSpecBuilder.addFilter(new RequestLoggingFilter()).addFilter(new ResponseLoggingFilter());
    }
    RestAssured.requestSpecification = reqSpecBuilder.build();
  }

  public void initResponseSpec() {
    ResponseSpecBuilder respSpecBuilder = new ResponseSpecBuilder().log(LogDetail.ALL);

    respSpecBuilder.expectStatusCode(
        isIn(Arrays.asList(SC_OK, SC_CREATED, SC_ACCEPTED, SC_MOVED_TEMPORARILY)));
    Long apiTimeout = PropertiesUtils.getAsLong(NGAST_API_TIMEOUT_KEY_NAME);
    if (apiTimeout != null) {
      respSpecBuilder.expectResponseTime(lessThanOrEqualTo(apiTimeout));
    }
    RestAssured.responseSpecification = respSpecBuilder.build();
  }
}
