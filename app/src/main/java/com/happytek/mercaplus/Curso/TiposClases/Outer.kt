package com.happytek.mercaplus.Curso.TiposClases;

class Outer {

    private var varAnidada: Int = 10

    class Anidada{
        fun funcionDeClaseAnidada() {
            var prueba: Int
           // prueba = varAniadada -> no tiene visibilidad
        }
    }

    inner class Interior {
        fun funciondeClaseInner() {
            var prueba : Int
            prueba = varAnidada
        }
    }
}
