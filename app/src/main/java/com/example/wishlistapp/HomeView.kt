package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current
    Scaffold(
        topBar = {AppBarView(title = "Wish List",{
            Toast.makeText(context, "Button Clicked",Toast.LENGTH_LONG).show()
        })},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .size(50.dp),
                onClick = {
                    Toast.makeText(context, "FAButton Clicked",Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route+ "/0L")
                          },
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.Light_Blue)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(27.dp))
            }
        },
        backgroundColor = Color.Black // Set the background color to black
    ){
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color to black
            .padding(it)){
            items(wishlist.value, key = {wish -> wish.id}){
                    wish ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart || it== DismissValue.DismissedToEnd){
                                viewModel.deleteWish(wish)
                            }
                            true
                        }
                    )
                
                SwipeToDismiss(
                    state = dismissState,
                    background = {},
                    directions = setOf(DismissDirection.EndToStart,DismissDirection.StartToEnd),
                    dismissThresholds = {FractionalThreshold(0.25f)},
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(route = Screen.AddScreen.route + "/$id")
                        }
                    }
                )
                

            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: ()-> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(start = 15.dp, top = 8.dp, end = 15.dp)
            .clickable {
                onClick()
            },
        elevation = 10.dp,
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = wish.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = wish.description,)
        }
    }
}
