package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Time;

public class TimeCrud implements ICRUD<Time> {

	private GenericDAOMySQL GD;
	
	public TimeCrud(GenericDAOMySQL GD) {
		super();
		this.GD = GD;
	}

	@Override
	public void registrar(Time t) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "INSERT INTO time VALUES (?, ?, ?)";

		PreparedStatement preparedStatement = c.prepareStatement(cmdSQL);
		preparedStatement.setInt(1, t.getIdTime());
		preparedStatement.setString(2, t.getNomeTime());
		preparedStatement.setString(3, t.getCidadeTime());

		preparedStatement.execute();
		preparedStatement.close();
		c.close();
		
	}

	@Override
	public void atualizar(Time t) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "UPDATE time SET nome = ?, nomeCidade = ? WHERE id = ?";

		PreparedStatement preparedStatement = c.prepareStatement(cmdSQL);
		preparedStatement.setInt(1, t.getIdTime());
		preparedStatement.setString(2, t.getNomeTime());
		preparedStatement.setString(3, t.getCidadeTime());

		preparedStatement.execute();
		preparedStatement.close();
		c.close();
		
	}

	@Override
	public void deletar(Time t) throws SQLException, ClassNotFoundException {
		Connection c = GD.getConnection();
		String cmdSQL = "DELETE FROM time WHERE id = ?";
		
		PreparedStatement ps = c.prepareStatement(cmdSQL);
		ps.setInt(1, t.getIdTime());
		
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public Time procurar(Time t) throws SQLException, ClassNotFoundException {
		Connection conn = GD.getConnection();
		String cmdSQL = "SELECT id, nome, nomeCidade FROM time WHERE id = ?";

		PreparedStatement preparedStatement = conn.prepareStatement(cmdSQL);
		preparedStatement.setInt(1, t.getIdTime());
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			t.setIdTime(resultSet.getInt("id"));
			t.setNomeTime(resultSet.getString("nome"));
			t.setCidadeTime(resultSet.getString("nomeCidade"));
		}

		resultSet.close();
		preparedStatement.close();
		conn.close();

		return t;
	}

	@Override
	public List<Time> lista() throws SQLException, ClassNotFoundException {
		List<Time> times = new ArrayList<>();

		Connection c = GD.getConnection();
		String cmdSQL = "SELECT id, nome, nomeCidade FROM time";

		PreparedStatement preparedStatement = c.prepareStatement(cmdSQL);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			Time t = new Time();
			t.setIdTime(resultSet.getInt("id"));
			t.setNomeTime(resultSet.getString("nome"));
			t.setCidadeTime(resultSet.getString("nomeCidade"));

			times.add(t);
		}

		resultSet.close();
		preparedStatement.close();
		c.close();

		return times;
	}

}
