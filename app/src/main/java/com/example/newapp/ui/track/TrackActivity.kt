package com.example.newapp.ui.track
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newapp.presentation.TrackViewModel
import kotlinx.coroutines.launch

class TrackActivity : ComponentActivity() {

    private val viewModel by viewModels<TrackViewModel> { TrackViewModel.getViewModelFactory("123") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.getLoadingStateFlow().collect { isLoading ->
                changeProgressBarVisibility(isLoading)
            }
        }
    }

    private fun changeProgressBarVisibility(visible: Boolean) {
        // Обновляем видимость прогресс-бара
    }

}

