package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Time;
import persistence.GenericDAOMySQL;
import persistence.TimeCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Time")
public class TimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TimeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("time.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmdType = request.getParameter("button");

		String teamId = request.getParameter("teamId");
		String teamName = request.getParameter("teamName");
		String teamCityName = request.getParameter("teamCityName");

		String consoleOutput = "";
		String consoleError = "";

		Time t = new Time();
		List<Time> times = new ArrayList<>();

		if (!cmdType.contains("List")) {
			t.setIdTime(Integer.parseInt(teamId));
		}

		if (cmdType.contains("Register") || cmdType.contains("Update")) {
			t.setNomeTime(teamName);
			t.setCidadeTime(teamCityName);
		}

		try {
			if (cmdType.contains("Search")) {
				t = procurarTime(t);
			}
			if (cmdType.contains("Register")) {
				registrarTime(t);
				consoleOutput = "Team registered successfully";
				t = null;
			}
			if (cmdType.contains("Update")) {
				atualizarTime(t);
				consoleOutput = "Team updated successfully";
				t = null;
			}
			if (cmdType.contains("Remove")) {
				removerTime(t);
				consoleOutput = "Team removed successfully";
				t = null;
			}
			if (cmdType.contains("List")) {
				times = listarTimes();
			}
		} catch (SQLException | ClassNotFoundException e) {
			consoleError = e.getMessage();
		} finally {
			request.setAttribute("consoleOutput", consoleOutput);
			request.setAttribute("consoleError", consoleError);
			request.setAttribute("team", t);
			request.setAttribute("teams", times);

			RequestDispatcher rd = request.getRequestDispatcher("team.jsp");
			rd.forward(request, response);
		}

	}

	private Time procurarTime(Time t) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		t = tc.procurar(t);
		return t;
	}

	private void registrarTime(Time t) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		tc.registrar(t);
	}

	private void atualizarTime(Time t) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		tc.atualizar(t);
	}

	private void removerTime(Time t) throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		tc.deletar(t);
	}

	private List<Time> listarTimes() throws SQLException, ClassNotFoundException {
		GenericDAOMySQL genericDAOMySQL = new GenericDAOMySQL();
		TimeCrud tc = new TimeCrud(genericDAOMySQL);

		List<Time> times = tc.lista();
		return times;
	}

}
