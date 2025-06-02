package ftrr.kadkviz.presentation.organiziraj

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.data.local.KvizEntity
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.components.utils.OrganizirajPopup
import ftrr.kadkviz.ui.theme.inverseSurfaceLight
import ftrr.kadkviz.ui.theme.primaryContainerLight
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizirajScreen(
    onSendClick: () -> Unit,
    viewModel: KadKvizViewModel
) {
    var imeKviza by remember { mutableStateOf("") }
    var lokacija by remember { mutableStateOf("") }
    var datum by remember { mutableStateOf("") }
    var vrijeme by remember { mutableStateOf("Odaberi vrijeme") }
    var iznosKotizacije by remember { mutableStateOf("") }
    var brojClanova by remember { mutableStateOf("") }
    var opis by remember { mutableStateOf("") }
    var storedTrivia by remember {
        mutableStateOf(
            KvizEntity(
                name = "",
                location = "",
                date = "",
                time = "",
                entryFee = ""
            )
        )
    }

    val scrollState = rememberScrollState()
    var openDialog by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }
    var openTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(0L) }

    if (showPopup) {
        OrganizirajPopup(
            kviz = storedTrivia,
            onDismiss = { showPopup = false },
            onConfirm = {
                showPopup = false
                viewModel.insertKviz(storedTrivia)
                onSendClick()
            }
        )
    } else {

        if (openTimePicker) {
            KvizTimePicker(
                onConfirm = {
                    openTimePicker = false
                    vrijeme = "${it.hour}:${it.minute}"
                },
                onDismiss = {
                    openTimePicker = false
                }
            )
        }

        if (openDialog) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = {
                    openDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                            datePickerState.selectedDateMillis?.let { selectedDate = it }
                            datum = millisecondsToFormattedDate(selectedDate)
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryContainerLight)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ORGANIZIRAJ KVIZ",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    color = inverseSurfaceLight,
                    fontWeight = FontWeight.Bold
                )
                Card(
                    modifier = Modifier
                        .padding(all = 24.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(inverseSurfaceLight)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Ime kviza:"
                        )

                        OutlinedTextField(
                            value = imeKviza,
                            onValueChange = { imeKviza = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Lokacija:"
                        )

                        OutlinedTextField(
                            value = lokacija,
                            onValueChange = { lokacija = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Datum:"
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { openDialog = true },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            if (selectedDate == 0L) {
                                Text(
                                    text = "Odaberi datum"
                                )
                            } else
                                Text(
                                    text = millisecondsToFormattedDate(selectedDate)
                                )
                        }

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Vrijeme:"
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                openTimePicker = true
                            }
                        ) {
                            Text(
                                text = vrijeme
                            )
                        }

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Iznos kotizacije:"
                        )

                        OutlinedTextField(
                            value = iznosKotizacije,
                            onValueChange = { iznosKotizacije = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Broj članova u ekipi:"
                        )

                        OutlinedTextField(
                            value = brojClanova,
                            onValueChange = { brojClanova = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Opis:"
                        )

                        OutlinedTextField(
                            value = opis,
                            onValueChange = { opis = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Button(
                            onClick = {
                                showPopup = true
                                storedTrivia = KvizEntity(
                                    name = imeKviza,
                                    location = lokacija,
                                    date = datum,
                                    time = vrijeme,
                                    entryFee = iznosKotizacije,
                                    teamSize = brojClanova,
                                    description = opis
                                )
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Pošalji"
                            )
                        }
                    }
                }
            }
        }
    }
}

fun millisecondsToFormattedDate(
    milliseconds: Long,
    formatPattern: String = "dd.MM.",
    locale: java.util.Locale = java.util.Locale.getDefault(),
    timeZone: java.util.TimeZone = java.util.TimeZone.getTimeZone("UTC") // Default to UTC for epoch interpretation
): String {
    return try {
        val sdf = SimpleDateFormat(formatPattern, locale)
        sdf.timeZone = timeZone
        val date = Date(milliseconds)
        sdf.format(date)
    } catch (e: IllegalArgumentException) {
        "Error: Invalid date format pattern"
    } catch (e: Exception) {
        "Error: Could not convert milliseconds to date"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvizTimePicker(onConfirm: (TimePickerState) -> Unit, onDismiss: () -> Unit) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

