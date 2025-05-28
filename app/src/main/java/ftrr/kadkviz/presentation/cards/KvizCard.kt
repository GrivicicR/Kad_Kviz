package ftrr.kadkviz.presentation.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.Kviz
import ftrr.kadkviz.ui.theme.inverseSurfaceLight

@Composable
fun KvizCard(
    kviz: Kviz
) {
    var expanded by remember { mutableStateOf(false) }

    val fastAnimationSpec = tween<Float>(durationMillis = 400)

    val fastSizeAnimationSpec = tween<IntSize>(durationMillis = 400)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(
                animationSpec = tween(durationMillis = 0)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(inverseSurfaceLight)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                // Removed SpaceEvenly to allow icon at the end
                horizontalArrangement = Arrangement.SpaceBetween, // To push icon to the end
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = kviz.date,
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 2,
                        modifier = Modifier.weight(0.3f)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Column(modifier = Modifier.weight(0.7f)) {
                        Text(
                            text = kviz.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = kviz.location,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = kviz.time,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Smanji" else "Proširi",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp) // Add some padding to the icon
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(animationSpec = fastSizeAnimationSpec) +
                        fadeIn(animationSpec = fastAnimationSpec),
                exit = shrinkVertically(animationSpec = fastSizeAnimationSpec) +
                        fadeOut(animationSpec = fastAnimationSpec)
            ) {
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    Text(
                        text = "Opis: ${kviz.description}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Kotizacija: ${kviz.entryFee}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Broj članova: ${kviz.teamSize}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* TODO: Handle button click */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = "Prijavi ekipu"
                        )
                    }
                }
            }
        }
    }
}