package com.happytek.mercaplus.Curso

class ClaseConObjeto private constructor(var sistema : String){


    init {
        count++
    }

    companion object {

        init {
            //Log(Init companion object)
        }
        var count: Int = 0
        fun create (sistema :String) : ClaseConObjeto = ClaseConObjeto(sistema)

    }
}