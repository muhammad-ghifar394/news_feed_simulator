package com.example.newsfeedsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedsimulator.repository.NewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.awaitAll

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _readNewsCount = MutableStateFlow(0)
    val readNewsCount: StateFlow<Int> = _readNewsCount

    private val _technologyNews = MutableStateFlow<List<String>>(emptyList())
    val technologyNews: StateFlow<List<String>> = _technologyNews

    private val _newsDetails = MutableStateFlow<List<String>>(emptyList())
    val newsDetails: StateFlow<List<String>> = _newsDetails

    init {
        viewModelScope.launch {
            repository.getTechnologyNews().collect { news ->
                _technologyNews.value =
                    _technologyNews.value + news
            }
        }
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