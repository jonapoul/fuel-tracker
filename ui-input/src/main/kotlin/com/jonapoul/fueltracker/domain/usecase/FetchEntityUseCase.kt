package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.common.core.requireMessage
import com.jonapoul.common.di.IODispatcher
import com.jonapoul.common.ui.SnackbarFeed
import com.jonapoul.common.ui.SnackbarMessage
import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.domain.InputTextCreator
import com.jonapoul.fueltracker.domain.model.FetchEntityResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FetchEntityUseCase @Inject constructor(
    @IODispatcher private val io: CoroutineDispatcher,
    private val dao: RefuelDao,
    private val textCreator: InputTextCreator,
    private val snackbarFeed: SnackbarFeed,
) {
    suspend fun fetchEntity(entityId: Long): FetchEntityResult {
        return try {
            val entity = withContext(io) { dao.getWithId(entityId) }
            if (entity == null) {
                FetchEntityResult.Failure("No data found with the given ID: $entityId")
            } else {
                FetchEntityResult.Success(entity)
            }
        } catch (e: Exception) {
            FetchEntityResult.Failure(e.requireMessage())
        }.also {
            if (it is FetchEntityResult.Failure) {
                snackbarFeed.add(
                    SnackbarMessage.Warning(
                        textCreator.failedFetchingEntity(it.errorMessage),
                    ),
                )
            }
        }
    }
}
