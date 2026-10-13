import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Cola {
    private final Queue<Pasajero> coleccion;

    public Cola() {
        coleccion = new LinkedList<>();
    }


    /**
     * Requerimiento 1 (R1): Registra un pasajero en la cola manteniendo el orden FIFO.
     * Valida que el objeto y el nombre no sean nulos o vacíos.
     * Comprueba que el asiento esté en el rango permitido (1-30).
     * Evita la duplicidad iterando la colección antes de la inserción.
     *
     * @param p Objeto Pasajero a encolar.
     * @throws IllegalArgumentException si alguna validación de datos o negocio falla.
     */
    public void agregar(Pasajero p) throws IllegalArgumentException {
        boolean valido = false;
        if (p == null) {
            throw new IllegalArgumentException("El pasajero no puede ser nulo");
        } else {
            valido = true;
        }

        // TODO R1: validar nombre, asiento entre 1 y 30 y asiento no repetido.

        if(p.getNombre() == null || p.getNombre().isEmpty() || p.getNombre().isBlank()){
            throw new IllegalArgumentException("El nombre es invalido, verifique si esta escrito correctamente");
        }else{
            valido = true;
        }

        if (p.getNumeroAsiento() < 1 || p.getNumeroAsiento() > 30) {
            throw new IllegalArgumentException("El numero de asiento debe estar entre 1 y 30");
        } else {
            valido = true;
        }

        for (Pasajero ver : coleccion) {
            if (ver.getNumeroAsiento() == p.getNumeroAsiento()) {
                throw new IllegalArgumentException("El asiento ya está tomado");
            } else {
                valido = true;
            }
        }

        // Validar ANTES de modificar la colección.


        if (valido == true) {
            coleccion.offer(p);
        }
    }

    public Pasajero extraer() {
        if (coleccion.isEmpty()) {
            throw new NoSuchElementException("Cola sin elementos");
        }
        return coleccion.poll();
    }

    public Pasajero frente() {
        if (coleccion.isEmpty()) {
            throw new NoSuchElementException("Cola sin elementos");
        }
        return coleccion.peek();
    }

    public int size() {
        return coleccion.size();
    }

    public boolean estaVacia() {
        return coleccion.isEmpty();
    }

    public List<Pasajero> obtenerPendientes() {
        return Collections.unmodifiableList(new ArrayList<>(coleccion));
    }
    /**
     * Requerimiento 2 (R2): Busca un pasajero en espera sin extraerlo de la fila.
     *
     * @param asiento Número de asiento a buscar.
     * @return El objeto Pasajero si existe, o null en caso contrario.
     */
    public Pasajero buscarPorAsiento(int asiento) {
        validarRangoAsiento(asiento);
        for (Pasajero pasajero : coleccion) {
            if (pasajero.getNumeroAsiento() == asiento) {
                return pasajero;
            }
        }
        return null;
    }
    /**
     * Requerimiento 2 (R2): Calcula la posición actual de un asiento en la fila FIFO.
     *
     * @param asiento Número de asiento a consultar.
     * @return Entero con la posición (inicia en 1), o -1 si no está en la fila.
     */
    public int posicionPorAsiento(int asiento) {
        validarRangoAsiento(asiento);
        int posicion = 1;
        for (Pasajero pasajero : coleccion) {
            if (pasajero.getNumeroAsiento() == asiento) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }

    private void validarRangoAsiento(int asiento) {
        if (asiento < 1 || asiento > 30) {
            throw new IllegalArgumentException("El asiento debe estar entre 1 y 30");
        }
    }

    public Pasajero cancelarPorAsiento(int asiento) {
        // 1. Reutilizamos la validación de rango (lanza IllegalArgumentException si falla)
        validarRangoAsiento(asiento);

        Pasajero pasajeroEncontrado = null;

        // 2. Buscamos al pasajero en la cola de espera
        for (Pasajero p : coleccion) {
            if (p.getNumeroAsiento() == asiento) {
                pasajeroEncontrado = p;
                break; // Lo encontramos, salimos del bucle
            }
        }

        // 3. Validar si realmente estaba en la fila
        if (pasajeroEncontrado == null) {
            throw new NoSuchElementException("El pasajero con ese asiento no está en la fila");
        }

        // 4. Retiramos únicamente al indicado.
        // remove(Object) conserva el orden relativo del resto de la cola FIFO.
        coleccion.remove(pasajeroEncontrado);

        return pasajeroEncontrado;
    }

    @Override
    public String toString() {
        if (coleccion.isEmpty()) {
            return "Cola sin elementos";
        }
        StringBuilder sb = new StringBuilder();
        int posicion = 1;
        for (Pasajero p : coleccion) {
            sb.append(posicion++).append(". ").append(p).append("\n");
        }
        return sb.toString();
    }
}
