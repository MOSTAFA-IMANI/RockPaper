package com.mostafa.imani.rockpaper.ui.models

import androidx.compose.ui.geometry.Offset


data class Scissor (
    override val id: Int,
    override var startOffset: Offset,
    override var currentOffset: Offset,
    override val objectSize: Int,

): AnimationObjects()