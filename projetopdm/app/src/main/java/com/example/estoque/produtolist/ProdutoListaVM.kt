package com.example.estoque.produtolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estoque.data.Produto

class ProdutoListaVM : ViewModel() {

    private val _produtoList : MutableLiveData<List<Produto>> = MutableLiveData(
        listOf(
            Produto(
                0,
                "562345",
                "Iogurte",
                "6",
                "Frio"
            ),
            Produto(
                1,
                "562331445",
                "Batata",
                "4",
                "Fruta"
            ),
            Produto(
                2,
                "562423345",
                "Maçã",
                "10",
                "Fruta"
            ),
            Produto(
                3,
                "563122345",
                "Bolo",
                "9",
                ""
            ),
            Produto(
                4,
                "515562345",
                "sabonete",
                "6",
                ""
            ),
            Produto(
                5,
                "519962345",
                "Coca-Cola",
                "6",
                "Bebidas"
            ),
        )
    )

    private val _filterBy : MutableLiveData<String> = MutableLiveData("")
    val filterBy: LiveData<String>
        get() = _filterBy

    val estoquelist: LiveData<List<Produto>>
        get() {
            return if(_filterBy.value == "")
                _produtoList
            else{
                val list: List<Produto> = _produtoList.value?.filter{ estoque ->
                    estoque.nome.contains(_filterBy.value ?: "")
                } ?: listOf()
                MutableLiveData(list)
            }
        }

    fun updateFilter(newFilter: String){
        _filterBy.value = newFilter
    }

    fun insertProduto(produto: Produto){
        val list: MutableList<Produto> = _produtoList.value?.toMutableList() ?: return
        list.add(produto)
        _produtoList.value = list
    }

    fun updateProduto(updatedProduto: Produto){
        var pos = -1
        _produtoList.value?.forEachIndexed { index, estoque ->
            if(updatedProduto.id == estoque.id)
                pos = index
        }
        val list: MutableList<Produto> = _produtoList.value?.toMutableList() ?: return
        list.removeAt(pos)
        list.add(pos, updatedProduto)
        _produtoList.value = list
    }

    fun removeItem(id: Int){
        var pos = -1
        _produtoList.value?.forEachIndexed { index, estoque ->
            if(id == estoque.id)
                pos = index
        }
        val list: MutableList<Produto> = _produtoList.value?.toMutableList() ?: return
        list.removeAt(pos)
        _produtoList.value = list
    }

    fun getProduto(id: Int): Produto {
        _produtoList.value?.forEach { estoque ->
            if(id == estoque.id)
                return  estoque
        }
        return Produto(
            -1,
            "",
            "",
            "",
            ""
        )
    }
}