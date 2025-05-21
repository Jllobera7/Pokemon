import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/EntrenadorServlet")
public class EntrenadorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Asegurar codificación UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        ArrayList<Entrenador> entrenadores = DatabaseConnection.obtenerTodosLosEntrenadores();
        ArrayList<Pokemon> pokemons = DatabaseConnection.obtenerTodosLosPokemons();
        String mensaje = request.getParameter("mensaje") != null ? URLDecoder.decode(request.getParameter("mensaje"), "UTF-8") : "";
        String resultadoBatalla = request.getParameter("resultadoBatalla") != null ? URLDecoder.decode(request.getParameter("resultadoBatalla"), "UTF-8") : "";

        // Mapa de colores por tipo de Pokémon
        Map<String, String> coloresPorTipo = new HashMap<>();
        coloresPorTipo.put("Fuego", "#FF4422");
        coloresPorTipo.put("Agua", "#3399FF");
        coloresPorTipo.put("Planta", "#77CC55");
        coloresPorTipo.put("Eléctrico", "#FFCC33");
        coloresPorTipo.put("Normal", "#AAAA99");
        coloresPorTipo.put("Volador", "#8899FF");
        coloresPorTipo.put("Lucha", "#BB5544");
        coloresPorTipo.put("Veneno", "#AA5599");
        coloresPorTipo.put("Tierra", "#DDAA44");
        coloresPorTipo.put("Roca", "#BBAA66");
        coloresPorTipo.put("Bicho", "#AABB22");
        coloresPorTipo.put("Fantasma", "#6666BB");
        coloresPorTipo.put("Acero", "#AAAABB");
        coloresPorTipo.put("Psíquico", "#FF5599");
        coloresPorTipo.put("Hielo", "#77DDFF");
        coloresPorTipo.put("Dragón", "#7766EE");
        coloresPorTipo.put("Siniestro", "#775544");
        coloresPorTipo.put("Hada", "#EE99EE");

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
            .append("<html>")
            .append("<head>")
            .append("<meta charset='UTF-8'>")
            .append("<title>Pokémon: Gestión de Entrenadores</title>")
            .append("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>")
            .append("<link href='https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Poppins:wght@600&display=swap' rel='stylesheet'>")
            .append("<style>")
            .append("body {")
            .append("  background: linear-gradient(to bottom, #4AB8FF, #E6F0FA);")
            .append("  background-size: cover; background-attachment: fixed; color: #333; font-family: 'Montserrat', sans-serif;")
            .append("}")
            .append(".container { max-width: 1200px; background: rgba(255, 255, 255, 0.95); padding: 20px; border-radius: 20px; box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); }")
            .append("h1 { font-family: 'Poppins', sans-serif; color: #D32F2F; text-align: center; margin-bottom: 30px; }")
            .append(".card { border: none; border-radius: 15px; overflow: hidden; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); }")
            .append(".card-header { background: linear-gradient(to right, #D32F2F, #FF5252); color: #fff; font-family: 'Poppins', sans-serif; font-size: 1.2em; }")
            .append(".form-label { color: #333; font-weight: 600; }")
            .append(".form-control, .form-select { border-radius: 10px; border: 1px solid #B0BEC5; background: #F5F7FA; transition: border-color 0.3s; }")
            .append(".form-control:focus, .form-select:focus { border-color: #D32F2F; box-shadow: 0 0 5px rgba(211, 47, 47, 0.5); }")
            .append(".btn-modern {")
            .append("  background: linear-gradient(to right, #D32F2F, #FF5252); color: #fff; border: none; border-radius: 25px; padding: 10px 20px;")
            .append("  font-family: 'Poppins', sans-serif; font-weight: 600; transition: transform 0.2s, box-shadow 0.2s;")
            .append("}")
            .append(".btn-modern:hover { transform: translateY(-2px); box-shadow: 0 4px 10px rgba(211, 47, 47, 0.4); }")
            .append(".alert-success { background: #4CAF50; border-color: #388E3C; color: #fff; border-radius: 10px; }")
            .append(".alert-danger { background: #D32F2F; border-color: #B71C1C; color: #fff; border-radius: 10px; }")
            .append(".alert-primary { background: #0288D1; border-color: #0277BD; color: #fff; border-radius: 10px; }")
            .append(".trainer-card {")
            .append("  background: #ECEFF1; border-radius: 15px; padding: 20px; margin-bottom: 20px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);")
            .append("  transition: transform 0.2s;")
            .append("}")
            .append(".trainer-card:hover { transform: translateY(-5px); }")
            .append(".trainer-card h4 { color: #D32F2F; font-family: 'Poppins', sans-serif; }")
            .append(".pokedex-card {")
            .append("  background: #fff; border-radius: 15px; padding: 10px; margin-bottom: 20px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);")
            .append("  border: 2px solid #B0BEC5; transition: transform 0.2s;")
            .append("}")
            .append(".pokedex-card:hover { transform: scale(1.05); }")
            .append(".pokedex-card img { max-width: 140px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); }")
            .append(".pokedex-card .card-body { border-radius: 10px; padding: 15px; color: #333; }")
            .append(".type-badge {")
            .append("  display: inline-block; padding: 5px 12px; border-radius: 15px; color: #fff; font-size: 0.9em; margin: 2px; font-weight: 600;")
            .append("}")
            .append("</style>")
            .append("</head>")
            .append("<body>")
            .append("<div class='container mt-5'>")
            .append("<h1>Pokémon: Gestión de Entrenadores</h1>");

        // Mostrar mensaje de éxito o error
        if (!mensaje.isEmpty()) {
            html.append("<div class='alert ").append(mensaje.startsWith("Error") ? "alert-danger" : "alert-success").append("'>")
                .append(mensaje).append("</div>");
        }

        // Mostrar resultado de la batalla
        if (!resultadoBatalla.isEmpty()) {
            html.append("<div class='alert alert-primary'>").append(resultadoBatalla).append("</div>");
        }

        // Formulario para crear entrenador
        html.append("<div class='card mb-4'>")
            .append("<div class='card-header'>Crear Nuevo Entrenador</div>")
            .append("<div class='card-body'>")
            .append("<form action='EntrenadorServlet' method='post'>")
            .append("<input type='hidden' name='accion' value='crearEntrenador'>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Nombre</label>")
            .append("<input type='text' class='form-control' name='nombre' required>")
            .append("</div>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Edad</label>")
            .append("<input type='number' class='form-control' name='edad' required>")
            .append("</div>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Región</label>")
            .append("<input type='text' class='form-control' name='region'>")
            .append("</div>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Descripción</label>")
            .append("<textarea class='form-control' name='descripcion'></textarea>")
            .append("</div>")
            .append("<button type='submit' class='btn btn-modern'>Crear Entrenador</button>")
            .append("</form>")
            .append("</div>")
            .append("</div>");

        // Formulario para añadir Pokémon al equipo
        html.append("<div class='card mb-4'>")
            .append("<div class='card-header'>Añadir Pokémon al Equipo</div>")
            .append("<div class='card-body'>")
            .append("<form action='EntrenadorServlet' method='post'>")
            .append("<input type='hidden' name='accion' value='anadirPokemon'>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Seleccionar Entrenador</label>")
            .append("<select class='form-select' name='idEntrenador' required>")
            .append("<option value=''>-- Selecciona un entrenador --</option>");
        
        for (Entrenador e : entrenadores) {
            html.append("<option value='").append(e.getIdEntrenador()).append("'>")
                .append(e.getNombre()).append("</option>");
        }
        
        html.append("</select>")
            .append("</div>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>IDs de Pokémon (separados por comas)</label>")
            .append("<input type='text' class='form-control' name='idPokemons' placeholder='Ejemplo: 1,2,3' required>")
            .append("</div>")
            .append("<button type='submit' class='btn btn-modern'>Añadir Pokémon</button>")
            .append("</form>")
            .append("</div>")
            .append("</div>");

        // Formulario para simular batalla
        html.append("<div class='card mb-4'>")
            .append("<div class='card-header'>Simular Batalla</div>")
            .append("<div class='card-body'>")
            .append("<form action='EntrenadorServlet' method='post'>")
            .append("<input type='hidden' name='accion' value='simularBatalla'>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Entrenador 1</label>")
            .append("<select class='form-select' name='idEntrenador1' required>")
            .append("<option value=''>-- Selecciona el primer entrenador --</option>");
        
        for (Entrenador e : entrenadores) {
            html.append("<option value='").append(e.getIdEntrenador()).append("'>")
                .append(e.getNombre()).append("</option>");
        }
        
        html.append("</select>")
            .append("</div>")
            .append("<div class='mb-3'>")
            .append("<label class='form-label'>Entrenador 2</label>")
            .append("<select class='form-select' name='idEntrenador2' required>")
            .append("<option value=''>-- Selecciona el segundo entrenador --</option>");
        
        for (Entrenador e : entrenadores) {
            html.append("<option value='").append(e.getIdEntrenador()).append("'>")
                .append(e.getNombre()).append("</option>");
        }
        
        html.append("</select>")
            .append("</div>")
            .append("<button type='submit' class='btn btn-modern'>Simular Batalla</button>")
            .append("</form>")
            .append("</div>")
            .append("</div>");

        // Lista de entrenadores como tarjetas
        html.append("<div class='card mb-4'>")
            .append("<div class='card-header'>Lista de Entrenadores</div>")
            .append("<div class='card-body'>");

        for (Entrenador e : entrenadores) {
            html.append("<div class='trainer-card'>")
                .append("<h4>").append(e.getNombre()).append(" (ID: ").append(e.getIdEntrenador()).append(")</h4>")
                .append("<p><strong>Edad:</strong> ").append(e.getEdad()).append("</p>")
                .append("<p><strong>Región:</strong> ").append(e.getRegion()).append("</p>")
                .append("<p><strong>Descripción:</strong> ").append(e.getDescripcion()).append("</p>")
                .append("<p><strong>Pokémon:</strong> ");
            ArrayList<Pokemon> pokemonsEntrenador = e.getPokemons();
            if (pokemonsEntrenador.isEmpty()) {
                html.append("Sin Pokémon");
            } else {
                for (Pokemon p : pokemonsEntrenador) {
                    html.append(p.getNombre()).append(" (HP: ").append(p.getHp())
                        .append(", Ataque: ").append(p.getAtaque())
                        .append(", Defensa: ").append(p.getDefensa()).append("), ");
                }
                // Eliminar la última coma y espacio
                html.setLength(html.length() - 2);
            }
            html.append("</p>")
                .append("</div>");
        }
        
        html.append("</div>")
            .append("</div>");

        // Minipokedex con estilo Pokémon moderno
        html.append("<div class='card mb-4'>")
            .append("<div class='card-header'>Minipokedex</div>")
            .append("<div class='card-body'>")
            .append("<div class='row'>");

        for (Pokemon p : pokemons) {
            String colorTipo = coloresPorTipo.getOrDefault(p.getTipo1(), "#B0BEC5");
            html.append("<div class='col-md-4 mb-3'>")
                .append("<div class='pokedex-card'>")
                .append("<div class='row g-0'>")
                .append("<div class='col-md-4 d-flex align-items-center'>")
                .append("<img src='").append(p.getImagen() != null ? p.getImagen() : "https://via.placeholder.com/140")
                .append("' class='img-fluid' alt='").append(p.getNombre()).append("'>")
                .append("</div>")
                .append("<div class='col-md-8'>")
                .append("<div class='card-body' style='background: linear-gradient(to bottom, ").append(colorTipo).append(", #FFFFFF);'>")
                .append("<h5 class='card-title'>#").append(p.getId()).append(" ").append(p.getNombre()).append("</h5>")
                .append("<p class='card-text'>")
                .append("<span class='type-badge' style='background: ").append(coloresPorTipo.getOrDefault(p.getTipo1(), "#B0BEC5")).append("'>")
                .append(p.getTipo1()).append("</span>")
                .append(p.getTipo2() != null ? "<span class='type-badge' style='background: " + coloresPorTipo.getOrDefault(p.getTipo2(), "#B0BEC5") + "'>" + p.getTipo2() + "</span>" : "")
                .append("<br>")
                .append("<strong>Altura:</strong> ").append(p.getAltura()).append(" m<br>")
                .append("<strong>Peso:</strong> ").append(p.getPeso()).append(" kg<br>")
                .append("<strong>HP:</strong> ").append(p.getHp()).append("<br>")
                .append("<strong>Ataque:</strong> ").append(p.getAtaque()).append("<br>")
                .append("<strong>Defensa:</strong> ").append(p.getDefensa()).append("<br>")
                .append("<strong>Descripción:</strong> ").append(p.getDescripcion())
                .append("</p>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</div>");
        }

        html.append("</div>")
            .append("</div>")
            .append("</div>")
            .append("</div>")
            .append("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js'></script>")
            .append("</body>")
            .append("</html>");

        response.getWriter().write(html.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Asegurar codificación UTF-8 para los parámetros
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("crearEntrenador".equals(accion)) {
            String nombre = request.getParameter("nombre");
            int edad = Integer.parseInt(request.getParameter("edad"));
            String region = request.getParameter("region");
            String descripcion = request.getParameter("descripcion");

            Entrenador entrenador = new Entrenador(nombre, edad, region, descripcion);
            DatabaseConnection.añadirEntrenador(entrenador);

            response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Entrenador creado con éxito", "UTF-8"));
        } else if ("anadirPokemon".equals(accion)) {
            int idEntrenador = Integer.parseInt(request.getParameter("idEntrenador"));
            String idPokemonsStr = request.getParameter("idPokemons");

            // Validar entrada
            if (idPokemonsStr == null || idPokemonsStr.trim().isEmpty()) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: Ingresa al menos un ID de Pokémon", "UTF-8"));
                return;
            }

            // Dividir la cadena y limpiar espacios
            String[] idPokemonsArray = Arrays.stream(idPokemonsStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);

            // Verificar que haya al menos un ID válido
            if (idPokemonsArray.length == 0) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: Ingresa IDs válidos separados por comas", "UTF-8"));
                return;
            }

            // Convertir a int[]
            int[] idPokemons = new int[idPokemonsArray.length];
            try {
                for (int i = 0; i < idPokemonsArray.length; i++) {
                    idPokemons[i] = Integer.parseInt(idPokemonsArray[i]);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: Los IDs deben ser números válidos", "UTF-8"));
                return;
            }

            // Añadir Pokémon al equipo
            boolean añadido = DatabaseConnection.añadirPokemonAEquipo(idEntrenador, idPokemons);
            String mensaje = añadido ? "Pokémon añadidos al equipo" : "Error: No se pudieron añadir los Pokémon (límite de 6 alcanzado, Pokémon duplicados o IDs inválidos)";
            
            response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode(mensaje, "UTF-8"));
        } else if ("simularBatalla".equals(accion)) {
            int idEntrenador1 = Integer.parseInt(request.getParameter("idEntrenador1"));
            int idEntrenador2 = Integer.parseInt(request.getParameter("idEntrenador2"));

            // Validar que no sea el mismo entrenador
            if (idEntrenador1 == idEntrenador2) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: No puedes seleccionar el mismo entrenador para la batalla", "UTF-8"));
                return;
            }

            // Obtener los entrenadores
            Entrenador entrenador1 = null, entrenador2 = null;
            for (Entrenador e : DatabaseConnection.obtenerTodosLosEntrenadores()) {
                if (e.getIdEntrenador() == idEntrenador1) {
                    entrenador1 = e;
                }
                if (e.getIdEntrenador() == idEntrenador2) {
                    entrenador2 = e;
                }
            }

            // Validar que los entrenadores existan y tengan Pokémon
            if (entrenador1 == null || entrenador2 == null) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: Uno o ambos entrenadores no existen", "UTF-8"));
                return;
            }
            if (entrenador1.getPokemons().isEmpty() || entrenador2.getPokemons().isEmpty()) {
                response.sendRedirect("EntrenadorServlet?mensaje=" + java.net.URLEncoder.encode("Error: Uno o ambos entrenadores no tienen Pokémon", "UTF-8"));
                return;
            }

            // Calcular puntajes de batalla
            int puntaje1 = 0, puntaje2 = 0;
            for (Pokemon p : entrenador1.getPokemons()) {
                puntaje1 += p.getHp() + p.getAtaque() + p.getDefensa();
            }
            for (Pokemon p : entrenador2.getPokemons()) {
                puntaje2 += p.getHp() + p.getAtaque() + p.getDefensa();
            }

            // Determinar el ganador
            String resultado;
            if (puntaje1 > puntaje2) {
                resultado = "¡" + entrenador1.getNombre() + " gana la batalla! (Puntaje: " + puntaje1 + " vs " + puntaje2 + ")";
            } else if (puntaje2 > puntaje1) {
                resultado = "¡" + entrenador2.getNombre() + " gana la batalla! (Puntaje: " + puntaje1 + " vs " + puntaje2 + ")";
            } else {
                resultado = "¡La batalla termina en empate! (Puntaje: " + puntaje1 + " vs " + puntaje2 + ")";
            }

            response.sendRedirect("EntrenadorServlet?resultadoBatalla=" + java.net.URLEncoder.encode(resultado, "UTF-8"));
        }
    }
}