package com.zxytech.ngast.util;

import static org.testng.Assert.assertEquals;

import io.qameta.allure.Flaky;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class PathUtilsTest {

  @Test(description = "ngast.environment=test" + "补齐环境前缀")
  public void testGetPathUsingFileName() {
    Path actual = PathUtils.get("order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Test(description = "ngast.environment=test" + "插入环境")
  public void testGetPathUsingDataFileName() {
    Path actual = PathUtils.get("data/order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Test(description = "绝对路径不做处理")
  public void testGetAbsolute() {
    Path actual =
        PathUtils.get("/ngast/ngast-core/src/test/resources/data/order/purchased-order.json");
    Path expected =
        Paths.get("/ngast/ngast-core/src/test/resources/data/order/purchased-order.json");
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Test(description = "ngast.environment=test" + "/data开头的绝对路径插入路径")
  public void testGetAbsoluteData() {
    Path actual = PathUtils.get("/data/order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Flaky
  @Test(description = "ngast.environment=test" + "环境不作处理")
  public void testGetPathUsingDataEnvFileName() {
    Path actual = PathUtils.get("data/test/order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Flaky
  @Test(description = "ngast.environment=test" + "环境不作处理")
  public void testGetPathUsingEnvFileName() {
    Path actual = PathUtils.get("test/order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }

  @Flaky
  @Test(description = "ngast.environment=test" + "环境不做处理")
  public void testGetAbsoluteDataEnv() {
    Path actual = PathUtils.get("/data/test/order/purchased-order.json");
    Path expected =
        Paths.get("target/test-classes/data/test/order/purchased-order.json").toAbsolutePath();
    if (log.isDebugEnabled()) {
      log.debug("{} {}", actual, expected);
    }
    assertEquals(actual, expected);
  }
}
