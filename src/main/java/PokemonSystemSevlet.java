import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@WebServlet("/PokemonSystem")
public class PokemonSystemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) action = "view";
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
            .append("<html><head>")
            .append("<meta charset='UTF-8'>")
            .append("<title>Sistema Pokémon</title>")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 20px; }")
            .append(".container { display: flex; flex-wrap: wrap; }")
            .append(".section { border: 2px solid #333; border-radius: 10px; padding: 15px; margin: 10px; flex: 1; min-width: 300px; }")
            .append(".pokemon-card { border: 1px solid #ddd; border-radius: 5px; padding: 10px; margin: 5px; display: inline-block; width: 120px; text-align: center; }")
            .append(".pokemon-card img { width: 80px; height: 80px; }")
            .append(".trainer-card { border: 1px solid #333; border-radius: 5px; padding: 10px; margin: 5px; background-color: #f0f0f0; }")
            .append(".team-display { display: flex; flex-wrap: wrap; }")
            .append(".team-slot { width: 100px; height: 120px; border: 1px dashed #999; margin: 5px; text-align: center; }")
            .append(".battle-log { height: 200px; overflow-y: scroll; border: 1px solid #333; padding: 10px; background-color: #222; color: #fff; }")
            .append("nav { background-color: #333; padding: 10px; margin-bottom: 20px; border-radius: 5px; }")
            .append("nav a { color: white; text-decoration: none; margin-right: 15px; }")
            .append("</style>")
            .append("</head><body>")
            .append("<nav>")
            .append("<a href='PokemonSystem?action=view'>Pokédex</a>")
            .append("<a href='PokemonSystem?action=viewTrainers'>Entrenadores</a>")
            .append("<a href='PokemonSystem?action=viewBattles'>Batallas</a>")
            .append("</nav>");
        
        switch (action) {
            case "view":
                showPokedex(html);
                break;
            case "viewTrainers":
                showTrainers(html);
                break;
            case "addTrainerForm":
                showAddTrainerForm(html);
                break;
            case "addTrainer":
                addTrainer(request, html);
                showTrainers(html);
                break;
            case "viewTrainer":
                int trainerId = Integer.parseInt(request.getParameter("id"));
                showTrainerDetails(trainerId, html);
                break;
            case "addPokemonForm":
                trainerId = Integer.parseInt(request.getParameter("trainerId"));
                showAddPokemonForm(trainerId, html);
                break;
            case "addPokemon":
                trainerId = Integer.parseInt(request.getParameter("trainerId"));
                int pokemonId = Integer.parseInt(request.getParameter("pokemonId"));
                addPokemonToTeam(trainerId, pokemonId, html);
                showTrainerDetails(trainerId, html);
                break;
            case "removePokemon":
                trainerId = Integer.parseInt(request.getParameter("trainerId"));
                pokemonId = Integer.parseInt(request.getParameter("pokemonId"));
                removePokemonFromTeam(trainerId, pokemonId, html);
                showTrainerDetails(trainerId, html);
                break;
            case "viewBattles":
                showBattleSection(html);
                break;
            case "startBattle":
                int trainer1Id = Integer.parseInt(request.getParameter("trainer1"));
                int trainer2Id = Integer.parseInt(request.getParameter("trainer2"));
                simulateBattle(trainer1Id, trainer2Id, html);
                showBattleSection(html);
                break;
            default:
                showPokedex(html);
        }
        
        html.append("</body></html>");
        response.getWriter().write(html.toString());
    }
    
    private void showPokedex(StringBuilder html) {
        ArrayList<Pokemon> pokemons = DatabaseConnection.obtenerTodosLosPokemons();
        
        html.append("<div class='section'>")
            .append("<h2>Pokédex</h2>")
            .append("<div class='container'>");
        
        for (Pokemon p : pokemons) {
            html.append("<div class='pokemon-card'>")
                .append("<img src='").append(p.getImagen()).append("' alt='").append(p.getNombre()).append("'>")
                .append("<h3>").append(p.getNombre()).append("</h3>")
                .append("<p>").append(p.getTipo1());
            
            if (p.getTipo2() != null && !p.getTipo2().isEmpty()) {
                html.append("/").append(p.getTipo2());
            }
            
            html.append("</p>")
                .append("<p>HP: ").append(p.getHp()).append("</p>")
                .append("</div>");
        }
        
        html.append("</div></div>");
    }
    
    private void showTrainers(StringBuilder html) {
        ArrayList<Entrenador> entrenadores = DatabaseConnection.obtenerTodosLosEntrenadores();
        
        html.append("<div class='section'>")
            .append("<h2>Entrenadores</h2>")
            .append("<a href='PokemonSystem?action=addTrainerForm'>Agregar Nuevo Entrenador</a>")
            .append("<div class='container'>");
        
        for (Entrenador e : entrenadores) {
            html.append("<div class='trainer-card'>")
                .append("<h3>").append(e.getNombre()).append("</h3>")
                .append("<p>Región: ").append(e.getRegion()).append("</p>")
                .append("<p>Edad: ").append(e.getEdad()).append("</p>")
                .append("<div class='team-display'>");
            
            for (Pokemon p : e.getPokemons()) {
                html.append("<div class='pokemon-card'>")
                    .append("<img src='").append(p.getImagen()).append("' alt='").append(p.getNombre()).append("'>")
                    .append("<p>").append(p.getNombre()).append("</p>")
                    .append("</div>");
            }
            
            for (int i = e.getPokemons().size(); i < 6; i++) {
                html.append("<div class='team-slot'>Vacío</div>");
            }
            
            html.append("</div>")
                .append("<a href='PokemonSystem?action=viewTrainer&id=").append(e.getIdEntrenador()).append("'>Ver Detalles</a>")
                .append("</div>");
        }
        
        html.append("</div></div>");
    }
    
    private void showAddTrainerForm(StringBuilder html) {
        html.append("<div class='section'>")
            .append("<h2>Agregar Nuevo Entrenador</h2>")
            .append("<form method='post' action='PokemonSystem'>")
            .append("<input type='hidden' name='action' value='addTrainer'>")
            .append("Nombre: <input type='text' name='nombre' required><br>")
            .append("Edad: <input type='number' name='edad' required><br>")
            .append("Región: <input type='text' name='region' required><br>")
            .append("Descripción: <textarea name='descripcion'></textarea><br>")
            .append("<input type='submit' value='Agregar'>")
            .append("</form>")
            .append("<a href='PokemonSystem?action=viewTrainers'>Cancelar</a>")
            .append("</div>");
    }
    
    private void addTrainer(HttpServletRequest request, StringBuilder html) {
        Entrenador e = new Entrenador();
        e.setNombre(request.getParameter("nombre"));
        e.setEdad(Integer.parseInt(request.getParameter("edad")));
        e.setRegion(request.getParameter("region"));
        e.setDescripcion(request.getParameter("descripcion"));
        e.setPokemons(new ArrayList<>());
        
        boolean success = DatabaseConnection.agregarEntrenador(e);
        
        if (success) {
            html.append("<script>alert('Entrenador agregado con éxito');</script>");
        } else {
            html.append("<script>alert('Error al agregar entrenador');</script>");
        }
    }
    
    private void showTrainerDetails(int trainerId, StringBuilder html) {
        Entrenador e = DatabaseConnection.obtenerEntrenadorPorId(trainerId);
        
        if (e == null) {
            html.append("<div class='section'><p>Entrenador no encontrado</p></div>");
            return;
        }
        
        html.append("<div class='section'>")
            .append("<h2>").append(e.getNombre()).append("</h2>")
            .append("<p>Región: ").append(e.getRegion()).append("</p>")
            .append("<p>Edad: ").append(e.getEdad()).append("</p>")
            .append("<p>").append(e.getDescripcion()).append("</p>")
            .append("<h3>Equipo Pokémon</h3>")
            .append("<div class='team-display'>");
        
        for (Pokemon p : e.getPokemons()) {
            html.append("<div class='pokemon-card'>")
                .append("<img src='").append(p.getImagen()).append("' alt='").append(p.getNombre()).append("'>")
                .append("<h3>").append(p.getNombre()).append("</h3>")
                .append("<p>").append(p.getTipo1());
            
            if (p.getTipo2() != null && !p.getTipo2().isEmpty()) {
                html.append("/").append(p.getTipo2());
            }
            
            html.append("</p>")
                .append("<p>HP: ").append(p.getHp()).append("</p>")
                .append("<p>Ataque: ").append(p.getAtaque()).append("</p>")
                .append("<p>Defensa: ").append(p.getDefensa()).append("</p>")
                .append("<a href='PokemonSystem?action=removePokemon&trainerId=").append(trainerId)
                .append("&pokemonId=").append(p.getId()).append("'>Quitar</a>")
                .append("</div>");
        }
        
        for (int i = e.getPokemons().size(); i < 6; i++) {
            html.append("<div class='team-slot'>")
                .append("<a href='PokemonSystem?action=addPokemonForm&trainerId=").append(trainerId).append("'>")
                .append("Agregar Pokémon")
                .append("</a>")
                .append("</div>");
        }
        
        html.append("</div>")
            .append("<a href='PokemonSystem?action=viewTrainers'>Volver a Entrenadores</a>")
            .append("</div>");
    }
    
    private void showAddPokemonForm(int trainerId, StringBuilder html) {
        ArrayList<Pokemon> pokemons = DatabaseConnection.obtenerTodosLosPokemons();
        
        html.append("<div class='section'>")
            .append("<h2>Agregar Pokémon al Equipo</h2>")
            .append("<form method='post' action='PokemonSystem'>")
            .append("<input type='hidden' name='action' value='addPokemon'>")
            .append("<input type='hidden' name='trainerId' value='").append(trainerId).append("'>")
            .append("<div class='container'>");
        
        for (Pokemon p : pokemons) {
            html.append("<div class='pokemon-card'>")
                .append("<input type='radio' name='pokemonId' value='").append(p.getId()).append("' required>")
                .append("<img src='").append(p.getImagen()).append("' alt='").append(p.getNombre()).append("'>")
                .append("<h3>").append(p.getNombre()).append("</h3>")
                .append("<p>").append(p.getTipo1());
            
            if (p.getTipo2() != null && !p.getTipo2().isEmpty()) {
                html.append("/").append(p.getTipo2());
            }
            
            html.append("</p>")
                .append("<p>HP: ").append(p.getHp()).append("</p>")
                .append("</div>");
        }
        
        html.append("</div>")
            .append("<input type='submit' value='Agregar al Equipo'>")
            .append("<a href='PokemonSystem?action=viewTrainer&id=").append(trainerId).append("'>Cancelar</a>")
            .append("</form>")
            .append("</div>");
    }
    
    private void addPokemonToTeam(int trainerId, int pokemonId, StringBuilder html) {
        boolean success = DatabaseConnection.agregarPokemonAEquipo(trainerId, pokemonId);
        
        if (success) {
            html.append("<script>alert('Pokémon agregado al equipo con éxito');</script>");
        } else {
            html.append("<script>alert('Error: El equipo ya tiene 6 Pokémon');</script>");
        }
    }
    
    private void removePokemonFromTeam(int trainerId, int pokemonId, StringBuilder html) {
        boolean success = DatabaseConnection.eliminarPokemonDeEquipo(trainerId, pokemonId);
        
        if (success) {
            html.append("<script>alert('Pokémon eliminado del equipo');</script>");
        } else {
            html.append("<script>alert('Error al eliminar Pokémon');</script>");
        }
    }
    
    private void showBattleSection(StringBuilder html) {
        ArrayList<Entrenador> entrenadores = DatabaseConnection.obtenerTodosLosEntrenadores();
        
        html.append("<div class='section'>")
            .append("<h2>Batalla Pokémon</h2>")
            .append("<form method='post' action='PokemonSystem'>")
            .append("<input type='hidden' name='action' value='startBattle'>")
            .append("<h3>Selecciona los entrenadores</h3>")
            .append("<div style='display: flex; justify-content: space-around;'>")
            .append("<div>")
            .append("<h4>Entrenador 1:</h4>")
            .append("<select name='trainer1' required>");
        
        for (Entrenador e : entrenadores) {
            html.append("<option value='").append(e.getIdEntrenador()).append("'>")
                .append(e.getNombre()).append("</option>");
        }
        
        html.append("</select>")
            .append("</div>")
            .append("<div>")
            .append("<h4>Entrenador 2:</h4>")
            .append("<select name='trainer2' required>");
        
        for (Entrenador e : entrenadores) {
            html.append("<option value='").append(e.getIdEntrenador()).append("'>")
                .append(e.getNombre()).append("</option>");
        }
        
        html.append("</select>")
            .append("</div>")
            .append("</div>")
            .append("<br><input type='submit' value='Comenzar Batalla!'>")
            .append("</form>");
        
        // Mostrar historial de batallas si existe
        if (request.getSession().getAttribute("battleLog") != null) {
            html.append("<h3>Última Batalla:</h3>")
                .append("<div class='battle-log'>")
                .append(request.getSession().getAttribute("battleLog"))
                .append("</div>");
        }
        
        html.append("</div>");
    }
    
    private void simulateBattle(int trainer1Id, int trainer2Id, StringBuilder html) {
        Entrenador trainer1 = DatabaseConnection.obtenerEntrenadorPorId(trainer1Id);
        Entrenador trainer2 = DatabaseConnection.obtenerEntrenadorPorId(trainer2Id);
        
        if (trainer1 == null || trainer2 == null || trainer1.getPokemons().isEmpty() || trainer2.getPokemons().isEmpty()) {
            html.append("<script>alert('Ambos entrenadores necesitan tener al menos un Pokémon');</script>");
            return;
        }
        
        StringBuilder battleLog = new StringBuilder();
        battleLog.append("¡Comienza la batalla entre ").append(trainer1.getNombre())
                .append(" y ").append(trainer2.getNombre()).append("!<br><br>");
        
        // Implementación simple de batalla por turnos
        Random rand = new Random();
        List<Pokemon> team1 = new ArrayList<>(trainer1.getPokemons());
        List<Pokemon> team2 = new ArrayList<>(trainer2.getPokemons());
        
        int current1 = 0;
        int current2 = 0;
        Pokemon pokemon1 = team1.get(current1);
        Pokemon pokemon2 = team2.get(current2);
        
        battleLog.append(trainer1.getNombre()).append(" envía a ").append(pokemon1.getNombre()).append("!<br>");
        battleLog.append(trainer2.getNombre()).append(" envía a ").append(pokemon2.getNombre()).append("!<br><br>");
        
        while (current1 < team1.size() && current2 < team2.size()) {
            pokemon1 = team1.get(current1);
            pokemon2 = team2.get(current2);
            
            // Pokémon 1 ataca
            int damage = calculateDamage(pokemon1, pokemon2);
            battleLog.append(pokemon1.getNombre()).append(" ataca a ").append(pokemon2.getNombre())
                    .append(" causando ").append(damage).append(" de daño!<br>");
            
            if (damage >= pokemon2.getHp()) {
                battleLog.append(pokemon2.getNombre()).append(" ha sido derrotado!<br>");
                current2++;
                if (current2 < team2.size()) {
                    pokemon2 = team2.get(current2);
                    battleLog.append(trainer2.getNombre()).append(" envía a ").append(pokemon2.getNombre()).append("!<br>");
                }
            }
            
            if (current2 >= team2.size()) break;
            
            // Pokémon 2 ataca
            damage = calculateDamage(pokemon2, pokemon1);
            battleLog.append(pokemon2.getNombre()).append(" ataca a ").append(pokemon1.getNombre())
                    .append(" causando ").append(damage).append(" de daño!<br>");
            
            if (damage >= pokemon1.getHp()) {
                battleLog.append(pokemon1.getNombre()).append(" ha sido derrotado!<br>");
                current1++;
                if (current1 < team1.size()) {
                    pokemon1 = team1.get(current1);
                    battleLog.append(trainer1.getNombre()).append(" envía a ").append(pokemon1.getNombre()).append("!<br>");
                }
            }
        }
        
        if (current1 >= team1.size()) {
            battleLog.append("<br>").append(trainer2.getNombre()).append(" gana la batalla!");
        } else {
            battleLog.append("<br>").append(trainer1.getNombre()).append(" gana la batalla!");
        }
        
        // Guardar el log en la sesión para mostrarlo
        request.getSession().setAttribute("battleLog", battleLog.toString());
    }
    
    private int calculateDamage(Pokemon attacker, Pokemon defender) {
        // Fórmula simple de daño considerando ataque/defensa
        double damage = (attacker.getAtaque() * 0.5) - (defender.getDefensa() * 0.3);
        damage = Math.max(1, damage); // Mínimo 1 de daño
        return (int) damage;
    }
}