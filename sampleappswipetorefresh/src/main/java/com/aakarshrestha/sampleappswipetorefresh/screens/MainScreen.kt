package com.aakarshrestha.sampleappswipetorefresh.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aakarshrestha.sampleappswipetorefresh.R
import com.aakarshrestha.sampleappswipetorefresh.models.Music
import com.aakarshrestha.sampleappswipetorefresh.network.fakeNetworkCall
import com.aakarshrestha.swipetorefresh.ComposePullToRefresh

@Composable
fun MainScreen(
    musicList: List<Music>,
    heading: String,
    title: String,
    subtitle: String
) {

    val refreshing = rememberSaveable { mutableStateOf(false) }

    ComposePullToRefresh(
        isRefreshing = refreshing.value,
        onRefresh = {

            refreshing.value = true

            fakeNetworkCall {
                refreshing.value = false
            }
        },
        indicatorColor = MaterialTheme.colors.onPrimary,
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TopAppBarSection()
                    BodySection(
                        musicList = musicList,
                        heading = heading,
                        title = title,
                        subtitle = subtitle
                    )
                }
            }
        }
    )


    /*
    val isRefreshing = rememberSaveable { mutableStateOf(false) }

    SwipeToRefresh(
        isRefreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true

            //make a fake network call and when done, update the isRefreshing to false
            fakeNetworkCall {
                isRefreshing.value = false
            }
        },
        refreshSectionBackgroundColor = MaterialTheme.colors.primary

    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TopAppBarSection()
                BodySection(
                    musicList = musicList,
                    heading = heading,
                    title = title,
                    subtitle = subtitle
                )
            }
        }
    }
    */
}

@Composable
fun TopAppBarSection() {
    TopAppBar( elevation = 0.dp, modifier = Modifier.background(color = MaterialTheme.colors.primary) ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_menu),
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(16.dp).clickable {
                        //do your thing
                    }
            )
        }
    }
}

@Composable
fun BodySection(
    musicList: List<Music>,
    heading: String,
    title: String,
    subtitle: String
) {
    LazyColumn {
        item {
            HeaderSection(
                heading = heading,
                title = title,
                subtitle = subtitle
            )
        }

        item {
            if (musicList.isEmpty()) { return@item }
            HorizontalMusicList(musicList.count())
        }

        item {
            if (musicList.isEmpty()) { return@item }
            VerticalMusicList(musicList)
        }
    }
}

@Composable
fun HeaderSection(
    heading: String,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        Text(
            text = heading,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 16.dp, top = 0.dp, end = 16.dp)
                .fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = subtitle,
            modifier = Modifier
                .padding(start = 16.dp, top = 5.dp, bottom = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            fontSize = 18.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Thin
        )
    }
}

@Composable
fun HorizontalMusicList(count: Int) {
    LazyRow {
        items(count = count) { item ->
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .height(350.dp)
                    .padding( start = if (item == 0) 16.dp else 8.dp,
                        top = 16.dp,
                        bottom = 16.dp,
                        end = if (item == 4) 16.dp else 8.dp
                    ),
                backgroundColor = Color.LightGray,
                shape = RoundedCornerShape(12)
            ) {
                //TODO
            }

        }
    }
}

@Composable
fun VerticalMusicList(musicList: List<Music>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 16.dp)
    ) {
        musicList.forEach { music ->
            MusicRow(
                musicType = music.musicType,
                songName = music.songName
            )
        }
    }
}

@Composable
fun MusicRow(
    musicType: String,
    songName: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_disc_vinyl),
                contentDescription = "Vinyl Icon",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Text(text = musicType, fontWeight = FontWeight.ExtraBold)
                Text(text = songName, fontWeight = FontWeight.Thin)
            }
        }

        Icon(
            painter = painterResource(R.drawable.ic_more),
            contentDescription = "more",
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .align(alignment = Alignment.CenterVertically)
        )
    }
}