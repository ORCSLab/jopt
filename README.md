# jOpt Framework

## 1. About

jOpt is a Java Framework for reducing work and time spent while developing optimization algorithms and performing computational experiments.

## 2. Download ##

### 2.1. Binary

To use jOpt Framework from the compiled .jar file, first you have to download it and its dependencies:
* [jopt-1.0.0.jar](https://github.com/andremaravilha/jopt/releases/download/1.0.0/jopt-1.0.0.jar)
* [dependency.tar.gz](https://github.com/andremaravilha/jopt/releases/download/1.0.0/dependency.tar.gz)

After download, you have to extract **dependency.tar.gz** and add **jopt-1.0.0.jar** and the dependencies on the classpath.

### 2.2. Maven

You can use jOpt Framework with Maven by adding the following to your **pom.xml**:

```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.andremaravilha</groupId>
    <artifactId>jopt</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

### 2.3. Gradle

You can use jOpt Framework with Gradle. First, add the following content in your root **build.gradle** at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

Then, add the dependency:

```
dependencies {
  compile 'com.github.andremaravilha:jopt:1.0.0'
}
```
