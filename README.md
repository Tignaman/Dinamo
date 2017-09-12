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
