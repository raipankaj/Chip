package com.example.materialchip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.component.jetchip.Chip
import com.component.jetchip.data.ChipItem
import com.component.jetchip.data.ChipType
import com.example.materialchip.ui.theme.MaterialChipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialChipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var chipSelectedLabel by remember {
                        mutableStateOf("")
                    }

                    val chipFilterItem = listOf(
                        ChipItem("Account", ChipType.Filter()),
                        ChipItem("Search", ChipType.Filter()),
                        ChipItem("Filter", ChipType.Filter())
                    )

                    val chipAssistItem = listOf(
                        ChipItem("Account", ChipType.Assist(Icons.Default.AccountBox)),
                        ChipItem("Search", ChipType.Assist(Icons.Default.Search)),
                        ChipItem("Filter", ChipType.Assist(Icons.Default.List))
                    )

                    val chipSuggestionItem = listOf(
                        ChipItem("Account", ChipType.Suggestion),
                        ChipItem("Search", ChipType.Suggestion),
                        ChipItem("Filter", ChipType.Suggestion)
                    )

                    Column {
                        Text(text = "Filter Type Chip", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
                        Chip(chipFilterItem) { selectedLabel ->
                            chipSelectedLabel = selectedLabel
                        }

                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(text = "Assist Type Chip", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
                        Chip(labels = chipAssistItem) {

                        }

                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(text = "Suggestion Type Chip", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
                        Chip(labels = chipSuggestionItem) {

                        }
                    }

                    when(chipSelectedLabel) {
                        "Account" -> {
                            //Toast.makeText(LocalContext.current, "Yes", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}

