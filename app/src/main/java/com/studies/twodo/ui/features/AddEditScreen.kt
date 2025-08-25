package com.studies.twodo.ui.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studies.twodo.ui.theme.TwodoTheme

@Composable
fun AddEditScreen() {
    AddEditScreenContent()
}

@Composable
fun AddEditScreenContent() {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Check, contentDescription = "Check")
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Title")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Description (Optional)")
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddEditContentPreview() {
    TwodoTheme {
        AddEditScreenContent()
    }
}