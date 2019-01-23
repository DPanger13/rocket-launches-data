package com.dpanger.android.launches.data.base.architecture

/**
 * Marker interface that serves documentation purposes for the repository pattern.
 *
 * A repository is a provider of data for an Android application and is one of the points of entry
 * to the data layer. It abstracts the entire data layer away from the Android application to
 * ensure decoupling. It should simply provide data and different ways to get it without any
 * revealing of logic or dependencies. Each repository has one or more sources of data (back-end
 * server, local cache, etc.) and manages how and when to use those sources.
 */
interface IRepository
