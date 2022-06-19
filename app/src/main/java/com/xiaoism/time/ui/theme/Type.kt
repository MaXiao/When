package com.xiaoism.time.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xiaoism.time.R

val ClashGrotesk =
    FontFamily(
        Font(R.font.clashgrotesk_regular),
        Font(R.font.clashgrotesk_bold, FontWeight.Bold),
        Font(R.font.clashgrotesk_semibold, FontWeight.SemiBold),
        Font(R.font.clashgrotesk_medium, FontWeight.Medium),
        Font(R.font.clashgrotesk_light, FontWeight.Light),
        Font(R.font.clashgrotesk_extralight, FontWeight.ExtraLight)
    )

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = ClashGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    ),
    h2 = TextStyle(
        fontFamily = ClashGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = ClashGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = ClashGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)