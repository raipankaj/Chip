package com.component.jetchip

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.component.jetchip.data.ChipItem
import com.component.jetchip.data.ChipType

@Composable
fun Chip(labels: List<ChipItem>,
         defaultTextColor: Color = Color.Black,
         selectedTextColor: Color = Color.White,
         chipColor: Color = Color.Blue.copy(0.5f),
         chipOnClick: (String) -> Unit) {

    val springSpec = SpringSpec<Float>(
        stiffness = 800f,
        dampingRatio = 0.8f
    )

    var selectedChip by remember {
        mutableStateOf(-1)
    }

    LazyRow {
        itemsIndexed(labels) { index, item ->
            ChipBuilder(
                text = item.label,
                animationSpec = springSpec,
                selected = selectedChip == index,
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(percent = 20)),
                chipType = item.type, defaultTextColor = defaultTextColor,
                selectedTextColor = selectedTextColor, chipColor = chipColor) {
                selectedChip = if (selectedChip == index) {
                    chipOnClick("")
                    -1
                } else {
                    chipOnClick(labels[index].label)
                    index
                }
            }
        }
    }
}

@Composable
fun ChipBuilder(
    text: String,
    animationSpec: AnimationSpec<Float>,
    selected: Boolean,
    modifier: Modifier,
    chipType: ChipType,
    defaultTextColor: Color,
    selectedTextColor: Color,
    chipColor: Color,
    onSelected: () -> Unit
) {
    val animatedBackgroundColor by animateColorAsState(targetValue = if (selected) chipColor else Color.Transparent)
    val animatedTextColor by animateColorAsState(targetValue = if (selected) selectedTextColor else defaultTextColor)

    Box(modifier = modifier
        .background(animatedBackgroundColor)
        .selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center) {

        val animationProgress by animateFloatAsState(targetValue = if (selected) 1f else 0f, animationSpec)

        when(chipType) {
            is ChipType.Filter -> {
                ChipIconLayout(
                    icon = {
                        Icon(imageVector = chipType.icon, contentDescription = "Chip Icon",
                            tint = animatedTextColor)
                    },
                    text = {
                        Text(text = text, color = animatedTextColor)
                    }, animationProgress = animationProgress)
            }

            is ChipType.Assist -> {
                ChipIconLayout(
                    icon = {
                        Icon(imageVector = chipType.icon, contentDescription = "Chip Icon",
                            tint = animatedTextColor)
                    },
                    text = {
                        Text(text = text, color = animatedTextColor)
                    }, animationProgress = 1f)
            }

            is ChipType.Suggestion -> {
                ChipSuggestionLayout(text = {
                    Text(text = text, color = animatedTextColor)
                })
            }
        }
    }
}

@Composable
fun ChipIconLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(content = {
        Box(modifier = Modifier
            .layoutId("text")
            .padding(horizontal = 12.dp),
            content = text)

        val scale = lerp(0.6f, 1f, animationProgress)
        Box(modifier = Modifier
            .layoutId("icon")
            .padding(horizontal = 12.dp)
            .graphicsLayer {
                alpha = animationProgress
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0f, 0.5f)
            },
            content = icon)
    }) { measurables, constraints ->

        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)

        val width = textPlaceable.width + iconPlaceable.width

        val textY = (constraints.maxHeight - textPlaceable.height) / 2
        val iconY = (constraints.maxHeight - iconPlaceable.height) / 2

        val iconWidth = iconPlaceable.width * animationProgress
        val textX = (width - textPlaceable.width) / 1.5
        val iconX = (width - iconWidth - textPlaceable.width) / 2

        val height = constraints.maxHeight

        layout(width = width, height = height) {
            if (animationProgress != 0f) {
                iconPlaceable.placeRelative(iconX.toInt(), iconY)
                textPlaceable.placeRelative((textX * 1.2f).toInt(), textY)
            } else {
                textPlaceable.placeRelative(textX.toInt(), textY)
            }
        }
    }
}

@Composable
fun ChipSuggestionLayout(
    text: @Composable BoxScope.() -> Unit
) {
    Layout(content = {
        Box(modifier = Modifier
            .layoutId("text")
            .padding(horizontal = 12.dp),
            content = text)
    }) { measurables, constraints ->

        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        val width = textPlaceable.width
        val height = constraints.maxHeight

        val textY = (constraints.maxHeight - textPlaceable.height) / 2
        val textX = (width - textPlaceable.width) / 2

        layout(width = width, height = height) {
            textPlaceable.placeRelative(textX, textY)
        }
    }
}