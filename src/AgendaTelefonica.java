import java.io.*;
import java.util.*;

/*
La clase AddressBook será la que contenga todos los datos de la agenda telefonica
HashMap<> crea una estructura de datos, un par de datos de tipo string y los guarda en la variable contacts
 */
class AddressBook {
    private HashMap<String, String> contacts;
    public AddressBook(){
        contacts = new HashMap<>();
    }
    // el metodo loadFile es el responsable de cargar los contactos desde un archivo
    public void loadFile(String archivo) {
        /* se usa try para poder manejar la excepción
        BufferedReader y FileReader son los que nos van a ayudar a abrir el archivo y leerlo
         */
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
            String line;
            // esta linea hace un loop que indica que mientras haya lineas que leer nos traera de regreso la info
            while ((line = lector.readLine()) != null) {
                String[] parts = line.split(",");
                //este ciclo if nos ayuda a separar la linea de texto en 2, siendo la primera el número y la segunda el nombre
                if (parts.length == 2){
                    String telefono = parts[0].trim();
                    String nombre = parts[1].trim();
                    contacts.put(telefono, nombre);
                }
            }
        } catch (IOException e){ //el catch nos ayuda a manejar la excepción en caso de que no pueda leer el archivo
            System.out.println("Error cargando contactos desde archivo: " + e.getMessage());
        }
    }

    //el metodo saveFile es el responsable de guardar los contactos al archivo
    public void saveFile(String archivo){
        /*
        se usa try para poder manejar la excepción
        BufferedWriter y FileWriter son los que nos van a ayudar a abrir el archivo y guardar los cambios
         */
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))){
            /*
            aquí el loop itera sobre todas las entradas que exista en contacts
            se indica que la primera entrada sera el key, el cual es el telefono
            la segunda entrada es el valor, el cual es el nombre
            ambos son separados por un coma
             */
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
            escritor.write(entry.getKey() + "," + entry.getValue());
            escritor.newLine();
            }
        } catch (IOException e) { ////el catch nos ayuda a manejar la excepción en caso de que no pueda guardar el archivo
            System.out.println("Error guardando contactos al archivo: " + e.getMessage());
        }
    }

    // el metodo list es el responsable de mostrar todos los contactos
    public void list() {
        // si la variable contactos esta vacia, mostrara un mensaje
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos");
        } else {
            System.out.println("Lista de contactos:");
            /*
             for nos ayuda que por cada entrada que exista, se va a imprimir el telefono seguido del nombre
             getKey es el telefono / getValue es el nombre
             */
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println("Número telefonico: " + entry.getKey() + " Nombre: " + entry.getValue());
            }
        }
    }

    // El metodo create nos ayuda a guardar contactos en el archivo
    public void create(String telefono, String nombre) {
       // usando el metodo put dentro de HashMap nos ayuda a agregar una nueva entrada con dos variables telefono y nombre
        contacts.put(telefono, nombre);
        System.out.println("Contacto añadido");
    }

    // El metodo delete nos ayuda a borrar contactos del archivo
    public void delete(String telefono) {
        // el if nos ayuda a revisar si el número de telefono que se desea borrar existe en el archivo
        if (contacts.containsKey(telefono)) {
            contacts.remove(telefono);
            System.out.println("Contacto borrado");
        } else { //en caso de no existir el número arroja un mensaje
            System.out.println("Contacto no encontrado");
        }
    }
}

// AgendaTelefonica es la clase principal del programa y al tener public static void main... indica que apartir de aquí se corre el programa
public class AgendaTelefonica {
    public static void main(String[] args) {
        AddressBook agendaTelefonica = new AddressBook();
        //con loadFile se abre el archivo de texto que contiene los datos
        agendaTelefonica.loadFile("contacts.txt");
        // El metodo scanner ayuda a leer inputs del usuario
        Scanner scanner = new Scanner(System.in);
        // se declara la variable elección que será la que nos ayuda a saber que tarea quiere hacer el usuario
        int eleccion;

       // do-while es lo que nos ayuda a desplegar el menu interactivo para el usuario
        do {
            System.out.println("\nLista de tareas:");
            System.out.println("1. Ver lista de contactos");
            System.out.println("2. Crear nuevo contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Salir y guardar cambios");
            System.out.print("Ingresa la tarea que deseas realizar: ");
            // el número que escriba el usuario será alojado en la variable eleccion
            eleccion = scanner.nextInt();

            // El switch nos ayuda a manejar las 4 diferentes tareas que el usuario puede hacer
            switch (eleccion) {
                case 1:
                    agendaTelefonica.list(); //se ejecuta el metodo list
                    break;
                case 2:
                    System.out.print("Ingresa el número de telefono: ");
                    String telefono = scanner.next(); // con scanner se guarda lo que tipea el usuario en la variable telefono
                    System.out.print("Ingresa el nombre: ");
                    scanner.nextLine();
                    String nombre = scanner.nextLine(); // con scanner se guarda lo que tipea el usuario en la variable nombre
                    agendaTelefonica.create(telefono, nombre);
                    break;
                case 3:
                    System.out.print("Ingresa el número de telefono a borrar: ");
                    telefono = scanner.next();
                    agendaTelefonica.delete(telefono);
                    break;
                case 4:
                    System.out.println("Guardando cambios y saliendo...");
                    break;
                default:
                    System.out.println("Opción invalida, intenta de nuevo");
                    break;
            }
        } while (eleccion != 4); //mientras el usuario no ponga el número 4 el ciclo do-while se seguirá ejecutando

        agendaTelefonica.saveFile("contacts.txt");
    }
}