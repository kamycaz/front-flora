package com.happytek.mercaplus.Curso.InterfazImplementacion

class Director : Trabajo {
    override fun salario(): Int {
        return 2100
    }

    override fun equipo(): String {
        return "Dirigencia"
    }
}