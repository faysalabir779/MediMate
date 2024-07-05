package com.example.medimate.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medimate.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpCompleteMsg(note: String = "") {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.complete),
            contentDescription = null,
            modifier = Modifier.scale(0.7f)
        )
        Text(text = "Your SigUp Process Is", fontSize = 18.sp, fontWeight = FontWeight.Light)
        Text(
            text = "Complete",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6671ff)
        )

        Spacer(modifier = Modifier.height(5.dp))
        if (note == stringResource(id = R.string.waiting_approval) || note == stringResource(id = R.string.blocked_success_text)) {
            Text(
                text = note, fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        } else {
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator( )
        }

    }
}