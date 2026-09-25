package com.example.newsfeedsimulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsfeedsimulator.repository.NewsRepository
import com.example.newsfeedsimulator.viewmodel.NewsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App() {

    val repository = remember {
        NewsRepository()
    }

    val viewModel = remember {
        NewsViewModel(repository)
    }

    val news by viewModel.news.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val readNewsCount by viewModel.readNewsCount.collectAsState()
    val newsDetails by viewModel.newsDetails.collectAsState()

    val categories = listOf(
        "All",
        "Technology",
        "Sports",
        "Health",
        "Economy",
        "Education"
    )

    val filteredNews = if (selectedCategory == "All") {
        news
    } else {
        news.filter { item ->
            item.category == selectedCategory
        }
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
                    .padding(16.dp)
            ) {

                Text(
                    text = "News Feed Simulator",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Berita dibaca: $readNewsCount",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Filter Kategori",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    categories.forEach { category ->

                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = {
                                viewModel.selectCategory(category)
                            },
                            label = {
                                Text(category)
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Button(
                    onClick = {
                        val newsIds = filteredNews
                            .take(3)
                            .map { it.id }

                        viewModel.fetchNewsDetails(newsIds)
                    },
                    enabled = filteredNews.isNotEmpty()
                ) {
                    Text("Fetch News Details")
                }

                if (newsDetails.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "News Details",
                        style = MaterialTheme.typography.titleMedium
                    )

                    newsDetails.forEach { detail ->

                        Text(
                            text = detail,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(filteredNews) { item ->

                        NewsCard(
                            title = item.title,
                            category = item.category,
                            summary = item.summary,
                            onRead = {
                                viewModel.markAsRead()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    title: String,
    category: String,
    summary: String,
    onRead: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = category,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onRead
            ) {
                Text("Tandai Dibaca")
            }
        }
    }
}