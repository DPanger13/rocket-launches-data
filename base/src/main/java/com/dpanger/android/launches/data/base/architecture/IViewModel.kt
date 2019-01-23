package com.dpanger.android.launches.data.base.architecture

/**
 * Marker interface that serves documentation purposes for the view model pattern.
 *
 * A view model is an observable container for a single piece of data (whether that data is a
 * composition of other view models/data or just a single piece of data). It is technically part of
 * the middle layer in an Android architecture but can also be part of the data layer if the point
 * of entry/point of abstraction needs to be higher. It manages the emission of said data as well as
 * any other relevant information (ie, whether "loading" or not) to any observers. A view model
 * should not contain references to the observers, but rather just emit data and relevant
 * information to them.
 */
interface IViewModel
