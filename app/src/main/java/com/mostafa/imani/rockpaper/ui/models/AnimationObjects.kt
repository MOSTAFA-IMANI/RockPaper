package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import com.mostafa.imani.rockpaper.util.extension.getDistanceFrom
import kotlin.math.abs

abstract class AnimationObjects() {
    abstract val id: Int
    abstract var startOffset: Offset
    abstract var currentOffset: Animatable<Offset, AnimationVector2D>
    abstract val destination: MutableState<Offset>
    abstract val objectSize: Int
    abstract var isRemoved: Boolean
    fun checkItemReceived(): Boolean {
        return !(abs(currentOffset.value.x - destination.value.x) > 1 || abs(currentOffset.value.y - destination.value.y) > 1)
    }

    fun getDistanceFrom(otherObject: AnimationObjects): Double {
        return currentOffset.value.getDistanceFrom(otherObject.currentOffset.value)
    }

    fun isHitMe(otherObject: AnimationObjects): Boolean {
        return getDistanceFrom(otherObject) <= objectSize
    }

    fun isNotRunningAway(): Boolean {
        return startOffset.getDistanceFrom(destination.value) > objectSize
    }

    fun runAwayOnOpposite(wallFrame: BoundFrame) {
        if (destination.value.getDistanceFrom(startOffset) < 50)
            destination.value = wallFrame.getRandomOffsetOnBound(objectSize)
        else
            destination.value = startOffset

    }

    abstract fun battleToOtherObject(objectB: AnimationObjects)

}
