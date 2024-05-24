import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.example.fishing_app.R

// lista ryb -> po kliknieciu na nazwe ryby wyswietlany jest dialog z opisem
// dodatkowo jako ostatni element jest odnosnik do zewnetrznej strony
@Composable
fun FishList() {
    val fishList = listOf(
        R.string.fish_1,
        R.string.fish_2,
        R.string.fish_3,
        R.string.fish_4,
        R.string.fish_5,
        R.string.fish_6,
        R.string.fish_7,
        R.string.fish_8,
        R.string.fish_9,
        R.string.fish_10,
        R.string.fish_11,
        R.string.fish_12,
        R.string.more_info
    )

    val fishDescriptions = listOf(
        R.string.fish_description_1,
        R.string.fish_description_2,
        R.string.fish_description_3,
        R.string.fish_description_4,
        R.string.fish_description_5,
        R.string.fish_description_6,
        R.string.fish_description_7,
        R.string.fish_description_8,
        R.string.fish_description_9,
        R.string.fish_description_10,
        R.string.fish_description_11,
        R.string.fish_description_12
    )

    val fishImages = listOf(
        R.drawable.szczupak,
        R.drawable.sum,
        R.drawable.wegorz,
        R.drawable.leszcz,
        R.drawable.lin,
        R.drawable.wzdrega,
        R.drawable.ukleja,
        R.drawable.karp,
        R.drawable.krap,
        R.drawable.ploc,
        R.drawable.jaz,
        R.drawable.karas
    )

    var selectedFishIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(fishList.size) { index ->
            if (index < fishDescriptions.size) {
                FishItem(
                    name = stringResource(id = fishList[index]),
                    onClick = { selectedFishIndex = index }
                )
            } else {
                MoreInfoItem(
                    name = stringResource(id = fishList[index]),
                    url = "https://atlasryb.online"
                )
            }
        }
    }

    selectedFishIndex?.let { index ->
        val fishName = stringResource(id = fishList[index])
        val fishDescription = stringResource(id = fishDescriptions[index])
        val fishImage = fishImages[index]

        FishDescriptionDialog(
            fishName = fishName,
            fishDescription = fishDescription,
            fishImage = fishImage,
            onDismissRequest = { selectedFishIndex = null }
        )
    }
}

// odpowiada za mozliwosc klikniecia w nazwe ryby
@Composable
fun FishItem(name: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
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

// odpowiada za ostatni element ktory przenosi na zewnetrzna strone przez podany URL
@Composable
fun MoreInfoItem(name: String, url: String) {
    val uriHandler = LocalUriHandler.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { uriHandler.openUri(url) },
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primary)
        }
    }
}

// funkcja odpowiedzialna za wyswietlanie dialogu -> zamkniecie po wcisnieciu "OK"
@Composable
fun FishDescriptionDialog(fishName: String, fishDescription: String, fishImage: Int, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = fishName, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                Image(painterResource(id = fishImage), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = fishDescription, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFishList() {
    FishList()
}
