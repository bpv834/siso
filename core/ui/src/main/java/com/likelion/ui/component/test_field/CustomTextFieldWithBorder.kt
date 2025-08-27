package com.likelion.ui.component.test_field

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomTextFieldWithBorder() {
    var customReasonText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = customReasonText,
        onValueChange = { customReasonText = it },
        modifier = Modifier
            .fillMaxWidth().height(73.dp)
            .border(
                width = 1.dp,
                // You can change the color here if needed
                color = androidx.compose.ui.graphics.Color.LightGray,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    CustomTextFieldWithBorder()
}