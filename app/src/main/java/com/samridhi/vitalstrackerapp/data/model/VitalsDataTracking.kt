package com.samridhi.vitalstrackerapp.data.model

data class VitalsDataTracking(
    val id: String = "",
    val sysBp : String = "",
    val diaBp : String = "",
    val kicks : String = "",
    val weight: String = "",
    val timeStamp: String = "",
    val day : String = ""
)

fun getDemoData(): List<VitalsDataTracking> {
    return listOf(
        VitalsDataTracking(
           kicks = "9 kicks",
           weight = "75 kg",
           sysBp = "80 bpm",
           diaBp = "120/80 mmHg",
           timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
        VitalsDataTracking(
            kicks = "9 kicks",
            weight = "75 kg",
            sysBp = "80 bpm",
            diaBp = "120/80 mmHg",
            timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
        VitalsDataTracking(
            kicks = "9 kicks",
            weight = "75 kg",
            sysBp = "80 bpm",
            diaBp = "120/80 mmHg",
            timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
        VitalsDataTracking(
            kicks = "9 kicks",
            weight = "75 kg",
            sysBp = "80 bpm",
            diaBp = "120/80 mmHg",
            timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
        VitalsDataTracking(
            kicks = "9 kicks",
            weight = "75 kg",
            sysBp = "80 bpm",
            diaBp = "120/80 mmHg",
            timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
        VitalsDataTracking(
            kicks = "9 kicks",
            weight = "75 kg",
            sysBp = "80 bpm",
            diaBp = "120/80 mmHg",
            timeStamp = " Monday 12 Jan 2023 03:45 pm"
        ),
    )
}
