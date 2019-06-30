package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fachada {
    private Operacoes ops = null;
    private Contas contas = null;
    private Validacoes valid = null;
    private Persistencia pers;
	private GregorianCalendar date;
	
    private static Fachada inst = null;
	
    private Contas curConta = null;
	
	private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private int segundo;

	
    private Fachada(){
		date = new GregorianCalendar();
		pers = Persistencia.getPersis();
		ops = new Operacoes(pers);
		contas = new Contas(pers, ops);	
	}

    public static void delete(){
        inst = null;
    }

    public static Fachada getFachada(){
        if(inst == null){
            inst = new Fachada();
        }
        return inst;
    }
	
	
	public boolean entrarConta(int ID){
		curConta = contas.getConta(ID);
		return curConta != null;
	}	
	
	
	public boolean credito(double valor){
		if(!Validacoes.deposito(valor)) return false;
		
		ops.addDeposito(
					date.get(GregorianCalendar.DAY_OF_MONTH),
					date.get(GregorianCalendar.MONTH+1),
					date.get(GregorianCalendar.YEAR),
					date.get(GregorianCalendar.HOUR),
					date.get(GregorianCalendar.MINUTE),
					date.get(GregorianCalendar.SECOND),
					curConta.getNumero(),
					curConta.getStatus(),
					valor);
		
		return true;
	}	
	
	
	//double saldo, double valor, double limite, double curLim
	public boolean debito(double valor){						
		if(!Validacoes.retirada(saldo(), valor, curConta.getLimRetiradaDiaria(), 
			ops.extrato(curConta.getNumero()).stream().
				filter(op -> op.getTipoOperacao() == 1 &&
					op.getDia() == date.get(GregorianCalendar.DAY_OF_MONTH) &&
					op.getMes() == date.get(GregorianCalendar.MONTH+1) &&
					op.getAno() == date.get(GregorianCalendar.YEAR)).
						mapToDouble(op -> op.getValorOperacao()).sum())
			) return false;
			
		ops.addRetirada(
					date.get(GregorianCalendar.DAY_OF_MONTH),
					date.get(GregorianCalendar.MONTH+1),
					date.get(GregorianCalendar.YEAR),
					date.get(GregorianCalendar.HOUR),
					date.get(GregorianCalendar.MINUTE),
					date.get(GregorianCalendar.SECOND),
					curConta.getNumero(),
					curConta.getStatus(),
					valor);
					
		curConta.retirada(valor);
		return true;		
	}	
	
	
	public List<Operacoes> extrato(){
		return ops.extrato(curConta.getNumero());
	}	
	
	
	public double saldo(){
		return curConta.getSaldo();
	}	
	
	
	public double saldoM(){
		return curConta.getSaldo();
	}
	
	
	public long totCred(){
		return ops.extrato(curConta.getNumero()).stream().filter(op -> op.getTipoOperacao() == 0).count();
	}
	
	
	public long	 totDeb(){
		return ops.extrato(curConta.getNumero()).stream().filter(op -> op.getTipoOperacao() == 1).count();
	}

}