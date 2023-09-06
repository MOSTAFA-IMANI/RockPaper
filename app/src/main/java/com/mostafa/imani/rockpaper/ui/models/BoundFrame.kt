package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset
import kotlin.random.Random
import kotlin.random.nextInt

data class BoundFrame(
    val width: Int,
    val height: Int,
    val stroke: Int = 0,
){


    fun getRandomOffsetOnBound(
        imageSize: Int,
        boundFrameType: BoundFrameType=getRandomBoundFrame(),
    ): Offset {
        return when (boundFrameType) {
            BoundFrameType.LEFT -> {
                Offset(0f, Random.nextInt(0..height - imageSize).toFloat())
            }

            BoundFrameType.RIGHT -> {
                Offset((width - imageSize).toFloat(), Random.nextInt(0..height - imageSize).toFloat())
            }

            BoundFrameType.UP -> {
                Offset(Random.nextInt(0..width - imageSize).toFloat(), 0f)
            }

            BoundFrameType.DOWN -> {
                Offset(Random.nextInt(0..height - imageSize).toFloat(), (height - imageSize).toFloat())
            }
        }
    }

    fun getRandomOffsetInBound(imageSize: Int): Offset {

        return Offset(
            Random.nextInt(0..width - imageSize).toFloat(),
            Random.nextInt(0..height - imageSize).toFloat()
        )

    }
    fun getRandomBoundFrame(): BoundFrameType {
        return when (Random.nextInt(1..4)) {
            1 -> {
                BoundFrameType.DOWN
            }

            2 -> {
                BoundFrameType.UP
            }

            3 -> {
                BoundFrameType.LEFT
            }

            4 -> {
                BoundFrameType.RIGHT
            }

            else -> {
                BoundFrameType.UP
            }
        }
    }
}
