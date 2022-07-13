package com.jonapoul.fueltracker.data.db

import app.cash.turbine.test
import com.jonapoul.common.test.CoroutineRule
import com.jonapoul.common.test.db.RoomDatabaseRule
import com.jonapoul.fueltracker.data.model.miles
import com.jonapoul.fueltracker.data.model.mpg
import com.jonapoul.fueltracker.data.model.mph
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
internal class RefuelDaoTest {
    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val databaseRule = RoomDatabaseRule(FuelTrackerDatabase::class)

    private lateinit var refuelDao: RefuelDao

    @Before
    fun before() {
        refuelDao = databaseRule.database.refuelDao()
    }

    @Test
    fun `Inserting into empty database`() = runTest {
        refuelDao.getAll().test {
            /* Given no items in the database */
            assertEquals(
                expected = emptyList(),
                actual = awaitItem()
            )

            /* When an entity is inserted */
            val insertedId = refuelDao.insert(EXAMPLE_ENTITY)

            /* Then a single item is emitted */
            assertEquals(expected = 1L, actual = insertedId)
            assertEquals(
                expected = listOf(EXAMPLE_ENTITY),
                actual = awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Inserting into database with one item`() = runTest {
        refuelDao.getAll().test {
            /* Given one item in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())
            refuelDao.insert(EXAMPLE_ENTITY)
            assertEquals(expected = listOf(EXAMPLE_ENTITY), actual = awaitItem())

            /* When a second entity is inserted with a different ID */
            val differentEntity = EXAMPLE_ENTITY.copy(id = 2L)
            refuelDao.insert(differentEntity)

            /* Then both items are emitted */
            assertEquals(
                expected = listOf(EXAMPLE_ENTITY, differentEntity),
                actual = awaitItem().sortedBy { it.id }
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Reinserting two entities with the same ID`() = runTest {
        refuelDao.getAll().test {
            /* Given one item in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())
            refuelDao.insert(EXAMPLE_ENTITY)
            assertEquals(expected = listOf(EXAMPLE_ENTITY), actual = awaitItem())

            /* When a second entity is inserted with different values but the same ID */
            val differentEntity = EXAMPLE_ENTITY.copy(distanceDriven = 100.miles)
            refuelDao.insert(differentEntity)

            /* Then the latter entity is emitted */
            assertEquals(
                expected = listOf(differentEntity),
                actual = awaitItem().sortedBy { it.id }
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Deleting when nothing is in the database`() = runTest {
        refuelDao.getAll().test {
            /* Given no items in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())

            /* When we attempt to delete a non-existent entity */
            val numDeleted = refuelDao.deleteById(id = 1L)

            /* Then nothing was deleted */
            assertEquals(expected = 0, actual = numDeleted)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Deleting one item`() = runTest {
        refuelDao.getAll().test {
            /* Given one item in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())
            refuelDao.insert(EXAMPLE_ENTITY)
            assertEquals(expected = listOf(EXAMPLE_ENTITY), actual = awaitItem())

            /* When we attempt to delete a non-existent entity */
            val numDeleted = refuelDao.deleteById(id = 1L)

            /* Then one item deleted and nothing is in the database */
            assertEquals(expected = 1, actual = numDeleted)
            assertEquals(expected = emptyList(), actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Get latest with nothing in the database`() = runTest {
        refuelDao.getAll().test {
            /* Given nothing in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())

            /* When we fetch the latest */
            val latest = refuelDao.getLatest()

            /* Then the result was null */
            assertEquals(expected = null, actual = latest)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Get latest with one item in the database`() = runTest {
        refuelDao.getAll().test {
            /* Given one row in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())
            refuelDao.insert(EXAMPLE_ENTITY)
            assertEquals(expected = listOf(EXAMPLE_ENTITY), actual = awaitItem())

            /* When we fetch the latest */
            val latest = refuelDao.getLatest()

            /* Then the result was that one item */
            assertEquals(expected = EXAMPLE_ENTITY, actual = latest)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Get latest with multiple items in the database`() = runTest {
        refuelDao.getAll().test {
            /* Given 20 rows in the database */
            assertEquals(expected = emptyList(), actual = awaitItem())
            val baseTime = EXAMPLE_ENTITY.time
            val tenMinutes = Duration.ofMinutes(10)
            val entities = List(size = 20) {
                EXAMPLE_ENTITY.copy(
                    id = it.toLong() + 1L,
                    time = baseTime + (tenMinutes.multipliedBy(it.toLong()))
                )
            }
            refuelDao.insertAll(entities)
            assertEquals(expected = entities, actual = awaitItem())

            /* When we fetch the latest */
            val latest = refuelDao.getLatest()

            /* Then the result was the item with the latest time */
            val latestInstant = entities.maxOf { it.time }
            assertEquals(expected = latestInstant, actual = latest?.time)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private companion object {
        val TIME = Instant.parse("2022-01-02T01:23:45.678Z")!!
        val DISTANCE_DRIVEN = 500.miles
        val DISTANCE_REMAINING = 50.miles
        val MILEAGE = 50.mpg
        val SPEED = 50.mph
        const val VENDOR = "SAINSBURYS"
        const val TOWN = "TOWNSVILLE"

        val EXAMPLE_ENTITY = RefuelEntity(
            id = 1L,
            time = TIME,
            distanceDriven = DISTANCE_DRIVEN,
            distanceRemaining = DISTANCE_REMAINING,
            mileage = MILEAGE,
            averageSpeed = SPEED,
            vendor = VENDOR,
            town = TOWN
        )
    }
}
