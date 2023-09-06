package com.mostafa.imani.rockpaper.ui.screen

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.mostafa.imani.rockpaper.util.extension.forEachSurvived
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
            val wallFrame = BoundFrame(width- objectSize, height-objectSize)
            PlayScreen(
                wallFrame, getListOfObjects(5, wallFrame, objectSize)
            )
        }
    }
}

val itemsToRemove = ArrayList<AnimationObjects>()
@Composable
fun PlayScreen(
    wallFrame: BoundFrame,
    gameObjectList: List<AnimationObjects>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(width = wallFrame.stroke.dp, color = Color.Black)
    ) {

        DrawObjects(gameObjectList)
        MoveObjects(gameObjectList)
        HitTheWallCheck(gameObjectList, wallFrame)
        HitObjectCheck(gameObjectList, wallFrame)
        stopedObjectCheck(gameObjectList)
    }


}

fun stopedObjectCheck(gameObjectList: List<AnimationObjects>) {
    gameObjectList.forEachSurvived {
        if (!it.currentOffset.isRunning) {
            Log.d("TAG", "stopedObjectCheck: $it ")
        }
    }
}

@Composable
fun HitObjectCheck(gameObjectList: List<AnimationObjects>, wallFrame: BoundFrame) {
    gameObjectList.forEachSurvived { objectA ->
        gameObjectList.forEachSurvived { objectB ->

            if (objectA != objectB) {

                if (checkHit(objectA, objectB)) {
                    checkToRemoveItem(objectA, objectB)
                    Log.d("TAG", "Gesture:**checkHit*************  ")
                    if (objectA.isNotRunningAway()) {
                        objectA.runAwayOnOpposite(wallFrame)
                    }
                    if (objectB.isNotRunningAway()) {
                        objectB.runAwayOnOpposite(wallFrame)

                    }
                }
            }

        }
    }
}

fun checkToRemoveItem(objectA: AnimationObjects, objectB: AnimationObjects) {
    objectA.battleToOtherObject(objectB)
}

fun checkHit(objectA: AnimationObjects, objectB: AnimationObjects): Boolean {
    return objectA.isHitMe(objectB)
}

private fun getListOfObjects(
    count: Int,
    wallFrame: BoundFrame,
    objectSize: Int,
): List<AnimationObjects> {
    val animationObjectsMutableList = mutableListOf<AnimationObjects>()

    repeat(count) {
        val start = wallFrame.getRandomOffsetOnBound(objectSize)

        animationObjectsMutableList.add(
            Paper(
                id = 100 + it,
                startOffset = start,
                currentOffset = Animatable(start, Offset.VectorConverter),
                destination = mutableStateOf(wallFrame.getRandomOffsetOnBound(objectSize)),
                objectSize = objectSize
            )
        )
    }
    repeat(count) {
        val start = wallFrame.getRandomOffsetOnBound(objectSize)
        animationObjectsMutableList.add(
            Scissor(
                id = 200 + it,
                startOffset = start,
                currentOffset = Animatable(start, Offset.VectorConverter),
                destination = mutableStateOf(wallFrame.getRandomOffsetOnBound(objectSize)),
                objectSize = objectSize
            )
        )
    }
    repeat(count) {
        val start = wallFrame.getRandomOffsetOnBound(objectSize)
        animationObjectsMutableList.add(
            Rock(
                id = 300 + it,
                startOffset = start,
                currentOffset = Animatable(start, Offset.VectorConverter),
                destination = mutableStateOf(wallFrame.getRandomOffsetOnBound(objectSize)),
                objectSize = objectSize
            )
        )
    }

    return animationObjectsMutableList
}

@Composable
fun HitTheWallCheck(gameObjectList: List<AnimationObjects>, wallFrame: BoundFrame) {
    gameObjectList.forEachSurvived {
        if (it.checkItemReceived() || wallFrame.isOffsetOutOfBound(it.currentOffset.value)) {
            it.startOffset = it.destination.value
            it.destination.value =
                wallFrame.findNewDistillation(it.startOffset, it.currentOffset.value)
        }

    }
}

@Composable
fun MoveObjects(objectList: List<AnimationObjects>) {
    objectList.forEachSurvived {
        LaunchedEffect(it.destination.value.x, it.destination.value.y) {
            it.currentOffset.stop()
            it.currentOffset.animateTo(it.destination.value, tween(durationMillis = 3000))
        }
    }
}

@Composable
fun DrawObjects(objectList: List<AnimationObjects>) {
    objectList.forEachSurvived {

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
        modifier = Modifier
            .size(OBJECT_SIZE.dp)
            .offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.ContentCut,
        contentDescription = "Scissor"
    )
}

@Composable
fun DrawRock(objectAnimationObjects: Rock) {
    Icon(
        modifier = Modifier
            .size(OBJECT_SIZE.dp)
            .offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.Circle,
        contentDescription = "Rock"
    )
}

@Composable
fun DrawPaper(objectAnimationObjects: Paper) {
    Icon(
        modifier = Modifier
            .size(OBJECT_SIZE.dp)
            .offset { objectAnimationObjects.currentOffset.value.toIntOffset() },
        imageVector = Icons.Filled.Newspaper,
        contentDescription = "Paper"
    )
}
