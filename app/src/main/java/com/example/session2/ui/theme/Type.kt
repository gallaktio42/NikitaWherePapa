package com.example.session2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.session2.R

val RobotoBlack = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black)
)

val RobotoBold = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold)
)

val RobotoMedium = FontFamily(
    Font(R.font.roboto_medium, FontWeight.Medium)
)

val RobotoRegular = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = RobotoBlack,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,

        ),
    bodyMedium = TextStyle(
        fontFamily = RobotoRegular,
        fontWeight = FontWeight.W500,
        color = GrayForText,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoRegular,
        fontWeight = FontWeight.W500,
        color = GrayForText,
        fontSize = 12.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = RobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    labelSmall  = TextStyle(
        fontFamily = RobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = RobotoRegular,
        fontWeight = FontWeight.Medium,
        color = BlackForText,
        fontSize = 14.sp,
    )
)