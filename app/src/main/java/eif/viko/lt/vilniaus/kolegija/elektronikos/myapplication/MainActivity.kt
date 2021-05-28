package eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.MediaPlayer

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.pager.*
import com.google.gson.Gson
import eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication.model.Item
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import android.os.Build
import android.os.Handler
import android.os.CountDownTimer





class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        val text = resources.openRawResource(R.raw.items).bufferedReader().use { it.readText() }
        val items: List<Item> = gson.fromJson(text, Array<Item>::class.java).toList()

        setContent {

            val context = LocalContext.current


            MyApplicationTheme {
                DisplayMuseumItems(items)
            }
        }

    }
}


//override fun playSound(item: Item) {
//    val url = item.audio                ///"http://........" // your URL here
//    val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
//        setAudioAttributes(
//            AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .build()
//        )
//        setDataSource(url)
//        prepare() // might take long! (for buffering, etc)
//        start()
//    }
//}

@ExperimentalPagerApi
@Composable
fun DisplayMuseumItems(museumItems: List<Item>) {

    val fabShape = CircleShape
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(pageCount = museumItems.size)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val stateMedia = remember { mutableStateOf(true) }
    val progresas = remember { mutableStateOf(0.0f) }

    progresas.value = 0.0f

    val currentLocale = Locale.getDefault().toLanguageTag()

    val locale: String = when {
        currentLocale.contains("lt") -> {
            "LT"
        }
        currentLocale.contains("ru") -> {
            "RU"
        }
        currentLocale.contains("en") -> {
            "EN"
        }
        else -> {
            "EN"
        }
    }

    val url= "https://raw.githubusercontent.com/eif-courses/audio/main/${locale}/${museumItems[pagerState.currentPage].media}.wav"


    var (loadAudio, setLoadResult) = remember { mutableStateOf<MediaPlayer?>(null) }




    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { Text("Drawer content") },
        topBar = {
            val items = listOf(
                ActionItemSpec("LT", Icons.Default.Phone, ActionItemMode.IF_ROOM) {

                    Locale.setDefault(Locale("lt"))
                },
                ActionItemSpec("EN", Icons.Default.PlayArrow, ActionItemMode.IF_ROOM) {
                    Locale.setDefault(Locale("en_US"))
                },
                ActionItemSpec("RU", Icons.Default.Menu, ActionItemMode.IF_ROOM) {
                    Locale.setDefault(Locale("ru_RU"))
                },
            )
            TopAppBar(
                title = {

                    // PAVADINIMAS
                    when (locale) {
                        "LT" -> TitleText(title = museumItems[pagerState.currentPage].title_lt)
                        "RU" -> TitleText(title = museumItems[pagerState.currentPage].title_ru)
                        else -> TitleText(title = museumItems[pagerState.currentPage].title_en)
                    }

                },
                actions = {
                    ActionMenu(items, defaultIconSpace = 0)
                }
            )

        },
        bottomBar = {
            BottomAppBar(
                cutoutShape = fabShape,
                modifier = Modifier.height(110.dp),
                backgroundColor = colorResource(
                    id = R.color.purple_500
                )
            ) {

                IconButton(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 30.dp)
                        .size(50.dp),
                    onClick = {
                        stateMedia.value = !stateMedia.value

                        if (!stateMedia.value) {

                            val mediaPlayer = MediaPlayer().apply {
                                setAudioAttributes(
                                    AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                                )
                                setDataSource(url)
                                prepareAsync() // might take long! (for buffering, etc)
                            }

                            mediaPlayer.setOnPreparedListener {
                                loadAudio = it
                                val temp = it.duration.toLong()


                                val waitTimer: CountDownTimer
                                waitTimer = object : CountDownTimer(temp, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {
                                        //called every 300 milliseconds, which could be used to
                                        //send messages or some other action
                                        progresas.value = (progresas.value+1/10.0f)
                                        //progresas.value/= 10.0f
                                    }

                                    override fun onFinish() {
                                        //After 60000 milliseconds (60 sec) finish current
                                        //if you would like to execute something when time finishes
                                    }
                                }.start()


                               // progresas.value = temp
                                it.start()
                            }

                        } else {
                            loadAudio?.release()
                            loadAudio = null
                        }

                    }
                )
                {
                    if (stateMedia.value) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentDescription = null,
                            tint = colorResource(id = R.color.green)
                        )

                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_pause_64),
                            modifier = Modifier
                                .fillMaxSize(),
                            contentDescription = null,
                            tint = colorResource(id = R.color.green)
                        )
                    }
                }

                LinearProgressIndicator(
                    backgroundColor = Color.White,
                    color = colorResource(id = R.color.purple_700),
                    progress = progresas.value,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .padding(start = 20.dp, top = 30.dp)
                )

                Text(
                    text = progresas.value.toString(),
                    modifier = Modifier.padding(start = 56.dp, top = 30.dp),
                    color = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = fabShape,
                modifier = Modifier
                    .size(80.dp),
                onClick = {
                    val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                    val intentUri =
                        Uri.parse("https://arvr.google.com/scene-viewer/1.0")
                            .buildUpon()
                            .appendQueryParameter(
                                "file",
                                "https://raw.githubusercontent.com/eif-courses/models/main/${museumItems[pagerState.currentPage].media}.glb"
                            ).build()
                    sceneViewerIntent.data = intentUri
                    sceneViewerIntent.setPackage("com.google.ar.core")
                    context.startActivity(sceneViewerIntent)


                }
            ) {
                Icon(painterResource(id = R.drawable.ic_baseline_3d_rotation_64), "")
            }
        }, content = {
            PageCard(pagerState, museumItems, locale, loadAudio)
        })
}


