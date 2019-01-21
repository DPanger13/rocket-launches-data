package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.LaunchApi.Mode
import io.mercury.android.movies.data.movies.LaunchApi.PagedLaunchDto
import io.mercury.android.movies.data.movies.LaunchApi.PagedLaunchSummaryDto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal open class BackendSource @Inject constructor(private val api: LaunchApi) {

    open fun getUpcomingLaunches(offset: Int? = null): Single<PagedLaunchSummaryDto> {
        return api.getUpcomingLaunches(offset).subscribeOn(Schedulers.io())
    }

    open fun getLaunch(id: Int): Single<PagedLaunchDto> =
            api.getLaunch(Mode.VERBOSE.mode, id).subscribeOn(Schedulers.io())

}
