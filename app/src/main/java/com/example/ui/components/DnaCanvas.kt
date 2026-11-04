package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun DnaDoubleHelixCanvas(
    modifier: Modifier = Modifier,
    strandSequence: String = "ATGCGATACGCTTGA",
    speedMillis: Int = 4000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dna_spin")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(speedMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dna_phase"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val amplitude = width * 0.35f
        val centerX = width / 2f
        val stepCount = 14
        val stepY = height / (stepCount + 1)

        // Draw base pairs & strands
        for (i in 0..stepCount) {
            val y = stepY * (i + 0.5f)
            val angle = phase + (i * 0.45f)
            val xOffset = sin(angle) * amplitude
            val depth = (sin(angle + PI / 2).toFloat() + 1f) / 2f // 0 to 1 for perspective

            val leftX = centerX - xOffset
            val rightX = centerX + xOffset

            val baseChar1 = strandSequence.getOrNull(i % strandSequence.length) ?: 'A'
            val baseChar2 = when (baseChar1) {
                'A' -> 'T'
                'T' -> 'A'
                'C' -> 'G'
                'G' -> 'C'
                else -> 'U'
            }

            val color1 = when (baseChar1) {
                'A' -> BaseAdenine
                'T' -> BaseThymine
                'C' -> BaseCytosine
                'G' -> BaseGuanine
                else -> BaseUracil
            }

            val color2 = when (baseChar2) {
                'A' -> BaseAdenine
                'T' -> BaseThymine
                'C' -> BaseCytosine
                'G' -> BaseGuanine
                else -> BaseUracil
            }

            // Hydrogen bond rung
            val alpha = 0.3f + (depth * 0.7f)
            val strokeWidth = 2.dp.toPx() + (depth * 2.dp.toPx())

            // Left half
            drawLine(
                color = color1.copy(alpha = alpha),
                start = Offset(leftX, y),
                end = Offset(centerX, y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            // Right half
            drawLine(
                color = color2.copy(alpha = alpha),
                start = Offset(centerX, y),
                end = Offset(rightX, y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            // Sugar-phosphate backbone nodes
            val nodeRadius = 4.dp.toPx() + (depth * 3.dp.toPx())
            drawCircle(
                color = color1,
                radius = nodeRadius,
                center = Offset(leftX, y)
            )
            drawCircle(
                color = color2,
                radius = nodeRadius,
                center = Offset(rightX, y)
            )
        }
    }
}
