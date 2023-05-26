package com.mhmd.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyView(modifier: Modifier = Modifier,message:String = stringResource(id = R.string.no_result)) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = "",
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth(.5f)
                    .align(Alignment.TopEnd)
                    .padding(top = 32.dp, end = 32.dp)
            )
        }


    }
}
