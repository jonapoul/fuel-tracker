package com.jonapoul.fueltracker.domain.model

import com.jonapoul.fueltracker.data.db.RefuelEntity

internal sealed class FetchEntityResult {
    data class Success(val entity: RefuelEntity) : FetchEntityResult()
    data class Failure(val errorMessage: String) : FetchEntityResult()
}
