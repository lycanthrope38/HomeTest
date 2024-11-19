package com.hometest.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hometest.R

private val PrimaryColor = Color(0xff031F2B)
private val SecondaryColor = Color(0xff031F2B)
private val ErrorColor = Color(0xffCF4018)

val montserratMedium = FontFamily(Font(R.font.montserrat_medium))
val montserratRegular = FontFamily(Font(R.font.montserrat_regular))
val montserratBold = FontFamily(Font(R.font.montserrat_bold))

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = SecondaryColor,
    onSecondary = Color.White,
    error = ErrorColor,
    background = Color.White,
    surfaceVariant = Color.White,
    surface = Color.White,
)

private val defaultTypography = Typography()
private val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = montserratRegular),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = montserratRegular),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = montserratRegular),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = montserratRegular),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = montserratRegular),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = montserratRegular),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = montserratRegular),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = montserratRegular),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = montserratRegular),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = montserratRegular),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = montserratRegular),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = montserratRegular),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = montserratBold),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = montserratRegular),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = montserratRegular)
)

@Immutable
data class HomeTestTypography(
    val heading: TextStyle,
    val title: TextStyle,
    val titleLarge: TextStyle,
    val titleSmall: TextStyle,
    val body: TextStyle,
    val bold: TextStyle,
    val caption: TextStyle,
    val captionTitle: TextStyle,
    val bodySmall: TextStyle,
    val textLink: TextStyle,
)

@Immutable
data class HomeTestShapes(
    val medium: Shape
)

val LocalHomeTestShapes = staticCompositionLocalOf {
    HomeTestShapes(
        medium = RoundedCornerShape(12.dp)
    )
}


val LocalHomeTestTypography = staticCompositionLocalOf {
    HomeTestTypography(
        body = TextStyle.Default,
        title = TextStyle.Default,
        bold = TextStyle.Default,
        heading = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodySmall = TextStyle.Default,
        caption = TextStyle.Default,
        captionTitle = TextStyle.Default,
        textLink = TextStyle.Default
    )
}

object HomeTestTheme {
    val typography: HomeTestTypography
        @Composable
        get() = LocalHomeTestTypography.current

    val shape: HomeTestShapes
        @Composable
        get() = LocalHomeTestShapes.current
}


@Composable
fun HomeTestTheme(
    statusBarColor: Color = Color.White,
    darkIcon: Boolean = true,
    content: @Composable () -> Unit,
) {
    val homeTestTypography = HomeTestTypography(
        body = TextStyle(fontSize = 16.sp, fontFamily = montserratRegular, color = PrimaryColor),
        title = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratBold,
            color = PrimaryColor
        ),
        bold = TextStyle(fontWeight = FontWeight.Bold, fontFamily = montserratBold, color = PrimaryColor),
        heading = TextStyle(fontSize = 24.sp, fontFamily = montserratMedium, color = PrimaryColor),
        titleLarge = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratBold,
            color = PrimaryColor
        ),
        titleSmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratBold,
            color = PrimaryColor
        ),
        bodySmall = TextStyle(fontSize = 12.sp, fontFamily = montserratRegular, color = PrimaryColor),
        caption = TextStyle(
            fontSize = 12.sp,
            fontFamily = montserratRegular,
            color = PrimaryColor,
            fontWeight = FontWeight.Medium
        ),
        captionTitle = TextStyle(
            fontSize = 12.sp,
            fontFamily = montserratBold,
            color = PrimaryColor,
            fontWeight = FontWeight.Bold
        ),
        textLink = TextStyle(
            fontSize = 16.sp,
            fontFamily = montserratBold,
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        ),
    )

    val homeTestShapes = HomeTestShapes(
        medium = RoundedCornerShape(12.dp)
    )
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = statusBarColor, darkIcons = darkIcon)
    CompositionLocalProvider(
        LocalHomeTestTypography provides homeTestTypography,
        LocalHomeTestShapes provides homeTestShapes
    ) {
        MaterialTheme(
            colorScheme = LightColors,
            typography = typography,
            content = content,
        )
    }
}