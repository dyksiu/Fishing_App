import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fishing_app.R

@Composable
fun FishList() {
    val fishList = listOf(
        R.string.fish_1,
        R.string.fish_2,
        R.string.fish_3,
        R.string.fish_4,
        R.string.fish_5,
        R.string.fish_6
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(fishList.size) { index ->
            FishItem(name = stringResource(id = fishList[index]))
        }
    }
}

@Composable
fun FishItem(name: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, style = MaterialTheme.typography.body1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFishList() {
    FishList()
}