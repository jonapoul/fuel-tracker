package com.jonapoul.fueltracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.jonapoul.fueltracker.domain.ListItem
import com.jonapoul.fueltracker.list.databinding.FragmentListItemBinding

internal class ListRecyclerAdapter(
    private val onClickView: (entityId: Long) -> Unit,
    private val onClickEdit: (entityId: Long) -> Unit,
    private val onClickDelete: (entityId: Long) -> Unit,
) : ListAdapter<ListItem, ListViewHolder>(DiffCallbacks.withId { entityId }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding, onClickView, onClickEdit, onClickDelete)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}
