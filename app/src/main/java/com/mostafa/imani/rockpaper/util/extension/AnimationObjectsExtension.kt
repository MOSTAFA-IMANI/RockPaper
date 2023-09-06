package com.mostafa.imani.rockpaper.util.extension

import com.mostafa.imani.rockpaper.ui.models.AnimationObjects

inline fun List<AnimationObjects>.forEachSurvived(action: (AnimationObjects) -> Unit){
    this.forEach { if (!it.isRemoved) action(it) }
}