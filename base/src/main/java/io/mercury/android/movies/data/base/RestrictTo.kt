package io.mercury.android.movies.data.base

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class RestrictTo(val scope: Scope)

enum class Scope {

    /**
     * Use should be restricted to the class's module.
     *
     * Some situations need classes to be declared public (ie, database models, dependency injection
     * containers) but should not be used outside the module. This Scope can be used to document
     * that situation.
     */
    MODULE

}
