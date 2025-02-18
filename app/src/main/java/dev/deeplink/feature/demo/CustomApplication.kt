package dev.deeplink.feature.demo

import android.app.Application
import dev.deeplink.feature.FeatureConfig
import dev.deeplink.feature.FeatureSdk


class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (baseContext.packageName.equals(packageName)) {
            //[Required] Register an account at https://console.dlink.cloud/. After creating an app on the platform, get the corresponding Appid of the app.
            val appId = "Appid from https://console.dlink.cloud"
            //[Required] Device ID in the developer's business system
            val deviceId = "ABCD-EFGH-IJKL-MNOP"
            //[Optional] If the developer wants to obtain feature data by themselves,
            // they can deploy a server to receive data and pass the server address to the SDK.
            val serverUrl = "https://xxx.xxx"

            //Please call the SDK initialization method in the thread.
            //Time-consuming operations will be performed during initialization.
            Thread {
                FeatureSdk.launch(
                    this,
                    FeatureConfig(appId = appId, deviceId = deviceId, apiBaseUrl = serverUrl)
                )
            }.start()
        }
    }
}