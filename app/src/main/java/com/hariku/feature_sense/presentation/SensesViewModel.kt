package com.hariku.feature_sense.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hariku.feature_sense.domain.model.SenseStep
import com.hariku.feature_sense.domain.model.SenseStepsData

class SensesViewModel : ViewModel() {
    
    private val steps = SenseStepsData.getAllSteps()
    
    var currentStepIndex by mutableIntStateOf(0)
        private set
    
    val currentStep: SenseStep?
        get() = steps.getOrNull(currentStepIndex)
    
    val isCompleted: Boolean
        get() = currentStepIndex >= steps.size
    
    val progress: Float
        get() = (currentStepIndex + 1).toFloat() / SenseStepsData.TOTAL_STEPS.toFloat()
    
    val progressText: String
        get() = if (isCompleted) {
            "5/5"
        } else {
            "${currentStepIndex + 1}/5"
        }
    
    fun nextStep() {
        if (currentStepIndex < steps.size) {
            currentStepIndex++
        }
    }
    
    fun resetToFirstStep() {
        currentStepIndex = 0
    }
}