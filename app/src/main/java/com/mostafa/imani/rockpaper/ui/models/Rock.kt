package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

data class Rock (
    override val id: Int,
    override var startOffset: Offset,
    override var currentOffset: Animatable<Offset, AnimationVector2D>,
    override val objectSize: Int,
    override val destination: MutableState<Offset>,
    override var isRemoved: Boolean= false
): AnimationObjects() {
    override fun battleToOtherObject(objectB: AnimationObjects) {
        when(objectB){
            is Paper -> {
                isRemoved = true
            }

        }
    }
}