package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Jogador;
import model.Time;
import persistence.GenericDAOMySQL;
import persistence.JogadorCRUD;
import persistence.TimeCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Jogador")
public class JogadorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JogadorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String consoleError = "";

		List<Time> times = new ArrayList<>();

		try {
			times = listaTime();
		} catch (SQLException | ClassNotFoundException e) {
			consoleError = e.getMessage();
		} finally {
			request.setAttribute("consoleError", consoleError);
			request.setAttribute("teams", times);

			RequestDispatcher rd = request.getRequestDispatcher("jogador.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmdType = request.getParameter("button");

		String playerId = request.getParameter("playerId");
		String playerName = request.getParameter("playerName");
		String playerBirthDate = request.getParameter("playerBirthDate");
		String playerHeight = request.getParameter("playerHeight");
		String playerWeight = request.getParameter("playerWeight");
		String playerTeamId = request.getParameter("playerTeamId");

		String consoleOutput = "";
		String consoleError = "";

		List<Time> times = new ArrayList<>();
		Jogador j = new Jogador();
		List<Jogador> jogadores = new ArrayList<>();

		if (!cmdType.contains("List")) {
			j.setIdJogador(Integer.parseInt(playerId));
		}

		try {
			times = listaTime();

			if (cmdType.contains("Register") || cmdType.contains("Update")) {
				j.setNomeJogador(playerName);
				j.setDataNascJ(LocalDate.parse(playerBirthDate));
				j.setAlturaJogador(Double.parseDouble(playerHeight));
				j.setPesoJogador(Double.parseDouble(playerWeight));

				Time t = new Time();
				t.setIdTime(Integer.parseInt(playerTeamId));
				t = procuraTime(t);
				j.setTimeJogador(t);
			}
			if (cmdType.contains("Search")) {
				j = procurarJogador(j);
			}
			if (cmdType.contains("Register")) {
				registrarJogador(j);
				consoleOutput = "Player registered successfully";
				j = null;
			}
			if (cmdType.contains("Update")) {
				atualizarJogador(j);
				consoleOutput = "Player updated successfully";
				j = null;
			}
			if (cmdType.contains("Remove")) {
				removerJogador(j);
				consoleOutput = "Player removed successfully";
				j = null;
			}
			if (cmdType.contains("List")) {
				jogadores = listarJogador();
			}
		} catch (SQLException | ClassNotFoundException e) {
			consoleError = e.getMessage();
		} finally {
			request.setAttribute("consoleOutput", consoleOutput);
			request.setAttribute("consoleError", consoleError);
			request.setAttribute("teams", times);
			request.setAttribute("player", j);
			request.setAttribute("players", jogadores);

			RequestDispatcher rd = request.getRequestDispatcher("player.jsp");
			rd.forward(request, response);
		}
	}

	private Time procuraTime(Time t) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		t = tc.procurar(t);
		return t;
	}

	private List<Time> listaTime() throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		List<Time> times = tc.lista();
		return times;
	}

	private Jogador procurarJogador(Jogador j) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		JogadorCRUD jc = new JogadorCRUD(genericDAOMySQL);

		j = jc.procurar(j);
		return j;
	}

	private void registrarJogador(Jogador j) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		JogadorCRUD jc = new JogadorCRUD(genericDAOMySQL);

		jc.registrar(j);
	}

	private void atualizarJogador(Jogador j) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		JogadorCRUD jc = new JogadorCRUD(genericDAOMySQL);

		jc.atualizar(j);
	}

	private void removerJogador(Jogador j) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		JogadorCRUD jc = new JogadorCRUD(genericDAOMySQL);

		jc.deletar(j);
	}

	private List<Jogador> listarJogador() throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		JogadorCRUD JC = new JogadorCRUD(genericDAOMySQL);

		List<Jogador> jogadores = JC.lista();
		return jogadores;
	}
}