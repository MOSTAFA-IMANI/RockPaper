package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.*
import org.junit.Test



class BoundFrameTest {

    @Test
    fun getRandomOffsetOnBound_test() {
        val boundFrame = BoundFrame(800, 600)
        val imageSize = 100
        val offset = boundFrame.getRandomOffsetOnBound(imageSize)
        assertTrue(offset.x in 0f..700f)
        assertTrue(offset.y in 0f..500f)
    }

    @Test
    fun getRandomOffsetInBound_test() {
        val boundFrame = BoundFrame(800, 600)
        val imageSize = 100
        val offset = boundFrame.getRandomOffsetInBound(imageSize)
        assertTrue(offset.x in 0f..700f)
        assertTrue(offset.y in 0f..500f)
    }

    @Test
    fun isOffsetOutOfBound_test() {
        val boundFrame = BoundFrame(800, 600)

        val offset1 = Offset(-20f, 300f)
        assertTrue(boundFrame.isOffsetOutOfBound(offset1))

        val offset2 = Offset(820f, 300f)
        assertTrue(boundFrame.isOffsetOutOfBound(offset2))

        val offset3 = Offset(400f, -20f)
        assertTrue(boundFrame.isOffsetOutOfBound(offset3))

        val offset4 = Offset(400f, 620f)
        assertTrue(boundFrame.isOffsetOutOfBound(offset4))

        val offset5 = Offset(400f, 300f)
        assertFalse(boundFrame.isOffsetOutOfBound(offset5))
    }

    @Test
    fun findNewDistillation_test() {
        val boundFrame = BoundFrame(800, 600)

        val start = Offset(100f, 100f)
        val end = Offset(200f, 200f)
        val distillation = boundFrame.findNewDistillation(start, end)

        assertTrue(distillation.x in 0f..800f)
        assertTrue(distillation.y in 0f..600f)
    }

}