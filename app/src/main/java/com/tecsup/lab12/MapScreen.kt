package com.tecsup.lab12

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import androidx.compose.foundation.layout.*
import com.google.maps.android.compose.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun MapScreen() {
    val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
    }
    var mapType by remember { mutableStateOf(MapType.NORMAL) }


    val context = LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.montanas)
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false) // Ajusta el tamaño según necesites

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.TopStart) {
            Row() {
                Button(onClick = { mapType = MapType.NORMAL }) {
                    Text("Normal")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { mapType = MapType.HYBRID }) {
                    Text("Hybrid")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { mapType = MapType.SATELLITE }) {
                    Text("Satellite")
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.TopStart) {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { mapType = MapType.TERRAIN }) {
                    Text("Terrain")
                }
            }
        }

    Box(modifier = Modifier.fillMaxSize()) {
        // Añadir GoogleMap al layout
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = mapType)
        ) {
            // Añadir marcador en Arequipa, Perú
            Marker(
                state = rememberMarkerState(position = ArequipaLocation),
                icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap), // Icono escalado
                title = "Arequipa, Perú",
            )
            val locations = listOf(
                LatLng(-16.433415,-71.5442652), // JLByR
                LatLng(-16.4205151,-71.4945209), // Paucarpata
                LatLng(-16.3524187,-71.5675994) // Zamacola
            )

            locations.forEach { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Punto de interés"
                )
            }

            val mallAventuraPolygon = listOf(
                LatLng(-16.432292, -71.509145),
                LatLng(-16.432757, -71.509626),
                LatLng(-16.433013, -71.509310),
                LatLng(-16.432566, -71.508853)
            )

            val parqueLambramaniPolygon = listOf(
                LatLng(-16.422704, -71.530830),
                LatLng(-16.422920, -71.531340),
                LatLng(-16.423264, -71.531110),
                LatLng(-16.423050, -71.530600)
            )

            val plazaDeArmasPolygon = listOf(
                LatLng(-16.398866, -71.536961),
                LatLng(-16.398744, -71.536529),
                LatLng(-16.399178, -71.536289),
                LatLng(-16.399299, -71.536721)
            )

            Polygon(
                points = plazaDeArmasPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue,
                strokeWidth = 5f
            )
            Polygon(
                points = parqueLambramaniPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Blue,
                strokeWidth = 5f
            )
            Polygon(
                points = mallAventuraPolygon,
                strokeColor = Color.Red,
                fillColor = Color.Black,
                strokeWidth = 5f
            )

            // Dibuja un triángulo en Arequipa
            Polygon(
                points = listOf(
                    LatLng(-16.398744, -71.54), // Punto B
                    LatLng(-16.4449, -71.55), // Punto A
                    LatLng(-16.4, -71.55), // Punto C
                    LatLng(-16.398866, -71.54) // Volver al punto A para cerrar el triángulo
                ),
                strokeColor = Color.Red,
                fillColor = Color.Magenta,
                strokeWidth = 5f
            )

            // Dibuja un trapecio en Arequipa
            Polygon(
                points = listOf(
                    LatLng(-16.398, -71.53), // Punto A
                    LatLng(-16.3985, -71.5375), // Punto B
                    LatLng(-16.3995, -71.5375), // Punto C
                    LatLng(-16.400, -71.53), // Punto D
                    LatLng(-16.398, -71.53) // Volver al punto A para cerrar el trapecio
                ),
                strokeColor = Color.Blue,
                fillColor = Color.Cyan,
                strokeWidth = 5f
            )
        }
    }
}
}
