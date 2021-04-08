package com.aakarshrestha.sampleappswipetorefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.aakarshrestha.sampleappswipetorefresh.data.MusicData
import com.aakarshrestha.sampleappswipetorefresh.models.Music
import com.aakarshrestha.sampleappswipetorefresh.screens.HeaderSection
import com.aakarshrestha.sampleappswipetorefresh.screens.MainScreen
import com.aakarshrestha.sampleappswipetorefresh.screens.Screen
import com.aakarshrestha.sampleappswipetorefresh.ui.theme.SampleAppSwipeToRefreshTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            SampleAppSwipeToRefreshTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp(MusicData.musicList)
                }
            }
        }
    }

    @Composable
    fun MyApp(musicList: List<Music>, screen: Screen? = Screen.MainScreen) {
        when(screen) {
               is Screen.MainScreen -> {
                   MainScreen(
                       musicList = musicList,
                       heading = "Beats that",
                       title = "Touch your Heart",
                       subtitle = "Best songs of the year"
                   )
               }
        }

    }
}