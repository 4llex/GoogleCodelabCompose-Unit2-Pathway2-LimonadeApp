package com.example.lemonadecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadecompose.ui.theme.LemonadeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeComposeTheme {
                LemonApp()
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    LemonApp()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonApp() {
    val imageResource: Int
    val imgContentDescription: Int
    val txtResource: Int

    var squeezeCount by remember { mutableStateOf(0) }
    var currentStep by remember { mutableStateOf(1) }

    when (currentStep) {
        1 -> {
            imageResource = R.drawable.lemon_tree
            imgContentDescription = R.string.lemon_tree_content_description
            txtResource = R.string.tap_the_lemon
            squeezeCount = (2..4).random()
        }
        2 -> {
            imageResource = R.drawable.lemon_squeeze
            imgContentDescription = R.string.lemon_content_description
            txtResource = R.string.keep_tapping
        }
        3 -> {
            imageResource = R.drawable.lemon_drink
            imgContentDescription = R.string.glass_of_lemonade_content_description
            txtResource = R.string.tap_the_lemonade
        }
        else -> {
            imageResource = R.drawable.lemon_restart
            imgContentDescription = R.string.empty_glass_content_description
            txtResource = R.string.tap_the_empty
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        if (currentStep == 1) {
                            currentStep = currentStep.plus(1)
                        } else if (currentStep == 2 && squeezeCount != 1) {
                            squeezeCount = squeezeCount.dec()
                            currentStep = 2
                        } else if (currentStep == 2 || currentStep == 3) {
                            currentStep = currentStep.plus(1)
                        } else {
                            currentStep = 1
                        }
                    },
                    border = BorderStroke(4.dp, Color.DarkGray),
                    shape = RoundedCornerShape(15),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFd2ead5)),
                    content = {
                        Image(
                            painter = painterResource(id = imageResource),
                            contentDescription = stringResource(id = imgContentDescription)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = txtResource, squeezeCount),
                    fontSize = 18.sp
                )
            }
        }
    }
}