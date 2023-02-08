package io.start.composesample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.startapp.sdk.ads.banner.Banner
import com.startapp.sdk.ads.nativead.NativeAdDetails
import com.startapp.sdk.ads.nativead.NativeAdPreferences
import com.startapp.sdk.ads.nativead.StartAppNativeAd
import com.startapp.sdk.adsbase.Ad
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import com.startapp.sdk.adsbase.adlisteners.AdEventListener
import io.start.composesample.ui.theme.ComposeSampleTheme
import io.start.composesample.ui.theme.NativeAdCustomBanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {
    val nativeUIState = MutableStateFlow(NativeUIState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG)
        //Consent From European Users (GDPR), true if user has consented
        //Consent is not required for users that are based outside EU member states
        StartAppSDK.setUserConsent (this, "pas", System.currentTimeMillis(), true)
        loadNativeAd(StartAppNativeAd(baseContext), nativeUIState)

        setContent {
            ComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val localContext= LocalContext.current
            Button(onClick = { StartAppAd.showAd(localContext) }) {
                Text("Show interstitial")
            }
            AndroidView(
                factory = { context ->
                    Banner(context)
                })
            nativeUIState.collectAsState().value.nativeAds.forEach {NativeAdCustomBanner(ad = it)}
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeSampleTheme {
            MainScreen()
        }
    }
}


fun loadNativeAd(startAppNativeAd: StartAppNativeAd, nativeUIState: MutableStateFlow<NativeUIState>) {

    val nativePrefs = NativeAdPreferences()
        .setAdsNumber(3) // Load 3 Native Ads
        .setAutoBitmapDownload(true) // Retrieve Images object
        .setPrimaryImageSize(2) // 150x150 image


    val adListener: AdEventListener = object : AdEventListener {
        override fun onReceiveAd(arg0: Ad) {
            val ads: ArrayList<NativeAdDetails> = startAppNativeAd.nativeAds // get NativeAds list

            nativeUIState.update { it.copy(nativeAds = ads) }
        }

        override fun onFailedToReceiveAd(arg0: Ad?) {
            Log.d("MyApplication", "FailedToReceiveAd")
        }
    }

    startAppNativeAd.loadAd(nativePrefs, adListener)
}

data class NativeUIState(
    var nativeAds: ArrayList<NativeAdDetails> = arrayListOf()
)

