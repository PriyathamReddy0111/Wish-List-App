package com.example.wishlistapp

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    navController: NavController,
    viewModel: WishViewModel
) {
    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()


    LaunchedEffect(id) {
        if (id != 0L) {
            viewModel.getAWishById(id).collect { wish ->
                viewModel.wishTitleState = wish.title
                viewModel.wishDescriptionState = wish.description
            }
        } else {
            viewModel.wishTitleState = ""
            viewModel.wishDescriptionState = ""
        }
    }

    Scaffold (

        topBar = {
            AppBarView(title = if (id != 0L) "Update Wish"
                    else "Add Wish",
                onBackNavClicked = {
                    navController.navigateUp()
                }
            )
        },
        scaffoldState= scaffoldState,
        backgroundColor = Color.Black
    ){
        Column (
            modifier = Modifier
                .padding(it)
                .wrapContentSize()
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value= viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChanged(it)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Description",
                value= viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChanged(it)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() &&
                    viewModel.wishDescriptionState.isNotEmpty()){
                    if (id!=0L){
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()

                            )
                        )
                    }
                    else{

                        viewModel.addWish(
                            Wish(
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )

                    }
                }


                scope.launch {
//
                    if (viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()) {
                        navController.navigateUp()
                    }
                    else{
                        scaffoldState.snackbarHostState.showSnackbar("Enter empty fields")
                    }
                }
            },
                modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = if(id!=0L) "Update Wish"
                    else "Add Wish",
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String)-> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.White) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}

@Preview
@Composable
fun WishTestFieldPreview(){
    WishTextField(label = "text", value = "text", onValueChanged = {})
}








