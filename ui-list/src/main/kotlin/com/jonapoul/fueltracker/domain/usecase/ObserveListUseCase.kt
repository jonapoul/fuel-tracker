package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.data.localisedFormatter
import com.jonapoul.fueltracker.domain.ListItem
import com.jonapoul.fueltracker.domain.model.EntityField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class ObserveListUseCase @Inject constructor(
    private val dao: RefuelDao,
) {
    private val entityField = MutableStateFlow(EntityField.CostTotal)

    val items: Flow<List<ListItem>>
        get() = combine(dao.getAll(), entityField) { entities, entityField ->
            entityField.sorting.invoke(entities).map { entity ->
                ListItem(
                    entityId = entity.id,
                    date = FORMATTER.format(entity.instant),
                    fieldTitle = entityField.title,
                    fieldValue = entityField.value.invoke(entity),
                )
            }
        }

    fun setSortingField(field: EntityField) {
        entityField.value = field
    }

    fun getSortingField(): EntityField =
        entityField.value

    private companion object {
        val FORMATTER = "EE dd MMM yyyy".localisedFormatter
    }
}
