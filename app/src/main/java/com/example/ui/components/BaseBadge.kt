package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun NucleotideBadge(
    base: Char,
    size: Dp = 36.dp,
    showSubtext: String? = null,
    highlight: Boolean = false,
    modifier: Modifier = Modifier
) {
    val char = base.uppercaseChar()
    val baseColor = when (char) {
        'A' -> BaseAdenine
        'T' -> BaseThymine
        'C' -> BaseCytosine
        'G' -> BaseGuanine
        'U' -> BaseUracil
        else -> Color.Gray
    }

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(baseColor.copy(alpha = if (highlight) 0.35f else 0.18f))
            .border(
                width = if (highlight) 2.dp else 1.dp,
                color = if (highlight) NeonCyan else baseColor.copy(alpha = 0.8f),
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = char.toString(),
                color = if (highlight) NeonCyan else baseColor,
                fontWeight = FontWeight.Black,
                fontSize = (size.value * 0.45f).sp,
                fontFamily = FontFamily.Monospace
            )
            if (showSubtext != null) {
                Text(
                    text = showSubtext,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
