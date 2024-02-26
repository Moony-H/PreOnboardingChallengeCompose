package com.example.preonboardingchallengecompose.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//xml에서 theme(light dark)를 설정했던 것 처럼 여기서도 색을 지정하는것 같다.
//android 12가 나왔을 때, GoogleIO에서 보았던 내용인 팔레트가 여기에서도 사용되는 것 같다.
private val DarkColorScheme = darkColorScheme(
    surface = Blue,
    onSurface = Navy,
    primary = Navy,
    onPrimary = Chartreuse
)

private val LightColorScheme = lightColorScheme(
    //이렇게 하면 기본적인 뷰의 스타일은 모두 이걸로 바뀐다.
    //아래에서 이 라이트 컬러 스키마를 사용하기 때문이고,
    //activity에서 setContent{}할때도 이 테마를 사용하기 때문이다.(편리하다.)

    surface = Blue,
    onSurface = Color.White,
    primary = LightBlue,
    onPrimary = Navy


    //다른 색들을 오버라이드 가능하다.

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PreOnboardingChallengeComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    Log.e("test","theme")
    val colorScheme = when {
        //dynamicColor는 아래에서 설명한다.
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            Log.e("test", "dark theme is $darkTheme")
            //함수를 들어가 보면, 기본 컬러 스키마를 따로 사용하는 것을 볼 수 있다.
            //이는 동적 색상이라는 것인데, 적응형이란다...
            //이곳이 동적 색상이다.
            //https://m3.material.io/styles/color/dynamic/choosing-a-source

            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> {
            Log.e("test", "dark theme")
            DarkColorScheme
        }

        else -> {
            Log.e("test", "light theme")
            LightColorScheme
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    //위에서 말한것 처럼 두개가 다른 것을 볼 수 있다.
    Log.e("test","scheme primary same? :${colorScheme.primary== DarkColorScheme.primary}")
    Log.e("test","scheme: ${colorScheme.primary.value} dark: ${DarkColorScheme.primary.value}")

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}