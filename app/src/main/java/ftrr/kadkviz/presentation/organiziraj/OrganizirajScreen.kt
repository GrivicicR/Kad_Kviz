package ftrr.kadkviz.presentation.organiziraj

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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

    //Error
    var imeKvizaError by remember { mutableStateOf(false) }
    var lokacijaError by remember { mutableStateOf(false) }
    var datumError by remember { mutableStateOf(false) }
    var vrijemeError by remember { mutableStateOf(false) }
    var iznosKotizacijeError by remember { mutableStateOf(false) }
    var brojClanovaError by remember { mutableStateOf(false) }
    var opisError by remember { mutableStateOf(false) }

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

    val errorMessage = "Polje je obavezno."

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
                onConfirm = { timePickerState ->
                    openTimePicker = false
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    vrijeme = String.format("%02d:%02d", hour, minute)
                    vrijemeError = false
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
                            datumError = false
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
                .imePadding()
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

                        OutlinedTextField(
                            value = imeKviza,
                            onValueChange = {
                                imeKviza = it;
                                if (imeKvizaError) imeKvizaError = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Ime kviza") },
                            placeholder = { Text("Unesite ime kviza") },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            isError = imeKvizaError,
                            supportingText = {
                                if (imeKvizaError) Text(errorMessage)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (imeKvizaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (imeKvizaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                ),
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = if (imeKvizaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        OutlinedTextField(
                            value = lokacija,
                            onValueChange = {
                                lokacija = it;
                                if (lokacijaError) lokacijaError = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Lokacija") },
                            placeholder = { Text("Unesite lokaciju kviza") },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            isError = lokacijaError,
                            supportingText = {
                                if (lokacijaError) Text(errorMessage)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (lokacijaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (lokacijaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                ),
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = if (lokacijaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Datum:"
                        )

                        Button(
                            onClick = { openDialog = true },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            if (datum.isBlank()) {
                                Text(
                                    text = "Odaberi datum",
                                    color = if (datumError) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = datum,
                                    color = if (datumError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                        if (datumError) {
                            Text(
                                text = "Datum je obavezan.",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Vrijeme:"
                        )

                        Button(
                            onClick = { openTimePicker = true },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            if (vrijeme == "Odaberi vrijeme" || vrijeme.isBlank()) {
                                Text(
                                    text = "Odaberi vrijeme",
                                    color = if (vrijemeError) MaterialTheme.colorScheme.inverseOnSurface
                                    else MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = vrijeme,
                                    color = if (vrijemeError) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                        if (vrijemeError) {
                            Text(
                                text = "Vrijeme je obavezno.",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.size(24.dp))

                        OutlinedTextField(
                            value = iznosKotizacije,
                            onValueChange = {
                                iznosKotizacije = it;
                                if (iznosKotizacijeError) iznosKotizacijeError = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Iznos kotizacije") },
                            placeholder = { Text("Unesite iznos kotizacije") },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = iznosKotizacijeError,
                            supportingText = {
                                if (iznosKotizacijeError) Text(errorMessage)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (iznosKotizacijeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (iznosKotizacijeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                ),
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = if (iznosKotizacijeError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        OutlinedTextField(
                            value = brojClanova,
                            onValueChange = {
                                brojClanova = it;
                                if (brojClanovaError) brojClanovaError = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Broj članova") },
                            placeholder = { Text("Unesite broj članova u ekipi") },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = brojClanovaError,
                            supportingText = {
                                if (brojClanovaError) Text(errorMessage)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (brojClanovaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (brojClanovaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                ),
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = if (brojClanovaError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        OutlinedTextField(
                            value = opis,
                            onValueChange = {
                                opis = it;
                                if (opisError) opisError = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Opis") },
                            placeholder = { Text("Unesite opis kviza") },
                            singleLine = false,
                            shape = RoundedCornerShape(8.dp),
                            isError = opisError,
                            supportingText = {
                                if (opisError) Text(errorMessage)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (opisError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (opisError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f
                                ),
                                cursorColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = if (opisError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            )
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Button(
                            onClick = {
                                var hasErrors = false

                                if (imeKviza.isBlank()) {
                                    imeKvizaError = true
                                    hasErrors = true
                                } else {
                                    imeKvizaError = false
                                }

                                if (lokacija.isBlank()) {
                                    lokacijaError = true
                                    hasErrors = true
                                } else {
                                    lokacijaError = false
                                }

                                if (datum.isBlank() || selectedDate == 0L) {
                                    datumError = true
                                    hasErrors = true
                                } else {
                                    datumError = false
                                }

                                if (vrijeme == "Odaberi vrijeme" || vrijeme.isBlank()) {
                                    vrijemeError = true
                                    hasErrors = true
                                } else {
                                    vrijemeError = false
                                }
                                if (iznosKotizacije.isBlank()) {
                                    iznosKotizacijeError = true
                                    hasErrors = true
                                } else {
                                    iznosKotizacijeError = false
                                }

                                if (brojClanova.isBlank()) {
                                    brojClanovaError = true
                                    hasErrors = true
                                } else {
                                    brojClanovaError = false
                                }

                                if (opis.isBlank()) {
                                    opisError = true
                                    hasErrors = true
                                } else {
                                    opisError = false
                                }

                                if (!hasErrors) {
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
                                }
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

