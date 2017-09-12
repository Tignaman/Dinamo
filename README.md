# Dinamo

> **Java 8 library that through the use of annotation processor helps to setup a project with particular focus on database entities.**

## Overview

Dinamo was designed to speed up and improve the initial setup of a project. Inside Dinamo, we can find several features that help you build code right away without worrying about building an underlying layer.

Unlike other libraries, Dinamo does not want to give precise rules to follow, but configurable utility features (you can only use a part of Dinamo without any problems). It consists of several internal modules, which can be used independently but by simultaneous use of these, many advantages will be obtained. The modules that are used inside are: 

* **[Sinfonia](https://github.com/Jaaaas/Sinfonia/)**
* **[Cosmo](https://github.com/Jaaaas/Cosmo)**

Today, Dinamo focuses mainly on database communication. We can say that it allows a circular mapping of database tables and offers a complete and configurable query management. 
Through an annotation processor, Dinamo lets you map the tables of an existing database with java classes. At the moment, you have already thought of ways to configure this mapping so that you do not always have to make the same changes as the classes are regenerated.

Dinamo and its modules are still under testing, but can still be used in a project. However, support must be added to the different types of RDBMS.

## Getting Started

Dinamo has an **annotation processor** that use **@DinamoBootstrap** annotation.

```java
@Retention(RetentionPolicy.SOURCE)
public @interface DinamoBootstrap
{
    String basePath();
}
```
**Path** property is the absolute path to src folder where Dinamo configuration package will be created.

Example:
```java
@DinamoBootstrap(basePath="absolute/Path/To/DinamoTest/src")
public class DinamoTest {}
```
After doing this, simply build the project and under src folder you should see **Dinamo/DinamoConfig** structure.
Inside *DinamoConfig* you should find two json file:

* **DBConnection**
* **ModelConfiguration**

Let's explain both of them.

### DBConnection

This file is really very clear. It is used for establishing the connection with the database.

```json
{
  "Driver": "",
  "Ip": "",
  "Port": "",
  "Username": "",
  "Password": "",
  "Database": "",
  "Timezone": ""
}
```
*Remember that the project must have the specified driver installed*.

Example of DBConnection filled:

```json
{
  "Driver": "com.mysql.cj.jdbc.Driver",
  "Ip": "jdbc:mysql://127.0.0.1",
  "Port": "3306",
  "Username": "username",
  "Password": "password",
  "Database": "dinamoDatabase",
  "Timezone": "Europe/Rome"
}
```

### ModelConfiguration

This file is a bit more complicated and it is used to manage database tables mapping into java classes.
```json
{
    "ModelPackage": "",
    "IgnoreTables": 
    [
        {
            "TableName": ""
        }
    ],
    "IgnoreAnnotations": 
    [
        {
            "AnnotationName": ""
        }
    ],
    "TableSpecification": 
    [
        {
            "TableName": "",
            "Mapping": 
            [
                {
                    "AttributeName": "",
                    "Type": "",
                    "Package": "",
                    "CustomAnnotation": 
                    [
                        {
                            "AnnotationName": "",
                            "Package": ""
                        }
                    ]
                }
            ]
        }
    ]
}
```
Explain it by keys.

* **ModelPackage**: is used to specify the package name where java classes will be created. For example

```json
{
    "ModelPath": "CosmoModels",
    ...
}
```
*Full package starting from src folder must be specified*

* **IgnoreTables**: here we can specify the classes we do not want to be mapped. For example

```json
"IgnoreTables": 
    [
        {
            "TableName": "firstTableName"
        },
        {
            "TableName": "secondTableName"
        }
    ]
```

* **IgnoreAnnotations**: Dinamo makes use of some annotations including:

1. AutoIncrement
2. ForeignKey
3. NotNullable
4. NotNullable
5. PrimaryKey
6. Unique

These can be used for whatever the user wants but if him does not want some, him can specify the name. For example

```json
"IgnoreAnnotations": 
    [
        {
            "AnnotationName": "PrimaryKey"
        }
    ]
```

In this case **@PrimaryKey** annotation won't be used during mapping process.

* **TableSpecification**: here we can specify configurations for each field in each table. Here several examples

```json
"TableSpecification": 
    [
        {
            "TableName": "Person",
            "Mapping": 
            [
                {
                    "AttributeName": "name",
                    "Type": "int",
                    "Package": "",
                    "CustomAnnotation": 
                    [
                        {
                            "AnnotationName": "",
                            "Package": ""
                        }
                    ]
                }
            ]
        }
    ]
```

```json
"TableSpecification": 
    [
        {
            "TableName": "Person",
            "Mapping": 
            [
                {
                    "AttributeName": "name",
                    "Type": "MyCustomObject",
                    "Package": "PackageWhereCustomObjectIs",
                    "CustomAnnotation": 
                    [
                        {
                            "AnnotationName": "",
                            "Package": ""
                        }
                    ]
                }
            ]
        }
    ]
```

```json
"TableSpecification": 
    [
        {
            "TableName": "Person",
            "Mapping": 
            [
                {
                    "AttributeName": "name",
                    "Type": "MyCustomObject",
                    "Package": "PackageWhereCustomObjectIs",
                    "CustomAnnotation": 
                    [
                        {
                            "AnnotationName": "MyCustomAnnotation",
                            "Package": "PackageWhereMyCustomAnnotationIs"
                        }
                    ]
                }
            ]
        }
    ]
```
*Package: full package starting from src folder must be specified*
