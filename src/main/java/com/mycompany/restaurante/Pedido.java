/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurante;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Julito
 */

// Clase "Pedido" que representa el pedido de un comensal
public class Pedido {
    // Identificador único del pedido
    private final int id;
    // Identificador de la mesa a la que está asignado el pedido
    private final int mesaId;
    // Bebida del pedido
    private final String bebida;
    // Primer plato del pedido
    private final String primerPlato;
    // Segundo plato del pedido
    private final String segundoPlato;
    // Postre del pedido
    private final String postre;
    // Comensal que hizo el pedido
    private final Comensal comensal;

    // Semáforos para los platos
    // Semáforo para controlar la preparación de la bebida
    private final Semaphore semBebida;
    // Semáforo para controlar la preparación del primer plato
    private final Semaphore semPrimerPlato;
    // Semáforo para controlar la preparación del segundo plato
    private final Semaphore semSegundoPlato;
    // Semáforo para controlar la preparación del postre
    private final Semaphore semPostre;

    public Pedido(int id, int mesaId, String bebida, String primerPlato, String segundoPlato, String postre, Comensal comensal) {
        this.id = id;
        this.mesaId = mesaId;
        this.bebida = bebida;
        this.primerPlato = primerPlato;
        this.segundoPlato = segundoPlato;
        this.postre = postre;
        this.comensal = comensal;

        // Inicializamos los semáforos para los platos, comenzando bloqueados (0 permisos)
        
        // Comienza bloqueado, el comensal debe esperar por la bebida
        semBebida = new Semaphore(0);
        // Comienza bloqueado, el comensal debe esperar por el primer plato
        semPrimerPlato = new Semaphore(0);
        // Comienza bloqueado, el comensal debe esperar por el segundo plato
        semSegundoPlato = new Semaphore(0);
        // Comienza bloqueado, el comensal debe esperar por el postre
        semPostre = new Semaphore(0);
    }

    // Métodos para servir los platos (liberan los semáforos)
    
    // Método para servir la bebida
    public void servirBebida() {
        // Libera el semáforo de la bebida, indicando que está lista
        semBebida.release();
        // Imprime en consola que la bebida ha sido servida
        System.out.println("Comensal " + comensal.getId() + " recibió su bebida.");
    }

    // Método para servir el primer plato
    public void servirPrimerPlato() {
        // Libera el semáforo del primer plato, indicando que está listo
        semPrimerPlato.release();
        // Imprime en consola que el primer plato ha sido servido
        System.out.println("Comensal " + comensal.getId() + " recibió su primer plato.");
    }

    // Método para servir el segundo plato
    public void servirSegundoPlato() {
        // Libera el semáforo del segundo plato, indicando que está listo
        semSegundoPlato.release();
        // Imprime en consola que el segundo plato ha sido servido
        System.out.println("Comensal " + comensal.getId() + " recibió su segundo plato.");
    }

    // Método para servir el postre
    public void servirPostre() {
        // Libera el semáforo del postre, indicando que está listo
        semPostre.release();
        // Imprime en consola que el postre ha sido servido
        System.out.println("Comensal " + comensal.getId() + " recibió su postre.");
    }

    // Métodos getter para los semáforos
    
    // Método para obtener el semáforo de la bebida
    public Semaphore getSemBebida() {
        return semBebida;
    }

    // Método para obtener el semáforo del primer plato
    public Semaphore getSemPrimerPlato() {
        return semPrimerPlato;
    }

    // Método para obtener el semáforo del segundo plato
    public Semaphore getSemSegundoPlato() {
        return semSegundoPlato;
    }

    // Método para obtener el semáforo del postre
    public Semaphore getSemPostre() {
        return semPostre;
    }

    // Método para verificar si todo el pedido ha sido servido
    public boolean todoElPedidoServido() {
        // Verifica si el semáforo de la bebida ha sido liberado
        return semBebida.availablePermits() > 0 &&
                // Verifica si el semáforo del primer plato ha sido liberado
               semPrimerPlato.availablePermits() > 0 &&
                // Verifica si el semáforo del segundo plato ha sido liberado
               semSegundoPlato.availablePermits() > 0 &&
                // Verifica si el semáforo del postre ha sido liberado
               semPostre.availablePermits() > 0;
    }

    // Método para obtener el ID del pedido
    public int getId() {
        return id;
    }

    // Método para obtener el ID de la mesa
    public int getMesaId() {
        return mesaId;
    }

    // Método para obtener la bebida del pedido
    public String getBebida() {
        return bebida;
    }

    // Método para obtener el primer plato del pedido
    public String getPrimerPlato() {
        return primerPlato;
    }

    // Método para obtener el segundo plato del pedido
    public String getSegundoPlato() {
        return segundoPlato;
    }

    // Método para obtener el postre del pedido
    public String getPostre() {
        return postre;
    }

    // Método para obtener el comensal que hizo el pedido
    public Comensal getComensal() {
        return comensal;
    }
}