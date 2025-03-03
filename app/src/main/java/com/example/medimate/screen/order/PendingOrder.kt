package com.example.medimate.screen.order

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.API.response.GetAllOrderDetailsItem
import com.example.medimate.AllViewModel
import com.example.medimate.R

@Composable
fun PendingOrder(allViewModel: AllViewModel, applicationContext: Context) {

    val savedData by allViewModel.preferenceData.collectAsState()

    var hasPendingOrder by remember { mutableStateOf(false) }

    val allOrder = allViewModel.allOrder.value
    Log.d("ggggg", "PendingOrder: ${savedData.userId}")

    LaunchedEffect(key1 = true) {
        allViewModel.getAllOrderDetails()
    }

    Column {
        LazyColumn {
            itemsIndexed(allOrder.reversed()) { index, item ->
                if (item.user_id == savedData.userId) {
                    if (item.isApproved == 2) {
                        hasPendingOrder = true
                        MyOrder(item)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
        if (!hasPendingOrder){
            EmptyMessage(
                orderStatus = "Pending Order",
                painter = painterResource(id = R.drawable.face),
                note = stringResource(
                    id = R.string.pending_text
                )
            )
        }
    }


}

@Composable
fun MyOrder(item: GetAllOrderDetailsItem) {
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
                text = item.product_name,
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
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Category: ${item.category}",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Quantity: ${item.quantity}",
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
                    Text(
                        text = "Total Price: ৳ ${item.total_amount}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Row {
                        Text(text = "Date: ", color = Color.White, fontSize = 14.sp)
                        Text(
                            text = item.date_of_order_creation,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

        }
    }
}