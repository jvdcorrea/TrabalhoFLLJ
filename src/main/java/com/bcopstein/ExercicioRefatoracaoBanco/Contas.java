package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Map;


public class Contas {
	public static final int SILVER = 0;
	public static final int GOLD = 1;
	public static final int PLATINUM = 2;
	public static final int LIM_SILVER_GOLD = 50000;
	public static final int LIM_GOLD_PLATINUM = 200000;
	public static final int LIM_PLATINUM_GOLD = 100000;
	public static final int LIM_GOLD_SILVER = 25000;

	private int numero;
	private String correntista;
	private double saldo;
	private int status;
	private int curDia, curMes, curAno;
	
    private Persistencia persistencia;
    private Operacoes operacoes;
	private Map<Integer,Contas> contas;

    
    public Contas(Persistencia persistencia, Operacoes operacoes) {
        this.persistencia = persistencia;
        this.operacoes = operacoes;        
        contas = persistencia.loadContas();    	
    }
	
	private Contas(int umNumero, String umNome) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		status = SILVER;
	}
	
	public Contas(int umNumero, String umNome,double umSaldo, int umStatus) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;
		status = umStatus;
	}
	
	
	public double getSaldo() {
		return saldo;}
	public Contas getConta(Integer ID) {
		return contas.get(ID);}
	public Integer getNumero() {
		return numero;}
	public String getCorrentista() {
		return correntista;}	
	public int getStatus() {
		return status;}
		
	
	public String getStrStatus() {
		switch(status) {
		case 0:  return "Silver";
		case 1:  return "Gold";
		case 2:  return "Platinum";
		default: return "none";

		}
	}
	
	
	public double getLimRetiradaDiaria() {
		switch(status) {
		case 0:  return 5000.0;
		case 1:  return 50000.0;
		case 2:  return 500000.0;
		default: return 0.0;
		}
	}
	
	
	public void deposito(double valor) {
		if (status == SILVER) {
			saldo += valor;
			if (saldo >= LIM_SILVER_GOLD) {
				status = GOLD;
			}
		} else if (status == GOLD) {
			saldo += valor * 1.01;
			if (saldo >= LIM_GOLD_PLATINUM) {
				status = PLATINUM;
			}
		} else if (status == PLATINUM) {
			saldo += valor * 1.025;
		}
	}

	
	public void retirada(double valor) {
		saldo = saldo - valor;
		if (status == PLATINUM) {
			if (saldo < LIM_PLATINUM_GOLD) {
				status = GOLD;
			}
		} else if (status == GOLD) {
			if (saldo < LIM_GOLD_SILVER) {
				status = SILVER;
			}
		}
	}

	
	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + ", status=" + status
				+ "]";
	}


}