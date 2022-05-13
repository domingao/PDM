package com.example.estoque.addeditproduto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estoque.data.Produto

class AddEditEstoqueVM : ViewModel() {

    private val _id : MutableLiveData<Int> = MutableLiveData(7)
    //val id : LiveData<Int>
    //    get() = _id
    //fun changeId(newId: Int){
    //    _id.value  = newId
    //}

    val codigoBarras : MutableLiveData<String> = MutableLiveData("")
    val nome : MutableLiveData<String> = MutableLiveData("")
    val quantidade : MutableLiveData<String> = MutableLiveData("")
    val descricao : MutableLiveData<String> = MutableLiveData("")


    fun insertProduto(
        onInstertProduto : (Produto) -> Unit
    ){
        val newProduto = Produto(
            _id.value ?: return,
            codigoBarras.value  ?: return,
            nome.value  ?: return,
            quantidade.value  ?: return,
            descricao.value ?: return,
        )
        onInstertProduto(newProduto)
        var tempId : Int = _id.value ?: return
        tempId++
        _id.value = tempId

        codigoBarras.value = ""
        nome.value = ""
        quantidade.value = ""
        descricao.value = ""
    }

    fun updateProduto(
        id: Int,
        onUpdateProduto: (Produto) -> Unit
    ){
        val produto = Produto(
            id,
            codigoBarras.value ?: return,
            nome.value ?: return,
            quantidade.value ?: return,
            descricao.value ?: return,
        )
        onUpdateProduto(produto)
    }
}