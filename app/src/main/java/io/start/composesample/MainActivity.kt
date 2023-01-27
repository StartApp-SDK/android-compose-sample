package io.start.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.startapp.sdk.ads.banner.Banner
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import io.start.composesample.ui.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG)
        //Consent From European Users (GDPR), true if user has consented
        //Consent is not required for users that are based outside EU member states
        StartAppSDK.setUserConsent (this, "pas", System.currentTimeMillis(), true)

        setContent {
            ComposeSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StartIOBanner()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        StartIOBanner()
    }
}

@Composable
fun StartIOBanner() {
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
    }
}