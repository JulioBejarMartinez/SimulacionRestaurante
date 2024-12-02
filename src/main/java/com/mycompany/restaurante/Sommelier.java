/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante;

import java.util.Queue;

/**
 *
 * @author Julito
 */
public class Sommelier implements Runnable { // Declaración de clase pública
    private final Queue<Pedido> colaPedidos;

    public Sommelier(Queue<Pedido> colaPedidos) {
        this.colaPedidos = colaPedidos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Pedido pedido = colaPedidos.poll();
                if (pedido != null) {
                    System.out.println("Sommelier preparando bebida para el pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 2000));
                    System.out.println("Sommelier terminó bebida para el pedido " + pedido.getId());
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
