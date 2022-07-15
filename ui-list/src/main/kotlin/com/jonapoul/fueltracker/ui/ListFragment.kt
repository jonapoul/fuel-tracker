package com.jonapoul.fueltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jonapoul.common.ui.CommonFragment
import com.jonapoul.common.ui.collectFlow
import com.jonapoul.common.ui.dialogs.setSimpleNegativeButton
import com.jonapoul.common.ui.dialogs.setSimplePositiveButton
import com.jonapoul.common.ui.dialogs.showCautionDialog
import com.jonapoul.common.ui.view.hideIfTrue
import com.jonapoul.common.ui.view.showIfTrue
import com.jonapoul.common.ui.viewbinding.viewBinding
import com.jonapoul.fueltracker.domain.model.EntityField
import com.jonapoul.fueltracker.list.R
import com.jonapoul.fueltracker.list.databinding.DialogViewBinding
import com.jonapoul.fueltracker.list.databinding.DialogViewRowBinding
import com.jonapoul.fueltracker.list.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : CommonFragment(layout = R.layout.fragment_list, menu = null) {
    override val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
    }

    private fun initialiseRecyclerView() {
        val adapter = ListRecyclerAdapter(
            onClickView = ::viewItem,
            onClickEdit = ::editItem,
            onClickDelete = ::deleteItem,
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        collectFlow(viewModel.items) { items ->
            adapter.submitList(items)
            val isEmpty = items.isEmpty()
            binding.recyclerView.hideIfTrue(isEmpty)
            binding.noneFoundText.showIfTrue(isEmpty)
        }
    }

    private fun viewItem(entityId: Long) {
        viewLifecycleOwner.lifecycleScope.launch {
            /* Fetch the data from the DB, returning if it fails */
            val entity = viewModel.getEntity(entityId)
                ?: return@launch

            /* Inflate the view and add our data to the table layout */
            val inflater = LayoutInflater.from(context)
            val dialogBinding = DialogViewBinding.inflate(inflater)
            EntityField.values().forEach {
                val row = DialogViewRowBinding.inflate(inflater)
                row.title.setText(it.title)
                row.value.text = it.value.invoke(entity)
                dialogBinding.root.addView(row.root)
            }

            /* Show the dialog */
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.view_dialog_title)
                .setView(dialogBinding.root)
                .setSimplePositiveButton()
                .show()
        }
    }

    private fun editItem(entityId: Long) {
        viewModel.editItem(navController, entityId)
    }

    private fun deleteItem(entityId: Long) {
        showCautionDialog(
            message = getString(R.string.delete_dialog_message),
            title = getString(R.string.delete_dialog_title),
            extraConfig = {
                setSimplePositiveButton { viewModel.deleteItem(entityId) }
                setSimpleNegativeButton()
            },
        )
    }
}
