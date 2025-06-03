package ftrr.kadkviz.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.ui.theme.inverseSurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KadKvizTopBar(
    onHomeClick: () -> Unit,
    onMenuItemClicked: (String) -> Unit,
    onAccountClick: () -> Unit,
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
            if (currentDestination != "ftrr.kadkviz.presentation.navigation.LoginScreen") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        // TODO: Handle account icon click
                        Log.d("TopBar", "Account Icon Clicked")
                    }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Račun",
                            modifier = Modifier.clickable {
                                onAccountClick()
                            }
                        )
                    }

                    Box {
                        IconButton(
                            onClick = {
                                menuExpanded = true
                            }
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.primary,
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Izbornik"
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier = Modifier
                                .width(220.dp)
                                .background(MaterialTheme.colorScheme.inverseOnSurface),
                            offset = DpOffset(x = (-8).dp, y = 6.dp)


                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Pretraži Kviz",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                onClick = {
                                    onMenuItemClicked("Home")
                                    menuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Organiziraj Kviz",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                onClick = {
                                    onMenuItemClicked("Organiziraj")
                                    menuExpanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Odjava", style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                onClick = {
                                    onMenuItemClicked("Prijava")
                                    menuExpanded = false
                                }
                            )

                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = inverseSurfaceLight
        )
    )
}