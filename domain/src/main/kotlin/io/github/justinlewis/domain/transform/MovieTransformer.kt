package io.github.justinlewis.domain.transform

import io.github.justinlewis.data.remote.dto.MovieDTO
import io.github.justinlewis.domain.pojo.Movie

/**
 * Created  on 11/20/2018.
 */
fun MovieDTO.toMovie() = Movie(id, overview, title, posterPath ?: "")
