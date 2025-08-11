package com.ieum.domain.model.image

import java.io.File

sealed interface ImageSource {
    data class Local(val file: File) : ImageSource

    data class Remote(val url: String) : ImageSource
}