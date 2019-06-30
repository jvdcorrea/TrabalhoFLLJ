//java com.bcopstein.ExercicioRefatoracaoBanco.Test

package com.bcopstein.ExercicioRefatoracaoBanco;

class Test{
	
	public static void main(String[] args){
		Fachada f = Fachada.getFachada();
		f.entrarConta(300);
		f.credito(200);
		f.debito(10);
		f.credito(20);
		f.debito(300);
		f.debito(400);
		f.debito(1000);
		f.debito(10000);
		f.debito(8290);
		f.debito(20000);
		f.debito(20000);
		f.debito(20000);
		f.extrato().stream().forEach(op -> System.out.println(op.toString()));
		System.out.printf("creds %d\n", f.totCred());
		System.out.printf("deb %d\n", f.totDeb());
		System.out.printf("Sald %f\n", f.saldo());
	}
	
}