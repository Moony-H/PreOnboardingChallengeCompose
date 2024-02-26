package com.example.preonboardingchallengecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme
import androidx.compose.foundation.lazy.items


class Chapter9Activity : ComponentActivity() {
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

        //Hoisting. 자세한 설명은 Onboarding Screen에 주석을 달아놨다.
        var shouldShowOnboarding by remember {
            mutableStateOf(true)
        }

        //shouldShowOnboarding은 mutableState이다. 즉, 이 변수가 바뀌면 recomposition을 하게 된다.
        //따라서 아래의 if()문이 다시 실행될 수 있다.
        //shouldShowOnboarding이 false에서 true로 바뀌면 화면을 다시 그려 Greetings를 실행시킨다.
        //아래의 로그는 state가 바뀌었을 때 call 되지 않는다. 다시그려야 할 부분은 오로지 Surface의 child 이기 때문이다.
        Log.d("test", "My App Called")
        //아마 shouldShowOnboarding state를 참조하는 애들만 다시 그리는게 아닐까?
        Surface(modifier) {
            Log.d("test", "surface child called")
            if (shouldShowOnboarding) {
                OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            } else {
                Greetings()
            }
        }
    }

    //OnboardingScreen은 상태를 가지지않게 만들기 위해 상태 값을 Hoisting 했다.(stateful -> stateless)
    //Hoisting이란 들어올린다 라는 뜻으로, 상태를 더 상위로 올려 화면을 그리게 한다.
    //다른 예시로는, TextField가 있는데, TextField는 사용자가 입력한 Text라는 상태가 필연적으로 들어가는데,
    //이를 textChange callback 함수를 람다로 Hoisting하여 상위 Composable(혹은 viewModel?)에게 맡긴다.
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
        //예제에서는 먼저 List를 단순히 100개로 늘리는데, 이는 100개의 Greeting을 한번에 렌더링할 것이다.
        //ListView와 동일할 것 같다.
        //LayColumn은 화면에서 보이는 항목만 렌더링한다고 한다. 이는 성능상의 이점을 준다.
        // RecyclerView와 동일하다고 한다. 다만 뷰를 재활용하지는 않는다고 하는데... 이는 조사해 봐야 겠다.
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
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        var expanded by remember { mutableStateOf(false) }

        val extraPadding = if (expanded) 48.dp else 0.dp

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding)
                ) {
                    Text(text = "Hello, ")
                    Text(text = name)
                }
                ElevatedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(if (expanded) "Show less" else "Show more")
                }
            }
        }
    }

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
