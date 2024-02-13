package com.example.supermercadoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen1.data.DataSource
import com.example.examen1.data.DataSource.productos
import com.example.examen1.data.Producto
import com.example.supermercadoapp.ui.theme.SupermercadoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SupermercadoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Pantalla()
                }
            }
        }
    }
}

@Composable
fun Pantalla(modifier: Modifier = Modifier, listaProductos: ArrayList<Producto> = DataSource.productos){

    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoPrecio by remember { mutableStateOf("") }

    var textoInferior by remember { mutableStateOf("Todavía no has añadido ningun valor") }
    Column (){
        Text(
            text = "Hola, soy alumn@ Ester",
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(0.25f)
                .padding(start = 20.dp, top = 50.dp)
        )
        ProductosYBotones(modifier = modifier.weight(0.75f), listaProductos = listaProductos,
            nombreText = nuevoNombre, precioText = nuevoPrecio,
            nombreNuevoProducto = { nuevoNombre = it },  precioNuevoProducto = { nuevoPrecio = it },
            clickAction ={
                val precioCash: Int
                precioCash = nuevoPrecio.toInt()
                for(producto in listaProductos){
                    if(producto.nombre.equals(nuevoNombre) && producto.precio == precioCash){
                        textoInferior =
                            "No se ha modificado nada del producto ${producto.nombre}, el precio es el mismo"
                        return@ProductosYBotones
                    }
                    if(producto.nombre.equals(nuevoNombre) && producto.precio != precioCash){
                        textoInferior =
                            "Se ha modificado el precio del producto ${producto.nombre} de ${producto.precio} € a ${precioCash} €"
                        producto.precio = precioCash
                        return@ProductosYBotones
                    }
                }
                val nuevoProducto = Producto (nuevoNombre, precioCash)
                listaProductos.add(nuevoProducto)
                textoInferior =
                    "Se ha añadido el producto ${nuevoProducto.nombre} con precio ${nuevoProducto.precio}"



        })
        Text(
            text = textoInferior,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(0.25f)
                .padding(top = 50.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductosYBotones (modifier: Modifier, clickAction: () -> Unit,
                               listaProductos: ArrayList<Producto>,
                               nombreText: String, nombreNuevoProducto: (String) -> Unit,
                               precioText: String, precioNuevoProducto: (String) -> Unit) {

    Row (modifier = modifier) {
        Column (modifier = modifier.weight(1f)){
            TextField(value = nombreText,
                onValueChange = nombreNuevoProducto,
                label = { Text(text = "Nombre")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(start = 10.dp, end= 10.dp, top = 20.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(value = precioText,
                onValueChange = precioNuevoProducto,
                label = { Text(text = "Precio")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(start = 10.dp, end= 10.dp, top = 20.dp))
            Button(
                onClick = {
                    clickAction.invoke()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Add/Update producto")
            }

        }
        LazyColumn(modifier = modifier
            .weight(1f)
            .fillMaxWidth()){
            items(productos){productos->
                Card( modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                ){
                    Text(text = "Nombre: ${productos.nombre}",
                        modifier= Modifier
                            .background(Color.Yellow)
                            .fillMaxWidth()
                            .padding(20.dp))
                    Text(text = "Precio: ${productos.precio}",
                        modifier= Modifier
                            .background(Color.Cyan)
                            .fillMaxWidth()
                            .padding(20.dp))
                }
            }
        }
    }


}
