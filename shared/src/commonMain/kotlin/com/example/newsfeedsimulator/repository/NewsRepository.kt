package com.example.newsfeedsimulator.repository

import com.example.newsfeedsimulator.data.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch

class NewsRepository {

    private val newsList = listOf(
        News(
            id = 1,
            title = "AI Mengubah Dunia Pendidikan",
            category = "Technology",
            summary = "Perkembangan AI semakin banyak digunakan dalam pendidikan."
        ),
        News(
            id = 2,
            title = "Timnas Indonesia Menang",
            category = "Sports",
            summary = "Timnas Indonesia meraih kemenangan dalam pertandingan."
        ),
        News(
            id = 3,
            title = "Perkembangan Teknologi Terbaru",
            category = "Technology",
            summary = "Teknologi baru terus berkembang dengan cepat."
        ),
        News(
            id = 4,
            title = "Tips Menjaga Kesehatan",
            category = "Health",
            summary = "Menjaga pola hidup sehat penting untuk kesehatan."
        ),
        News(
            id = 5,
            title = "Berita Ekonomi Hari Ini",
            category = "Economy",
            summary = "Perkembangan ekonomi menjadi perhatian masyarakat."
        )
    )

    fun getNews(): Flow<News> = flow {

        while (true) {

            for (news in newsList) {
                emit(news)
                delay(2000)
            }

        }
    }

    fun getTechnologyNews(): Flow<String> {
        return getNews()
            .filter { news ->
                news.category == "Technology"
            }
            .map { news ->
                "[${news.category}] ${news.title}"
            }
            .catch { error ->
                println("Terjadi error: ${error.message}")
            }
    }

    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000)

        return "Detail berita dengan ID $newsId"
    }
}
