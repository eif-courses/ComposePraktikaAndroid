package eif.viko.lt.vilniaus.kolegija.elektronikos.myapplication

import android.content.Intent
import android.content.res.Resources
import android.media.AudioAttributes
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


class MainActivity : ComponentActivity() {


    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            MyApplicationTheme {
                Scaffold(
                    content = { Greeting("Android") },
                    bottomBar = {
                        BottomAppBar {

                            IconButton(modifier = Modifier
                                .width(64.dp)
                                .height(50.dp), onClick = {

                                val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                                val intentUri =
                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                        .appendQueryParameter(
                                            "file",
                                            "https://raw.githubusercontent.com/eif-courses/models/main/acer_anywhere_laptop.glb"
                                        ).build()
                                sceneViewerIntent.data = intentUri
                                sceneViewerIntent.setPackage("com.google.ar.core")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_play_arrow_64),
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.green)
                                )
                            }
                            IconButton(modifier = Modifier
                                .width(64.dp)
                                .height(50.dp), onClick = {

                                val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                                val intentUri =
                                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                        .appendQueryParameter(
                                            "file",
                                            "https://raw.githubusercontent.com/eif-courses/models/main/acer_anywhere_laptop.glb"
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
                        }
                    })
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
fun Greeting(name: String) {


    val context = LocalContext.current
    val temp = listOf(
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas",
        "Labas",
        "Krabas",
        "BNabas"
    )


    val size = 64.dp

    // Display 10 items
    val pagerState = rememberPagerState(pageCount = temp.size)

    HorizontalPager(state = pagerState, itemSpacing = 10.dp) { page ->
        // Our page content
        Card(

            elevation = 4.dp, modifier = Modifier
                .fillMaxHeight(0.93f)
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
                            "Dviejų diapazonų ampermetras TSRS 1967 m.",
                            maxLines = 20,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = colorResource(id = R.color.description_background))
                                .padding(10.dp),
                            fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                        )

                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.oscilografas),
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
                            "Dviejų diapazonų ampermetras TSRS 1967 m. Padidinto tikslumo dviejų diapozonų rodiklinis fiksuotos ritės - judančio magneto ampermetras skirtas matuoti nuolatinę ir kintamą srovę. Matavimo skalės: 0-2.5 ampero ir 0-5 voltai. Kintamos srovės dažnis 45 - 1500Hz. Matavimo paklaidos visuose diapazonuose 0.5%.",
                            maxLines = 20,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = colorResource(id = R.color.other_description_background))
                                .padding(10.dp), fontSize = 16.sp, fontWeight = FontWeight.W300,
                            color = colorResource(id = R.color.other_description_color)
                        )

                    }

                }




                Row() {
                    Text(
                        text = "${page + 1} / ${temp.size}",
                        maxLines = 20,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white)
                    )
                }


            }


        }


    }


//
//    LazyRow() {
//        items(temp) {
//        }
//
//
//
//
//        }

}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    MyApplicationTheme {
        Scaffold(
            content = { Greeting("Android") },
            bottomBar = {
                BottomAppBar {

                    IconButton(modifier = Modifier
                        .width(64.dp)
                        .height(50.dp), onClick = {

                        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                        val intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                .appendQueryParameter(
                                    "file",
                                    "https://raw.githubusercontent.com/eif-courses/models/main/acer_anywhere_laptop.glb"
                                ).build()
                        sceneViewerIntent.data = intentUri
                        sceneViewerIntent.setPackage("com.google.ar.core")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_play_arrow_64),
                            contentDescription = null,
                            tint = colorResource(id = R.color.green)
                        )
                    }
                    IconButton(modifier = Modifier
                        .width(64.dp)
                        .height(50.dp), onClick = {

                        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                        val intentUri =
                            Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                .appendQueryParameter(
                                    "file",
                                    "https://raw.githubusercontent.com/eif-courses/models/main/acer_anywhere_laptop.glb"
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
                }
            })
    }
}