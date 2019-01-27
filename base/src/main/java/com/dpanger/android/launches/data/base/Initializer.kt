package com.dpanger.android.launches.data.base

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Responsible for initializing resources in the base module.
 *
 * [init] should be called at the earliest possible time in an app's [Application.onCreate]. Each
 * module that extends this base module should provide their own [Initializer].
 */
abstract class Initializer {

    @CallSuper
    open fun init(context: Context) {
        AndroidThreeTen.init(context)
    }

}