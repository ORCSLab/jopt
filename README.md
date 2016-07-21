# jOpt Framework

## 1. About

jOpt is a Java Framework for reducing work and time spent while developing optimization algorithms and performing computational experiments. It is written using Java 8 programming language, so codes that depend on jOpt Framework needs JDK 8 and JRE 8 for compiling and running.

## 2. Download ##

### 2.1. Binary

To use jOpt Framework from the compiled .jar file, first you have to download it and its dependencies:
* [jopt-1.1.0.jar](https://github.com/andremaravilha/jopt/releases/download/1.1.0/jopt-1.1.0.jar)
* [dependency.zip](https://github.com/andremaravilha/jopt/releases/download/1.1.0/dependency.zip) or [dependency.tar.gz](https://github.com/andremaravilha/jopt/releases/download/1.1.0/dependency.tar.gz)

After download, you have to extract **dependency.zip** (or **dependency.tar.gz**) and add **jopt-1.1.0.jar** and the dependencies on the classpath.

You may prefer to use just [jopt-1.1.0-jar-with-dependencies.jar](https://github.com/andremaravilha/jopt/releases/download/1.1.0/jopt-1.1.0-jar-with-dependencies.jar), which already includes all dependencies.

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
    <version>1.1.0</version>
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
  compile 'com.github.andremaravilha:jopt:1.1.0'
}
```
