package io.ak1.nytimes.ui.screens.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import io.ak1.nytimes.R
import io.ak1.nytimes.model.Results
import io.ak1.nytimes.utility.timeAgo

@Composable
fun PostElementExpanded(results: State<Results>, modifier: Modifier) {
    val story = results.value
    val context = LocalContext.current
    LazyColumn(modifier) {
        val keepTagsHidden = true
        item {
            Card(
                elevation = 5.dp, modifier = Modifier
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberCoilPainter(
                        request = story.urlLarge,
                        previewPlaceholder = android.R.color.darker_gray,
                        fadeIn = true
                    ),
                    contentDescription = story.title ?: "empty",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(214.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(Modifier.padding(16.dp, 0.dp)) {

                Text(
                    text = story.title ?: "empty",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))

                Text(
                    text = story.abstractText ?: "empty",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = story.byline?.trim() ?: "",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )

                Spacer(Modifier.height(8.dp))
                Text(
                    text = story.publishedDate.timeAgo(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                )

                val tags = story.desFacet.trim().split(",")
                if (!keepTagsHidden) {
                    LazyRow(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    ) {
                        items(tags) {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                                    .wrapContentSize()
                                    .background(
                                        MaterialTheme.colors.secondary,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .padding(18.dp, 10.dp)
                            )

                        }


                    }
                }

            }

            Button(
                onClick = {
                    Intent(Intent.ACTION_VIEW, Uri.parse(story.shortUrl ?: "")).let {
                        context.startActivity(it)
                    }
                }, modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_external_link),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                    contentDescription = stringResource(id = R.string.open_in_browser),
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = stringResource(id = R.string.open_in_browser),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp, 0.dp, 16.dp, 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}