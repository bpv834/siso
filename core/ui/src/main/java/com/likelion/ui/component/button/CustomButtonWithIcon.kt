package com.likelion.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Painter,
    iconTint: Color = Color.White,
    containerColor: Color = Color.Red,
    iconSize: Dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor// Example color, can be customized
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = borderWidth, color = borderColor)
    ) {
        Image(
            painter = icon,
            contentDescription = null, // decorative icon
            modifier = modifier.size(size = iconSize),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
        )
    }
}