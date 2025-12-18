package com.example.passwordmanager.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager.R
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.ui.add.AddPasswordBottomSheet
import com.example.passwordmanager.ui.detail.PasswordDetailBottomSheet
import com.example.passwordmanager.viewmodel.PasswordViewModel

private val ScreenBackground = Color(0xFFF3F5FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: PasswordViewModel) {

    val passwords by viewModel.passwords.collectAsState()

    var showAddSheet by remember { mutableStateOf(false) }
    var showDetailSheet by remember { mutableStateOf(false) }
    var selectedPassword by remember { mutableStateOf<PasswordEntity?>(null) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBackground)
                .padding(padding)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                Text(
                    text = "Password Manager",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )


                LazyColumn {
                    items(passwords) { item ->
                        PasswordRow(
                            name = item.accountName,
                            onClick = {
                                selectedPassword = item
                                showDetailSheet = true
                            }
                        )
                    }
                }
            }

            /* SVG ADD BUTTON */
            Icon(
                painter = painterResource(id = R.drawable.add_button),
                contentDescription = "Add Password",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(134.dp)
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .clickable {
                        showAddSheet = true
                    }
            )
        }

        /* ADD PASSWORD SHEET */
        if (showAddSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showAddSheet = false },
                containerColor = Color(0xFFF9F9F9),   // 🔑 FIX
                tonalElevation = 0.dp
            ) {
                AddPasswordBottomSheet { account, username, password ->
                    viewModel.addPassword(account, username, password)
                    showAddSheet = false
                }
            }
        }

        /* DETAIL SHEET */
        if (showDetailSheet && selectedPassword != null) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showDetailSheet = false
                    selectedPassword = null
                }
            ) {
                PasswordDetailBottomSheet(
                    password = selectedPassword!!,
                    onUpdateClick = {
                        viewModel.updatePassword(it)
                        showDetailSheet = false
                        selectedPassword = null
                    },
                    onDeleteClick = {
                        viewModel.deletePassword(it)
                        showDetailSheet = false
                        selectedPassword = null
                    }
                )
            }
        }
    }
}

@Composable
fun PasswordRow(
    name: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(28.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 22.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "••••••••",
            fontSize = 16.sp,
            color = Color(0xFFC6C6C6)
        )
    }
}

