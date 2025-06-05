package example.mardsoul.draganddroplazycolumn.ui

import androidx.lifecycle.ViewModel
import example.mardsoul.draganddroplazycolumn.R
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ViewModel() {
	private val _state = MutableStateFlow(YandexMusicState())
	val uiState = _state
}

data class YandexMusicState(
	val list: List<YandexMusicItem> = YandexMusicItem.mockData
)

data class YandexMusicItem(
	val id: Int,
	val name: String,
	val author: String,
	val img: Int
) {
	companion object {
		val mockData = listOf(
			YandexMusicItem(1, "Стали другими", "Morzza, Isupov", R.drawable.isupov),
			YandexMusicItem(2, "Регресс", "Jony", R.drawable.i),
			YandexMusicItem(3, "Дороже золота", "MONA", R.drawable.mona),
			YandexMusicItem(4, "Мысли", "Xcho, Nekki", R.drawable.nekki),
			YandexMusicItem(5, "Вдвоем (feat. Artik & Asti)", "Миша Марвин, Artik & Asti", R.drawable.misha),
			YandexMusicItem(6, "Кристалл", "Elman", R.drawable.elman),
			YandexMusicItem(7, "Асвальт", "Jakone, kiliana", R.drawable.jakone),
			YandexMusicItem(8, "Особенная", "Vlad Hosh", R.drawable.vlad),
			YandexMusicItem(9, "Medicine", "Miyagi & Andy panda", R.drawable.medicine),
			YandexMusicItem(10, "Лилии", "Мот & Jony", R.drawable.mot),
			YandexMusicItem(11, "Черная любовь", "Elman & Mona", R.drawable.elman_mona),
		)
	}
}