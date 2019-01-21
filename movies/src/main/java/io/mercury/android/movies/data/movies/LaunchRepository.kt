package io.mercury.android.movies.data.movies

import io.reactivex.Flowable
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

internal class LaunchRepository @Inject constructor(
        private val backendSource: BackendSource
) : ILaunchRepository {

    override fun getUpcomingLaunches(offset: Int?): Flowable<PagedLaunchSummary> =
            backendSource.getUpcomingLaunches(offset)
                    .toFlowable()
                    .map { it.toPagedLaunchSummary() }

    override fun getLaunch(id: Int): Flowable<Launch> =
            backendSource.getLaunch(id)
                    .toFlowable()
                    .map { it.toLaunch() }
}

internal fun LaunchApi.PagedLaunchSummaryDto.toPagedLaunchSummary(): PagedLaunchSummary {
    val launchSummaries = mutableListOf<LaunchSummary>()
    for (launchSummaryDto in launches) {
        val launchSummary = LaunchSummary(
                launchSummaryDto.id,
                launchSummaryDto.name,
                LocalDateTime.parse(launchSummaryDto.dateTime, LaunchApi.dateTimeFormatter)
        )
        launchSummaries.add(launchSummary)
    }

    return PagedLaunchSummary(
            offset,
            count,
            total,
            launchSummaries
    )
}

internal fun LaunchApi.PagedLaunchDto.toLaunch(): Launch {
    val numLaunches = launches.size
    if (numLaunches > 1) {
        val msg = "Received $numLaunches launches instead of just 1. Only returning first launch."
        Timber.w(msg)
    }

    val launchDto = launches[0]
    val missions = mutableListOf<Launch.Mission>()
    for (mission in launchDto.missions) {
        missions.add(Launch.Mission(mission.name))
    }

    return Launch(
            launchDto.id,
            launchDto.name,
            LocalDateTime.parse(launchDto.dateTime, LaunchApi.dateTimeFormatter),
            Launch.Location(launchDto.location.name),
            Launch.Rocket(launchDto.rocket.name),
            missions
    )
}
