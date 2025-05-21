package ftrr.kadkviz.presentation.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.Kviz
import ftrr.kadkviz.ui.theme.inverseSurfaceLight

@Composable
fun KvizCard(
    kviz: Kviz
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(inverseSurfaceLight)
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = kviz.date,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 2
            )

            Spacer(modifier = Modifier.size(24.dp))

            Column {
                Text(
                    text = kviz.name
                )

                Text(
                    text = kviz.location
                )

                Text(
                    text = kviz.time
                )
            }
        }
    }
}