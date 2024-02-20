package com.example.preonboardingchallengecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.preonboardingchallengecompose.ui.theme.PreOnboardingChallengeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //xml에서 사용했던 setContentView(R.layout.~~~)과 비슷하다.

        setContent {
            //프로젝트 이름+Theme가 이름이 된다.
            PreOnboardingChallengeComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //밑에서 정의한 Greeting 함수이다. 함수 내부에서는 Text()를 호출한다.
                    Greeting("Android")
                }
            }
        }
    }
}

//composable annotation이 붙은 함수는 "구성 가능한 함수"(composable function)이다.
//composable annotation을 쓰면 함수 내부에서 다른 composable 함수를 호출할 수 있다.
//원래 코틀린에서 함수의 이름은 "동사로 시작" "소문자로 시작"이 관습인데, Composable은 대문자 시작하며 명사이다.(이건 조사해봐야 할듯?)
//컴파일러에서도 소문자로 시작하면 경고(?)를 띄우는데, 이건 경고가 뜨지 않는다, Composable의 관습인가 보다.(마치 플러터를 하는것 같다. 선언형 UI)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //내부를 검색한 결과 Text()함수도 @Composable이 붙은 구성 가능한 함수이다.
    //@Composable 함수이기 때문에 내부에서 Text함수를 호출할 수 있다.
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//미리보기를 사용하고 싶다면 @Preview annotation을 사용하면 된다.
//맨 처음 빌드가 한번 필요하다. 그리고 split으로 보면 미리보기로 볼수 있다.
//코드를 바꾸면 xml layout처럼 실시간으로 미리보기 뷰가 바뀐다.
//미리보기를 위해 Moony로 텍스트를 바꿔 보았다.
//@Preview는 필수로 작성해야 레이아웃을 구성할수 있을 것 같다.
@Preview(showBackground = true, name = "Moony Text Preview")
@Composable
fun GreetingPreview() {
    PreOnboardingChallengeComposeTheme {
        Greeting("Moony")
    }
}