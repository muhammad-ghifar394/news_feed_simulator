package com.example.newsfeedsimulator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.example.newsfeedsimulator.repository.NewsRepository
import com.example.newsfeedsimulator.viewmodel.NewsViewModel

import newsfeedsimulator.shared.generated.resources.Res
import newsfeedsimulator.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {

    val repository = remember {
        NewsRepository()
    }

    val viewModel = remember {
        NewsViewModel(repository)
    }

    val technologyNews by viewModel.technologyNews.collectAsState()
    val readNewsCount by viewModel.readNewsCount.collectAsState()
    val newsDetails by viewModel.newsDetails.collectAsState()

    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "News Feed Simulator",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Berita dibaca: $readNewsCount"
            )

            Button(
                onClick = {
                    viewModel.fetchNewsDetails(listOf(1, 2, 3))
                }
            ) {
                Text("Fetch News Details")
            }

            newsDetails.forEach { detail ->
                Text(text = detail)
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(technologyNews) { news ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = news
                        )

                        Button(
                            onClick = {
                                viewModel.markAsRead()
                            }
                        ) {
                            Text("Mark as Read")
                        }
                    }
                }
            }
        }
    }
}