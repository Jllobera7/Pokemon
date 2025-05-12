import java.util.InputMismatchException;
import java.util.Scanner;

public class Pokemon {

	//Atributos
	
	private int id;
	private String nombre;
	private String tipo1;
	private String tipo2;
	private double altura;
	private int peso;
	
	private static int contador=0;

	//Getters y Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		
		if(id<=contador) {
			System.out.println("Error: El Id ya existe, por favor introduzca un Id válido.");
		}
		else{
		this.id = id;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo1() {
		return tipo1;
	}

	public void setTipo1(String tipo1) {
	    String tipoLower = tipo1.toLowerCase();

	    if (tipoLower.equals("normal") ||
	        tipoLower.equals("fuego") ||
	        tipoLower.equals("agua") ||
	        tipoLower.equals("planta") ||
	        tipoLower.equals("eléctrico") || tipoLower.equals("electrico") ||  
	        tipoLower.equals("hielo") ||
	        tipoLower.equals("lucha") || 
	        tipoLower.equals("veneno") ||
	        tipoLower.equals("tierra") ||
	        tipoLower.equals("volador") ||
	        tipoLower.equals("psíquico") || tipoLower.equals("psiquico") ||
	        tipoLower.equals("bicho") ||
	        tipoLower.equals("roca") ||
	        tipoLower.equals("fantasma") ||
	        tipoLower.equals("dragón") || tipoLower.equals("dragon") ||
	        tipoLower.equals("siniestro") ||
	        tipoLower.equals("acero") ||
	        tipoLower.equals("hada")) {
	        
	        this.tipo1 = tipo1;
	    } else {
	        System.out.println("Tipo inválido: debe ser normal, fuego, planta, eléctrico, hielo, lucha, "
	        		+ "veneno, tierra, volador, psíquico, bicho, roca, fantasma,dragón, siniestro,acero,hada");
	    }
	}


	public void setTipo2(String tipo2) {
	    String tipoLower = tipo1.toLowerCase();

	    if (tipoLower.equals("normal") ||
	        tipoLower.equals("fuego") ||
	        tipoLower.equals("agua") ||
	        tipoLower.equals("planta") ||
	        tipoLower.equals("eléctrico") || tipoLower.equals("electrico") ||
	        tipoLower.equals("hielo") ||
	        tipoLower.equals("lucha") || 
	        tipoLower.equals("veneno") ||
	        tipoLower.equals("tierra") ||
	        tipoLower.equals("volador") ||
	        tipoLower.equals("psíquico") || tipoLower.equals("psiquico") ||
	        tipoLower.equals("bicho") ||
	        tipoLower.equals("roca") ||
	        tipoLower.equals("fantasma") ||
	        tipoLower.equals("dragón") || tipoLower.equals("dragon") ||
	        tipoLower.equals("siniestro") ||
	        tipoLower.equals("acero") ||
	        tipoLower.equals("hada")) {
	        
	        this.tipo2 = tipo2;
	    } else {
	        System.out.println("Tipo inválido: debe ser normal, fuego, planta, eléctrico, hielo, lucha, "
	        		+ "veneno, tierra, volador, psíquico, bicho, roca, fantasma,dragón, siniestro,acero,hada");
	    }
	}



	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public void setContador() {
		contador++;
	}


	//Constructores
	
	
	public Pokemon() { //Constructor vacío
		setContador();
		setId(contador);
		setAltura(0.0);
		setPeso(0);
	}
	
	
	
	
	public Pokemon(String nombre, String tipo1, String tipo2, double altura, int  peso ){
	setContador();
	setId(contador);
	setNombre(nombre);
	setTipo1(tipo1);
	setTipo2(tipo2);
	setAltura(altura);
	setPeso(peso);
	}	
	

}


public static void main(String[] args) {
	
	Scanner sc = new Scanner(System.in);
	int opcion = 0;
	
	while (opcion != 4) {
		System.out.println("\n--- MENÚ POKEDEX ---");
		System.out.println("1. Crear Pokemon");
		System.out.println("2. Ver Pokemon");
		System.out.println("3. Eliminar Pokemon por ID");
		System.out.println("4. Salir");
		System.out.print("Escoje una opción: ");
		
		try {
			opcion = sc.nextInt();
			sc.nextLine(); // Limpiar buffer
		} catch (InputMismatchException e) {
			System.out.println("Error: Debe introducir un número.");
			sc.next(); // Limpiar el buffer
			continue;
		}
		
		switch (opcion) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción no válida.");
				break;
		}
	}

	
	
	


}
