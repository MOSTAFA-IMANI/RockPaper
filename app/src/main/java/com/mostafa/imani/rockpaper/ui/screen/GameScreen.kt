package com.mostafa.imani.rockpaper.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.mostafa.imani.rockpaper.ui.models.AnimationObjects
import com.mostafa.imani.rockpaper.ui.models.BoundFrame
import com.mostafa.imani.rockpaper.ui.models.ConstantsConfig.OBJECT_SIZE
import com.mostafa.imani.rockpaper.ui.models.Paper
import com.mostafa.imani.rockpaper.ui.models.Rock
import com.mostafa.imani.rockpaper.ui.models.Scissor
import com.mostafa.imani.rockpaper.util.extension.toIntOffset
import kotlin.math.roundToInt


@Composable
fun GameScreen(
    density: Density,

    ) {
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }



    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .onGloballyPositioned { coordinates ->
                width = coordinates.size.width
                height = coordinates.size.height
            },
    )
    {
        val objectSize =
            with(density) { OBJECT_SIZE.dp.toPx().roundToInt() }
        if (width > 0 && height > 0) {
            val wallFrame = BoundFrame(width, height)
            PlayScreen(
                wallFrame, objectSize,
                getListOfObjects(1, wallFrame, objectSize)
            )
        }
    }
}

private fun getListOfObjects(
    count: Int,
    wallFrame: BoundFrame,
    objectSize: Int,
): List<AnimationObjects> {
    val start = wallFrame.getRandomOffsetOnBound(objectSize)
    return listOf<AnimationObjects>(
        Paper(
            id = 1,
            startOffset = start,
            currentOffset = Animatable(start, Offset.VectorConverter),
            destination = mutableStateOf(wallFrame.getRandomOffsetOnBound(objectSize)),
            objectSize = objectSize
        )
    )
}

@Composable
fun PlayScreen(
    wallFrame: BoundFrame,
    itemSizePx: Int,
    initialOGameObjectList: List<AnimationObjects>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        DrawObjects(initialOGameObjectList)


    }


}

@Composable
fun DrawObjects(objectList: List<AnimationObjects>) {
    objectList.forEach {
        when (it) {
            is Paper -> {
                DrawPaper(it)
            }

            is Rock -> {
                DrawRock(it)
            }

            is Scissor -> {
                DrawScissor(it)
            }
        }
    }
}

@Composable
fun DrawScissor(objectAnimationObjects: Scissor) {
    Icon(
        modifier = Modifier.offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.Close,
        contentDescription = "sci"
    )
}

@Composable
fun DrawRock(objectAnimationObjects: Rock) {
    Icon(
        modifier = Modifier.offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.Close,
        contentDescription = "sci"
    )
}

@Composable
fun DrawPaper(objectAnimationObjects: Paper) {
    Icon(
        modifier = Modifier.offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.Close,
        contentDescription = "sci"
    )
}
