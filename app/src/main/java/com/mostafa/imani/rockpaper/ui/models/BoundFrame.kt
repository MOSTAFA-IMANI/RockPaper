package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset
import com.mostafa.imani.rockpaper.ui.models.ConstantsConfig.DEGREE_IGNORE_THRESHOLD
import com.mostafa.imani.rockpaper.ui.models.ConstantsConfig.ERROR_IGNORE_THRESHOLD
import com.mostafa.imani.rockpaper.ui.models.ConstantsConfig.OBJECT_SIZE
import com.mostafa.imani.rockpaper.util.extension.findX
import com.mostafa.imani.rockpaper.util.extension.findY
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

data class BoundFrame(
    val width: Int,
    val height: Int,
    val stroke: Int = 10,
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

    private fun getRandomBoundFrame(): BoundFrameType {
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

    fun isOffsetOutOfBound(currentPosition: Offset): Boolean {
        if(currentPosition.x < 0 - ERROR_IGNORE_THRESHOLD)
            return true
        if ( currentPosition.x > width + ERROR_IGNORE_THRESHOLD)
            return true
        if (currentPosition.y < 0 - ERROR_IGNORE_THRESHOLD)
            return true
        if (currentPosition.y > height+ ERROR_IGNORE_THRESHOLD)
            return true
        return (currentPosition.x < 0 || currentPosition.x > width) && (currentPosition.y < 0 || currentPosition.y > height).also { println(it) }
    }

    fun findNewDistillation(start: Offset, end: Offset): Offset {
        val mirrorDegree = bounceDegree(start, end)
        val currentBound = findCurrentBound(end)
        return findPointOnBound(mirrorDegree, end, currentBound ?: BoundFrameType.RIGHT)
    }


    private fun findPointOnBound(
        degree: Double,
        startPosition: Offset,
        currentBound: BoundFrameType,
    ): Offset {
        var cX: Int
        var cY: Int
        var currentPosition: Offset = startPosition

        while (true) {
            cX = (currentPosition.x.findX(degree.roundToInt())).roundToInt()
            cY = (currentPosition.y.findY(degree.roundToInt())).roundToInt()
            currentPosition = currentPosition.copy(cX.toFloat(), cY.toFloat())
            if (isOffsetOnTheBound( currentPosition, currentBound))
                break
            if (isOffsetOutOfBound( currentPosition))
                currentPosition = getRandomOffsetOnBound(OBJECT_SIZE,)
        }
        return currentPosition
    }

    private fun isOffsetOnTheBound(currentPosition: Offset, currentBound: BoundFrameType): Boolean {
        return when (currentBound) {

            BoundFrameType.LEFT -> {

                isOnRightBound(currentPosition)
                        || isOnUpBound(currentPosition)
                        || isOnDownBound( currentPosition)


            }

            BoundFrameType.RIGHT -> {
                isOnLeftBound(currentPosition)
                        || isOnUpBound(currentPosition)
                        || isOnDownBound(currentPosition)

            }

            BoundFrameType.UP -> {
                isOnLeftBound(currentPosition)
                        || isOnRightBound(currentPosition)
                        || isOnDownBound(currentPosition)

            }

            BoundFrameType.DOWN -> {
                isOnLeftBound(currentPosition)
                        || isOnRightBound( currentPosition)
                        || isOnUpBound( currentPosition)

            }
        }
    }


    private fun findCurrentBound(position: Offset): BoundFrameType? {
        return if (isOnLeftBound( position))
            BoundFrameType.LEFT
        else if (isOnDownBound( position))
            BoundFrameType.DOWN
        else if (isOnRightBound(position))
            BoundFrameType.RIGHT
        else if (isOnUpBound( position))
            BoundFrameType.UP
        else
            null
    }

    private fun isOnUpBound(currentPosition: Offset): Boolean {
        return ((0 - ERROR_IGNORE_THRESHOLD..width + ERROR_IGNORE_THRESHOLD).contains(
            currentPosition.x.roundToInt()
        ) && currentPosition.y.roundToInt() < ERROR_IGNORE_THRESHOLD)
            .also { if (it) println("isOnUpBound") }


    }

    private fun isOnDownBound(currentPosition: Offset): Boolean {
        return (0 - ERROR_IGNORE_THRESHOLD..height + ERROR_IGNORE_THRESHOLD).contains(
            currentPosition.x.roundToInt()
        ) && (height - ERROR_IGNORE_THRESHOLD..height + ERROR_IGNORE_THRESHOLD).contains(
            currentPosition.y.roundToInt()
        )
            .also { if (it) println("isOnDownBound") }

    }

    private fun isOnRightBound(currentPosition: Offset): Boolean {
        return ((width - ERROR_IGNORE_THRESHOLD..width + ERROR_IGNORE_THRESHOLD).contains(
            currentPosition.x.roundToInt()
        ) && (0 - ERROR_IGNORE_THRESHOLD..height - OBJECT_SIZE).contains(
            currentPosition.y.roundToInt()
        ))
            .also { if (it) println("isOnRightBound") }

    }

    private fun isOnLeftBound(currentPosition: Offset): Boolean {
        return (currentPosition.x < ERROR_IGNORE_THRESHOLD && (-ERROR_IGNORE_THRESHOLD..height + ERROR_IGNORE_THRESHOLD).contains(
            currentPosition.y.roundToInt()
        ))
            .also { if (it) println("isOnLeft") }
    }

    private fun bounceDegree(
        startPosition: Offset,
        currentPosition: Offset,
    ): Double {


        var degree = getDegree(startPosition, currentPosition)

        if (currentPosition.x + OBJECT_SIZE >= width) {
            if (degree in (0.0..90.0)) {
                degree = getRandomDegree(90, 180)

            } else if (degree in (270.0..360.0)) {
                degree = getRandomDegree(180, 270)
            }
        }

        if (currentPosition.y + OBJECT_SIZE >= height) {
            if (degree in (90.0..180.0)) {
                degree = getRandomDegree(180, 270)
            } else if (degree in (0.0..90.0)) {
                degree = getRandomDegree(270, 360)
            }

        }

        if (currentPosition.x - ERROR_IGNORE_THRESHOLD <= 0) {
            if (degree in (180.0..270.0)) {
                degree = getRandomDegree(270, 360)
            } else if (degree in (90.0..180.0)) {
                degree = getRandomDegree(0, 90)
            }

        }

        if (currentPosition.y - ERROR_IGNORE_THRESHOLD <= 0) {
            if (degree in (270.0..360.0)) {
                degree = getRandomDegree(0, 90)
            } else if (degree in (180.0..270.0)) {
                degree = getRandomDegree(90, 180)
            }
        }
        return degree

    }

    private fun getRandomDegree(start: Int, end: Int): Double {
        return (start + DEGREE_IGNORE_THRESHOLD..end - DEGREE_IGNORE_THRESHOLD).random()
            .toDouble()
    }

    private fun getDegree(
        startingPosition: Offset,
        currentPosition: Offset,
    ): Double {
        val dx = currentPosition.x - startingPosition.x
        val dy = currentPosition.y - startingPosition.y
        val radian = atan2(dy.toDouble(), dx.toDouble())
        val degree = Math.toDegrees(radian)

        return degree + 180
    }
}
