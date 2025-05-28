package ftrr.kadkviz.presentation

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
import androidx.compose.foundation.verticalScroll
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
import ftrr.kadkviz.ui.theme.inverseSurfaceLight
import ftrr.kadkviz.ui.theme.primaryContainerLight

@Composable
fun OrganizirajScreen(
    onSendClick: (KvizEntity) -> Unit,
    viewModel: KadKvizViewModel
) {
    var imeKviza by remember { mutableStateOf("") }
    var lokacija by remember { mutableStateOf("") }
    var datum by remember { mutableStateOf("") }
    var vrijeme by remember { mutableStateOf("") }
    var iznosKotizacije by remember { mutableStateOf("") }
    var brojClanova by remember { mutableStateOf("") }
    var opis by remember { mutableStateOf("") }

    var scrollState = rememberScrollState()

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
                color = inverseSurfaceLight
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

                    OutlinedTextField(
                        value = datum,
                        onValueChange = { datum = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Text(
                        text = "Vrijeme:"
                    )

                    OutlinedTextField(
                        value = vrijeme,
                        onValueChange = { vrijeme = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Text(
                        text = "Iznos kotizacije:"
                    )

                    OutlinedTextField(
                        value = iznosKotizacije,
                        onValueChange = { iznosKotizacije = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Text(
                        text = "Broj članova u ekipi:"
                    )

                    OutlinedTextField(
                        value = brojClanova,
                        onValueChange = { brojClanova = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
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
                            onSendClick(
                                KvizEntity(
                                    name = imeKviza,
                                    location = lokacija,
                                    date = datum,
                                    time = vrijeme
                                )
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