package com.dpanger.android.launches.data.launches

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

internal open class BackendSource @Inject constructor(private val api: LaunchApi) {

    open fun getUpcomingLaunches(offset: Int? = null): Single<LaunchApi.PagedLaunchSummaryDto> {
        Timber.d("Fetching upcoming launches with offset: $offset")

        return api.getUpcomingLaunches(offset).subscribeOn(Schedulers.io())
    }

    open fun getLaunch(id: Int): Single<LaunchApi.PagedLaunchDto> {
        Timber.d("Fetching launch with ID: $id")

        return api.getLaunch(LaunchApi.Mode.VERBOSE.mode, id).subscribeOn(Schedulers.io())
    }

}
