package com.aakarshrestha.composecollapsingtoolbarlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aakarshrestha.collapsableappbarutil.CollapsableToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun App() {
        CollapsableToolbar(
            contentToAddInTopAppBar = {
                Text(text = "Title", modifier = Modifier.padding(start = 16.dp))
            },
            body = {
                LazyColumn {
                    items(25) { item ->
                        Text(text = "Item $item", modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth())
                    }
                }
            },
            contentToAddInBottomAppBar = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize().align(alignment = Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Inbox", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                        Text(text = "Outbox", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                        Text(text = "Settings", modifier = Modifier.fillMaxHeight().padding(16.dp), textAlign = TextAlign.Center)
                    }
                }
            }
        )
    }
}