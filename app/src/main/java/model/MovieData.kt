package model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
    val title: String = "Убийца убийц 2",
    val genre: String = "AAA action",
    val overview: String = "Он убийца. Убийца убийц. Но когда они убили его будущее...",
    val vote_average: Double = 5.0,
    val year: Int = 2022,
    val id: Int = 100
) : Parcelable

fun getTopThree(): List<MovieData> {
    return listOf(
        MovieData("Карты, деньги, два ствола", "комедия", "-0.1257400", 1.0, 2, 100),
        MovieData("Леон", "ужас", "139.6917100", 3.0, 4, 101),
        MovieData("Таксист", "мелодрама", " 2.3488000)", 0.0, 6, 103)
    )
}



fun getSomeMovies(): List<MovieData> {
    return listOf(
        MovieData("Система безопасности", "ужас", "-0.1257400", 1.0, 2, 502),
        MovieData("TOP GUN", "экшн", "139.6917100", 3.0, 4, 744),
        MovieData("Назад в будущее", "драма", " 2.3488000)", 5.0, 6, 105)
    )
}
