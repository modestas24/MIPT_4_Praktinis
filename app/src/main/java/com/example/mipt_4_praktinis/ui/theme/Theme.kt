package com.example.mipt_4_praktinis.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun CustomTheme(
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    var colorScheme: ColorScheme = MaterialTheme.colorScheme

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme)
            colorScheme = dynamicDarkColorScheme(context)
        else
            colorScheme = dynamicLightColorScheme(context)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}