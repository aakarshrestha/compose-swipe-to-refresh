package com.aakarshrestha.sampleappswipetorefresh.data

import com.aakarshrestha.sampleappswipetorefresh.models.Music

object MusicData {
    val musicList = arrayListOf<Music>(
        Music(musicType = "Club Music", songName = "When I am gone"),
        Music(musicType = "House Music", songName = "Like it or not"),
        Music(musicType = "Rock Music", songName = "Smells like teen spirit"),
        Music(musicType = "Metal Music", songName = "Sad but true"),
        Music(musicType = "Pop Music", songName = "Oh, Carol!"),
    )
}