# Ngast

Nast (`|nɑːst|`) 基于 Test(NG) + (A)llure + Re(st) Assured 实现接口测试


## 使用单模块版本

```bash
mvn archetype:generate -DarchetypeGroupId=com.zxytech.ngast -DarchetypeArtifactId=ngast-archetype -DartifactId=examples
```

## 使用多模块版本

```bash
mvn archetype:generate -DarchetypeGroupId=com.zxytech.ngast -DarchetypeArtifactId=ngast-modules-archetype -DartifactId=modules-examples
```

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