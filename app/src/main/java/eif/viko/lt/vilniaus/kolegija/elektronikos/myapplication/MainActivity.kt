package eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication

import android.content.Intent
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.absoluteValue
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication.model.Item


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

    val context = LocalContext.current


    val size = 64.dp

    // Display 10 items


    val pagerState = rememberPagerState(pageCount = museumItems.size)

    HorizontalPager(state = pagerState, itemSpacing = 10.dp) { page ->
        // Our page content
        Card(

            elevation = 4.dp, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = R.drawable.background
                ),
                contentDescription = null
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
            ) {


                Row {


                    DisableSelection {
                        Text(
                            museumItems[page].title,
                            maxLines = 20,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.primary)
                                .padding(10.dp),
                            fontSize = 20.sp, fontWeight = FontWeight.W300, color = colorResource(
                                id = R.color.purple_700
                            )
                        )

                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(
                            id = context.resources.getIdentifier(
                                museumItems[page].poster.substringBefore(
                                    '.'
                                ), "drawable", context.packageName
                            )
                        ),
                        contentDescription = "3D",
                        modifier = Modifier
                            .size(300.dp)
                            .background(colorResource(id = R.color.item_background))
                    )

                }



                Row() {

                    Image(
                        painter = painterResource(id = R.drawable.fingers),
                        contentDescription = null, contentScale = ContentScale.Crop,
                        modifier = Modifier.size(170.dp, 50.dp)
                    )


                }




                Row() {
                    DisableSelection {
                        Text(
                            museumItems[page].description,
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
                Divider(
                    modifier = Modifier
                        .size(2.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BottomAppBar {

                        Button(
                            modifier = Modifier
                                .width(120.dp)
                                .height(70.dp), onClick = {


                                //val url = "https://eif-muziejus.lt/audio_ru/oscilografas.wav"
                                val url = "https://raw.githubusercontent.com/eif-courses/modelsausdio/main/EN/${museumItems[page].audio}"
                                ///"http://........" // your URL here

                                println(url)


                                val mediaPlayer: MediaPlayer = MediaPlayer().apply {
                                    setAudioAttributes(
                                        AudioAttributes.Builder()
                                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                            .setUsage(AudioAttributes.USAGE_MEDIA)
                                            .build()
                                    )
                                    setDataSource(url)
                                    prepare() // might take long! (for buffering, etc)
                                    start()
                                }

                                    // https://raw.githubusercontent.com/eif-courses/modelsausdio/main/EN/acer_ak_anywhere_en.wav


                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_64),
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.green)
                                )

                            }

                                Button(modifier = Modifier
                                    .width(120.dp)
                                    .height(70.dp), onClick = {

                                    val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                                    val intentUri =
                                        Uri.parse("https://arvr.google.com/scene-viewer/1.0")
                                            .buildUpon()
                                            .appendQueryParameter(
                                                "file",
                                                "https://raw.githubusercontent.com/eif-courses/models/main/${museumItems[page].model}"
                                            ).build()
                                    sceneViewerIntent.data = intentUri
                                    sceneViewerIntent.setPackage("com.google.ar.core")
                                    context.startActivity(sceneViewerIntent)

                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vaizdoperziura),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.black)
                                    )

                                }
                                Text(
                                    text = "Page ${page + 1}/${museumItems.size}",
                                    modifier = Modifier.padding(start = 30.dp),
                                    color = Color.Black
                                )

                            }
                    }


                }


            }


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