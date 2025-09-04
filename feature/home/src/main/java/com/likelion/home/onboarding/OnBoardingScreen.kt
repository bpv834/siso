package com.likelion.home.onboarding

import android.view.View
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.likelion.ui.R
import com.likelion.ui.theme.SisoColorTokens
import com.likelion.ui.theme.SisoTheme
import com.likelion.ui.theme.SisoTypoTokens
import kotlinx.coroutines.launch


@Composable
fun OnBoardingRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    actionSnackbar: () -> Unit = {},
    navigateToHome: () -> Unit
) {
    OnBoardingScreen(
        navigateToHome = navigateToHome
    )
}

@Composable
fun OnBoardingScreen(
    navigateToHome: () -> Unit
) {
    val viewModel = hiltViewModel<OnBoardingViewModel>()
    Pager(
        pages = listOf(
            { onNext -> OnboardingPage1(onNext, navigateToHome, viewModel) },
            { onNext -> OnboardingPage2(onNext, navigateToHome, viewModel) },
            { onNext -> OnboardingPage3(navigateToHome, viewModel) }
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(
    pages: List<@Composable (onNext: () -> Unit) -> Unit>,
    transitionMillis: Int = 400
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    val snapSpec = tween<Float>(
        durationMillis = transitionMillis,
        easing = FastOutSlowInEasing
    )
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        snapAnimationSpec = snapSpec
    )

    val onNext: () -> Unit = {
        scope.launch {
            val next = (pagerState.currentPage + 1).coerceAtMost(pages.size - 1)
            pagerState.animateScrollToPage(next)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            flingBehavior = fling
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                pages[page].invoke(onNext)
            }
        }
    }
}

@Composable
fun OnboardingPage1(
    onNext: () -> Unit = {},
    navigateToHome: () -> Unit,
    viewModel: OnBoardingViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(116.dp))
        Box {
            Image(
                painter = painterResource(R.drawable.img_guide01), contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "계속하기",
                            style = SisoTypoTokens.Button1,
                            color = SisoColorTokens.Gray90
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                    Text(
                        text = "건너뛰기",
                        style = SisoTypoTokens.Button2,
                        color = SisoColorTokens.Gray50,
                        modifier = Modifier.clickable {
                            viewModel.handleEvent(event = OnBoardingEvent.ChangeOnBoardingSKip(true))
                            navigateToHome()
                        }
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                }
            }
        }

    }
}

@Composable
fun OnboardingPage2(
    onNext: () -> Unit = {},
    navigateToHome: () -> Unit,
    viewModel: OnBoardingViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(116.dp))
        Box {
            Image(
                painter = painterResource(R.drawable.img_guide02), contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "계속하기",
                            style = SisoTypoTokens.Button1,
                            color = SisoColorTokens.Gray90
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                    Text(
                        text = "건너뛰기",
                        style = SisoTypoTokens.Button2,
                        color = SisoColorTokens.Gray50,
                        modifier = Modifier.clickable {
                            viewModel.handleEvent(event = OnBoardingEvent.ChangeOnBoardingSKip(true))
                            navigateToHome()
                        }
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                }
            }
        }

    }
}

@Composable
fun OnboardingPage3(
    navigateToHome: () -> Unit,
    viewModel: OnBoardingViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(116.dp))
        Box {
            Image(
                painter = painterResource(R.drawable.img_guide03), contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = navigateToHome,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "시작하기",
                            style = SisoTypoTokens.Button1,
                            color = SisoColorTokens.Gray90
                        )
                    }
                    Spacer(modifier = Modifier.size(14.dp))
                    Text(
                        text = "다시보지않기",
                        style = SisoTypoTokens.Button2,
                        color = SisoColorTokens.Gray50,
                        modifier = Modifier.clickable {
                            viewModel.handleEvent(event = OnBoardingEvent.ChangeOnBoardingSKip(true))
                            navigateToHome()
                        }
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                }
            }
        }

    }
}

@Composable
@Preview
fun OnboardingPage1Preview() {
    SisoTheme {
        OnboardingPage1(onNext = {}, navigateToHome = {}, hiltViewModel())
    }
}