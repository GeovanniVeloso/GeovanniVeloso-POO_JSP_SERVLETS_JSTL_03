package model;

import java.time.LocalDate;

public class Jogador {

	private int idJogador;
	private String nomeJogador;
	private LocalDate dataNascJ;
	private double alturaJogador;
	private double pesoJogador;
	private Time timeJogador;
	
	public Jogador() {
		super();
	}

	public int getIdJogador() {
		return idJogador;
	}

	public void setIdJogador(int idJogador) {
		this.idJogador = idJogador;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public LocalDate getDataNascJ() {
		return dataNascJ;
	}

	public void setDataNascJ(LocalDate dataNascJ) {
		this.dataNascJ = dataNascJ;
	}

	public double getAlturaJogador() {
		return alturaJogador;
	}

	public void setAlturaJogador(double alturaJogador) {
		this.alturaJogador = alturaJogador;
	}

	public double getPesoJogador() {
		return pesoJogador;
	}

	public void setPesoJogador(double pesoJogador) {
		this.pesoJogador = pesoJogador;
	}

	public Time getTimeJogador() {
		return timeJogador;
	}

	public void setTimeJogador(Time timeJogador) {
		this.timeJogador = timeJogador;
	}
	
	

}
