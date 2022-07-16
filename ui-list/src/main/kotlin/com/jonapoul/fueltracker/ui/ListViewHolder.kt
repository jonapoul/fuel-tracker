package com.jonapoul.fueltracker.ui

import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jonapoul.fueltracker.domain.ListItem
import com.jonapoul.fueltracker.list.R
import com.jonapoul.fueltracker.list.databinding.FragmentListItemBinding

internal class ListViewHolder(
    private val binding: FragmentListItemBinding,
    private val onClickView: (entityId: Long) -> Unit,
    private val onClickEdit: (entityId: Long) -> Unit,
    private val onClickDelete: (entityId: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: ListItem) {
        with(binding) {
            date.text = item.date
            fieldName.setText(item.fieldTitle)
            fieldValue.text = item.fieldValue

            menuButton.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.menu_list_item, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_view -> onClickView.invoke(item.entityId)
                        R.id.action_edit -> onClickEdit.invoke(item.entityId)
                        R.id.action_delete -> onClickDelete.invoke(item.entityId)
                        else -> return@setOnMenuItemClickListener false
                    }
                    true
                }
                popup.show()
            }
        }
    }
}
