package com.example.medimate.screen.DashBoard

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.AllViewModel
import com.example.medimate.navigation.OrderRoutes
import com.example.medimate.screen.order.ApprovedOrder
import com.example.medimate.screen.order.DeclinedOrder
import com.example.medimate.screen.order.NewOrder
import com.example.medimate.screen.order.PendingOrder
import kotlinx.coroutines.launch

@Composable
fun Inventory(allViewModel: AllViewModel, applicationContext: Context) {

    var newOrder by remember {
        mutableStateOf(true)
    }
    var pendingOrder by remember {
        mutableStateOf(false)
    }
    var approvedOrder by remember {
        mutableStateOf(false)
    }
    var declinedOrder by remember {
        mutableStateOf(false)
    }


    var orderState by remember {
        mutableStateOf(OrderRoutes.NewOrder)
    }

    var rowWidth by remember {
        mutableStateOf(0)
    }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val buttonWidth = 150.dp.toPx()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 15.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.16f)
        ) {
            Column {
                Text(text = "My Inventory", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            rowWidth =(coordinates.size.width)
                        }
                        .horizontalScroll(scrollState)
                )
                {
                    Button(
                        onClick = {
                            orderState = OrderRoutes.NewOrder
                            newOrder = true
                            pendingOrder = false
                            approvedOrder = false
                            declinedOrder = false
                            coroutineScope.launch {
                                scrollToCenter(scrollState, 0, rowWidth, buttonWidth)
                            }
                                  },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp)
                            .border(
                                width = if (newOrder && !pendingOrder && !approvedOrder && !declinedOrder) 0.dp else 2.dp,
                                color = Color(0xFF111111),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (newOrder && !pendingOrder && !approvedOrder && !declinedOrder) Color(
                                0xFF111111
                            ) else Color.Transparent,
                            contentColor = if (newOrder && !pendingOrder && !approvedOrder && !declinedOrder) Color.White else Color.Black
                        )
                    ) {
                        Text(text = "New Order")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = { orderState = OrderRoutes.Pending
                            newOrder = false
                            pendingOrder = true
                            approvedOrder = false
                            declinedOrder = false
                            coroutineScope.launch {
                                scrollToCenter(scrollState, 1, rowWidth, buttonWidth)
                            }},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp)
                            .border(
                                width = if (!newOrder && pendingOrder && !approvedOrder && !declinedOrder) 0.dp else 2.dp,
                                color = Color(0xFF111111),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!newOrder && pendingOrder && !approvedOrder && !declinedOrder) Color(
                                0xFF111111
                            ) else Color.Transparent,
                            contentColor = if (!newOrder && pendingOrder && !approvedOrder && !declinedOrder) Color.White else Color.Black
                        )
                    ) {
                        Text(text = "Pending Order")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = { orderState = OrderRoutes.Approved
                            newOrder = false
                            pendingOrder = false
                            approvedOrder = true
                            declinedOrder = false
                            coroutineScope.launch {
                                scrollToCenter(scrollState, 2, rowWidth, buttonWidth)
                            }},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp)
                            .border(
                                width = if (newOrder && !pendingOrder && approvedOrder && !declinedOrder) 0.dp else 2.dp,
                                color = Color(0xFF111111),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!newOrder && !pendingOrder && approvedOrder && !declinedOrder) Color(
                                0xFF111111
                            ) else Color.Transparent,
                            contentColor = if (!newOrder && !pendingOrder && approvedOrder && !declinedOrder) Color.White else Color.Black
                        )
                    ) {
                        Text(text = "Approved Order")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = { orderState= OrderRoutes.DeclinedOrder
                            newOrder = false
                            pendingOrder = false
                            approvedOrder = false
                            declinedOrder = true
                            coroutineScope.launch {
                                scrollToCenter(scrollState, 3, rowWidth, buttonWidth)
                            }},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(48.dp)
                            .border(
                                width = if (!newOrder && !pendingOrder && !approvedOrder && declinedOrder) 0.dp else 2.dp,
                                color = Color(0xFF111111),
                                shape = RoundedCornerShape(50.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!newOrder && !pendingOrder && !approvedOrder && declinedOrder) Color(
                                0xFF111111
                            ) else Color.Transparent,
                            contentColor = if (!newOrder && !pendingOrder && !approvedOrder && declinedOrder) Color.White else Color.Black
                        )
                    ) {
                        Text(text = "Declined Order")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight().padding(top = 15.dp)
        ) {

            when (orderState) {
                OrderRoutes.NewOrder -> {
                    NewOrder(allViewModel, applicationContext)
                }

                OrderRoutes.Pending -> {
                    PendingOrder(allViewModel, applicationContext)
                }

                OrderRoutes.Approved -> {
                    ApprovedOrder(allViewModel, applicationContext)
                }

                OrderRoutes.DeclinedOrder -> {
                    DeclinedOrder(allViewModel, applicationContext)
                }
            }
        }

    }
}

suspend fun scrollToCenter(
    scrollState: ScrollState,
    index: Int,
    rowWidth: Int,
    buttonWidth: Float
) {
    val position = (index * (buttonWidth + 5.dp.toPx())).toInt()
    val offset = (rowWidth / 2) - (buttonWidth / 2)
    scrollState.animateScrollTo((position - offset).toInt())
}

fun Dp.toPx(): Float {
    return this.value * 2.625f // Approximate density conversion for preview
}