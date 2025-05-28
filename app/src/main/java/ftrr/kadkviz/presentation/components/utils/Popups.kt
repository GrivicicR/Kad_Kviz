package ftrr.kadkviz.presentation.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.data.local.KvizEntity

// A composable that displays a popup with an error message. It can be dismissed by tapping
// anywhere outside of the popup.
@Composable
fun OrganizirajPopup(
    kviz: KvizEntity,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 48.dp),
            shape = RoundedCornerShape(size = 8.dp)
        ) {
            Column {
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                ) {
                    Button(onClick = { onDismiss() }, modifier = Modifier.weight(1f)) {
                        Text("Otkaži")
                    }

                    Button(onClick = { onConfirm() }, modifier = Modifier.weight(1f)) {
                        Text("Potvrdi")
                    }
                }
            }
        }
    }
}

@Composable
fun PrijaviEkipuPopup(
    onConfirm: () -> Unit
) {
    var imeEkipe by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 48.dp),
            shape = RoundedCornerShape(size = 8.dp)
        ) {
            Column {
                Text(
                    text = "Upiši ime ekipe:"
                )

                OutlinedTextField(
                    value = imeEkipe,
                    onValueChange = { imeEkipe = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(onClick = { onConfirm() }) {
                        Text("Prijavi")
                    }
                }
            }
        }
    }
}