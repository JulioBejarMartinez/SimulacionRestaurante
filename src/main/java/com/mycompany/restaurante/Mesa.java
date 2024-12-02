/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante;

/**
 *
 * @author Julito
 */
public class Mesa {
    private final int id;
    private boolean ocupada;

    public Mesa(int id) {
        this.id = id;
        this.ocupada = false;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void ocupar() {
        this.ocupada = true;
    }

    public void liberar() {
        this.ocupada = false;
    }
}
