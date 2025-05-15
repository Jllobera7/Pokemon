

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class PokemonServlet
 */
@WebServlet("/PokemonServlet")
public class PokemonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PokemonServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        ArrayList<Pokemon> pokemons = DatabaseConnection.obtenerTodosLosPokemons();
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
            .append("<html><head><meta charset='UTF-8'><title>Listado de Pokémon</title></head><body>")
            .append("<h1>Listado de Pokémon</h1>")
            .append("<table border='1'>")
            .append("<tr><th>ID</th><th>Nombre</th><th>Tipo 1</th><th>Tipo 2</th><th>HP</th><th>Ataque</th><th>Defensa</th><th>Imagen</th></tr>");
        
        for (Pokemon p : pokemons) {
            html.append("<tr>")
                .append("<td>").append(p.getId()).append("</td>")
                .append("<td>").append(p.getNombre()).append("</td>")
                .append("<td>").append(p.getTipo1()).append("</td>")
                .append("<td>").append(p.getTipo2()).append("</td>")
                .append("<td>").append(p.getHp()).append("</td>")
                .append("<td>").append(p.getAtaque()).append("</td>")
                .append("<td>").append(p.getDefensa()).append("</td>")
                .append("<td><img src='").append(p.getImagen()).append("' width='80'></td>")
                .append("</tr>");
        }
        
        html.append("</table></body></html>");
        response.getWriter().write(html.toString());
    }
	
	

}