@ExperimentalPagerApi
@Composable
fun PageCard(pagerState: PagerState, museumItems: List<Item>, locale: String, mediaPlayer: MediaPlayer?) {

    val context = LocalContext.current

    HorizontalPager(state = pagerState, itemSpacing = 10.dp) { page ->
        // Our page content

        //val url = "https://eif-muziejus.lt/audio_ru/oscilografas.wav"



        Card(

            elevation = 4.dp, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = colorResource(id = R.color.color_soft_1))
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(
                            id = context.resources.getIdentifier(
                                museumItems[page].media, "drawable", context.packageName
                            )
                        ),
                        contentDescription = "3D",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .shadow(2.dp)
                            .padding(10.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .shadow(1.dp)
                        .padding(10.dp)
                ) {
                    when (locale) {
                        "LT" -> DescriptionTextArea(language = museumItems[page].description_lt)
                        "RU" -> DescriptionTextArea(language = museumItems[page].description_ru)
                        else -> DescriptionTextArea(language = museumItems[page].description_en)
                    }
                }

            }

        }

    }
}


@Composable
fun TitleText(title: String) {
    DisableSelection {
        Text(
            title,
            maxLines = 3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(
                id = R.color.black
            )
        )
    }
}

@Composable
fun DescriptionTextArea(language: String) {
    DisableSelection {
        Text(
            language,
            maxLines = 20,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = colorResource(id = R.color.other_description_background))
                .padding(10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.W300,
            color = colorResource(id = R.color.other_description_color)
        )
    }
}


@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val gson = Gson()
    val text =
        context.resources.openRawResource(R.raw.items).bufferedReader().use { it.readText() }
    val items: List<Item> = gson.fromJson(text, Array<Item>::class.java).toList()
    MyApplicationTheme {
        DisplayMuseumItems(items)
    }
}


data class ActionItemSpec(
    val name: String,
    val icon: ImageVector,
    val visibility: ActionItemMode = ActionItemMode.IF_ROOM,
    val onClick: () -> Unit,
)

// Whether to show the action item as an icon or not (or if room)
enum class ActionItemMode {
    ALWAYS_SHOW, IF_ROOM, NEVER_SHOW
}

