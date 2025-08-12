package com.likelion.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.likelion.ui.R

object SisoFontSizeTokens {
    val H1 = 32.sp
    val Title1 = 32.sp
    val Title2 = 24.sp
    val Title3 = 22.sp
    val Body1 = 20.sp
    val Body2 = 20.sp
    val Body3 = 18.sp
    val Body4 = 16.sp
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
    val Body1 = 28.sp
    val Body2 = 30.sp
    val Body3 = 27.sp
    val Body4 = 24.sp
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
    private val sisoFontFamily = FontFamily(
        Font(R.font.jeju_myeongjo, FontWeight.W400),
        Font(R.font.pretendard_bold, FontWeight.W700),
        Font(R.font.pretendard_semi_bold, FontWeight.W600),
        Font(R.font.pretendard_medium, FontWeight.W500),
        Font(R.font.pretendard_regular, FontWeight.W400),
    )

    val H1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.H1,
        lineHeight = SisoLineHeightTokens.H1,
        letterSpacing = SisoLetterSpaceTokens.Spacing2
    )

    val Title1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = SisoFontSizeTokens.Title1,
        lineHeight = SisoLineHeightTokens.Title1
    )
    val Title2 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = SisoFontSizeTokens.Title2,
        lineHeight = SisoLineHeightTokens.Title2
    )
    val Title3 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Title3,
        lineHeight = SisoLineHeightTokens.Title3
    )
    val Body1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Body1,
        lineHeight = SisoLineHeightTokens.Body1,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body2 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body2,
        lineHeight = SisoLineHeightTokens.Body2,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body3 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body3,
        lineHeight = SisoLineHeightTokens.Body3,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Body4 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Body4,
        lineHeight = SisoLineHeightTokens.Body4,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Label1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = SisoFontSizeTokens.Label1,
        lineHeight = SisoLineHeightTokens.Label1,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
    val Label2 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Label2,
        lineHeight = SisoLineHeightTokens.Label2,
        letterSpacing = SisoLetterSpaceTokens.Spacing2
    )
    val SubTitle1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.SubTitle1,
        lineHeight = SisoLineHeightTokens.SubTitle1,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val SubTitle2 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.SubTitle2,
        lineHeight = SisoLineHeightTokens.SubTitle2,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Button1 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Button1,
        lineHeight = SisoLineHeightTokens.Button1,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Button2 = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = SisoFontSizeTokens.Button2,
        lineHeight = SisoLineHeightTokens.Button2,
        letterSpacing = SisoLetterSpaceTokens.Spacing3
    )
    val Caption = TextStyle(
        fontFamily = sisoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = SisoFontSizeTokens.Caption,
        lineHeight = SisoLineHeightTokens.Caption,
        letterSpacing = SisoLetterSpaceTokens.Spacing1
    )
}