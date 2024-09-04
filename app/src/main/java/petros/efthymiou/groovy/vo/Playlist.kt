package petros.efthymiou.groovy.vo

import petros.efthymiou.groovy.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.mipmap.playlist
)
