package ftrr.kadkviz.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.ui.theme.inverseSurfaceLight
import ftrr.kadkviz.ui.theme.primaryContainerLight


@Composable
fun PretragaScreen(modifier: Modifier) {
    var grad by remember { mutableStateOf("") }
    var kviz by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryContainerLight),
            contentAlignment = Alignment.Center
        ) {  Column {
                Text(
                    text = "PRETRAŽI KVIZ",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    color = inverseSurfaceLight,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.size(48.dp))

                Card(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.background(inverseSurfaceLight)
                            .padding(16.dp),
                    ) {
                        Text(
                            text = "Po gradu:"
                        )

                        OutlinedTextField(
                            value = grad,
                            onValueChange = { grad = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.size(24.dp))

                        Text(
                            text = "Po kvizu:"
                        )

                        OutlinedTextField(
                            value = kviz,
                            onValueChange = { kviz = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                        )

                        Spacer(modifier = Modifier.size(48.dp))

                        Button(
                            onClick = {},
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Pretraži"
                            )
                        }
                    }
                }
        }

        }
    }
@Preview
@Composable
fun PretragaScreenPreview() {
    PretragaScreen(modifier = Modifier)
}


