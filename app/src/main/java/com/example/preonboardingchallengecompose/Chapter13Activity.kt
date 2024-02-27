package com.example.preonboardingchallengecompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme

class Chapter13Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreOnboardingChallengeComposeTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }


    @Composable
    fun MyApp(modifier: Modifier = Modifier) {

        var shouldShowOnboarding by rememberSaveable {
            mutableStateOf(true)
        }

        Surface(modifier, color = MaterialTheme.colorScheme.background) {
            if (shouldShowOnboarding) {
                OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            } else {
                Greetings()
            }
        }
    }

    @Composable
    fun OnboardingScreen(
        onContinueClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }

    @Composable
    private fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) { "$it" }
    ) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }

    }

    @Preview(showBackground = true, widthDp = 320, heightDp = 320)
    @Composable
    fun OnboardingPreview() {
        PreOnboardingChallengeComposeTheme {
            OnboardingScreen(onContinueClicked = {})
        }
    }

    @Composable
    private fun Greeting(name: String, modifier: Modifier = Modifier) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name)
        }
    }

    //chapter 13에 새로 추가된 함수.
    //Greeting에 있던 콘텐츠를 따로 뺐다.
    @Composable
    private fun CardContent(name: String) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    //spring 애니메이션을 추가한다.
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        //긴 텍스트 출력
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            //row의 두번째 composable.
            //show more, show less button을 IconButton으로 대체하였다.
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    //벡터이미지이다.
                    //현재는 화살표 이미지를 넣었다.
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }

    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        name = "GreetingPreviewDark"
    )
    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun GreetingPreview() {
        PreOnboardingChallengeComposeTheme {
            Greetings()
        }
    }

    @Preview
    @Composable
    fun MyAppPreview() {
        PreOnboardingChallengeComposeTheme {
            MyApp(Modifier.fillMaxSize())
        }
    }

}