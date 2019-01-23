package com.dpanger.android.launches.data.launches

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal open class BackendSource @Inject constructor(private val api: LaunchApi) {

    open fun getUpcomingLaunches(offset: Int? = null): Single<LaunchApi.PagedLaunchSummaryDto> {
        return api.getUpcomingLaunches(offset).subscribeOn(Schedulers.io())
    }

    open fun getLaunch(id: Int): Single<LaunchApi.PagedLaunchDto> =
            api.getLaunch(LaunchApi.Mode.VERBOSE.mode, id).subscribeOn(Schedulers.io())

}
