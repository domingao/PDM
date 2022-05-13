package com.example.estoque.addeditproduto

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import com.example.estoque.data.Produto

@Composable
fun AddEditProdutoTela(
    navController: NavController,
    addEditEstoqueVM: AddEditEstoqueVM,
    onInsertProduto: (Produto) -> Unit,
    onUpdateProduto: (Produto) -> Unit,
    onRemoveProduto: (Int) -> Unit,
    produto: Produto
) {
    Scaffold(
        floatingActionButton ={
            FloatingActionButton(onClick = {
                if(produto.id == -1)
                    addEditEstoqueVM.insertProduto(onInsertProduto)
                else
                    addEditEstoqueVM.updateProduto(
                        produto.id,
                        onUpdateProduto
                    )

                navController.navigate("produtolist"){
                    popUpTo("produtolist"){
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirm")
            }
        }
    ) {
        addEditEstoqueVM.nome.value = produto.nome
        addEditEstoqueVM.codigoBarras.value = produto.codigoBarras
        addEditEstoqueVM.quantidade.value = produto.quantidade
        addEditEstoqueVM.descricao.value = produto.descricao


        AddEditProdutoForm(
            addEditEstoqueVM,
            produto.id,
            onRemoveProduto,
        ){
            navController.navigate("produtolist"){
                popUpTo("produtolist"){
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun AddEditProdutoForm(
    addEditEstoqueVM: AddEditEstoqueVM,
    id: Int,
    onRemoveProduto: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val codigoBarras = addEditEstoqueVM.codigoBarras.observeAsState()
    val nome = addEditEstoqueVM.nome.observeAsState()
    val quantidade = addEditEstoqueVM.quantidade.observeAsState()
    val descricao = addEditEstoqueVM.descricao.observeAsState()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text(text = "Nome") },
                value = "${nome.value}",
                onValueChange =
                    { newName -> addEditEstoqueVM.nome.value = newName })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text(text = "Quantidade") },
                value = "${quantidade.value}",
                onValueChange =
                { newQuantidade -> addEditEstoqueVM.quantidade.value = newQuantidade })
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                label = { Text(text = "Codigo de Barras") },
                value = "${codigoBarras.value}",
                onValueChange =
                { newQuantidade -> addEditEstoqueVM.codigoBarras.value = newQuantidade })
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descrição") },
                value = "${descricao.value}", onValueChange =
                { newDescricao -> addEditEstoqueVM.descricao.value = newDescricao })

        }
        if( id != -1)
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    onRemoveProduto(id)
                    navigateBack() }
            ) { Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete") }
    }
}
