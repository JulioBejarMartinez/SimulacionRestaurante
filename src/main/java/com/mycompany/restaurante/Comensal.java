/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Julito
 */
// Clase "Comensal" que implementa la interfaz Runnable, lo que permite que sea ejecutada en un hilo
public class Comensal implements Runnable {
    // Identificador único del comensal
    private final int id;
    // Semáforo para gestionar las mesas disponibles
    private final Semaphore mesasDisponibles;
    // Lista de mesas del restaurante
    private final List<Mesa> mesas;
    // Cola donde se almacenan los pedidos de los comensales
    private final Queue<Pedido> colaPedidos;
    // Referencia al camarero de sala para asignar y liberar mesas
    private final CamareroSala camareroSala;
    // La mesa que le será asignada al comensal
    private Mesa mesaAsignada;
    // El pedido que hará el comensal
    private Pedido pedido;

    // Constructor del comensal
    public Comensal(int id, Semaphore mesasDisponibles, List<Mesa> mesas, Queue<Pedido> colaPedidos, CamareroSala camareroSala) {
        // Asigna el ID del comensal
        this.id = id;
        // Asigna el semáforo de mesas disponibles
        this.mesasDisponibles = mesasDisponibles;
        // Asigna la lista de mesas
        this.mesas = mesas;
        // Asigna la cola de pedidos
        this.colaPedidos = colaPedidos;
        // Asigna el camarero de sala
        this.camareroSala = camareroSala;
    }

    @Override
    // Método principal del hilo que ejecutará la lógica del comensal
    public void run() {
        try {
            // Imprime que el comensal ha llegado y está esperando mesa
            System.out.println("Comensal " + id + " ha llegado y espera mesa.");

            // Esperar a que se asigne una mesa
            mesasDisponibles.acquire();

            // Se sincroniza el acceso a la lista de mesas para evitar problemas de concurrencia
            synchronized (mesas) {
                // Recorre todas las mesas en busca de una libre
                for (Mesa mesa : mesas) {
                    // Si la mesa no está ocupada
                    if (!mesa.isOcupada()) {
                        // Marca la mesa como ocupada
                        mesa.ocupar();
                        // Asigna la mesa al comensal
                        mesaAsignada = mesa;
                        // Imprime en consola la asignación de la mesa
                        System.out.println("Camarero de sala asignó la mesa " + mesa.getId());
                        // Sale del bucle una vez asignada la mesa
                        break;
                    }
                }
            }

            // El comensal hace el pedido
            // Esta parte en la cual hace el pedido se cambiara en un futuro para que el pedido lo haga en funcion a la informacion de una BBDD en MongoDB
            // Crea un nuevo pedido con los detalles del comensal y los items que desea
            pedido = new Pedido(id, mesaAsignada.getId(), "Vino", "Ensalada", "Pasta", "Helado", this);
            // Agrega el pedido a la cola de pedidos
            colaPedidos.offer(pedido);

            // Imprime en consola que el comensal está esperando su pedido
            System.out.println("Comensal " + id + " se sentó en la mesa " + mesaAsignada.getId() + " y está esperando su pedido.");

            // Llama al método para esperar a que el pedido sea completado
            esperarPedido();

            // Una vez que todo el pedido esté servido, el comensal paga y se va
            System.out.println("Comensal " + id + " pagó y se va de la mesa " + mesaAsignada.getId());
            // Marca la mesa como libre
            mesaAsignada.liberar();
            // Libera el semáforo de mesas, aumentando el contador de mesas disponibles
            mesasDisponibles.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para esperar hasta que el pedido esté completado
    private void esperarPedido() throws InterruptedException {
        // El comensal espera hasta que se liberen todos los semáforos correspondientes a su pedido
        // Espera hasta que la bebida esté lista
        pedido.getSemBebida().acquire();
        // Espera hasta que el primer plato esté listo
        pedido.getSemPrimerPlato().acquire();
        // Espera hasta que el segundo plato esté listo
        pedido.getSemSegundoPlato().acquire();
        // Espera hasta que el postre esté listo
        pedido.getSemPostre().acquire();
    }

    // Método para obtener el ID del comensal
    public int getId() {
        // Retorna el ID del comensal
        return id;
    }
}