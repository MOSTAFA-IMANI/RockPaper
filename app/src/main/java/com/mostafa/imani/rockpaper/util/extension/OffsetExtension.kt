package com.mostafa.imani.rockpaper.util.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import com.mostafa.imani.rockpaper.ui.models.AnimationObjects
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
fun Offset.getDistanceFrom(offset: Offset) = calculateDistance(this,offset,)

private fun calculateDistance(offset1: Offset, offset2: Offset, ): Double {
    val dx = offset2.x - offset1.x
    val dy = offset2.y - offset1.y

    return sqrt(dx * dx + dy * dy.toDouble())
}
