package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Jogador;
import model.Time;

public class JogadorCRUD implements ICRUD<Jogador>{
	
	private GenericDAOMySQL GD;

	public JogadorCRUD(GenericDAOMySQL GD) {
		super();
		this.GD = GD;
	}

	@Override
	public void registrar(Jogador j) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "INSERT INTO jogador VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = c.prepareStatement(cmdSQL);
		ps.setInt(1, j.getIdJogador());
		ps.setString(2, j.getNomeJogador());
		ps.setDate(3, java.sql.Date.valueOf(j.getDataNascJ()));
		ps.setDouble(4, j.getAlturaJogador());
		ps.setDouble(5, j.getPesoJogador());
		ps.setInt(6, j.getTimeJogador().getIdTime());
		
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public void atualizar(Jogador j) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "UPDATE jogador SET nome = ?, dataNasc = ?, altura = ?, peso =  ?, idTime = ? WHERE id = ?)";
		
		PreparedStatement ps = c.prepareStatement(cmdSQL);
		ps.setInt(1, j.getIdJogador());
		ps.setString(2, j.getNomeJogador());
		ps.setDate(3, java.sql.Date.valueOf(j.getDataNascJ()));
		ps.setDouble(4, j.getAlturaJogador());
		ps.setDouble(5, j.getPesoJogador());
		ps.setInt(6, j.getTimeJogador().getIdTime());
		
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public void deletar(Jogador j) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "DELETE FROM jogador WHERE id = ?";
		
		PreparedStatement ps = c.prepareStatement(cmdSQL);
		ps.setInt(1, j.getIdJogador());
		
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public Jogador procurar(Jogador j) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		StringBuffer cmdSQL = new StringBuffer();
		cmdSQL.append("SELECT jogador.id AS idJogador, jogador.nome AS nomeJogador, ");
		cmdSQL.append("jogador.dataNasc AS dataNascJogador, jogador.altura AS alturaJogador, ");
		cmdSQL.append("jogador.peso AS pesoJogador, ");
		cmdSQL.append("time.id AS idTime, time.nome AS nomeTime, time.nomeCIdade AS nomeCidadeTime ");
		cmdSQL.append("FROM time, pjogador ");
		cmdSQL.append("WHERE time.id = jogador.idTime ");
		cmdSQL.append("AND jogador.id = ?");

		PreparedStatement preparedStatement = c.prepareStatement(cmdSQL.toString());
		preparedStatement.setInt(1, j.getIdJogador());
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			Time t = new Time();

			t.setIdTime(resultSet.getInt("teamId"));
			t.setNomeTime(resultSet.getString("teamName"));
			t.setCidadeTime(resultSet.getString("teamCityName"));

			j.setIdJogador(resultSet.getInt("id"));
			j.setNomeJogador(resultSet.getString("name"));
			j.setDataNascJ(resultSet.getDate("birthDate").toLocalDate());
			j.setAlturaJogador(resultSet.getDouble("height"));
			j.setPesoJogador(resultSet.getDouble("weight"));
			j.setTimeJogador(t);
		}

		resultSet.close();
		preparedStatement.close();
		c.close();

		return j;
		
	}

	@Override
	public List<Jogador> lista() throws SQLException, ClassNotFoundException {
		List<Jogador> jogadores = new ArrayList<>();

		Connection c = GD.getConnection();
		StringBuffer cmdSQL = new StringBuffer();
		cmdSQL.append("SELECT jogador.id AS idJogador, jogador.nome AS nomeJogador, ");
		cmdSQL.append("jogador.dataNasc AS dataNascJogador, jogador.altura AS alturaJogador, ");
		cmdSQL.append("jogador.peso AS pesoJogador, ");
		cmdSQL.append("time.id AS idTime, time.nome AS nomeTime, time.nomeCIdade AS nomeCidadeTime ");
		cmdSQL.append("FROM time, jogador ");
		cmdSQL.append("WHERE time.id = jogador.teamId");

		PreparedStatement preparedStatement = c.prepareStatement(cmdSQL.toString());
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Time t = new Time();
			t.setIdTime(resultSet.getInt("teamId"));
			t.setNomeTime(resultSet.getString("teamName"));
			t.setCidadeTime(resultSet.getString("teamCityName"));
			
			Jogador j = new Jogador();
			
			j.setIdJogador(resultSet.getInt("id"));
			j.setNomeJogador(resultSet.getString("name"));
			j.setDataNascJ(resultSet.getDate("birthDate").toLocalDate());
			j.setAlturaJogador(resultSet.getDouble("height"));
			j.setPesoJogador(resultSet.getDouble("weight"));
			j.setTimeJogador(t);
			
			jogadores.add(j);
		}

		resultSet.close();
		preparedStatement.close();
		c.close();

		return jogadores;
	}	

}
