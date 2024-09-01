package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

object Texts {
    @Composable
    fun H1(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h1,
        )
    }

    @Composable
    fun H2(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h2,
        )
    }

    @Composable
    fun H3(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h3,
        )
    }

    @Composable
    fun H4(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h4,
        )
    }

    @Composable
    fun H5(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h5,
        )
    }

    @Composable
    fun H6(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h6,
        )
    }

    @Composable
    fun Subtitle1(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
        )
    }

    @Composable
    fun Subtitle2(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2,
        )
    }

    @Composable
    fun Body1(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
        )
    }

    @Composable
    fun Body2(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2,
        )
    }

    @Composable
    fun Button(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.button,
        )
    }

    @Composable
    fun Caption(
        text: String,
        color: Color = Color.Unspecified,
        maxLines: Int = Int.MAX_VALUE,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            modifier = modifier,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview
@Composable
private fun Texts_Preview() {
    Column {
        Texts.H1("H1")
        Texts.H2("H2")
        Texts.H3("H3")
        Texts.H4("H4")
        Texts.H5("H5")
        Texts.H6("H6")
        Texts.Subtitle1("Subtitle1")
        Texts.Subtitle2("Subtitle2")
        Texts.Body1("Body1")
        Texts.Body2("Body2")
        Texts.Button("Button")
        Texts.Caption("Caption")
    }
}
