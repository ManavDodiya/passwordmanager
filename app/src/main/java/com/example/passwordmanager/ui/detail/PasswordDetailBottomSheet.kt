package com.example.passwordmanager.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager.crypto.CryptoManager
import com.example.passwordmanager.data.PasswordEntity

@Composable
fun PasswordDetailBottomSheet(
    password: PasswordEntity,
    onUpdateClick: (PasswordEntity) -> Unit,
    onDeleteClick: (PasswordEntity) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    var accountName by remember { mutableStateOf(password.accountName) }
    var username by remember { mutableStateOf(password.username) }
    var decryptedPassword by remember {
        mutableStateOf(CryptoManager.decrypt(password.encryptedPassword))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {

        /* Header */
        Text(
            text = "Account Details",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3F7DE3),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        /* Account Type */
        Text("Account Type", fontSize = 12.sp, color = Color(0xFFD1D1D1))
        if (isEditing) {
            OutlinedTextField(
                value = accountName,
                onValueChange = { accountName = it },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = accountName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        /* Username */
        Text("Username / Email", fontSize = 12.sp, color = Color(0xFFD1D1D1))
        if (isEditing) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = username,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        /* Password */
        Text("Password", fontSize = 12.sp, color = Color(0xFFD1D1D1))
        if (isEditing) {
            OutlinedTextField(
                value = decryptedPassword,
                onValueChange = { decryptedPassword = it },
                visualTransformation =
                    if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector =
                                if (showPassword) Icons.Default.VisibilityOff
                                else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (showPassword) decryptedPassword else "••••••••",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )


                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector =
                            if (showPassword) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }

        /* Buttons */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = {
                    if (isEditing) {
                        onUpdateClick(
                            password.copy(
                                accountName = accountName,
                                username = username,
                                encryptedPassword = CryptoManager.encrypt(decryptedPassword)
                            )
                        )
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (isEditing) Color(0xFF008000)
                        else Color(0xFF2F2F2F)
                )
            ) {
                Text(
                    text = if (isEditing) "Save" else "Edit",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { onDeleteClick(password) },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF04646)
                )
            ) {
                Text(
                    text = "Delete",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

