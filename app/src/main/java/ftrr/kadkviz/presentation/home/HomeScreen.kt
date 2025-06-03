package ftrr.kadkviz.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ftrr.kadkviz.presentation.KadKvizViewModel
import ftrr.kadkviz.presentation.cards.KvizCard
import ftrr.kadkviz.presentation.components.utils.PrijaviEkipuPopup
import ftrr.kadkviz.ui.theme.primaryContainerLight

@Composable
fun HomeScreen(
    viewModel: KadKvizViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showRegistrationPopup by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllKviz()
    }

    val displayedItems = remember(searchQuery, state.triviaList) {
        val listToFilter = state.triviaList

        if (searchQuery.isBlank()) {
            listToFilter
        } else {
            listToFilter.filter { kviz ->
                kviz.name.contains(searchQuery, ignoreCase = true) || kviz.location.contains(
                    searchQuery,
                    ignoreCase = true
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                placeholder = { Text("Pretraži kvizove...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (displayedItems.isEmpty()) {
                Text(
                    if (searchQuery.isNotBlank()) "Nema rezultata za '$searchQuery'"
                    else "Učitavamo kvizove."
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(displayedItems, key = { it.id!! }) { kviz ->
                        KvizCard(
                            kviz = kviz,
                            onApplyClick = {
                                showRegistrationPopup = true
                            }
                        )
                    }
                }
            }
        }
        if (showRegistrationPopup) {
            PrijaviEkipuPopup(
                onConfirm = { imeEkipe ->
                    showRegistrationPopup = false
                    Toast.makeText(context, "Uspješno ste prijavili ekipu $imeEkipe!", Toast.LENGTH_LONG).show()

                },
                onDismiss = {
                    showRegistrationPopup = false
                }
            )
        }
    }
}