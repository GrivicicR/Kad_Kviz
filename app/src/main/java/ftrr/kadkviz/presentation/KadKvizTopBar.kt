package ftrr.kadkviz.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ftrr.kadkviz.ui.theme.inverseSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KadKvizTopBar(
    onHomeClick: () -> Unit,
    onMenuItemClicked: (String) -> Unit,
    currentDestination: String?
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (currentDestination != "ftrr.kadkviz.presentation.navigation.LoginScreen") {
                Text(
                    text = "KAD KVIZ",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onHomeClick() }
                )
            }
        },
        actions = {
            Log.d("TAG", "KadKvizTopBar: $currentDestination")
            if (currentDestination != "ftrr.kadkviz.presentation.navigation.LoginScreen") {
                Box {
                    IconButton(
                        onClick = {
                            menuExpanded = true
                        }
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = Icons.Filled.Menu,
                            contentDescription = ""
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Pretraži Kviz") },
                            onClick = {
                                onMenuItemClicked("Home")
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Organiziraj Kviz") },
                            onClick = {
                                onMenuItemClicked("Organiziraj")
                                menuExpanded = false
                            }
                        )
//                        DropdownMenuItem(
//                            text = { Text("Pretraga Kvizova") },
//                            onClick = {
//                                onMenuItemClicked("Pretraga")
//                                menuExpanded = false
//                            }
//                        )

                        DropdownMenuItem(
                            text = { Text("Odjava") },
                            onClick = {
                                onMenuItemClicked("Prijava")
                                menuExpanded = false
                            }
                        )

//                        DropdownMenuItem(
//                            text = { Text("Registracija") },
//                            onClick = {
//                                onMenuItemClicked("Registracija")
//                                menuExpanded = false
//                            }
//                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = inverseSurfaceLight
        )
    )
}