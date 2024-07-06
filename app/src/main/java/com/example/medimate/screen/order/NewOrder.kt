package com.example.medimate.screen.order

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.AllViewModel
import com.example.medimate.R


@Composable
fun NewOrder(allViewModel: AllViewModel, applicationContext: Context) {

    val savedData by allViewModel.preferenceData.collectAsState()

    var data = allViewModel.data.value

    LaunchedEffect(key1 = true) {
        allViewModel.getALlProduct()
    }

    var dropDown by remember {
        mutableStateOf(false)
    }

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf(0.0) }
    var productquantity by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf(0.0) }
    var productId by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var certified by remember { mutableStateOf(0) }
    var stock by remember { mutableStateOf(0) }
    var category by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(0) }

    var quantityExceeds by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    color = Color(0xFF111111), shape = RoundedCornerShape(10.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = productName.ifEmpty { "Select Product" },
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(17.dp))
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(17.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Category: $productCategory",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Available Stock: $stock",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .height(40.dp) // Adjust height asneeded
                            .width(1.dp),
                        color = Color.White
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "Price: $productPrice", color = Color.White, fontSize = 14.sp)
                        Row {
                            Text(text = "Certified: ", color = Color.White, fontSize = 14.sp)
                            Text(
                                text = if (certified == 1) "Yes" else "No",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Drop Down Icon",
                        tint = Color(0xFF111111),
                        modifier = Modifier
                            .rotate(if (dropDown) 180f else 0f)
                            .clickable { dropDown = !dropDown }
                    )
                },
                placeholder = { Text(text = "Select Product") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF111111)
                ),

                )

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(color = Color.White),
                expanded = dropDown,
                onDismissRequest = {
                    dropDown = false
                },
            ) {
                data.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it!!.name)
                        },
                        onClick = {
                            productName = it!!.name
                            productPrice = it.price
                            productId = it.id.toString()
                            productCategory = it.category
                            stock = it.stack
                            certified = it.certified
                            category = it.category
                            dropDown = false
                        })
                }

            }
        }

        Spacer(modifier = Modifier.height(15.dp))


        OutlinedTextField(
            value = productquantity,
            onValueChange = {
                productquantity = it
                quantityExceeds = productquantity.toIntOrNull() ?: 0 >= stock
                totalAmount = if (productquantity.isNotEmpty() && !quantityExceeds) {
                    productPrice * productquantity.toDouble()
                } else {
                    0.0
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Enter Quantity")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF111111)
            )
        )

        if (quantityExceeds) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.quantity_exceeds),
                fontSize = 14.sp,
                color = Color(0xFFFF0808),
                modifier = Modifier.padding(horizontal = 3.dp)
            )

        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Single Price")
            Text(
                text = if (!quantityExceeds) {
                    "$$productPrice"
                } else {
                    "$ 0.0"
                }
            )
        }

        Spacer(modifier = Modifier.height(3.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Total Price", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                text = if (!quantityExceeds) {
                    "$$totalAmount"
                } else {
                    "$ 0.0"
                }, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (productName == "") {
                    Toast.makeText(
                        applicationContext,
                        "Please Select a Product",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (productquantity.isEmpty()) {
                    Toast.makeText(applicationContext, "Quantity is empty", Toast.LENGTH_SHORT)
                        .show()
                } else if (quantityExceeds) {
                    quantityExceeds = true
                    Toast.makeText(applicationContext, "Quantity Exceeds", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    allViewModel.addOrder(
                        applicationContext,
                        productId,
                        savedData.name,
                        savedData.userId,
                        savedData.address,
                        savedData.phone,
                        productName,
                        category,
                        totalAmount.toString(),
                        productquantity,
                        status,
                        productPrice.toString()
                    )
                    productName = ""
                    productPrice = 0.0
                    productquantity = ""
                    totalAmount = 0.0
                    stock = 0
                    productId = ""
                    productCategory = ""
                    certified = 0
                    Log.d("alldata", "NewOrder: ${savedData.pinCode}")
                }

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
            Text(text = "Order Now")
        }
    }
}
