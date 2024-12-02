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
// Declaración de clase pública implementada como runnable para que actue como hilo
public class Cocinero implements Runnable { 
    // Cola de pedidos compartida
    private final Queue<Pedido> colaPedidos;

    // Constructor que recibe la cola de pedidos como parámetro
    public Cocinero(Queue<Pedido> colaPedidos) {
        // Asigna la cola de pedidos a la variable de instancia
        this.colaPedidos = colaPedidos;
    }

    
    @Override
    // Método run que se ejecuta cuando el hilo se inicia
    public void run() {
        // Bucle infinito para que el camarero siempre esté disponible
        while (true) {
            try {
                Pedido pedido = colaPedidos.poll();
                if (pedido != null) {
                    System.out.println("Cocinero preparando primer plato del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 5000));
                    System.out.println("Cocinero terminó primer plato del pedido " + pedido.getId());
                    System.out.println("Cocinero preparando segundo plato del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 5000));
                    System.out.println("Cocinero terminó segundo plato del pedido " + pedido.getId());
                    System.out.println("Cocinero preparando postre del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 5000));
                    System.out.println("Cocinero terminó postre del pedido " + pedido.getId());
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
