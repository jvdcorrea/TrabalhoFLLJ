package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.List;
import java.util.stream.Collectors;


public class Operacoes {
	public final int CREDITO = 0;
	public final int DEBITO = 1;
    
	private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private int segundo;
    private int numeroConta;
    private int statusConta;
    private double valorOperacao;
    private int tipoOperacao;
    private Persistencia persistencia;
    private List<Operacoes> operacoes;
	

	public int getDia() {
		return dia;}
	public int getMes() {
		return mes;}
	public int getAno() {
		return ano;}
	public int getHora() {
		return hora;}
	public int getMinuto() {
		return minuto;}
	public int getSegundo() {
		return segundo;}
	public int getNumeroConta() {
		return numeroConta;}
	public int getStatusConta() {
		return statusConta;}
	public double getValorOperacao() {
		return valorOperacao;}
	public int getTipoOperacao() {
		return tipoOperacao;}
		
    
    public Operacoes(Persistencia persistencia) {
        this.persistencia = persistencia;
    	operacoes = persistencia.loadOperacoes();
    }    
	
	public Operacoes(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta, double valorOperacao, int tipoOperacao) {
		super();
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
		this.numeroConta = numeroConta;
		this.statusConta = statusConta;
		this.valorOperacao = valorOperacao;
		this.tipoOperacao = tipoOperacao;
	}
	
	
	public void addDeposito(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta, double valorOperacao){
		operacoes.add(new Operacoes(dia,mes,ano,hora,minuto,segundo,numeroConta,statusConta,valorOperacao,0));
	}
	
	public void addRetirada(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta, double valorOperacao){
		operacoes.add(new Operacoes(dia,mes,ano,hora,minuto,segundo,numeroConta,statusConta,valorOperacao,1));
	}
    

    public void save(){
        persistencia.saveOperacoes(operacoes);
    }
    

    public List<Operacoes> getOps(int numeroConta){
        return operacoes
                .stream()
                .filter(op -> op.getNumeroConta() == numeroConta)
                .collect(Collectors.toList());
    }
	
	
	public List<Operacoes> extrato(int ID){
		return operacoes
			.stream()
			.filter(op -> op.getNumeroConta() == ID)
			.collect(Collectors.toList());
	}
    
	
	@Override
	public String toString() {
		String tipo = "<C>";
		if (tipoOperacao == 1) {
			tipo = "<D>"; 
		}
		String line = dia+"/"+mes+"/"+ano+" "+
	                  hora+":"+minuto+":"+segundo+" "+
				      numeroConta+" "+
	                  statusConta +" "+
				      tipo+" "+
	                  valorOperacao;
		return(line);
	}

    
}
