package com.example.medimate.screen.DashBoard

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.AllViewModel
import com.example.medimate.R

@Composable
fun Dashboard(
    allViewModel: AllViewModel,
    applicationContext: Context
) {
    val savedData by allViewModel.preferenceData.collectAsState()

    var availableProducts = allViewModel.availableProducts.value
    Log.d("ffff", "Dashboard: $availableProducts")


    var dropDown by remember {
        mutableStateOf(false)
    }
    var quantityExceeds by remember { mutableStateOf(false) }

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf(0.0) }
    var productId by remember { mutableStateOf("0.0") }
    var productquantity by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf(0.0) }
    var productCategory by remember { mutableStateOf("") }
    var certified by remember { mutableStateOf(0) }
    var stock by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = true) {
        allViewModel.getAvailableProductsByUserId(savedData.userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(text = "Dashboard", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(18.dp))

        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(140.dp)
                    .background(
                        color = Color(0xFF111111), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.graph),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .fillMaxSize()
                        .align(Alignment.Center)
                )

            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .height(140.dp)
                    .background(
                        color = Color(0xFF111111), shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pie),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

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
                            .height(40.dp)
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
                availableProducts.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it.product_name)
                        },
                        onClick = {
                            productName = it!!.product_name
                            productPrice = it.price
                            productCategory = it.category
                            productId = it.product_id
                            stock = it.stock
                            productCategory = it.category
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
                quantityExceeds = (productquantity.toIntOrNull() ?: 0) >= stock
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

        totalAmount = if (productquantity.isNotEmpty()) {
            productPrice * productquantity.toDouble()
        } else {
            0.0 // Or any default value you want when quantity is empty
        }
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
                    var remainingStock = stock.toInt() - productquantity.toInt()
                    allViewModel.updateAvailableProducts(productId, remainingStock)
                    allViewModel.sell(
                        productId,
                        productquantity,
                        remainingStock.toString(),
                        totalAmount,
                        productPrice,
                        productName,
                        savedData.name,
                        savedData.userId,
                        applicationContext
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