@Composable
fun ActionMenu(
    items: List<ActionItemSpec>,
    defaultIconSpace: Int = 3, // includes overflow menu
    menuExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    // decide how many ifRoom icons to show as actions
    val (actionItems, overflowItems) = remember(items, defaultIconSpace) {
        separateIntoActionAndOverflow(items, defaultIconSpace)
    }

    for (item in actionItems) {
        IconButton(onClick = item.onClick) {
            Icon(item.icon, item.name)
        }
    }
    if (overflowItems.isNotEmpty()) {
        IconButton(onClick = { menuExpanded.value = true }) {

            val currentLocale = Locale.getDefault().toLanguageTag()

            when {
                currentLocale.contains("lt") -> {
                    Image(
                        painter = painterResource(id = R.drawable.lt),
                        contentDescription = "Choose language", modifier = Modifier.padding(5.dp)
                    )
                }
                currentLocale.contains("ru") -> {
                    Image(
                        painter = painterResource(id = R.drawable.ru),
                        contentDescription = "Choose language", modifier = Modifier.padding(5.dp)
                    )
                }
                else -> {
                    Image(
                        painter = painterResource(id = R.drawable.en),
                        contentDescription = "Choose language", modifier = Modifier.padding(5.dp)
                    )
                }

            }
        }
        DropdownMenu(
            expanded = menuExpanded.value,
            onDismissRequest = { menuExpanded.value = false }
        ) {
            for (item in overflowItems) {
                DropdownMenuItem(onClick = item.onClick) {
                    //Icon(item.icon, item.name) just have text in the overflow menu


                    val textpos = 5.dp

                    when (item.name) {
                        "LT" -> {
                            Image(
                                painter = painterResource(id = R.drawable.lt),
                                contentDescription = item.name,
                                modifier = Modifier.shadow(10.dp)
                            )
                            Text(modifier = Modifier.padding(start = textpos), text = "LT")
                        }
                        "EN" -> {
                            Image(
                                painter = painterResource(id = R.drawable.en),
                                contentDescription = item.name
                            )
                            Text(modifier = Modifier.padding(start = textpos), text = "EN")
                        }
                        "RU" -> {
                            Image(
                                painter = painterResource(id = R.drawable.ru),
                                contentDescription = item.name
                            )
                            Text(modifier = Modifier.padding(start = textpos), text = "RU")
                        }
                    }


                    // Text(item.name)
                }
            }
        }

    }
}

private fun separateIntoActionAndOverflow(
    items: List<ActionItemSpec>,
    defaultIconSpace: Int
): Pair<List<ActionItemSpec>, List<ActionItemSpec>> {
    var (alwaysCount, neverCount, ifRoomCount) = Triple(0, 0, 0)
    for (item in items) {
        when (item.visibility) {
            ActionItemMode.ALWAYS_SHOW -> alwaysCount++
            ActionItemMode.NEVER_SHOW -> neverCount++
            ActionItemMode.IF_ROOM -> ifRoomCount++
        }
    }

    val needsOverflow = alwaysCount + ifRoomCount > defaultIconSpace || neverCount > 0
    val actionIconSpace = defaultIconSpace - (if (needsOverflow) 1 else 0)

    val actionItems = ArrayList<ActionItemSpec>()
    val overflowItems = ArrayList<ActionItemSpec>()

    var ifRoomsToDisplay = actionIconSpace - alwaysCount
    for (item in items) {
        when (item.visibility) {
            ActionItemMode.ALWAYS_SHOW -> {
                actionItems.add(item)
            }
            ActionItemMode.NEVER_SHOW -> {
                overflowItems.add(item)
            }
            ActionItemMode.IF_ROOM -> {
                if (ifRoomsToDisplay > 0) {
                    actionItems.add(item)
                    ifRoomsToDisplay--
                } else {
                    overflowItems.add(item)
                }

            }
        }
    }
    return Pair(actionItems, overflowItems)

}