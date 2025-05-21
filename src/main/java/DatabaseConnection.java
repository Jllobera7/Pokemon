import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
    
    private static final String url = "jdbc:mysql://localhost:3306/pokemon_db";
    private static final String user = "root";
    private static final String password = "";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de MySQL: " + e.getMessage());
        }
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
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

    public static void añadirEntrenador(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador (nombre, edad, region, descripcion) VALUES ('" 
            + entrenador.getNombre() + "', " 
            + entrenador.getEdad() + ", '" 
            + entrenador.getRegion() + "', '" 
            + entrenador.getDescripcion() + "')";
        try (Connection conexion = conectar();
             Statement st = conexion.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al añadir entrenador: " + e.getMessage());
        }
    }

    public static ArrayList<Entrenador> obtenerTodosLosEntrenadores() {
        ArrayList<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador";
        try (Connection conexion = conectar();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Entrenador e = new Entrenador();
                e.setIdEntrenador(rs.getInt("idEntrenador"));
                e.setNombre(rs.getString("nombre"));
                e.setEdad(rs.getInt("edad"));
                e.setRegion(rs.getString("region"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setPokemons(obtenerPokemonsPorEntrenador(rs.getInt("idEntrenador")));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener entrenadores: " + e.getMessage());
        }
        return lista;
    }

    public static boolean añadirPokemonAEquipo(int idEntrenador, int[] idPokemons) {
        // Verificar cuántos Pokémon ya tiene el entrenador
        String countSql = "SELECT COUNT(*) FROM equipo WHERE idEntrenador = " + idEntrenador;
        int pokemonActuales = 0;
        try (Connection conexion = conectar();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(countSql)) {
            if (rs.next()) {
                pokemonActuales = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar equipo: " + e.getMessage());
            return false;
        }

        // Verificar si se excedería el límite de 6 Pokémon
        if (pokemonActuales + idPokemons.length > 6) {
            return false; // Límite excedido
        }

        // Verificar duplicados y existencia de Pokémon
        for (int idPokemon : idPokemons) {
            // Verificar si el Pokémon ya está en el equipo
            String checkSql = "SELECT * FROM equipo WHERE idEntrenador = " + idEntrenador + " AND idPokemon = " + idPokemon;
            try (Connection conexion = conectar();
                 Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery(checkSql)) {
                if (rs.next()) {
                    return false; // Pokémon ya está en el equipo
                }
            } catch (SQLException e) {
                System.out.println("Error al verificar duplicado: " + e.getMessage());
                return false;
            }

            // Verificar si el Pokémon existe
            String existSql = "SELECT * FROM pokemon WHERE id = " + idPokemon;
            boolean pokemonExiste = false;
            try (Connection conexion = conectar();
                 Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery(existSql)) {
                if (rs.next()) {
                    pokemonExiste = true;
                }
            } catch (SQLException e) {
                System.out.println("Error al verificar existencia de Pokémon: " + e.getMessage());
                return false;
            }
            if (!pokemonExiste) {
                return false; // Pokémon no existe
            }
        }

        // Añadir Pokémon al equipo
        boolean exito = true;
        for (int idPokemon : idPokemons) {
            String sql = "INSERT INTO equipo (idEntrenador, idPokemon) VALUES (" + idEntrenador + ", " + idPokemon + ")";
            try (Connection conexion = conectar();
                 Statement st = conexion.createStatement()) {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println("Error al añadir Pokémon al equipo: " + e.getMessage());
                exito = false;
            }
        }
        return exito;
    }

    public static ArrayList<Pokemon> obtenerPokemonsPorEntrenador(int idEntrenador) {
        ArrayList<Pokemon> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM pokemon p JOIN equipo e ON p.id = e.idPokemon WHERE e.idEntrenador = " + idEntrenador;
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
            System.out.println("Error al obtener pokémon del entrenador: " + e.getMessage());
        }
        return lista;
    }
}