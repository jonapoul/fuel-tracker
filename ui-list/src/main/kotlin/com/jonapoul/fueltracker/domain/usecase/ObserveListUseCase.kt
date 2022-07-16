package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.fueltracker.data.db.RefuelDao
import com.jonapoul.fueltracker.data.localisedFormatter
import com.jonapoul.fueltracker.domain.ListItem
import com.jonapoul.fueltracker.domain.ListPreferences
import com.jonapoul.fueltracker.domain.model.EntityField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class ObserveListUseCase @Inject constructor(
    private val dao: RefuelDao,
    private val preferences: ListPreferences,
) {
    val items: Flow<List<ListItem>>
        get() = combine(dao.getAll(), preferences.sortingFieldFlow) { entities, entityField ->
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
        preferences.sortingField = field
    }

    fun getSortingField(): EntityField =
        preferences.sortingField

    private companion object {
        val FORMATTER = "EE dd MMM yyyy".localisedFormatter
    }
}
