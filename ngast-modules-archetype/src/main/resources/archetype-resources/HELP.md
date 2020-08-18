# Ngast API Test

## 参考

1. [Rest Assured](https://github.com/rest-assured/rest-assured/wiki/GettingStarted#rest-assured)
2. [TestNG](https://testng.org/doc/documentation-main.html)
3. [Allure](https://docs.qameta.io/allure/)


## 运行测试

```bash
mvn test
```

## 生成报告

```bash
mvn allure:aggregate
mvn allure:report
```

## 本地查看

```bash
mvn allure:serve
```