package com.example.estoque.data

data class Produto(
    val id: Int,
    val codigoBarras: String,
    val nome: String,
    val quantidade: String,
    val descricao: String,
)
