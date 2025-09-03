package com.likelion.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.likelion.ui.R

object SisoFontSizeTokens {
    val H1 = 32.sp
    val Title1 = 32.sp
    val Title2 = 24.sp
    val Title3 = 22.sp
    val Body1 = 22.sp
    val Body2 = 20.sp
    val Body3 = 20.sp
    val Body4 = 18.sp
    val Body5 = 16.sp
    val Label1 = 18.sp
    val Label2 = 16.sp
    val SubTitle1 = 18.sp
    val SubTitle2 = 16.sp
    val Button1 = 18.sp
    val Button2 = 16.sp
    val Caption = 15.sp
}

object SisoLineHeightTokens {
    val H1 = 41.6.sp
    val Title1 = 41.6.sp
    val Title2 = 31.2.sp
    val Title3 = 28.6.sp
    val Body1 = 33.sp
    val Body2 = 28.sp
    val Body3 = 30.sp
    val Body4 = 27.sp
    val Body5 = 24.sp
    val Label1 = 23.4.sp
    val Label2 = 20.8.sp
    val SubTitle1 = 23.4.sp
    val SubTitle2 = 20.8.sp
    val Button1 = 23.4.sp
    val Button2 = 20.8.sp
    val Caption = 19.5.sp
}

object SisoLetterSpaceTokens {
    val Spacing1 = (-0.01).em
    val Spacing2 = (-0.02).em
    val Spacing3 = (-0.03).em
    val Spacing4 = (-0.04).em
}

object SisoTypoTokens {
    private val jejuFontFamily = FontFamily(
        Font(R.font.jeju_myeongjo, FontWeight.W400)
    )
    private val pretendardFontFamily = FontFamily(
        Font(R.font.pretendard_regular, FontWeight.W400),
        Font(R.font.pretendard_bold, FontWeight.W700),
        Font(R.font.pretendard_semi_bold, FontWeight.W600),
        Font(R.font.pretendard_medium, FontWeight.W500),
    )

    val H1 = TextStyle(
        fontFamily = jejuFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.H1,
        lineHeight = SisoLineHeightTokens.H1,
        letterSpacing = SisoLetterSpaceTokens.Spacing2
    )

    val Title1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = SisoFontSizeTokens.Title1,
        lineHeight = SisoLineHeightTokens.Title1
    )
    val Title2 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = SisoFontSizeTokens.Title2,
        lineHeight = SisoLineHeightTokens.Title2
    )
    val Title3 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Title3,
        lineHeight = SisoLineHeightTokens.Title3
    )
    val Body1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Body1,
        lineHeight = SisoLineHeightTokens.Body1,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body2 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Body2,
        lineHeight = SisoLineHeightTokens.Body2,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body3 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body3,
        lineHeight = SisoLineHeightTokens.Body3,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body4 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body4,
        lineHeight = SisoLineHeightTokens.Body4,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Body5 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body5,
        lineHeight = SisoLineHeightTokens.Body5,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Label1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = SisoFontSizeTokens.Label1,
        lineHeight = SisoLineHeightTokens.Label1,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Label2 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Label2,
        lineHeight = SisoLineHeightTokens.Label2,
        letterSpacing = SisoLetterSpaceTokens.Spacing2
    )
    val SubTitle1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.SubTitle1,
        lineHeight = SisoLineHeightTokens.SubTitle1,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val SubTitle2 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.SubTitle2,
        lineHeight = SisoLineHeightTokens.SubTitle2,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Button1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Button1,
        lineHeight = SisoLineHeightTokens.Button1,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Button2 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Button2,
        lineHeight = SisoLineHeightTokens.Button2,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Caption1 = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Caption,
        lineHeight = SisoLineHeightTokens.Caption,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
}

@Composable
@Preview
fun TextPreview() {
    Column {
        // HeadLine1
        Text("HeadLine1", style = SisoTypoTokens.H1)
        // Title
        Text("Title 1", style = SisoTypoTokens.Title1)
        Text("Title 1", style = SisoTypoTokens.Title2)
        Text("Title 1", style = SisoTypoTokens.Title3)
        // Body
        Text("Body 1", style = SisoTypoTokens.Body1)
        Text("Body 2", style = SisoTypoTokens.Body2)
        Text("Body 3", style = SisoTypoTokens.Body3)
        Text("Body 4", style = SisoTypoTokens.Body4)
        Text("Body 5", style = SisoTypoTokens.Body5)
        // Label
        Text("Label 1", style = SisoTypoTokens.Label1)
        Text("Label 2", style = SisoTypoTokens.Label2)
        // SubTitle
        Text("SubTitle 1", style = SisoTypoTokens.SubTitle1)
        Text("SubTitle 2", style = SisoTypoTokens.SubTitle2)
        // Button
        Text("Button 1", style = SisoTypoTokens.Button1)
        Text("Button 2", style = SisoTypoTokens.Button2)
        // Caption
        Text("Caption 1", style = SisoTypoTokens.Caption1)

    }

}