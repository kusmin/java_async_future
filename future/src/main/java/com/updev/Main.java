package com.updev;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Tarefa iniciada!");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simula operação demorada
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 42; // Resultado da operação
        });

        System.out.println("Tarefa rodando em paralelo, realizando outras ações...");

        future.thenAccept(resultado ->
                System.out.println("Resultado da tarefa: " + resultado)
        ).exceptionally(e -> {
            System.err.println("Erro durante execução: " + e.getMessage());
            return null;
        });

        // Simula outras operações enquanto a tarefa está rodando
        for (int i = 0; i < 5; i++) {
            System.out.println("Trabalhando na thread principal...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Aguarda conclusão apenas para evitar encerramento prematuro do programa
        // foi colocado apenas para fins didáticos
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Fim da execução.");
    }
}
