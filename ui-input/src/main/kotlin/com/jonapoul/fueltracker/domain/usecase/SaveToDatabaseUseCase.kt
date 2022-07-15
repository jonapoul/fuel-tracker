package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.common.di.IODispatcher
import com.jonapoul.common.ui.SnackbarFeed
import com.jonapoul.common.ui.SnackbarMessage
import com.jonapoul.fueltracker.data.DraftRefuelEntity
import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.domain.InputTextCreator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

internal class SaveToDatabaseUseCase @Inject constructor(
    @IODispatcher private val io: CoroutineDispatcher,
    private val dao: RefuelDao,
    private val textCreator: InputTextCreator,
    private val snackbarFeed: SnackbarFeed,
) {
    suspend fun saveToDatabase(draft: DraftRefuelEntity?): Boolean {
        val entity = draft?.toEntityOrNull()
        return if (entity == null) {
            Timber.e("Failed finalising entity $draft")
            snackbarFeed.add(
                SnackbarMessage.Warning(textCreator.failedFinalisingEntity),
            )
            false
        } else {
            withContext(io) {
                val id = dao.insert(entity)
                Timber.d("Successfully inserted $entity with id of $id")
            }
            true
        }
    }
}
