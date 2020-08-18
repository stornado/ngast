package com.zxytech.ngast.util;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertThrows;

import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class JsonUtilsTest {

  @DataProvider
  public Object[][] fetchJsonDictValues() {
    return JsonUtils.fetchJsonDictValues(
        "[\n"
            + "    {\n"
            + "        \"name\": \"Rest Assured\",\n"
            + "        \"page\": 1\n"
            + "    },\n"
            + "    {\n"
            + "        \"name\": \"TestNG\",\n"
            + "        \"page\": 2\n"
            + "    }\n"
            + "]");
  }

  @DataProvider
  public Object[][] fetchJsonArrayElements() {
    return JsonUtils.fetchJsonArrayElements(
        "[\n"
            + "    [\n"
            + "        \"Rest Assured\",\n"
            + "        1,\n"
            + "        true\n"
            + "    ],\n"
            + "    [\n"
            + "        \"TestNG\",\n"
            + "        2,\n"
            + "        false\n"
            + "    ]\n"
            + "]");
  }

  @Test(dataProvider = "fetchJsonDictValues")
  public void testFetchJsonDictValues(JsonPrimitive name, JsonPrimitive page) {
    log.debug("{} {}", name, page);
    assertThat(name.getAsString(), is(in(asList("Rest Assured", "TestNG"))));
    assertThat(page.getAsInt(), is(in(asList(1, 2))));

    assertThrows(NullPointerException.class, () -> JsonUtils.fetchJsonDictValues(null));
  }

  @Test(dataProvider = "fetchJsonArrayElements")
  public void testFetchJsonArrayElements(
      JsonPrimitive name, JsonPrimitive page, JsonPrimitive sortByStars) {
    log.debug("{} {} {}", name, page, sortByStars);
    assertThat(name.getAsString(), is(in(asList("Rest Assured", "TestNG"))));
    assertThat(page.getAsInt(), is(in(asList(1, 2))));
    assertThat(sortByStars.getAsBoolean(), is(in(asList(true, false))));

    assertThrows(NullPointerException.class, () -> JsonUtils.fetchJsonArrayElements(null));
  }
}
