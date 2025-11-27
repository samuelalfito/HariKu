package com.hariku.feature_statistic.data.repository

import com.hariku.feature_home.data.local.MoodDao
import com.hariku.feature_home.data.local.MoodEntity
import com.hariku.feature_statistic.domain.model.StatisticData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.Calendar

class StatisticRepositoryImplTest {

    @Mock
    private lateinit var moodDao: MoodDao

    private lateinit var repository: StatisticRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = StatisticRepositoryImpl(moodDao)
    }

    @Test
    fun `getStatisticData returns correct calendar mood data`() = runTest {
        // Given
        val userId = "testUser"
        val year = 2024
        val month = 11 // December (0-indexed)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val mockMoods = listOf(
            MoodEntity(
                id = "1",
                userId = userId,
                moodType = "SENANG",
                date = "2024-12-01",
                timestamp = calendar.timeInMillis
            ),
            MoodEntity(
                id = "2",
                userId = userId,
                moodType = "SEDIH",
                date = "2024-12-02",
                timestamp = calendar.apply { set(Calendar.DAY_OF_MONTH, 2) }.timeInMillis
            )
        )

        `when`(moodDao.getAllMoods(userId)).thenReturn(mockMoods)

        // When
        val result = repository.getStatisticData(userId, year, month)

        // Then
        assertNotNull(result)
        assertEquals(2, result.calendarMoodData.size)
        assertEquals("SENANG", result.calendarMoodData[1])
        assertEquals("SEDIH", result.calendarMoodData[2])
    }

    @Test
    fun `getStatisticData calculates sentiment correctly`() = runTest {
        // Given
        val userId = "testUser"
        val year = 2024
        val month = 11

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val mockMoods = listOf(
            MoodEntity("1", userId, "SENANG", "2024-12-01", calendar.timeInMillis),
            MoodEntity("2", userId, "SENANG", "2024-12-02", calendar.timeInMillis),
            MoodEntity("3", userId, "SEDIH", "2024-12-03", calendar.timeInMillis),
            MoodEntity("4", userId, "BIASA", "2024-12-04", calendar.timeInMillis)
        )

        `when`(moodDao.getAllMoods(userId)).thenReturn(mockMoods)

        // When
        val result = repository.getStatisticData(userId, year, month)

        // Then
        // 2 positive (SENANG) out of 4 = 50%
        // 1 negative (SEDIH) out of 4 = 25%
        // 1 neutral (BIASA) out of 4 = 25%
        assertEquals(50f, result.sentimentData.positive, 0.1f)
        assertEquals(25f, result.sentimentData.negative, 0.1f)
        assertEquals(25f, result.sentimentData.neutral, 0.1f)
    }

    @Test
    fun `getStatisticData returns empty data when no moods exist`() = runTest {
        // Given
        val userId = "testUser"
        val year = 2024
        val month = 11

        `when`(moodDao.getAllMoods(userId)).thenReturn(emptyList())

        // When
        val result = repository.getStatisticData(userId, year, month)

        // Then
        assertTrue(result.calendarMoodData.isEmpty())
        assertEquals(0f, result.sentimentData.positive, 0.1f)
        assertEquals(0f, result.sentimentData.negative, 0.1f)
        assertEquals(0f, result.sentimentData.neutral, 0.1f)
        assertTrue(result.moodStatistics.isEmpty())
        assertTrue(result.weeklySentiments.isEmpty())
    }

    @Test
    fun `getStatisticData calculates mood statistics correctly`() = runTest {
        // Given
        val userId = "testUser"
        val year = 2024
        val month = 11

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val mockMoods = listOf(
            MoodEntity("1", userId, "SENANG", "2024-12-01", calendar.timeInMillis),
            MoodEntity("2", userId, "SENANG", "2024-12-02", calendar.timeInMillis),
            MoodEntity("3", userId, "SENANG", "2024-12-03", calendar.timeInMillis),
            MoodEntity("4", userId, "SEDIH", "2024-12-04", calendar.timeInMillis)
        )

        `when`(moodDao.getAllMoods(userId)).thenReturn(mockMoods)

        // When
        val result = repository.getStatisticData(userId, year, month)

        // Then
        assertEquals(2, result.moodStatistics.size)

        // First should be SENANG (most frequent)
        assertEquals("SENANG", result.moodStatistics[0].moodType)
        assertEquals(3, result.moodStatistics[0].count)
        assertEquals(75f, result.moodStatistics[0].percentage, 0.1f)

        // Second should be SEDIH
        assertEquals("SEDIH", result.moodStatistics[1].moodType)
        assertEquals(1, result.moodStatistics[1].count)
        assertEquals(25f, result.moodStatistics[1].percentage, 0.1f)
    }
}

