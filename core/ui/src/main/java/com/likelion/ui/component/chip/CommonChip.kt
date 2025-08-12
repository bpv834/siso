package com.likelion.ui.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens


@Composable
    fun CommonChip(
        text: String,
        onClick: () -> Unit,
    ) {
        Surface(
            modifier = Modifier
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(999.dp),
            border = BorderStroke(1.dp, Color.Gray),
            color = SisoColorTokens.GrayScale20
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                color = SisoColorTokens.GrayScale90,
                style = SisoTypoTokens.SubTitle1
            )
        }
    }

@Preview
@Composable
fun CommonChipPreview(
){
    CommonChip("음악감상",{})
}
