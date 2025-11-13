import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes

data class ChatSession(
    val id: Int,
    @DrawableRes val avatarResId: Int, // <-- Diubah dari Color ke DrawableRes
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int
)

val mockChatList = listOf(
    ChatSession(1, R.drawable.ic_avatar_hariku, "Hariku", "Halo! Aku Hariku, siap membantumu...", "29/05", 2),
    ChatSession(2, R.drawable.ic_avatar_quesar, "Quesar", "Halo! Aku Hariku, siap membantumu...", "29/05", 1),
    ChatSession(3, R.drawable.ic_avatar_hariku2, "Hariku (2)", "Halo! Aku Hariku, siap membantumu...", "29/05", 0),
    ChatSession(4, R.drawable.ic_avatar_noir, "Noir", "Halo! Aku Hariku, siap membantumu...", "29/05", 0),
    ChatSession(5, R.drawable.ic_avatar_maman, "Maman (Baru!)", "Halo! Aku Maman, siap membantumu...", "29/05", 0),
)

@Composable
fun ChatScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFF9F9F9),
        topBar = { ChatTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                containerColor = Color(0xFFD9A188),
                modifier = Modifier.size(72.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_tambah_obrolan),
                    contentDescription = "Tambah Obrolan",
                    modifier = Modifier.size(72.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            items(mockChatList) { session ->
                ChatItem(
                    todo = {
                        Log.d("DEBUG", "Chat Detail")
                        navController.navigate(Routes.DetailChatbot.createRoute(session.id.toString()))
                    },
                    session = session
                )
                androidx.compose.material3.HorizontalDivider(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ChatTopBar() {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Obrolan",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .width(72.76923.dp)
                    .height(39.69231.dp)
                    .background(
                        color = Color(0xFFF68674),
                        shape = RoundedCornerShape(size = 55.1282.dp)
                    )
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SOS",
                    style = TextStyle(
                        fontSize = 16.54.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFDFCFC),
                        letterSpacing = 1.1.sp,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun ChatItem(todo: () -> Unit, session: ChatSession) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                todo()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = session.avatarResId),
            contentDescription = "Avatar ${session.name}",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = session.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = session.lastMessage,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = session.timestamp,
                color = Color.Gray,
                fontSize = 12.sp
            )
            if (session.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${session.unreadCount}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen(rememberNavController())
    }
}
