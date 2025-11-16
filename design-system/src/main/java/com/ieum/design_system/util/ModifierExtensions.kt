package com.ieum.design_system.util

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.dropShadow(
    shape: Shape = MaterialTheme.shapes.medium,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 4.dp,
    blur: Dp = 30.dp,
    spread: Dp = 0.dp,
    color: Color = Color(0xFFC2C2C2).copy(alpha = 0.2f),
) = composed {
    val density = LocalDensity.current

    val paint = remember(color, blur) {
        Paint().apply {
            this.color = color
            val blurPx = with(density) { blur.toPx() }
            if (blurPx > 0f) {
                this.asFrameworkPaint().maskFilter =
                    BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
            }
        }
    }

    drawBehind {
        val spreadPx = spread.toPx()
        val offsetXPx = offsetX.toPx()
        val offsetYPx = offsetY.toPx()

        val shadowWidth = size.width + spreadPx
        val shadowHeight = size.height + spreadPx

        if (shadowWidth <= 0f || shadowHeight <= 0f) return@drawBehind

        val shadowSize = Size(shadowWidth, shadowHeight)
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.translate(offsetXPx, offsetYPx)
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
        }
    }
}

fun Modifier.topBorder(
    height: Float,
    color: Color,
) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = height,
    )
}

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    this.clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
    )
}