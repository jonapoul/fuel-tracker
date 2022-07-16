package com.jonapoul.fueltracker.domain

import android.content.SharedPreferences
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.jonapoul.common.core.PrefPair
import com.jonapoul.common.domain.getString
import com.jonapoul.fueltracker.domain.model.EntityField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ListPreferences @Inject constructor(
    private val prefs: SharedPreferences,
    private val flowSharedPreferences: FlowSharedPreferences,
) {
    var sortingField: EntityField
        get() = EntityField.valueOf(prefs.getString(ENTITY_FIELD))
        set(value) = prefs.putIfNotNull(ENTITY_FIELD, value.name)

    val sortingFieldFlow: Flow<EntityField>
        get() = flowSharedPreferences.getString(ENTITY_FIELD)
            .asFlow()
            .map(EntityField::valueOf)

    companion object {
        val ENTITY_FIELD = PrefPair(key = "entity_field", default = EntityField.default.name)
    }
}
