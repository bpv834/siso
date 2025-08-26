<<<<<<<< HEAD:feature/home/src/main/java/com/likelion/home/find/FindScreen.kt
package com.likelion.home.find
========
package com.likelion.home.chat
>>>>>>>> origin/feature/home/my_page/jaemin:feature/home/src/main/java/com/likelion/home/chat/ChatScreen.kt

import android.view.View
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

@Composable
<<<<<<<< HEAD:feature/home/src/main/java/com/likelion/home/find/FindScreen.kt
fun FindRoute(
========
fun ChatRoute(
>>>>>>>> origin/feature/home/my_page/jaemin:feature/home/src/main/java/com/likelion/home/chat/ChatScreen.kt
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {}
) {
<<<<<<<< HEAD:feature/home/src/main/java/com/likelion/home/find/FindScreen.kt
    FindScreen()
}

@Composable
fun FindScreen() {
    Text(text = "Find")
========
    ChatScreen()
}

@Composable
fun ChatScreen() {
    Text(text = "Chat")
>>>>>>>> origin/feature/home/my_page/jaemin:feature/home/src/main/java/com/likelion/home/chat/ChatScreen.kt
}