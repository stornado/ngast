package ${package};

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import com.zxytech.ngast.restassured.BaseNgastTestCase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Allure examples")
@Feature("GithubAPI示例")
@Slf4j
public class GithubApiTest extends BaseNgastTestCase {

    @DataProvider
    public static Object[][] fetchRepositoryNames(Method method, ITestContext testCtx) {
        log.debug("{} {} {}", method.getName(), testCtx.getHost(), testCtx.getName());
        Object[][] objects = new Object[][] {
            { "Rest Assured", "stars" },
            { "TestNG", "stars" },
            { "Allure", "forks" }
        };
        return objects;
    }

    @BeforeClass
    @Description("设置BaseURI")
    public void setUP() {
        RestAssured.requestSpecification.baseUri("https://api.github.com");
    }

    @Test(dataProvider = "fetchRepositoryNames", description = "搜索仓库地址", suiteName = "Github Api Test", testName = "Search Repositories", timeOut = 10_000)
    @Story("示例搜索")
    @Description("参数化搜索仓库")
    @Step("搜索第一页数据")
    public void testSearchRepositories(String name, String sort) {
        log.debug("{} {}", name, sort);
        step(String.format("search repositories for %s first page", name));
        // https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}

        Response response = given()
            .header(new Header("Host", "api.github.com"))
            .param("q", name)
            .param("per_page", 3)
            .param("sort", sort)
            .when()
            .get("/search/repositories")
            .thenReturn();

        response
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .time(lessThanOrEqualTo(15L), TimeUnit.SECONDS)
            .contentType(ContentType.JSON)
            .body("total_count", greaterThan(0))
            .body("items", everyItem(hasKey("stargazers_count")))
            .body("items", everyItem(hasKey("forks_count")))
            .body("items", everyItem(hasKey("watchers_count")));

        step("check first repo detail");
        log.debug(response.jsonPath().getString("items[2].owner.login"));

        given()
            .get("/repos/{owner}/{repo}",
                response.jsonPath().getString("items[2].owner.login"),
                response.jsonPath().getString("items[2].name"))
            .then()
            .statusCode(HttpStatus.SC_OK)
            .time(lessThanOrEqualTo(15L), TimeUnit.SECONDS)
            .contentType(ContentType.JSON)
            .body("$", hasKey("stargazers_count"))
            .body("$", hasKey("forks_count"))
            .body("$", hasKey("watchers_count"))
            .body("stargazers_count", equalTo(response.jsonPath().getInt("items[2].stargazers_count")))
            .body("id", equalTo(response.jsonPath().getInt("items[2].id")))
            .body("node_id", equalTo(response.jsonPath().getString("items[2].node_id")))
            .body("full_name", equalTo(response.jsonPath().getString("items[2].full_name")));

    }
}
