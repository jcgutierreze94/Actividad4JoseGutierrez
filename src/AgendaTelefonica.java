import java.io.*;
import java.util.*;

class AddressBook {
    private HashMap<String, String> contacts;
    public AddressBook(){
        contacts = new HashMap<>();
    }

    public void loadFile(String archivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))){
            String line;
            while ((line = lector.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2){
                    String telefono = parts[0].trim();
                    String nombre = parts[1].trim();
                    contacts.put(telefono, nombre);
                }
            }
        } catch (IOException e){
            System.out.println("Error cargando contactos desde archivo: " + e.getMessage());
        }
    }

    public void saveFile(String archivo){
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))){
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
            escritor.write(entry.getKey() + "," + entry.getValue());
            escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando contactos al archivo: " + e.getMessage());
        }
    }

    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos");
        } else {
            System.out.println("Lista de contactos:");
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                System.out.println("Número telefonico: " + entry.getKey() + " Nombre: " + entry.getValue());
            }
        }
    }

    public void create(String telefono, String nombre) {
        contacts.put(telefono, nombre);
        System.out.println("Contacto añadido");
    }

    public void delete(String telefono) {
        if (contacts.containsKey(telefono)) {
            contacts.remove(telefono);
            System.out.println("Contacto borrado");
        } else {
            System.out.println("Contacto no encontrado");
        }
    }
}

public class AgendaTelefonica {
    public static void main(String[] args) {
        AddressBook agendaTelefonica = new AddressBook();
        agendaTelefonica.loadFile("contacts.txt");
        Scanner scanner = new Scanner(System.in);
        int eleccion;

        do {
            System.out.println("\nLista de tareas:");
            System.out.println("1. Ver lista de contactos");
            System.out.println("2. Crear nuevo contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Salir y guardar cambios");
            System.out.print("Ingresa la tarea que deseas realizar: ");
            eleccion = scanner.nextInt();

            switch (eleccion) {
                case 1:
                    agendaTelefonica.list();
                    break;
                case 2:
                    System.out.print("Ingresa el número de telefono: ");
                    String telefono = scanner.next();
                    System.out.print("Ingresa el nombre: ");
                    scanner.nextLine();
                    String nombre = scanner.nextLine();
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
        } while (eleccion != 4);

        agendaTelefonica.saveFile("contacts.txt");
    }
}