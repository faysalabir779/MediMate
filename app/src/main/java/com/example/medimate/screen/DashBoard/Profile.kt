package com.example.medimate.screen.DashBoard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.AllViewModel


@Composable
fun Profile(allViewModel: AllViewModel, applicationContext: Context) {

    val savedUserData by allViewModel.preferenceData.collectAsState()

    LaunchedEffect(key1 = true) {
        allViewModel.getSpecificUser(savedUserData.userId!!)
    }
    val specificUser = allViewModel.specificUser.value

    val user = remember { specificUser.firstOrNull() }

    var name by remember { mutableStateOf(user?.name ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var phone by remember { mutableStateOf(user?.phone_number ?: "") }
    var password by remember { mutableStateOf(user?.password ?: "") }
    var address by remember { mutableStateOf(user?.address ?: "") }
    var userId by remember { mutableStateOf(user?.user_id ?: "") }

    var readOnly by remember { mutableStateOf(true) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Text(text = "Profile", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .size(140.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(color = Color(0xFFD1D1D1)), contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = null,
                tint = Color(0xFF48494E),
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                readOnly = !readOnly
            }, horizontalArrangement = Arrangement.End
        ) {
            Icon(Icons.Filled.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Click To Edit", color = Color(0xFF111111), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(10.dp))

        ProfileTextField("Name", name, readOnly) { name = it }
        Spacer(modifier = Modifier.height(17.dp))
        ProfileTextField("Email", email, readOnly) { email = it }
        Spacer(modifier = Modifier.height(17.dp))
        ProfileTextField("Phone", phone, readOnly) { phone = it }
        Spacer(modifier = Modifier.height(17.dp))
        ProfileTextField("Address", address, readOnly) { address = it }
        Spacer(modifier = Modifier.height(17.dp))
        ProfileTextField("Password", password, readOnly) { password = it }
        Spacer(modifier = Modifier.height(17.dp))

        if (!readOnly) {
            Button(
                onClick = {
                    allViewModel.updateUser(
                        userId,
                        name,
                        password,
                        email,
                        phone,
                        address,
                        applicationContext
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF111111),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .heightIn(min = 50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(text = "Update Details")
            }
        }
    }
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    readOnly: Boolean,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) Color(0xFF111111) else Color(0xFF212121), // Conditional border color
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.fillMaxWidth(0.24f),
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = value,
                readOnly = readOnly,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .onFocusChanged { isFocused = it.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}

