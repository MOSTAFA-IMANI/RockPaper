package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset

abstract class AnimationObjects(){
    abstract val id:Int
    abstract var startOffset:Offset
    abstract var currentOffset: Animatable<Offset, AnimationVector2D>
    abstract val destination: MutableState<Offset>
    abstract val objectSize:Int
}
