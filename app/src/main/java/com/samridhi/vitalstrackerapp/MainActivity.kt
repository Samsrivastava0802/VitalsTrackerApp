package com.samridhi.vitalstrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samridhi.vitalstrackerapp.jobs.ReminderWorker
import com.samridhi.vitalstrackerapp.presentation.home.HomeScreen
import com.samridhi.vitalstrackerapp.ui.theme.VitalsTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReminderWorker.scheduleVitalsReminder(applicationContext)
        setContent {
            VitalsTrackerAppTheme {
                HomeScreen()
            }
        }
    }
}

