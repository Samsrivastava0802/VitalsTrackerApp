package com.samridhi.vitalstrackerapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samridhi.vitalstrackerapp.domain.GetAllVitalsUseCase
import com.samridhi.vitalstrackerapp.domain.InsertVitalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val insertVitalsUseCase: InsertVitalsUseCase,
    private val getAllVitalsUseCase: GetAllVitalsUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(HomeScreenUiState())
        private set

    init {
        getVitals()
    }

    private fun getVitals() {
        viewModelScope.launch {
            uiState = uiState.copy(vitalsList = getAllVitalsUseCase().map {
                VitalsLog(
                    heartRate = it.heartRate,
                    bloodPressure = it.bloodPressure,
                    babyKicks = it.babyKicks,
                    weight = it.weight,
                    timeStamp = it.timeStamp
                )
            })
        }
    }


    fun onEvent(event: HomeScreenUIEvent) {
        when (event) {
            HomeScreenUIEvent.OnAddButton -> {
                uiState = uiState.copy(showDialog = true)
            }

            HomeScreenUIEvent.OnSubmit -> {
                viewModelScope.launch {
                    insertVitalsUseCase.invoke(
                        heartRate = uiState.heartRate.text,
                        bloodPressure = uiState.bloodPressure.text,
                        weight = uiState.weight.text,
                        babyKicks = uiState.babyKicks.text
                    )
                }
                uiState = uiState.copy(
                    vitalsList = uiState.vitalsList + VitalsLog(
                        heartRate = uiState.heartRate.text,
                        bloodPressure = uiState.bloodPressure.text,
                        babyKicks = uiState.babyKicks.text,
                        weight = uiState.weight.text,
                        timeStamp = uiState.timeStamp
                    ),
                    heartRate = TextFieldValue(),
                    bloodPressure = TextFieldValue(),
                    babyKicks = TextFieldValue(),
                    weight = TextFieldValue(),
                    showDialog = false
                )
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
) {
    fun isEnabled() = heartRate.text.isNotEmpty() && babyKicks.text.isNotEmpty()
            && bloodPressure.text.isNotEmpty() && weight.text.isNotEmpty()
}


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




