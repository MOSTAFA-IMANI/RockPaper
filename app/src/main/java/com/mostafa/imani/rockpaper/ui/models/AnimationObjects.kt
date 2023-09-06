package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset

abstract class AnimationObjects(){
    abstract val id:Int
    abstract var startOffset:Offset
    abstract var currentOffset:Offset
    abstract val objectSize:Int

}
