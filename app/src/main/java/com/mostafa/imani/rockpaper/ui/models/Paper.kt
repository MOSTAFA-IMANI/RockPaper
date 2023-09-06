package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape

data class Paper (
    override val id: Int,
    override var startOffset: Offset,
    override var currentOffset: Offset,
    override val objectSize: Int,

): AnimationObjects()