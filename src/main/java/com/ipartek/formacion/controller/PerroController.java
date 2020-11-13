package com.ipartek.formacion.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.modelo.PerroDAOSqlite;
import com.ipartek.formacion.pojo.Perro;

/**
 * @WebServlet("/perro") es la URL donde escucha este controlador
 */
@WebServlet("/perro")
public class PerroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PerroDAOSqlite dao = PerroDAOSqlite.getInstance();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Perro> lista = new ArrayList<Perro>();

		// conseguir perros llamado al modelo
		try {
			
			lista = dao.listar();

		} catch (Exception e) {

			e.printStackTrace();

		} 

		// enviarlos a la JSP
		request.setAttribute("perros", lista);

		// ir a la JSP
		request.getRequestDispatcher("perros.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		//recibri datos del formulario, fijaros en el input el atributo 'name'
		String parametroNombre = request.getParameter("nombre");
		String raza = request.getParameter("raza");
		
		Perro p = new Perro();
		p.setNombre(parametroNombre);
		p.setRaza(raza);
		
		//guardarlo en la bbdd
		try {
			dao.crear(p);
			
		} catch (Exception e) {			
			e.printStackTrace();
			request.setAttribute("mensaje", "Lo sentimos pero " + p.getNombre() +" de perro ya existe" );
		}
		
		// enviarlos a la JSP
		request.setAttribute("perro", p);

		// ir a la JSP
		request.getRequestDispatcher("perro.jsp").forward(request, response);
	}

}
