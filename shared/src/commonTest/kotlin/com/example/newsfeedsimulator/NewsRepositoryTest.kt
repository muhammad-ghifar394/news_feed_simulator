package com.example.newsfeedsimulator

import com.example.newsfeedsimulator.repository.NewsRepository
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class NewsRepositoryTest {

    @Test
    fun technologyNews_shouldOnlyContainTechnologyCategory() = runTest {
        val repository = NewsRepository()

        val news = repository
            .getTechnologyNews()
            .take(2)
            .toList()

        news.forEach { item ->
            assertTrue(item.startsWith("[Technology]"))
        }
    }
}