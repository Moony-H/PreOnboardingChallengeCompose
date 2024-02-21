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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme

class Chapter6Activity:ComponentActivity() {
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
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            //row, 말그대로 행이다. 가로로 하위 composable들을 배치할 수 있다.
            Row(modifier = Modifier.padding(24.dp)) {
                //열이다. 텍스트를 세로로 두개 넣는다.
                //가중치를 설정할 수 있다. 이는 부모의 layout에 얼만큼의 가중치를 넣을 것인지이다.
                //현재는 가중치가 없는 녀석(ElevatedButton)을 끝으로 밀어내는 용도로 사용하였다.
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Hello ")
                    Text(text = name)
                }
                ElevatedButton(
                    onClick = {  },
                    //modifier=Modifier.weight(1f)
                ) {
                    //composable이 들어갈 수 있는 람다이다.
                    //이번 챕터에서는 텍스트를 넣었다.
                    Text("Show more")
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
