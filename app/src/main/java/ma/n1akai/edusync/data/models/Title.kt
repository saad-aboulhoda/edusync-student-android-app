package ma.n1akai.edusync.data.models

import androidx.navigation.NavDirections

data class Title(
    val title: String,
    val icon: Int,
    var navDirections: NavDirections? = null
)