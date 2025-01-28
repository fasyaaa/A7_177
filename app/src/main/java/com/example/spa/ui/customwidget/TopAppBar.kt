import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoustumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
    backgroundColor: Color = colorResource(id = R.color.Background)
    ){
    CenterAlignedTopAppBar(

                title = { Text(title) },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "",
                        modifier = modifier.clickable {
                            onRefresh()
                        })
                },
                modifier = modifier,
                scrollBehavior = scrollBehavior, navigationIcon = {
                    if (canNavigateBack) {
                        TextButton(onClick = navigateUp) {
                            Text("Kembali", color = Color.Black)
                        }
                    }
                },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarCst(
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    showProfile: Boolean = true,
    showBackButton: Boolean = true,
    title1: String = "",
    title2: String = "",
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
){
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(LocalConfiguration.current.screenHeightDp.dp / 3)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.TopBorder),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
        ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.topborder
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alpha = 0.8f,
                    modifier = Modifier.fillMaxSize()
                )
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 60.sp,
                    modifier = Modifier
                        .padding(top = 60.dp)
                )
                Text(
                    text = title2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 60.sp,
                    modifier = Modifier
                        .padding(top = 60.dp)
                )
            }
        }
    }
}