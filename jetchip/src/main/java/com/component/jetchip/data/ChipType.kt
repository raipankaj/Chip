package com.component.jetchip.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ChipType {
    class Assist(val icon: ImageVector): ChipType()
    class Filter(val icon: ImageVector = Icons.Default.Check): ChipType()
    object Suggestion: ChipType()
}