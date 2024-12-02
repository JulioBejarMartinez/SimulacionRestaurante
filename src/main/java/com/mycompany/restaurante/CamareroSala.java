/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Julito
 */
// Clase "CamareroSala" que implementa la interfaz Runnable, lo que permite que sea ejecutada en un hilo
public class CamareroSala implements Runnable {
    // Lista de mesas que el camarero de sala gestionara.
    private final List<Mesa> mesas;
    // Semaforo para gestionar las mesas disponibles
    private final Semaphore mesasDisponibles;
    // Objeto de sincronización utilizado para coordinar la espera y notificación entre hilos
    private final Object lock = new Object(); 

    public CamareroSala(List<Mesa> mesas, Semaphore mesasDisponibles) {
        // Asigna las mesas del restaurante
        this.mesas = mesas;
        this.mesasDisponibles = mesasDisponibles;
    }

    @Override
    // Mwtodo principal del hilo que ejecutara la logica del camarero de sala
    public void run() {
        // Ciclo infinito para que el camarero de sala siga trabajando mientras se ejecute el programa
        while (true) {
            try {
                // El camarero espera a que llegue un comensal (si es necesario)
                // Se sincroniza el acceso al objeto lock para que el camarero espere de forma controlada
                synchronized (lock) { 
                    // Espera hasta que un comensal necesite mesa
                    lock.wait(); 
                }

                // Una vez que un comensal llega, el camarero de sala asigna una mesa
                
                // Espera por una mesa disponible
                mesasDisponibles.acquire(); 
                // Se sincroniza el acceso a la lista de mesas para evitar problemas de concurrencia
                synchronized (mesas) {
                    // Recorre todas las mesas en busca de una libre
                    for (Mesa mesa : mesas) {
                        // Si la mesa no está ocupada
                        if (!mesa.isOcupada()) {
                            // Marca la mesa como ocupada
                            mesa.ocupar(); 
                            // Imprime en consola la asignación de la mesa
                            System.out.println("Camarero de sala asignó la mesa " + mesa.getId());
                            // Sale del bucle una vez la mesa ha sido asiganada
                            break; 
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para notificar que un comensal ha llegado y necesita una mesa
    public void notificarLlegadaComensal() {
        // Se sincroniza el acceso al objeto lock para evitar conflictos con otros hilos
        synchronized (lock) {
            // Despierta al camarero de sala para asignar una mesa
            lock.notify(); 
        }
    }

    // Método para liberar la mesa cuando el comensal se va
    public void liberarMesa(Mesa mesa) {
        // Se sincroniza el acceso a la lista de mesas para evitar problemas de concurrencia
        synchronized (mesas) {
            // Marca la mesa como libre
            mesa.liberar();
            // Libera el semáforo de mesas, aumentando el contador de mesas disponibles
            mesasDisponibles.release(); 
            // Imprime en consola que la mesa ha sido liberada
            System.out.println("Camarero de sala liberó la mesa " + mesa.getId());
        }
    }
}