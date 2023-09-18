package com.mitch.dimlight.ui.screens.home.components

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.mitch.dimlight.R

@Composable
fun FlashlightImage(
    brightnessLevel: Int,
    modifier: Modifier = Modifier
) {
    val flashlightBrightnessDrawable = AppCompatResources.getDrawable(
        LocalContext.current,
        R.drawable.flashlight
    )

    Column(modifier = modifier) {
        if (flashlightBrightnessDrawable != null && brightnessLevel > 0) {
            /*flashlightBrightnessDrawable.level = convert(brightnessLevel)
                .fromRange(FlashlightUtils.brightnessActiveRange)
                .toRange(FlashlightUtils.brightnessDrawableRange)*/

            Image(
                painter = rememberDrawablePainter(flashlightBrightnessDrawable),
                contentDescription = null
            )
        }
        Image(
            painter = painterResource(id = R.drawable.flashlight),
            contentDescription = null
        )
    }
}
