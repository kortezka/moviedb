package model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
    val name: String = "Убийца убийц 2",
    val genre: String = "AAA action",
    val synopsis: String = "Он убийца. Убийца убийц. Но когда они убили его будущее...",
    val rating: Int = 5,
    val year: Int = 2022,
):Parcelable

fun getTopThree():List<MovieData>{
    return listOf(
    MovieData("шедевр1", "комедия", "-0.1257400", 1, 2),
    MovieData("шедевр2", "ужас", "139.6917100", 3, 4),
    MovieData("шедевр3", "мелодрама", " 2.3488000)", 5, 6)
    )
}

fun getSomeComedy(): List<MovieData> {
    return listOf(
        MovieData("ситком", "комедия", "-0.1257400", 1, 2),
        MovieData("ситком2", "комедия", "139.6917100", 3, 4),
        MovieData("ситком3", "комедия", " 2.3488000)", 5, 6)
    )
}

fun getSomeMovies(): List<MovieData> {
    return listOf(
        MovieData("все подряд1", "ужас", "-0.1257400", 1, 2),
        MovieData("все подряд2", "экшн", "139.6917100", 3, 4),
        MovieData("все подряд3", "драма", " 2.3488000)", 5, 6)
    )
}
