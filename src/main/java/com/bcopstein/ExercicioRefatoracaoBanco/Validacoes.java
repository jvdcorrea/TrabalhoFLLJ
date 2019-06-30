package com.bcopstein.ExercicioRefatoracaoBanco;


public class Validacoes{


    public static boolean deposito(double valor){
		return valor > 0;
	}


    public static boolean retirada(double saldo, double valor, double limite, double curLim){
		return (valor <= saldo) && ((curLim+valor) <= limite);
	}
    
    
}