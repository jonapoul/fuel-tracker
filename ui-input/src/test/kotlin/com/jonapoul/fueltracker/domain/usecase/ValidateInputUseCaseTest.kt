package com.jonapoul.fueltracker.domain.usecase

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class ValidateInputUseCaseTest {
    private lateinit var useCase: ValidateInputUseCase

    @Before
    fun before() {
        useCase = ValidateInputUseCase()
    }

    @Test
    fun `Validate distance`() {
        assertCall(useCase::validateDistance, input = "", isValid = false)
        assertCall(useCase::validateDistance, input = "1", isValid = true)
        assertCall(useCase::validateDistance, input = "11", isValid = true)
        assertCall(useCase::validateDistance, input = "1.", isValid = true)
        assertCall(useCase::validateDistance, input = "1.1", isValid = true)
        assertCall(useCase::validateDistance, input = "1.11", isValid = true)
        assertCall(useCase::validateDistance, input = "1.111", isValid = true)
        assertCall(useCase::validateDistance, input = "1.1111", isValid = false)
        assertCall(useCase::validateDistance, input = ".1111", isValid = false)
        assertCall(useCase::validateDistance, input = ".111", isValid = true)
        assertCall(useCase::validateDistance, input = ".11", isValid = true)
        assertCall(useCase::validateDistance, input = ".1", isValid = true)
        assertCall(useCase::validateDistance, input = "-1", isValid = false)
        assertCall(useCase::validateDistance, input = "-1.1", isValid = false)
        assertCall(useCase::validateDistance, input = "-.1", isValid = false)
    }

    @Test
    fun `Validate mileage`() {
        assertCall(useCase::validateMileage, input = "", isValid = false)
        assertCall(useCase::validateMileage, input = "1", isValid = true)
        assertCall(useCase::validateMileage, input = "11", isValid = true)
        assertCall(useCase::validateMileage, input = "1.", isValid = true)
        assertCall(useCase::validateMileage, input = "1.1", isValid = true)
        assertCall(useCase::validateMileage, input = "1.11", isValid = false)
        assertCall(useCase::validateMileage, input = "1.111", isValid = false)
        assertCall(useCase::validateMileage, input = "1.1111", isValid = false)
        assertCall(useCase::validateMileage, input = ".1111", isValid = false)
        assertCall(useCase::validateMileage, input = ".111", isValid = false)
        assertCall(useCase::validateMileage, input = ".11", isValid = false)
        assertCall(useCase::validateMileage, input = ".1", isValid = true)
        assertCall(useCase::validateMileage, input = "-1", isValid = false)
        assertCall(useCase::validateMileage, input = "-1.1", isValid = false)
        assertCall(useCase::validateMileage, input = "-.1", isValid = false)
    }

    @Test
    fun `Validate speed`() {
        assertCall(useCase::validateSpeed, input = "", isValid = false)
        assertCall(useCase::validateSpeed, input = "1", isValid = true)
        assertCall(useCase::validateSpeed, input = "11", isValid = true)
        assertCall(useCase::validateSpeed, input = "1.", isValid = true)
        assertCall(useCase::validateSpeed, input = "1.1", isValid = true)
        assertCall(useCase::validateSpeed, input = "1.11", isValid = false)
        assertCall(useCase::validateSpeed, input = "1.111", isValid = false)
        assertCall(useCase::validateSpeed, input = "1.1111", isValid = false)
        assertCall(useCase::validateSpeed, input = ".1111", isValid = false)
        assertCall(useCase::validateSpeed, input = ".111", isValid = false)
        assertCall(useCase::validateSpeed, input = ".11", isValid = false)
        assertCall(useCase::validateSpeed, input = ".1", isValid = true)
        assertCall(useCase::validateSpeed, input = "-1", isValid = false)
        assertCall(useCase::validateSpeed, input = "-1.1", isValid = false)
        assertCall(useCase::validateSpeed, input = "-.1", isValid = false)
    }

    @Test
    fun `Validate total cost`() {
        assertCall(useCase::validateTotalCost, input = "", isValid = false)
        assertCall(useCase::validateTotalCost, input = "1", isValid = true)
        assertCall(useCase::validateTotalCost, input = "11", isValid = true)
        assertCall(useCase::validateTotalCost, input = "1.", isValid = true)
        assertCall(useCase::validateTotalCost, input = "1.1", isValid = true)
        assertCall(useCase::validateTotalCost, input = "1.11", isValid = true)
        assertCall(useCase::validateTotalCost, input = "1.111", isValid = false)
        assertCall(useCase::validateTotalCost, input = "1.1111", isValid = false)
        assertCall(useCase::validateTotalCost, input = ".1111", isValid = false)
        assertCall(useCase::validateTotalCost, input = ".111", isValid = false)
        assertCall(useCase::validateTotalCost, input = ".11", isValid = true)
        assertCall(useCase::validateTotalCost, input = ".1", isValid = true)
        assertCall(useCase::validateTotalCost, input = "-1", isValid = false)
        assertCall(useCase::validateTotalCost, input = "-1.1", isValid = false)
        assertCall(useCase::validateTotalCost, input = "-.1", isValid = false)
    }

    @Test
    fun `Validate cost per`() {
        assertCall(useCase::validateCostPer, input = "", isValid = false)
        assertCall(useCase::validateCostPer, input = "1", isValid = true)
        assertCall(useCase::validateCostPer, input = "11", isValid = true)
        assertCall(useCase::validateCostPer, input = "1.", isValid = true)
        assertCall(useCase::validateCostPer, input = "1.1", isValid = true)
        assertCall(useCase::validateCostPer, input = "1.11", isValid = true)
        assertCall(useCase::validateCostPer, input = "1.111", isValid = true)
        assertCall(useCase::validateCostPer, input = "1.1111", isValid = false)
        assertCall(useCase::validateCostPer, input = ".1111", isValid = false)
        assertCall(useCase::validateCostPer, input = ".111", isValid = true)
        assertCall(useCase::validateCostPer, input = ".11", isValid = true)
        assertCall(useCase::validateCostPer, input = ".1", isValid = true)
        assertCall(useCase::validateCostPer, input = "-1", isValid = false)
        assertCall(useCase::validateCostPer, input = "-1.1", isValid = false)
        assertCall(useCase::validateCostPer, input = "-.1", isValid = false)
    }

    private fun <T> assertCall(methodCall: (String) -> T?, input: String, isValid: Boolean) {
        val result = methodCall.invoke(input)
        println("Input = $input, result = $result, should be valid = $isValid")
        assertEquals(
            expected = isValid,
            actual = result != null,
        )
    }
}
