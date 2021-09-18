package model

import android.icu.text.CaseMap

data class MovieDTO(
    val title: String?,
    val vote_average: Double?,
    val overview: String?,
    val release_date: String?,
    val id: Int?
)
