package br.com.povengenharia.fitlife

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val drawabledId: Int,
    @StringRes val textStringId: Int,
    val color: Int
)
