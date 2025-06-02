package ftrr.kadkviz.presentation.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ftrr.kadkviz.data.local.KvizEntity
import ftrr.kadkviz.ui.theme.inversePrimaryLight
import ftrr.kadkviz.ui.theme.onSurfaceVariantDark


@Composable
fun OrganizirajPopup(
    kviz: KvizEntity,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .padding(all = 48.dp),
            shape = RoundedCornerShape(size = 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(all = 24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Ime kviza: ${kviz.name}"
                )

                Text(
                    text = "Lokacija: ${kviz.location}"
                )

                Text(
                    text = "Datum: ${kviz.date}"
                )

                Text(
                    text = "Vrijeme: ${kviz.time}"
                )

                Text(
                    text = "Iznos kotizacije: ${kviz.entryFee}"
                )

                Text(
                    text = "Broj članova u ekipi: ${kviz.teamSize}"
                )

                Text(
                    text = "Opis: ${kviz.description}"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Otkaži", color = MaterialTheme.colorScheme.primary)
                    }

                    Button(
                        onClick = { onConfirm() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("POTVRDI",
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun PrijaviEkipuPopup(
    onDismiss: () -> Unit,
    onConfirm: (imeEkipe: String) -> Unit
) {
    var imeEkipe by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    fun validateInput(): Boolean {
        if (imeEkipe.isBlank()) {
            isError = true
            return false
        }
        isError = false
        return true
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Prijavi Ekipu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Upišite ime ekipe za prijavu na kviz.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 1.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = imeEkipe,
                    onValueChange = {
                        imeEkipe = it
                        if (isError && it.isNotBlank()) {
                            isError = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Ime ekipe") },
                    placeholder = { Text("Unesite ime ekipe") },
                    singleLine = true,
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                text = "Ime ekipe je obavezno.",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.5f
                        ),
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    )
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Otkaži", color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            if (validateInput()) {
                                onConfirm(imeEkipe)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Prijavi")
                    }
                }
            }
        }
    }
}