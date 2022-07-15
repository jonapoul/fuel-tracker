package com.jonapoul.fueltracker.domain

import android.content.Context
import com.jonapoul.common.domain.TextCreator
import com.jonapoul.fueltracker.data.localisedFormatter
import com.jonapoul.fueltracker.list.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.threeten.bp.Instant
import javax.inject.Inject

internal class ListTextCreator @Inject constructor(
    @ApplicationContext context: Context,
) : TextCreator(context) {
    val failedDelete: String
        get() = fromRes(R.string.delete_failed)

    val dataAlreadyDeleted: String
        get() = fromRes(R.string.data_already_deleted)

    val deleteSuccessfullyUndone: String
        get() = fromRes(R.string.delete_undone)

    fun succeededDelete(instant: Instant): String =
        context.getString(
            R.string.delete_succeeded,
            DATE_FORMATTER.format(instant),
        )

    private companion object {
        val DATE_FORMATTER = "EE dd MMM yyyy".localisedFormatter
    }
}
