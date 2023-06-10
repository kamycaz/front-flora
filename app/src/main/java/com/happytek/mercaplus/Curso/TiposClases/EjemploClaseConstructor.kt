package com.happytek.mercaplus.Curso.TiposClases

class EjemploClaseConstructor constructor(nombre: String, model : String) {

    val nombre : String
    val fabricante : String


    init {
        this.nombre = nombre
        this.fabricante = model
    }
}