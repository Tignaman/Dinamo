# Dinamo

> **Work-in-progress Java 8 library that through the use of annotation processor helps to setup a project with particular focus on database entities.**

## Overview

Dinamo was designed to speed up and improve the initial setup of a project. Inside Dinamo, we can find several features that help you build code right away without worrying about building an underlying layer.

Unlike other libraries, Dinamo does not want to give precise rules to follow, but configurable utility features (you can only use a part of Dinamo without any problems). It consists of several internal modules, which can be used independently but by simultaneous use of these, many advantages will be obtained. The modules that are used inside are: 

* **[Sinfonia](https://github.com/Jaaaas/Sinfonia/)**
* **[Cosmo](https://github.com/Jaaaas/Cosmo)**

Today, Dinamo focuses mainly on database communication. We can say that it allows a circular mapping of database tables and offers a complete and configurable query management. 
Through an annotation processor, Dinamo lets you map the tables of an existing database with java classes. At the moment, we have already thought of ways to configure this mapping so that you do not always have to make the same changes as the classes are regenerated.

Dinamo and its modules are still under testing, but can still be used in a project. However, support must be added to the different types of RDBMS.

## Getting Started

### Prerequisites
In order to use Sinfonia you simply need to import the **JAR** 
(you can download it from here [Dinamo](https://github.com/Tignaman/Dinamo/releases/tag/v1.0.1) or from the releases) into the project and you can use its features.

### Features
Dinamo has an **annotation processor** that use **@DinamoBootstrap** annotation.

```java
@Retention(RetentionPolicy.SOURCE)
public @interface DinamoBootstrap
{
    String path();
}
```
**Path** property is the absolute path to src folder where Dinamo configuration package will be created.

Example:
```java
@DinamoBootstrap(path="absolute/Path/To/DinamoTest/src")
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
    "ModelPath": "DinamoModels"
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
                    "AttributeName": "age",
                    "Type": "String",
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
                    "Type": "MyCustomType",
                    "Package": "PackageWhereMyCustomTypeIs",
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
                    "Type": "MyCustomType",
                    "Package": "PackageWhereMyCustomType",
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

### Creation phase

After filling both files (for **ModelConfiguration** file only *ModelPackage* is required), we build the project.
Now we should see in the specified path all the java classes.

Thanks to Dinamo's internal modules, we can start creating queries through **[Cosmo](https://github.com/Jaaaas/Cosmo)**, and perform them through **[Sinfonia](https://github.com/Jaaaas/Sinfonia/)** considering the classes created by Dinamo.


### Related project
* **[Cosmo](https://github.com/Jaaaas/Cosmo)**
* **[Sinfonia](https://github.com/Jaaaas/Sinfonia/)**

### Contributors
* **[suxl89](https://github.com/suxl89)**
* **[Nazzareno Di Pietro](https://github.com/Tignaman)**
* **[fpafumi](https://github.com/fpafumi)**

### Future developments

* **Testing**
* **Support for different RDBMS**
* **Maven integration**
* **New mapping operation (extends, implements, compless annotation )**
* **New modules integration**
* **Improves performance**
