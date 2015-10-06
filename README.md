# Uid - a unique identifier utility for Java

A Java library for working with a simple unique identifier object.

[![Build Status](https://travis-ci.org/cookingfox/uid-java.svg?branch=master)](https://travis-ci.org/cookingfox/uid-java)

## Download

[![Download](https://api.bintray.com/packages/cookingfox/maven/uid-java/images/download.svg)](https://bintray.com/cookingfox/maven/uid-java/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.cookingfox/uid-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.cookingfox/uid-java)

The distribution is hosted on [Bintray](https://bintray.com/cookingfox/maven/uid-java/view).
To include the package in your projects, you can add the jCenter repository.

### Gradle

Add jCenter to your `repositories` block:

```groovy
repositories {
    jcenter()
}
```

and add the project to the `dependencies` block in your `build.gradle`:

```groovy
dependencies {
    compile 'com.cookingfox:uid-java:0.2.0'
}
```

### Maven

Add jCenter to your repositories in `pom.xml` or `settings.xml`:

```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
```

and add the project declaration to your `pom.xml`:

```xml
<dependency>
    <groupId>com.cookingfox</groupId>
    <artifactId>uid-java</artifactId>
    <version>0.2.0</version>
</dependency>
```
