package com.example.newsfeedsimulator.repository

import com.example.newsfeedsimulator.data.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class NewsRepository {

    private val newsList = listOf(
        News(
            id = 1,
            title = "Smartphone Baru Hadir dengan Teknologi AI",
            category = "Technology",
            summary = "Perangkat terbaru menghadirkan berbagai fitur AI untuk membantu aktivitas pengguna."
        ),
        News(
            id = 2,
            title = "Indonesia Raih Kemenangan di Pertandingan Internasional",
            category = "Sports",
            summary = "Tim Indonesia berhasil meraih hasil positif dalam pertandingan internasional."
        ),
        News(
            id = 3,
            title = "Tips Menjaga Pola Tidur Tetap Sehat",
            category = "Health",
            summary = "Pola tidur yang cukup dan teratur dapat membantu menjaga kondisi tubuh."
        ),
        News(
            id = 4,
            title = "Harga Komoditas Mulai Mengalami Perubahan",
            category = "Economy",
            summary = "Perubahan harga komoditas menjadi perhatian pelaku usaha dan masyarakat."
        ),
        News(
            id = 5,
            title = "Platform Pembelajaran Digital Semakin Populer",
            category = "Education",
            summary = "Penggunaan platform digital membantu mahasiswa mengakses materi pembelajaran dengan lebih mudah."
        ),
        News(
            id = 6,
            title = "Laptop Generasi Terbaru Resmi Diperkenalkan",
            category = "Technology",
            summary = "Laptop terbaru menawarkan peningkatan performa dan efisiensi daya."
        ),
        News(
            id = 7,
            title = "Atlet Muda Indonesia Raih Prestasi Baru",
            category = "Sports",
            summary = "Seorang atlet muda Indonesia berhasil mencatatkan prestasi dalam kompetisi nasional."
        ),
        News(
            id = 8,
            title = "Olahraga Ringan yang Bisa Dilakukan di Rumah",
            category = "Health",
            summary = "Beberapa olahraga sederhana dapat dilakukan di rumah untuk menjaga kebugaran."
        ),
        News(
            id = 9,
            title = "UMKM Mulai Memanfaatkan Pembayaran Digital",
            category = "Economy",
            summary = "Semakin banyak pelaku UMKM menggunakan pembayaran digital untuk mempermudah transaksi."
        ),
        News(
            id = 10,
            title = "Mahasiswa Mulai Memanfaatkan AI untuk Belajar",
            category = "Education",
            summary = "Teknologi AI mulai digunakan mahasiswa sebagai salah satu alat bantu dalam proses belajar."
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