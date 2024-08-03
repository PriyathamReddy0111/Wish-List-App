package com.example.wishlistapp.data

data class Wish(
    val id: Long = 0L,
    val title : String = "",
    val description: String = ""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Welcome to Wish List",
            description = "Wish List is great")
    )
}