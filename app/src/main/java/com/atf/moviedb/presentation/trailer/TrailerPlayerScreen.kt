package com.atf.moviedb.presentation.trailer

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext

@Composable
fun TrailerPlayerScreen(
    youtubeKey: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {

        val activity = context as Activity

        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    BackHandler {
        onBack()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {

                WebView(it).apply {

                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    webChromeClient =
                        WebChromeClient()

                    webViewClient =
                        WebViewClient()

                    loadData(
                        youtubeHtml(youtubeKey),
                        "text/html",
                        "utf-8"
                    )
                }

            }
        )
    }
}

private fun youtubeHtml(
    key: String
): String {

    return """
        <html>
            <body style="
                margin:0;
                padding:0;
                background:black;
            ">
            
            <iframe
                width="100%"
                height="100%"
                src="https://www.youtube.com/embed/$key?autoplay=1"
                frameborder="0"
                allow="
                autoplay;
                encrypted-media;
                fullscreen">
            </iframe>

            </body>
        </html>
    """
}