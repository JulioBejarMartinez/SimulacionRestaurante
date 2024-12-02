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
public class Camarero implements Runnable { // Declaración de clase pública
    private final Queue<Pedido> colaPedidos;

    public Camarero(Queue<Pedido> colaPedidos) {
        this.colaPedidos = colaPedidos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Pedido pedido = colaPedidos.poll();
                if (pedido != null) {
                    System.out.println("Camarero tomando pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 1000));
                    System.out.println("Camarero sirviendo bebida del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 1000)); // Simula el tiempo de servicio
                    System.out.println("Camarero sirviendo primer plato del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 1000)); // Simula el tiempo de servicio
                    System.out.println("Camarero sirviendo segundo plato del pedido " + pedido.getId());
                    Thread.sleep((int) (Math.random() * 1000)); // Simula el tiempo de servicio
                    System.out.println("Camarero sirviendo postre del pedido " + pedido.getId());
                    
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
