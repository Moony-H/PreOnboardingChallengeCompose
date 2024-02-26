package com.example.preonboardingchallengecompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme

class Chapter11And12Activity : ComponentActivity() {
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

        Surface(modifier) {
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
        names: List<String> = List(100) { "$it" }
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

        var expanded by rememberSaveable { mutableStateOf(false) }

        //애니메이션 전용 state가 추가되었다.

        val extraPadding by animateDpAsState(
            if (expanded) 48.dp else 0.dp, label = ""
        )
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = "Hello, ")
                    Text(
                        text = name,
                        //data class의 copy인줄 알았는데, 아니었다. 만약, 내 앱의 테마의 글꼴을 가져다 쓰는데, 이 부분만 특별히 조금 다르게 해야 한다면,
                        //이렇게 copy를 써서 복사한 다음 fontWeight과 같이 세밀한 부분만 바꿔 조정하면 된다.
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }
                ElevatedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(if (expanded) "Show less" else "Show more")
                }

            }
        }
    }

    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = UI_MODE_NIGHT_YES,
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
