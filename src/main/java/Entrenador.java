import java.util.ArrayList;

public class Entrenador {

	private int idEntrenador;
	private String nombre;
	private int edad;
	private String region;
	private String descripcion;
	private ArrayList<Pokemon> pokemons;
	
	private static int contador=0;

	//Constructores
	
	public Entrenador() {
		setContador();
		this.idEntrenador = contador;
		this.nombre = "";
		this.edad = 0;
		this.region = "";
		this.descripcion = "";
		this.pokemons = new ArrayList<Pokemon>();
	}
	
	
	public Entrenador(String nombre, int edad, String region, String descripcion) {
		setContador();
		this.idEntrenador = contador;
		this.nombre = nombre;
		this.edad = edad;
		this.region = region;
		this.descripcion = descripcion;
		this.pokemons = new ArrayList<Pokemon>();
	}
	
	
	
	
	
	//Getters y Setters
  
	public int getIdEntrenador() {
		return idEntrenador;
	}

	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(ArrayList<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public static int getContador() {
		return contador;
	}

	public static void setContador() {
		contador++;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
