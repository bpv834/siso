package com.likelion.navigation

sealed class NavigationRoute(val route: String) {
    object OnBoardingScreen : NavigationRoute("onboarding")

    object LoginScreen : NavigationRoute("login")
    object InputScreen : NavigationRoute("input")
    object HomeScreen : NavigationRoute("home")
    object ChatScreen : NavigationRoute("chat") {
        object AlarmScreen : NavigationRoute("chat/alarm")
        object ChatRoomScreen: NavigationRoute("chat/chatroom")
        object PartnerProfileScreen : NavigationRoute("chat/partner_screen")
    }

    object MyPageScreen : NavigationRoute("myPage"){
        object MainEditScreen : NavigationRoute("myPage/main_edit") {
            object PotoEditScreen : NavigationRoute("myPage/main_edit/poto_edit")
            object VoiceEditScreen : NavigationRoute("myPage/main_edit/voice_edit")
            object LocationEditScreen : NavigationRoute("myPage/main_edit/location_edit")
            object ReligionEditScreen : NavigationRoute("myPage/main_edit/religion_edit")
            object SmokingEditScreen : NavigationRoute("myPage/main_edit/smoking_edit")
            object AlcoholEditScreen : NavigationRoute("myPage/main_edit/alcohol_edit")
            object MBTIEditScreen : NavigationRoute("myPage/main_edit/MBTI_edit")
            object InterestEditScreen : NavigationRoute("myPage/main_edit/Interest_edit")
            object MatchingEditScreen : NavigationRoute("myPage/main_edit/matching_edit")
        }

        object SettingScreen : NavigationRoute("myPage/setting") {
            object CallEditScreen : NavigationRoute("myPage/setting/call_edit")
        }
    }
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
