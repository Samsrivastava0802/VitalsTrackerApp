package com.samridhi.vitalstrackerapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(HomeScreenUiState())
        private set


    fun onEvent(event: HomeScreenUIEvent) {
        when (event) {
            HomeScreenUIEvent.OnAddButton -> {
                uiState = uiState.copy(showDialog = true)
            }

            HomeScreenUIEvent.OnSubmit -> {

            }

            is HomeScreenUIEvent.OnBabyKicksChange -> {
                uiState = uiState.copy(babyKicks = event.babyKicksText)
            }

            is HomeScreenUIEvent.OnHeartRateChange -> {
                uiState = uiState.copy(heartRate = event.heartText)
            }

            is HomeScreenUIEvent.OnSysBpChange -> {
                uiState = uiState.copy(bloodPressure = event.sysText)
            }

            is HomeScreenUIEvent.OnWeightChange -> {
                uiState = uiState.copy(weight = event.weightText)
            }

            HomeScreenUIEvent.OnDismissDialog -> {
                uiState = uiState.copy(showDialog = false)
            }
        }
    }
}


data class HomeScreenUiState(
    val heartRate: TextFieldValue = TextFieldValue(),
    val bloodPressure: TextFieldValue = TextFieldValue(),
    val babyKicks: TextFieldValue = TextFieldValue(),
    val weight: TextFieldValue = TextFieldValue(),
    val timeStamp: String = "",
    val vitalsList: List<VitalsLog> = emptyList(),
    val showDialog: Boolean = false,
)


data class VitalsLog(
    val heartRate: String,
    val bloodPressure: String,
    val babyKicks: String,
    val weight: String,
    val timeStamp: String,
)


sealed class HomeScreenUIEvent {
    data object OnAddButton : HomeScreenUIEvent()
    data object OnSubmit : HomeScreenUIEvent()
    data object OnDismissDialog : HomeScreenUIEvent()
    data class OnHeartRateChange(val heartText: TextFieldValue) : HomeScreenUIEvent()
    data class OnSysBpChange(val sysText: TextFieldValue) : HomeScreenUIEvent()
    data class OnWeightChange(val weightText: TextFieldValue) : HomeScreenUIEvent()
    data class OnBabyKicksChange(val babyKicksText: TextFieldValue) : HomeScreenUIEvent()
}




