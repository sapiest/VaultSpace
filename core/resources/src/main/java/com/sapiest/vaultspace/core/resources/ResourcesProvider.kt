package com.sapiest.vaultspace.core.resources

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getString(@StringRes resId: Int): String
    fun getColor(@ColorRes resId: Int): Int
    fun getDrawable(@DrawableRes resId: Int): Drawable?
}