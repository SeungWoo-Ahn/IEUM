package com.ieum.presentation.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.ieum.design_system.icon.UpIcon
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.textfield.IEUMTextField
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.theme.Gray300
import com.ieum.design_system.theme.Lime400
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.R
import com.ieum.presentation.model.post.CommentUiModel

@Composable
fun CommentListArea(
    modifier: Modifier = Modifier,
    commentList: LazyPagingItems<CommentUiModel>,
    onMenu: (Int, DropDownMenu) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
    ) {
        when (commentList.loadState.refresh) {
            LoadState.Loading -> IEUMLoadingComponent()
            is LoadState.Error -> ErrorComponent(onRetry = commentList::retry)
            is LoadState.NotLoading -> {
                if (commentList.itemSnapshotList.isEmpty()) {
                    EmptyComment()
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(all = 28.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        items(
                            count = commentList.itemCount,
                            key = commentList.itemKey { it.id }
                        ) {
                            commentList[it]?.let { comment ->
                                CommentItem(
                                    comment = comment,
                                    onMenu = onMenu,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyComment(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.empty_comment_description),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun CommentItem(
    modifier: Modifier = Modifier,
    comment: CommentUiModel,
    onMenu: (Int, DropDownMenu) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = comment.nickname,
                style = MaterialTheme.typography.titleMedium,
            )
            IEUMDropDownMenu(
                isMine = comment.isMine,
                onMenu = { onMenu(comment.id, it) },
            )
        }
        Text(
            text = comment.content,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W400,
        )
    }
}

@Composable
fun TypeCommentArea(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
    postEnabled: Boolean,
    onPost: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Gray300,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = screenPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IEUMTextField(
                modifier = Modifier.weight(1f),
                state = state,
                placeHolder = stringResource(R.string.comment_placeholder),
                singleLine = false,
                textStyle = MaterialTheme.typography.titleLarge,
                innerPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Unspecified,
            )
            PostCommentButton(
                enabled = postEnabled,
                onClick = onPost,
            )
        }
    }
}

@Composable
private fun PostCommentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Lime400,
            disabledContentColor = Slate300,
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) Lime500 else Slate300
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        onClick = onClick,
    ) {
        UpIcon()
    }
}