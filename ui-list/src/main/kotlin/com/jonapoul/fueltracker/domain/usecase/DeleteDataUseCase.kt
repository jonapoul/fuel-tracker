package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.common.di.IODispatcher
import com.jonapoul.common.ui.SnackbarFeed
import com.jonapoul.common.ui.SnackbarMessage
import com.jonapoul.common.ui.notifier.Notifier
import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.domain.ListTextCreator
import com.jonapoul.fueltracker.list.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

internal class DeleteDataUseCase @Inject constructor(
    @IODispatcher private val io: CoroutineDispatcher,
    private val scope: CoroutineScope,
    private val dao: RefuelDao,
    private val textCreator: ListTextCreator,
    private val snackbarFeed: SnackbarFeed,
) {
    suspend fun deleteItem(entityId: Long) {
        val entity = withContext(io) {
            dao.getWithId(entityId)
        } ?: run {
            snackbarFeed.add(SnackbarMessage.Caution(textCreator.dataAlreadyDeleted))
            return
        }

        val deletedRows = withContext(io) { dao.deleteById(entityId) }
        if (deletedRows != 1) {
            Timber.e("Should have deleted 1 row, actually deleted $deletedRows")
            snackbarFeed.add(
                SnackbarMessage.Warning(textCreator.failedDelete),
            )
        } else {
            onSuccess(entity)
        }
    }

    private suspend fun onSuccess(entity: RefuelEntity) {
        snackbarFeed.add(
            SnackbarMessage.Success(
                message = textCreator.succeededDelete(entity.instant),
                action = Notifier.Action(
                    actionText = R.string.undo_delete,
                    onClick = { undoDelete(entity) },
                ),
            ),
        )
    }

    private fun undoDelete(entity: RefuelEntity) {
        scope.launch(io) {
            dao.insert(entity)
            snackbarFeed.add(
                SnackbarMessage.Success(textCreator.deleteSuccessfullyUndone),
            )
        }
    }
}
