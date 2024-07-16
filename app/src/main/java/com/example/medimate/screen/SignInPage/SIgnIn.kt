package com.example.medimate.screen.SignInPage

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.medimate.AllViewModel
import com.example.medimate.R
import com.example.medimate.State
import com.example.medimate.navigation.Routes

@Preview(showBackground = true)
@Composable
fun SignInScreen(
    navController: NavController = rememberNavController(),
    allViewModel: AllViewModel,
    applicationContext: Context,
) {

    //no ripple effect
    val interactionSource = remember { MutableInteractionSource() }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {

        //imagebox
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(0.7f)
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 95.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            modifier = Modifier.padding(start = 38.dp),
            text = "Welcome To",
            fontSize = 18.sp,
            color = Color(0xFF111111)
        )
        Text(
            modifier = Modifier.padding(start = 38.dp),
            text = "MediMate",
            fontSize = 33.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF010754)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 38.dp, end = 38.dp),
            text = stringResource(id = R.string.sign_in_text),
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFF111111)
        )

        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier.padding(start = 38.dp),
            text = "Login Here",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF111111)
        )
        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            placeholder = {
                Text(text = "Your Email", fontSize = 15.sp, color = Color(0xFF111111))
            },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = null)
            },
            maxLines = 1,
            textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF111111)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 38.dp),

            shape = RoundedCornerShape(15.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF111111)
            ),
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password, onValueChange = { password = it },
            placeholder = {
                Text(text = "Password", fontSize = 15.sp, color = Color(0xFF111111))
            },
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null)
            },
            maxLines = 1,
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
            textStyle = TextStyle(fontSize = 15.sp, color = Color(0xFF111111)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 38.dp),

            shape = RoundedCornerShape(15.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF111111)
            ),
        )



        Spacer(modifier = Modifier.height(28.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    //for noRippleEffect
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate(Routes.SignUp)
                    }
                    .padding(start = 38.dp),
                text = "Sign Up",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF111111)
            )
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            applicationContext,
                            "Fill All the Details",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        allViewModel.login(email, password)
                        when (allViewModel.state.value) {
                            State.SUCCESS.name -> {
                                navController.navigate(Routes.Home)
                                Toast.makeText(
                                    applicationContext,
                                    "Sign In Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            State.FAILED.name -> {
                                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }, modifier = Modifier
                    .width(150.dp)
                    .height(55.dp)
                    .padding(end = 38.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF111111),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 18.sp
                )
            }
        }

    }

}





