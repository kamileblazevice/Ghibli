package com.example.ghibli.ui.features.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.ghibli.ui.features.list.model.FilmListEvent
import com.example.ghibli.ui.helper.UiState
import com.example.ghibli.ui.shared.ErrorView
import com.example.ghibli.ui.shared.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.list_screen_title))
                },
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
                        viewModel.onEvent(FilmListEvent.OnReloadData)
                    }
                }

                is UiState.Success<*> -> {
                    FilmList(films = (state as UiState.Success<List<Film>>).data) { id ->
                        viewModel.onEvent(FilmListEvent.OnNavigateToDetailPage(id))
                    }
                }
            }
        }
    }
}

@Composable
fun FilmList(films: List<Film>, onFilmClicked: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(films) { film ->
            FilmItem(film = film) {
                onFilmClicked(film.id)
            }
            HorizontalDivider()
        }
    }
}

@Composable
fun FilmItem(film: Film, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(dimensionResource(R.dimen.margin_medium))
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
                .size(dimensionResource(R.dimen.list_image_size))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.margin_small)))
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.margin_medium)))

        Column {
            Text(
                text = film.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(id = R.string.list_item_director, film.director),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = stringResource(id = R.string.list_item_release_year, film.releaseDate),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmItemPreview() {
    FilmItem(
        film = Film(
            id = "id",
            title = "Title",
            description = "Description",
            image = "Image",
            director = "Director",
            releaseDate = "2018"
        )
    ) {}
}
