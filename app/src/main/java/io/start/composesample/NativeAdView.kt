package io.start.composesample.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.startapp.sdk.ads.nativead.NativeAdDetails
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@Composable
fun NativeAdCustomBanner(ad: NativeAdDetails) {
    Row() {
        Box(modifier = Modifier.width(200.dp).height(200.dp).padding(20.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ad.imageBitmap)
                    .crossfade(true)
                    .build(),
                contentDescription = "Ad banner",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column() {
            Text(ad.title, style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
            Text(ad.rating.toString(), modifier = Modifier.padding(20.dp, 0.dp))

            Text(ad.description, style = MaterialTheme.typography.body1,  modifier = Modifier.padding(20.dp, 10.dp) )

            Text(ad.category, style = MaterialTheme.typography.body2, modifier = Modifier.padding(20.dp, 0.dp))
        }
    }
}
