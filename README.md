Reddit Finder
=======================

Welcome to the Reddit Finder. This repo provides an app to search and get reddit questions for an keyword.
 
## Getting Started

**NOTE: These instructions assume that you are using a Mac with OSX installed.**

* Install Java SE Development Kit (JDK) 7 _or_ 8 , if you don't already have either. Also, ensure that your bash profile includes variables pointing to the installations, such as the following:

    ```shell
    export JAVA7_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_60.jdk/Contents/Home
    export JAVA8_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_51.jdk/Contents/Home
    export JAVA_HOME=$JAVA8_HOME
    ```

* Install the latest Android Studio or, if you prefer another IDE, just the "Stand-alone SDK Tools". Then launch the "SDK Manager" (from Android Studio: Tools > Android > SDK Manager) In addition to an SDK "platform" (currently using Android 8.0/API 26), you will need to install the following additional components (latest versions, probably):
    * Android SDK Platform-tools
    * Android SDK Build-tools
    * Android Support Library
    * Android Support Repository

## Initial Configuration

The project requires the installation of numerous versions of the Android SDK platforms and tools.

Following installation of these versions, project properties must be defined so that the platform
projects compile successfully.

To achieve a compiling project, run at the command line:

```
./gradlew clean build
```

To install the app, run at the command line:
 
```
./gradlew clean instalDebug
```