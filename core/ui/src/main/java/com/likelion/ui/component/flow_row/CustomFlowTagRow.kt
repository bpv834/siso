package com.likelion.ui.component.flow_row

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTypoTokens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomFlowTagRow(
    modifier: Modifier = Modifier,
    tags: List<String>,
    maxLines: Int,
    overflow: FlowRowOverflow,
    spacing: Dp = 8.dp
) {
    FlowRow(
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    ) {
        tags.forEach { tag ->
            Text(
                text = "#$tag",
                style = SisoTypoTokens.Button1,
                color = SisoColorTokens.Gray90
            )
            Spacer(modifier = Modifier.size(spacing))
        }
    }
}