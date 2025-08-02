package com.samridhi.vitalstrackerapp.presentation.home

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.samridhi.vitalstrackerapp.R
import com.samridhi.vitalstrackerapp.alias.AppDrawable
import com.samridhi.vitalstrackerapp.alias.AppString
import com.samridhi.vitalstrackerapp.presentation.common.VitalsTextField
import com.samridhi.vitalstrackerapp.ui.theme.ht1
import com.samridhi.vitalstrackerapp.ui.theme.ht2
import com.samridhi.vitalstrackerapp.ui.theme.lightPurple
import com.samridhi.vitalstrackerapp.ui.theme.purple
import com.samridhi.vitalstrackerapp.ui.theme.purple4
import com.samridhi.vitalstrackerapp.ui.theme.purple5
import com.samridhi.vitalstrackerapp.ui.theme.purple6
import com.samridhi.vitalstrackerapp.ui.theme.purple7
import com.samridhi.vitalstrackerapp.ui.theme.purple8
import com.samridhi.vitalstrackerapp.util.TimeUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val permissionGranted = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted.value = isGranted
    }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            permissionGranted.value = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.track_my_pregnancy),
                        color = purple5,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purple4
                )
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            viewModel.onEvent(HomeScreenUIEvent.OnAddButton)
                        },
                    tint = purple,
                    painter = painterResource(id = AppDrawable.plus_icon),
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            uiState = viewModel.uiState,
        )
    }

    AnimatedVisibility(viewModel.uiState.showDialog) {
        GenericDialog(
            onDismissRequest = {
                viewModel.onEvent(HomeScreenUIEvent.OnDismissDialog)
            },
            content = {
                AddVitalsDialog(
                    uiState = viewModel.uiState,
                    onEvent = viewModel::onEvent
                )
            }
        )
    }

}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: HomeScreenUiState,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(uiState.vitalsList) {
            HealthStats(data = it,
                createdAt = it.timeStamp)
        }
    }
}

@Composable
fun HealthStats(
    data: VitalsLog,
    createdAt: String
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(color = purple7)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(AppDrawable.heart_rate),
                    contentDescription = "Heart rate icon",
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(AppString.bpm, data.heartRate),
                    style = MaterialTheme.typography.ht1.copy(color = Color.Black, fontSize = 12.sp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(AppDrawable.blood_pressure),
                    contentDescription = "Blood pressure icon",
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(AppString.mmhg, data.bloodPressure),
                    style = MaterialTheme.typography.ht1.copy(color = Color.Black, fontSize = 12.sp)
                )
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(AppDrawable.scale),
                    contentDescription = "Weight icon",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(AppString.kg, data.weight),
                    style = MaterialTheme.typography.ht1.copy(color = Color.Black, fontSize = 12.sp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(AppDrawable.newborn),
                    contentDescription = "Baby kicks icon",
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(AppString.kicks, data.babyKicks),
                    style = MaterialTheme.typography.ht1.copy(color = Color.Black, fontSize = 12.sp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = purple6),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = TimeUtil.convertMillisToDate(createdAt),
                color = Color.White
            )
        }
    }
}


@Composable
fun GenericDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true
    ),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}


@Composable
fun AddVitalsDialog(
    uiState: HomeScreenUiState,
    onEvent: (HomeScreenUIEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Add Vitals",
                style = MaterialTheme.typography.ht2.copy(color = purple8, fontSize = 16.sp),
                fontSize = 20.sp,
                color = purple8
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                VitalsTextField(
                    textFieldValue = uiState.heartRate,
                    onValueChange = {
                        onEvent(HomeScreenUIEvent.OnHeartRateChange(it))
                    },
                    label = "Sys BP",
                    modifier = Modifier.weight(1f)
                )
                VitalsTextField(
                    textFieldValue = uiState.bloodPressure,
                    onValueChange = {
                        onEvent(HomeScreenUIEvent.OnSysBpChange(it))
                    },
                    label = "Dia BP",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            VitalsTextField(
                textFieldValue = uiState.weight,
                onValueChange = {
                    onEvent(HomeScreenUIEvent.OnWeightChange(it))
                },
                label = "Weight (in kg)",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            VitalsTextField(
                textFieldValue = uiState.babyKicks,
                onValueChange = {
                    onEvent(HomeScreenUIEvent.OnBabyKicksChange(it))
                },
                label = "Baby Kicks",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(40.dp))
            Button(
                enabled = uiState.isEnabled(),
                content = {
                    Text("Submit")
                },
                modifier = Modifier
                    .width(134.dp)
                    .height(48.dp)
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = purple6),
                onClick = {
                    onEvent(HomeScreenUIEvent.OnSubmit)
                }
            )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Preview() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.track_my_pregnancy),
                        color = purple5,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purple4
                )
            )
        },
        floatingActionButton = {

            Icon(
                modifier = Modifier.size(38.dp),
                tint = purple,
                painter = painterResource(id = AppDrawable.plus_icon),
                contentDescription = ""
            )

        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.padding(innerPadding),
            uiState = HomeScreenUiState(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        HealthStats(
            data = VitalsLog(
                heartRate = "78",
                bloodPressure = "120/80",
                weight = "60",
                babyKicks = "5",
                timeStamp = "2025-08-01 10:00 AM"
            ),
            createdAt = ""
        )
        HealthStats(
            data = VitalsLog(
                heartRate = "82",
                bloodPressure = "118/76",
                weight = "61",
                babyKicks = "6",
                timeStamp = "2025-08-01 02:30 PM"
            ),
            createdAt = ""
        )
        HealthStats(
            data = VitalsLog(
                heartRate = "75",
                bloodPressure = "115/75",
                weight = "59.8",
                babyKicks = "4",
                timeStamp = "2025-07-31 09:45 AM"
            ),
            createdAt = ""
        )
        HealthStats(
            data = VitalsLog(
                heartRate = "80",
                bloodPressure = "122/81",
                weight = "60.5",
                babyKicks = "7",
                timeStamp = "2025-07-30 06:20 PM"
            ),
            createdAt = ""
        )
        HealthStats(
            data = VitalsLog(
                heartRate = "79",
                bloodPressure = "117/78",
                weight = "60.2",
                babyKicks = "6",
                timeStamp = "2025-07-29 11:00 AM"
            ),
            createdAt = ""
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview3() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // AddVitalsDialog()
    }
}
