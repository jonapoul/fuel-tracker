package com.jonapoul.fueltracker.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

object DiffCallbacks {
    fun <T : Any> basic(): DiffUtil.ItemCallback<T> =
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                oldItem == newItem

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                oldItem == newItem
        }

    fun <T : Any, ID : Any> withId(itemId: T.() -> ID): DiffUtil.ItemCallback<T> =
        object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                oldItem.itemId() == newItem.itemId()

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                oldItem == newItem
        }
}
