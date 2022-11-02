package com.skymapglobal.cctest.workspace.details.presentation

import androidx.lifecycle.ViewModel
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetDarkModeSettingUseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.SetDarkModeSettingUseCase

class DetailsViewModel(
    private val getDarkModeSettingUseCase: GetDarkModeSettingUseCase,
    private val setDarkModeSettingUseCase: SetDarkModeSettingUseCase,
) : ViewModel() {
    suspend fun getDarkMode(): Boolean = getDarkModeSettingUseCase.execute()
    suspend fun setDarkMode(mode: Boolean) = setDarkModeSettingUseCase.execute(mode)
}