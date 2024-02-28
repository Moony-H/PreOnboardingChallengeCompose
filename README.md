# PreOnboardingChallengeCompose

## 개요

Wandted에서 진행하는 Android PreOnboarding을 계기로 미뤄왔던 Jetpack Compose에 대하여 공부했다.

이 Repository는 [Jetpack Compose 기초](https://developer.android.com/codelabs/jetpack-compose-basics?hl=ko#0)에서 학습한 내용을 담고 있다.

<br/>


## 기존 뷰의 문제점

<br/>

나는 위의 codelab을 학습하기 전에 [compose 이해](https://developer.android.com/jetpack/compose/mental-model?hl=ko#recomposition)를 보고 Jetpack Compose가 왜 나왔는지, 어떻게 사용할지를 먼저 살펴보았다.

<br/>

위의 글에서 설명하는 기존 view의 단점은 아래처럼 설명한다.

<br/>

>지금까지 Android 뷰 계층 구조는 UI 위젯의 트리로 표시할 수 있었습니다. 사용자 상호작용 등의 이유로 인해 앱의 상태가 변경되면, 현재 데이터를 표시하기 위해 UI 계층 구조를 업데이트해야 합니다. UI를 업데이트하는 가장 일반적인 방법은 findViewById()와 같은 함수를 사용하여 트리를 탐색하고 button.setText(String), container.addChild(View) 또는 img.setImageBitmap(Bitmap)과 같은 메서드를 호출하여 노드를 변경하는 것입니다. 이러한 메서드는 위젯의 내부 상태를 변경합니다.
>
>뷰를 수동으로 조작하면 오류가 발생할 가능성이 커집니다. 데이터를 여러 위치에서 렌더링한다면 데이터를 표시하는 뷰 중 하나를 업데이트하는 것을 잊기 쉽습니다. 또한 두 업데이트가 예기치 않은 방식으로 충돌할 경우 잘못된 상태를 야기하기도 쉽습니다. 예를 들어 업데이트가 UI에서 방금 삭제된 노드의 값을 설정하려고 할 수 있습니다. 일반적으로 업데이트가 필요한 뷰의 수가 많을수록 소프트웨어 유지관리 복잡성이 증가합니다.

<br/>

결국 문제는 **"유지관리가 복잡해진다."** 와 **"위젯 내부 상태를 변경할 때 충돌할 경우 버그가 생긴다."** 정도로 생각해 볼 수 있다.

그래서 이런 단점을 극복하기 위해 선언형 UI인 Jetpack Compose가 나왔다.

요즘은 업계 전반에서 선언형 UI를 쓴다고 한다.

>지난 몇 년에 걸쳐 업계 전반에서 선언형 UI 모델로 전환하기 시작했으며, 이에 따라 사용자 인터페이스 빌드 및 업데이트와 관련된 엔지니어링이 크게 간소화되었습니다. 이 기법은 처음부터 화면 전체를 개념적으로 재생성한 후 필요한 변경사항만 적용하는 방식으로 작동합니다. 이러한 접근 방식은 스테이트풀(Stateful) 뷰 계층 구조를 수동으로 업데이트할 때의 복잡성을 방지할 수 있습니다. Compose는 선언형 UI 프레임워크입니다.


마침 나는 운 좋게 개인 프로젝트로 Flutter를 해 봐서 선언형 UI를 짜는 방식에 익숙해진 상태였다.

그리고 Stateless Widget, Stateful Widget의 방식을 안드로이드에선 어떻게 Compose로 할지 기대도 되었다.


## Chapter 1 ~ 5

<br/>

먼저 프로젝트를 생성한다.

compose 프로젝트 템플릿이 이미 있으니 이 것을 사용하면 된다.

![project](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/4c767baf-5f99-4e29-ba01-50f3d48febb7)

그럼 아래와 같은 코드로 만들어지는데,

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
```

내가 자주 사용하던 AppCompatActivity랑 다르다.

그래서 안으로 들어가봤다.

![componentActivity](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/579fc44d-50bd-422b-965f-9deec15bb110)

Java로 되어있고 굉장히 복잡하고 많은 interface를 상속받는다.

이것을 보자마자 내가 알던 AppCompatActivity보다 상위의 객체일것 같다는 감이 왔다. 그리고 알아보니

![componentActivityClass](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/e9260dc4-7715-4866-94d3-2adddef53006)

Activity 다음이 바로 ComponentActivity였다.

내가 아는 AppCompatActivity는 아래와 같다.

![AppCompatActivityClass](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/706c97e9-9b56-4918-aa61-0ff4e7fd2134)


상속 관계를 나열한다면

**Activity -> ComponentActivity -> FragmentActivity -> AppCompatActivity**

위의 순으로 상속을 받는다.

내가 들은 바로, Jetpack Compose를 사용하면 Fragment를 사용하지 않아도 된다고 들었는데, 그 이유로 Activity를 가볍게 하기 위해 ComponentActivity를 사용하지 않았나 싶다.

참고로 ComponentActivity에서는 바로 Jetpack Compose UI를 사용할 수 있지만, AppCompatActivity는 따로 설정을 해야 Compose UI를 사용할 수 있다고 한다.


<br/>

<br/>

여튼 내가 아는 방식이랑 뭐가 다른지 유심히 살펴보았다.

처음 보인 것은 setContent{} 였다.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //오잉..? 람다가?
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
```

내가 평소에 알던 setContentView(R.layout.어쩌구저쩌구)는 없고, 람다를 받는 setContent라는 함수로 대체되었다.

그리고 설명을 읽어보니, chapter3에는 이러한 설명이 있었다.

>구성 가능한 함수는 @Composable이라는 주석이 달린 일반 함수입니다. 이렇게 하면 함수가 내부에서 다른 @Composable 함수를 호출할 수 있습니다. Greeting 함수를 @Composable로 어떻게 표시하는지 확인할 수 있습니다. 이 함수는 지정된 입력(String)을 표시하는 UI 계층 구조를 생성합니다. Text는 라이브러리에서 제공하는 구성 가능한 함수입니다.

요약하면 **compose는 compose끼리만 부를 수 있다.** 였다. 그렇다면 setContent{} 이것의 람다도 분명 Composable일 것 이다.


![setContent](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/417eb7e6-5fa0-4d9c-aed2-7aefb2edc494)

(역시 그렇다.)

그 이후 

```kotlin
setContent {
    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Android")
        }
    }
}
```

MyApplicationTheme와 Surface, Greeting 순으로 들어간다.

MyApplicationTheme는 내가 지정한 프로젝트 이름을 따라서 {프로젝트이름}Theme로 설정된다.

MyApplicationTheme Surface, Greeting 모두 @Composable이 들어간 함수이다.


<br/>

그리고 또 @Preview를 배웠다.

이는 미리보기 같은 것인데, 마치 커스텀 뷰를 짤 때 Code Split Design 탭이 옆에 뜨는데,

![splittab](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/54ea423f-48d6-46ad-86ef-a13bf82e26f5)

@Preview를 단 함수를 작성하고, 그 안에 보고싶은 Compose UI를 넣으면 제목과 함께 미리 화면의 구성을 볼 수 있다.

```kotlin
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    PreOnboardingChallengeComposeTheme {
        MyApp()
    }
}
```

![edit](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/4fa9a22c-4c25-44a2-938f-ea91318a9323)

주의할 점은 반드시 한번 빌드를 해야 미리보기를 볼 수 있다.

이 것은 CustomView를 짤 때도 마찬가지였다.

<br/>

### 새롭게 안 것

1. @Composable이 붙은 함수끼리만 내부에서 Composable을 호출할 수 있다.

2. @Preview를 사용하면 split 혹은 design으로 미리 구성한 UI를 볼 수 있다.

3. 특이한 annotation을 발견했는데, Surface에 붙어있는 @NonRestartableComposable 이다.

![Surface](https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/c2619870-be75-41ab-b4cc-4855bfa60b55)


@Composable과 함께 @NonRestartableComposable이 같이 붙어있는데,

검색 결과 다시 구성(Recomposition)을 방지하는 annotation이라는 것을 알았다.

화면이 바뀔 때, 다시 구성하지 않음으로서 화면을 그릴 때 성능 향상을 기대할 수 있다.

<br/>

<br/>

## Chapter 6 Row Column

Chapter 6에서는 Row와 Column에 대해 배웠다.

Row와 Column, 말 그대로 행과 열이다.

Composable을 나열할 수 있는 Composable 함수이다.

Row와 Column의 다른 점은 가로로 나열과 세로로 나열이다.

마치 LinearLayout의 orientation을 설정하여 자식 View들을 나열하는 것과 같다.

<img width="734" alt="rowcolumn" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/5ca5b59c-4d87-4e62-b9a3-294401511a68">




LinearLayout과 마찬가지로, 자식 Composable에게 weight를 설정할 수 있다.

1,1을 설정했을 때는 1:1 비율로 설정되며, 2,1을 설정했을 때는 2:1 비율로 설정된다.


1 대 1일때

<img width="587" alt="1by1code" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/7f0318f5-a508-474e-b256-bccf445eb6bb">

<img width="253" alt="1by1preview" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/fbc67f94-0530-4950-bd16-885e36c06b8b">

2 대 1일때

<img width="588" alt="2by1code" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/e61b8b70-efcd-4d46-b8a5-925901b7b971">

<img width="247" alt="2by1preview" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/67a07bc8-9e90-455e-b600-b69f52be6db9">


<br/>

<br/>

또한 ListView == Row or Column이 되었다.

ListView 또한 아이템 view를 xml로 작성한 후에, ListAdapter로 inflate를 해서 설정해야 했다.

매우 번거로운 일이었는데, 이를 Row, Column으로 대체하면서 굉장히 편해 졌다.

<img width="664" alt="columnIsListView" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/1879f324-5557-4b32-8ddc-ee60dec7f94f">

(이 부분에서 감동했다...)

또한 위와 같이 Composable도 함수다. 즉, 반복문을 통해서 여러번 호출할 수도 있게 되었다.

이전까지는 "Compose UI가 강력한 도구인가?"에 대해 의구심이 들었지만 for문과 함께 동적으로 UI를 작성할 수 있다는걸 눈으로 보니 재사용성이 강력한 도구임을 느꼈다.

하지만 아직까지도 의구심이 드는데, 뷰를 재사용하는 RecyclerView와 달리 이번 Column은 ListView의 단점과 마찬가지로 한번에 모든 목록을 그린다는 단점이 있었다.

이는 다음 chapter에서 LazyColumn을 사용하면서 더욱 강력하고 효과적이게 사용하게 된다.

## Chapter 7 state

이번 챕터에서는 상태에 대해서 배웠다.

뷰에서는 상태를 가져야 할 때가 있다.

토글 버튼의 경우 체크된 상태와 체크가 해제된 상태가 있듯이 뷰 안에 상태를 가지게 된다.

이런 경우 Compose UI에서는 MutableState라는 객체를 제공한다.

```kotlin
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        //state와 mutableState는 값을 가지고 있고, 그 값이 변경될 때 마다 recomposition(UI 재구성)을 하는 인터페이스이다.
        //하지만 val expanded = mutableStateOf() 만 하게 된다면, recomposition간에 상태를 유지할 수 없다.
        //계속 할당되기 때문.
        //마치 onResume 안에 변수를 선언하여, 언제 stop 되고 다시 resume 될지도 모르는데, 데이터를 유지하기를 바라는 느낌?
        //그렇기 때문에 remember를 사용하여 state의 리컴포지션을 방지한다.
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
```

주석의 설명대로 compose 안에서 expanded = mutableStateOf(false)를 사용한다면

Gretting함수가 호출될 때 마다 계속 새로 할당하개 될 것이다.

그래서 값을 유지할 수 있게 람다로 객체를 할당하는 remember 함수를 사용한다.

<img width="723" alt="rememberFunc" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/8f47c3f0-c038-4c23-92e8-db7d584b8b93">

이제 remember 함수가 적당한 때에 calculation 람다를 실행하여 MutableState를 초기화 해 줄 것이다.

<br/>

<img width="554" alt="mutableState" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/2eca19f7-39cb-4598-b327-d5c6ad219daa">

이제 mutableState를 바꾸어 보자. 그럼 mutableState를 참조하는 composable 함수들이 재호출되어 뷰를 다시 그리게 된다.

<br/>

<br/>

preview의 새로운 기능을 설명해줬다.

바로 사용자 입력을 받을수 있다...!

<img width="499" alt="inter" src="https://github.com/Moony-H/PreOnboardingChallengeCompose/assets/53536205/e133adfb-93d4-4c99-bdd0-8b8f75894c3c">


위와 같이 interactive mode를 켜면 앱을 실행하지 않고도 사용자 입력을 테스트 할 수 있다.

(이제 뷰를 작성할 때는 Compose UI를 애용해야 겠다고 생각했다.)

<br/>

<br/>


### 알아볼 것들

정말 신기하게도 mutableState를 참조하는 이들만 recomposition이 이뤄진다.

mutableState를 참조하는 애들이 상태가 변경되면 다시 호출되는데,

나는 처음에 mutableState를 초기화한 위치의 함수 전체를 다시 호출하는 줄 알았다.

그러나 정말 mutableState를 사용하는 곳만 다시 호출했다.

이 방식을 좀더 조사해봐야갰다.








