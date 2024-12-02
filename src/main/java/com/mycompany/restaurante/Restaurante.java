/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Julito
 */
// Declaración de la clase pública "Restaurante"
public class Restaurante { 
    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] args) {
        // Creación de un semáforo con 10 permisos, representando 10 mesas disponibles
        Semaphore mesasDisponibles = new Semaphore(10);
        // Creación de una cola bloqueante para almacenar los pedidos de los comensales
        Queue<Pedido> colaPedidos = new LinkedBlockingQueue<>();

        // Creación de una lista para almacenar las mesas del restaurante
        List<Mesa> mesas = new ArrayList<>();
        // Ciclo para crear 10 mesas en el restaurante
        for (int i = 0; i < 10; i++) {
            // Agregar una nueva mesa a la lista, con ID de 1 a 10
            mesas.add(new Mesa(i + 1));
        }

        // Crear el objeto CamareroSala
        CamareroSala camareroSala = new CamareroSala(mesas, mesasDisponibles); // Se crea una instancia del camarero de sala, pasando las mesas y el semáforo de mesas disponibles

        // Crear el hilo para el camarero de sala
        Thread camareroSalaThread = new Thread(camareroSala); // Se crea un hilo que ejecutará al camarero de sala
        // Inicia la ejecución de cada hilo de camarero
        camareroSalaThread.start();
        
        // Se crea un hilo para el sommelier, que atenderá la cola de pedidos
        Thread sommelier = new Thread(new Sommelier(colaPedidos));
        // Inicia la ejecución del hilo del sommelier
        sommelier.start();
        
        // Arreglo de 4 hilos para los camareros
        Thread[] camareros = new Thread[4];
        // Arreglo de 2 hilos para los cocineros
        Thread[] cocineros = new Thread[2];

        // Ciclo para crear e iniciar 4 hilos de camareros
        for (int i = 0; i < 4; i++) {
            // Se crea un hilo para cada camarero, pasando la cola de pedidos
            camareros[i] = new Thread(new Camarero(colaPedidos));
            // Inicia la ejecución de cada hilo de camarero
            camareros[i].start();
        }

        // Ciclo para crear e iniciar 2 hilos de cocineros
        for (int i = 0; i < 2; i++) {
            // Se crea un hilo para cada cocinero, pasando la cola de pedidos
            cocineros[i] = new Thread(new Cocinero(colaPedidos));
            // Inicia la ejecución de cada hilo de cocinero
            cocineros[i].start();
        }

        // Crear y comenzar comensales
        // Ciclo para crear e iniciar 2 hilos de comensales
        for (int i = 0; i < 2; i++) {
            // Se crea un hilo para cada comensal, pasando la información necesaria como el número de comensal, el semáforo de mesas, la lista de mesas, la cola de pedidos y el camarero de sala
            new Thread(new Comensal(i + 1, mesasDisponibles, mesas, colaPedidos, camareroSala)).start();
            try {
                // Se realiza una espera aleatoria entre 0 y 1000 milisegundos antes de crear el siguiente comensal
                Thread.sleep((int) (Math.random() * 1000));
                // Captura cualquier excepción de interrupción del hilo
            } catch (InterruptedException e) {
                // Imprime el error si ocurre una excepción
                e.printStackTrace();
            }
        }
    }
}