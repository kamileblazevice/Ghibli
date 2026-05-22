package com.example.ghibli.ui.features.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ghibli.R
import com.example.ghibli.data.model.Film
import com.example.ghibli.ui.features.detail.model.FilmDetailEvent
import com.example.ghibli.ui.helper.UiState
import com.example.ghibli.ui.shared.ErrorView
import com.example.ghibli.ui.shared.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailScreen(
    filmId: String,
    viewModel: FilmDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val film = (state as? UiState.Success<Film>)?.data

    LaunchedEffect(Unit) {
        viewModel.getFilmDetail(filmId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = film?.title ?: stringResource(R.string.detail_screen_title),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(FilmDetailEvent.OnNavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detail_back_icon_description),
                        )
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            when (state) {
                UiState.Loading -> {
                    LoadingView()
                }

                is UiState.Error -> {
                    ErrorView {
                        viewModel.onEvent(
                            FilmDetailEvent.OnReloadData(filmId)
                        )
                    }
                }

                is UiState.Success -> {
                    FilmDetails(film = (state as UiState.Success<Film>).data)
                }
            }
        }
    }
}

@Composable
fun FilmDetails(film: Film) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.margin_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(film.image)
                .crossfade(true)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .fallback(R.drawable.image_error)
                .build(),
            contentDescription = film.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.detail_image_size))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.margin_small))),
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_medium)))

        Text(text = film.title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = stringResource(R.string.detail_item_director, film.director),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(
                R.string.detail_item_release_year,
                film.releaseDate
            ),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_medium)))

        Text(text = film.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmDetailsPreview() {
    FilmDetails(
        film = Film(
            id = "id",
            title = "Title",
            description = "Description",
            image = "Image",
            director = "Director",
            releaseDate = "2018"
        ),
    )
}
