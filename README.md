# Ngast

Ngast (`|nɑːst|`) 基于 Test(NG) + (A)llure + Re(st) Assured 实现接口测试


## 使用单模块版本

```shell
mvn archetype:generate -DarchetypeGroupId=com.zxytech.ngast -DarchetypeArtifactId=ngast-archetype -DartifactId=examples
```

**生成内容**

```shell
.
└── examples
    ├── .editorconfig
    ├── .gitignore
    ├── HELP.md
    ├── README.md
    ├── pom.xml
    └── src
        └── test
            ├── java
            │   └── com
            │       └── zxytech
            │           └── ngast
            │               └── cases
            │                   └── GithubApiTest.java
            └── resources
                ├── allure.properties
                ├── data
                │   └── test
                ├── ngast-test.properties
                └── ngast.properties

11 directories, 9 files
```

## 使用多模块版本

```shell
mvn archetype:generate -DarchetypeGroupId=com.zxytech.ngast -DarchetypeArtifactId=ngast-modules-archetype -DartifactId=modules-examples
```

**生成内容**

```shell
.
└── modules-examples
    ├── .editorconfig
    ├── .gitignore
    ├── HELP.md
    ├── README.md
    ├── allure.properties
    ├── ngast-examples
    │   ├── pom.xml
    │   └── src
    │       └── test
    │           ├── java
    │           │   └── com
    │           │       └── zxytech
    │           │           └── ngast
    │           │               └── cases
    │           │                   └── GithubApiTest.java
    │           └── resources
    │               ├── data
    │               │   └── test
    │               ├── ngast-test.properties
    │               └── ngast.properties
    └── pom.xml

12 directories, 10 files
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
