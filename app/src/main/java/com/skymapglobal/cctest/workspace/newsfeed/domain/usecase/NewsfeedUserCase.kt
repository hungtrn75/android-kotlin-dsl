package com.skymapglobal.cctest.workspace.newsfeed.domain.usecase

import arrow.core.Either
import com.skymapglobal.cctest.core.UseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo

class GetTopHeadingsUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    UseCase<Either<String, NewsResp>, NewsfeedQuery>() {
    override suspend fun execute(params: NewsfeedQuery) =
        newsfeedRepo.topHeadLines(params.page, params.q)
}

class GetNewsUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    UseCase<Either<String, NewsResp>, NewsfeedQuery>() {
    override suspend fun execute(params: NewsfeedQuery) =
        newsfeedRepo.everything(params.page, params.q)
}

class SetDarkModeSettingUsecase