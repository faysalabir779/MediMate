package com.example.medimate.screen.SignUpPage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.medimate.AllViewModel
import com.example.medimate.R
import com.example.medimate.State
import com.example.medimate.navigation.Routes
import com.example.medimate.screen.LoadingPage
import com.example.medimate.screen.SignUpCompleteMsg


@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    allViewModel: AllViewModel,
) {
    val context = LocalContext.current
    //no ripple
    val interactionSource = remember { MutableInteractionSource() }

    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }




    when (allViewModel.state.value) {
        State.DEFAULT.name -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                    Image(
                        painter = painterResource(id = R.drawable.bg),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(1.2f)
                    )
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(top = 93.dp, start = 38.dp)
                    ) {
                        Text(text = "Welcome To", fontSize = 18.sp, color = Color(0xFF6671ff))
                        Text(
                            text = "MediMate",
                            fontSize = 33.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF010754)
                        )
                        Spacer(modifier = Modifier.height(70.dp))
                        Text(
                            text = "Sign Up Here",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF6671ff)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(14.dp))

                TextField(
                    value = name, onValueChange = { name = it },
                    placeholder = {
                        Text(text = "Full Name", fontSize = 15.sp, color = Color(0xFF313ccb))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),

                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))


                TextField(
                    value = password, onValueChange = { password = it },
                    placeholder = {
                        Text(text = "Password", fontSize = 15.sp, color = Color(0xFF313ccb))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),

                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = phone, onValueChange = { phone = it },
                    placeholder = {
                        Text(text = "Enter Your Phone", fontSize = 15.sp, color = Color(0xFF313ccb))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = email, onValueChange = { email = it },
                    placeholder = {
                        Text(text = "Enter Your Email", fontSize = 15.sp, color = Color(0xFF313ccb))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = pin, onValueChange = { pin = it },
                    placeholder = {
                        Text(
                            text = "Enter Your Pin Code",
                            fontSize = 15.sp,
                            color = Color(0xFF313ccb)
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Create,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = address, onValueChange = { address = it },
                    placeholder = {
                        Text(
                            text = "Enter Your Address",
                            fontSize = 15.sp,
                            color = Color(0xFF313ccb)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF313ccb)
                        )
                    },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF313ccb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),

                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFe6e8ff),
                        focusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )
                Spacer(modifier = Modifier.height(28.dp))


                Button(
                    onClick = {
                        if (name.isBlank() || password.isBlank() || phone.isBlank() || email.isBlank() || pin.isBlank() || address.isBlank()) {
                            Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            allViewModel.createUser(name, password, email, address, phone, pin)
                        }
                    }, modifier = Modifier
                        .width(150.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6671ff),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

                Row {
                    Text(text = "Already have an account?", fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.navigate(Routes.SignIn)
                        },
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF313ccb)
                    )
                }


            }
        }

        State.LOADING.name -> {
            LoadingPage(allViewModel)
        }

        State.FAILED.name -> {
            Text(text = "Try Again")
        }

        State.SUCCESS.name -> SignUpCompleteMsg("")
    }


}