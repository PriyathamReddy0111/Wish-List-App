package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase
    val wishRepository by lazy {
        WishRepository(wishDao = database.wishdao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java,"wishlist.db").build()
    }


}