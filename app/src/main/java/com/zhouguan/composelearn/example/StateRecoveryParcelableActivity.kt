package com.guan.jetpackcomposestate.example

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhouguan.composelearn.ui.theme.ComPoseLearnTheme
import kotlinx.parcelize.Parcelize

class StateRecoveryParcelableActivity : ComponentActivity() {
    @Parcelize
    data class City(
        val name: String,
        val country: String
    ) : Parcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComPoseLearnTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    private fun CityScreen(modifier: Modifier) {
        val (city, setCity) = rememberSaveable {
            mutableStateOf(City("Manchester", "UK"))
        }

//        val (city, setCity) = remember {
//            mutableStateOf(City("Manchester", "UK"))
//        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            TextButton(
                colors = ButtonDefaults.buttonColors(),
                onClick = { setCity(City("New York", "USA")) }
            ) {
                Text(text = "Click to change")
            }
            Text(text = city.name, modifier = modifier.padding(10.dp))
        }
    }
}