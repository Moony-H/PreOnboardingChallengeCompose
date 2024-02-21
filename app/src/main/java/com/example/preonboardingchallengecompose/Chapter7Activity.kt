package com.example.preonboardingchallengecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme

class Chapter7Activity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreOnboardingChallengeComposeTheme {
                // A surface container using the 'background' color from the theme
                //저번과 똑같이
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //밑에서 정의한 함수이다.
                    MyApp(Modifier.fillMaxSize())
                }
            }
        }
    }

    @Composable
    fun MyApp(
        modifier: Modifier = Modifier,
        names: List<String> = listOf("World", "Compose")
    ) {
        //column. 말그대로 column이다. 하위 composable을 열을 세워 놀 수 있다.
        //하위 composable 패딩을 4로 놓는다.
        Column(modifier = modifier.padding(vertical = 4.dp)) {
            //for문을 통해 동적으로 갯수를 설정할 수 있다.
            //여타 다른 함수들과 같이 호출할 수 있다. 편하고 재사용이 쉽다.
            for (name in names) {
                Greeting(name = name)
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        //state와 mutableState는 값을 가지고 있고, 그 값이 변경될 때 마다 recomposition(UI 재구성)을 하는 인터페이스이다.
        //하지만 val expanded = mutableStateOf() 만 하게 된다면, recomposition간에 상태를 유지할 수 없다.
        //계속 할당되기 때문.
        //마치 onResume 안에 변수를 선언하여, 언제 stop 되고 다시 resume 될지도 모르는데, 데이터를 유지하기를 바라는 느낌?
        //그렇기 때문에 remember를 사용하여 state의 리컴포지션을 방지한다.
        //근데 나같으면 viewModel에 stateflow 하나 두어서 관찰로 사용할 것 같다...
        //(이러면 생명 주기에 영향을 받지 않고 값을 유지할 수 있다.)
        val expanded = remember { mutableStateOf(false) }

        val extraPadding = if (expanded.value) 48.dp else 0.dp
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {

                Column(modifier = Modifier.weight(1f)
                    .padding(bottom = extraPadding)) {
                    Text(text = "Hello ")
                    Text(text = name)
                }
                ElevatedButton(
                    onClick = { expanded.value = !expanded.value },
                ) {
                    Text(if (expanded.value) "Show less" else "Show more")
                }
            }
        }
    }

    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun GreetingPreview() {
        PreOnboardingChallengeComposeTheme {
            MyApp()
        }
    }
}
