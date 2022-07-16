package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.common.di.IODispatcher
import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.data.db.RefuelEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FetchDataUseCase @Inject constructor(
    @IODispatcher private val io: CoroutineDispatcher,
    private val dao: RefuelDao,
) {
    suspend fun fetchEntity(entityId: Long): RefuelEntity? =
        withContext(io) { dao.getWithId(entityId) }
}
