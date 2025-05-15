import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseConnection {
	 
	private static final String  url = "jdbc:mysql://localhost:3306/pokemon_db";
	 
	 
	 public static Connection conectar () {
	       
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			System.out.println("Error al cargar el driver de MySQL" + e.getMessage());
		}
	       Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(url, "root", "");
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos" + e.getMessage());
		}
	       return  conexion;
	    }
	 
	 public static ArrayList<Pokemon> obtenerTodosLosPokemons() {
		    ArrayList<Pokemon> lista = new ArrayList<>();

		    String sql = "SELECT * FROM pokemon";

		    try (Connection conexion = conectar();
		         Statement st = conexion.createStatement();
		         ResultSet rs = st.executeQuery(sql)) {

		        while (rs.next()) {
		            Pokemon p = new Pokemon();
		            p.setId(rs.getInt("id"));
		            p.setNombre(rs.getString("nombre"));
		            p.setTipo1(rs.getString("tipo1"));
		            p.setTipo2(rs.getString("tipo2"));
		            p.setAltura(rs.getDouble("altura"));
		            p.setPeso(rs.getInt("peso"));
		            p.setHp(rs.getInt("hp"));
		            p.setAtaque(rs.getInt("ataque"));
		            p.setDefensa(rs.getInt("defensa"));
		            p.setDescripcion(rs.getString("descripcion"));
		            p.setImagen(rs.getString("imagen"));

		            lista.add(p);
		        }

		    } catch (SQLException e) {
		        System.out.println("Error al obtener pokémon: " + e.getMessage());
		    }

		    return lista;
		}

	 
}
		/* listar entrenadores (mostrar)
			añadirentrenador
			listar equipos (mostrar)
			añadirpokemon*/
		
	
	
	
	
	

