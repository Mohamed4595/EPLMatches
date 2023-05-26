package com.mhmd.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> List<T>.PaginatedLazyColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    showEmptyView: Boolean = true,
    onLoadMore: () -> Unit,
    content: @Composable LazyListScope.(T, Int) -> Unit,
    canLoadMore: Boolean
) {


    val contentList = this
    if (showEmptyView && contentList.isEmpty())
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            item { EmptyView(modifier = modifier) }
        }
    else
        LazyColumn(
            modifier = modifier,
            state = listState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            itemsIndexed(contentList) { index, element ->
                Row(Modifier.padding(contentPadding)) {
                    this@LazyColumn.content(element, index)
                }
            }
            item {
                val isScrollToEnd by remember {
                    derivedStateOf {
                        (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1)
                                && listState.isScrollInProgress
                    }
                }
                if (isScrollToEnd && canLoadMore) {
                    LoadingItem(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    onLoadMore.invoke()
                }
            }
        }


}
