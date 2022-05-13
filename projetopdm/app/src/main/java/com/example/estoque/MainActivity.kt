package com.example.estoque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.estoque.addeditproduto.AddEditProdutoTela
import com.example.estoque.addeditproduto.AddEditEstoqueVM
import com.example.estoque.produtolist.ProdutoListaTela
import com.example.estoque.produtolist.ProdutoListaVM
import com.example.estoque.ui.theme.ComposeAula03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val produtoListaVM: ProdutoListaVM by viewModels()
        val addEditEstoqueVM : AddEditEstoqueVM by viewModels()

        setContent {
            ComposeAula03Theme {
                // A surface container using the 'background' color from the theme
                MyApp(produtoListaVM, addEditEstoqueVM)
            }
        }
    }
}

@Composable
fun MyApp(
    produtoListaVM : ProdutoListaVM,
    addEditEstoqueVM : AddEditEstoqueVM
) {
    val navController = rememberNavController()
    Scaffold(){
        NavHost(navController = navController, startDestination = "produtolist"){
            composable("produtolist"){
                ProdutoListaTela(navController,produtoListaVM)
            }
            composable(
                route = "addeditproduto?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val produto = produtoListaVM.getProduto(id)
                AddEditProdutoTela(
                    navController,
                    addEditEstoqueVM,
                    produtoListaVM::insertProduto,
                    produtoListaVM::updateProduto,
                    produtoListaVM::removeItem,
                    produto

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeAula03Theme {

    }
}