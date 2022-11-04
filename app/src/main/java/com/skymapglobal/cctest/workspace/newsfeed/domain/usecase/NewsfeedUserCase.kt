package com.skymapglobal.cctest.workspace.newsfeed.domain.usecase

import arrow.core.Either
import com.skymapglobal.cctest.core.NoParamsUseCase
import com.skymapglobal.cctest.core.UseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo

class GetTopHeadingsUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    UseCase<Either<String, NewsResp>, NewsfeedQuery>() {
    override suspend fun execute(params: NewsfeedQuery) =
        newsfeedRepo.topHeadLines(params.page, params.category)
}

class GetDarkModeSettingUseCase(private val newsfeedRepo: NewsfeedRepo) :
    NoParamsUseCase<Boolean>() {
    override suspend fun execute(): Boolean = newsfeedRepo.getDarkModeSetting()
}

class SetDarkModeSettingUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    UseCase<Unit, Boolean>() {
    override suspend fun execute(params: Boolean) =
        newsfeedRepo.setDarkModeSetting(params)
}