package com.likelion.navigation

sealed class NavigationRoute(val route: String) {
    object OnBoardingScreen : NavigationRoute("onboarding")
    object LoginScreen : NavigationRoute("login")
    object InputScreen : NavigationRoute("input")
    object HomeScreen : NavigationRoute("home")
    object ChatScreen : NavigationRoute("chat") {
        object AlarmScreen : NavigationRoute("chat/alarm")
    }
    object MyPageScreen : NavigationRoute("myPage")
    object CallForCallerScreen : NavigationRoute("caller/{userId}/{otherUserId}")
    object CallForReceiverScreen : NavigationRoute("receiver")
    /*
    * object 보여줄화면: NavigationRoute("보여줄화면")
    *
    * 만약 화면안에 화면이라면?
    * object 부모화면 : NavigationRoute("부모화면"){
    *   object 자식화면 : NavigationRoute("부모화면/자식화면")
    * }
    * */
}
