package com.bcopstein.ExercicioRefatoracaoBanco;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;


public class Persistencia {
    private final String NomeBDContas = "BDContasBNG.txt";
    private final String NomeBDOperacoes = "BDOperBNG.txt";
	

    private static Persistencia inst = null;

    private Persistencia(){}

    public static void delete(){
        inst = null;
    }

    public static Persistencia getPersis(){
        if(inst == null){
            inst = new Persistencia();
        }
        return inst;
    }

    
    public Map<Integer,Contas> loadContas(){
    	Map<Integer,Contas> contas = new HashMap<>();
    	
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"/"+NomeBDContas;
        //System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete); 
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){ 
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha 
           int numero;
           String nomeCorr;
           double saldo;
           int status;
           while (sc.hasNext()){ 
               numero = Integer.parseInt(sc.next()); 
               nomeCorr = sc.next();
               saldo = Double.parseDouble(sc.next());
               status = Integer.parseInt(sc.next());
               Contas conta = new Contas(numero,nomeCorr,saldo,status);
               contas.put(numero, conta);
           }
        }catch (IOException x){ 
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        } 
        return contas;
    }


    public void saveContas(Collection<Contas> contas) {
        Path path1 = Paths.get(NomeBDContas); 
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset()))) 
        { 
            for(Contas c: contas) 
                writer.format(Locale.ENGLISH,
                		      "%d;%s;%f;%d;",
                		      c.getNumero(),c.getCorrentista(), 
                              c.getSaldo(),c.getStatus()); 
        } 
        catch (IOException x) 
        { 
            System.err.format("Erro de E/S: %s%n", x); 
        } 
    }

    
    public void saveOperacoes(Collection<Operacoes> operacoes) {
        Path path1 = Paths.get(NomeBDOperacoes); 
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.defaultCharset()))) 
        { 
            for(Operacoes op:operacoes) 
                writer.format(Locale.ENGLISH,
                		      "%d;%d;%d;%d;%d;%d;%d;%d;%f;%d;",  
                              op.getDia(),op.getMes(),op.getAno(),
                              op.getHora(),op.getMinuto(),op.getSegundo(),
                              op.getNumeroConta(),op.getStatusConta(),
                              op.getValorOperacao(),op.getTipoOperacao()
                             ); 
        } 
        catch (IOException x) 
        { 
            System.err.format("Erro de E/S: %s%n", x); 
        } 
    }

    
    public List<Operacoes> loadOperacoes(){
        List<Operacoes> operacoes = new LinkedList<Operacoes>();
        
    	String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir+"/"+NomeBDOperacoes;
        //System.out.println(nameComplete);
        Path path2 = Paths.get(nameComplete); 
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))){ 
           sc.useDelimiter("[;\n]"); // separadores: ; e nova linha 
           int dia,mes,ano;
           int hora,minuto,segundo;
           int numero,status,tipo;
           double valor;
       
           while (sc.hasNext()){ 
               dia = Integer.parseInt(sc.next()); 
               mes = Integer.parseInt(sc.next()); 
               ano = Integer.parseInt(sc.next()); 
               hora = Integer.parseInt(sc.next()); 
               minuto = Integer.parseInt(sc.next()); 
               segundo = Integer.parseInt(sc.next()); 
               numero = Integer.parseInt(sc.next()); 
               status = Integer.parseInt(sc.next()); 
               valor = Double.parseDouble(sc.next());
               tipo = Integer.parseInt(sc.next());
               
               Operacoes op = new Operacoes(
            		   dia, mes, ano,
            		   hora, minuto, segundo,
	                   numero, status,
	                   valor, tipo);
               
               operacoes.add(op);
           }
        }catch (IOException x){ 
            System.err.format("Erro de E/S: %s%n", x);
            return null;
        } 
        return operacoes;    	
    }


}
