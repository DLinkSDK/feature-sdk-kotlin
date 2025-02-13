# feature-sdk-kotlin

Step 1: Get the Appid

Register an account at [https://console.dlink.cloud/](https://console.dlink.cloud). After creating an app on the platform, get the corresponding Appid of the app.

Step 2: Get the SDK

(1) Configure the Maven repository
```kotlin   
repositories {
   maven { url 'https://maven.deeplink.dev/repository/maven-releases/' }
}
```

Note: The Maven repository address needs to be configured in both 'buildscript' and 'allprojects' in the root directory's 'build.gradle'.

(2) If you are using Gradle for integration, add the following code to your project's build.gradle:
```kotlin
implementation 'dev.deeplink:feature:3.0.6'
```

Step 3: Configure AndroidManifest

Find the project configuration file AndroidManifest.xml in your project, and add the following permissions:

```kotlin
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

If you need to add obfuscation during packaging, please add the following code to the obfuscation configuration file:
```kotlin
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keep class dev.deeplink.feature.proto.**{*;}
```

Step 4: Initialize the SDK
If your application is in multi-process mode, please initialize the SDK in the main process. Here is the reference code:
```kotlin
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (baseContext.packageName.equals(packageName)) {
            //[Required] Register an account at https://console.dlink.cloud/. After creating an app on the platform, get the corresponding Appid of the app.
            val appId = "Appid from https://console.dlink.cloud"
            //[Required] Device ID in the developer's business system
            val deviceId = "ABCD-EFGH-IJKL-MNOP"

            //Please call the SDK initialization method in the thread.
            //Time-consuming operations will be performed during initialization.
            Thread {
                FeatureSdk.launch(
                    this,
                    FeatureConfig(appId = appId, deviceId = deviceId)
                )
            }.start()
        }
    }
}
```