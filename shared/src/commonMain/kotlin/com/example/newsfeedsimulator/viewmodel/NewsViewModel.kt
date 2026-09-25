package com.example.newsfeedsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedsimulator.data.News
import com.example.newsfeedsimulator.repository.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _readNewsCount = MutableStateFlow(0)
    val readNewsCount: StateFlow<Int> = _readNewsCount.asStateFlow()

    private val _newsDetails = MutableStateFlow<List<String>>(emptyList())
    val newsDetails: StateFlow<List<String>> = _newsDetails.asStateFlow()

    init {
        observeNews()
    }

    private fun observeNews() {
        viewModelScope.launch {
            repository.getNews().collect { newNews ->

                _news.value = _news.value + newNews
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun markAsRead() {
        _readNewsCount.value += 1
    }

    fun fetchNewsDetails(newsIds: List<Int>) {
        viewModelScope.launch {

            val details = newsIds.map { newsId ->
                async {
                    repository.fetchNewsDetail(newsId)
                }
            }.awaitAll()

            _newsDetails.value = details
        }
    }
}