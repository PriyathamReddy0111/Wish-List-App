package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish

@Composable
fun HomeView(){
    val context = LocalContext.current
    Scaffold(
        topBar = {AppBarView(title = "Wish List",{
            Toast.makeText(context, "Button Clicked",Toast.LENGTH_LONG).show()
        })},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all =20.dp),
                onClick = { Toast.makeText(context, "FAButton Clicked",Toast.LENGTH_LONG).show() },
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.Light_Blue)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        backgroundColor = Color.Black // Set the background color to black
    ){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color to black
            .padding(it)){
            items(DummyWish.wishList){
                    wish -> WishItem(wish = wish) {

            }
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: ()-> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        elevation = 10.dp,
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}
