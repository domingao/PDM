package com.example.estoque.produtolist

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.estoque.data.Produto


@Composable
fun ProdutoListaTela(
    navController: NavController,
    produtoListaVM: ProdutoListaVM,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addeditproduto")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Produto")
            }
        }
    ) {
        val produtoList by produtoListaVM.estoquelist.observeAsState(listOf())
        val filter by produtoListaVM.filterBy.observeAsState("")

        Column() {
            Search(filter,produtoListaVM::updateFilter)
            ProdutoList(produtos = produtoList,navController = navController)
        }

    }

}

@Composable
fun Search(
    filter: String,
    onFilterChange: (String) -> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        label = {
            Row(){
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                Text(text = "Search")
            }
        },
        value = filter,
        onValueChange = onFilterChange
    )
}

@Composable
fun ProdutoList(
    produtos: List<Produto>,
    navController: NavController
){
    LazyColumn(){
         items(produtos){ produto ->
            ProdutoEntry(produto = produto){
                navController.navigate("addeditproduto?id=${produto.id}")
            }
        }
    }
}

@Composable
fun ProdutoEntry(
    produto: Produto,
    onEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${produto.nome[0].uppercase()}",
                        style = MaterialTheme.typography.h4
                    )
                }
                    Text(
                        modifier = Modifier.padding(start = 8.dp).weight(1f),
                        text = produto.nome,
                        style = MaterialTheme.typography.h6
                    )
                if(expanded){
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                            .clickable {
                                onEdit()
                            },
                        imageVector =Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
            if(expanded){
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "Codigo de barras ${produto.codigoBarras}",
                    style = MaterialTheme.typography.subtitle2.copy(color = Color.LightGray)
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp, bottom = 6.dp, end = 6.dp),
                    text = "Quantidade ${produto.quantidade}",
                    style = MaterialTheme.typography.subtitle2.copy(color = Color.LightGray)
                )
            }
        }


    }
}




@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProdutoListaTelaPreview() {
    val viewModel : ProdutoListaVM = viewModel()
    ProdutoListaTela(rememberNavController(),viewModel)
}

@Preview
@Composable
fun ProdutoListaPreview() {

}

@Preview
@Composable
fun ProdutoEntryPreview() {

}