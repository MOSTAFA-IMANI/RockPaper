package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset


data class Scissor (
    override val id: Int,
    override var startOffset: Offset,
    override var currentOffset: Animatable<Offset, AnimationVector2D>,
    override val destination: MutableState<Offset>,
    override val objectSize: Int,
): AnimationObjects()