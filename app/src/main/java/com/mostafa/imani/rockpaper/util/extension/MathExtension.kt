package com.mostafa.imani.rockpaper.util.extension

import com.mostafa.imani.rockpaper.ui.models.ConstantsConfig.ERROR_IGNORE_THRESHOLD
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


fun Float.findX(degree:Int): Double = this + ERROR_IGNORE_THRESHOLD * cos(degree * (PI / 180.0))
fun Float.findY(degree: Int): Double =  this + ERROR_IGNORE_THRESHOLD * sin(degree * (PI / 180.0))