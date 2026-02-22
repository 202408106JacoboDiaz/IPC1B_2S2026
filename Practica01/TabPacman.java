import java.util.Scanner;
import java.util.Random;

public class TabPacman {

    //Arreglos para los historiales
    static String[] historialNombres = new String[100];
    static int[] historialPuntos = new int[100];
    static int totalPartidas = 0;

    //Variable para cambiar el valor de los premios
    static int puntos1 = 0; static int puntos2 = 0;

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        boolean appActiva = true;

        while (appActiva) {
            //Menú de inicio
            System.out.println("==== MENÚ ====");
            System.out.println("1 <- Iniciar Juego");
            System.out.println("2 <- Historial");
            System.out.println("3 <- Salir");
            System.out.print("SELECCIONE UNA OPCIÓN: ");
            String opcionMenu = lector.nextLine().trim();
            //Toma de decisión en menú de inicio
            switch (opcionMenu) {
                case "1":
                    iniciarJuego(lector);
                    break;
                case "2":
                    mostrarHistorial();
                    break;
                case "3":
                    System.out.println("SALIENDO...");
                    appActiva = false;
                    break;
                default:
                    System.out.println("OPCIÓN NO VÁLIDA, INTENTE DE NUEVO.");
            }
        }
        lector.close();
    }
    //M_Mostrar historial
    static void mostrarHistorial() {
        System.out.println("============== HISTORIAL ==============");
        System.out.println("USUARIO              PUNTEO");
        System.out.println("---------------------------------------");
        if (totalPartidas == 0) {
            System.out.println("SIN PARTIDAS.");
        } else {
            // Mostrar de la partida más reciente a la más antigua
            int numero = 1;
            for (int i = totalPartidas - 1; i >= 0; i--) {
                System.out.println(numero + ".   " + historialNombres[i] + "    " + historialPuntos[i]);
                numero++;
            }
        }
        System.out.println("=======================================");
    }
    //M_Guardar partidas
    static void guardarPartida(String nombre, int puntos) {
        if (totalPartidas < 100) {
            historialNombres[totalPartidas] = nombre;
            historialPuntos[totalPartidas] = puntos;
            totalPartidas++;
        }
    }
    //M_Mostrar panel de control
    static void mostrarPanelControl(String usuario, int punteo, int vidas) {
        System.out.println("----------------");
        System.out.println("USUARIO: " + usuario);
        System.out.println("PUNTEO:  " + punteo);
        System.out.println("VIDAS:   " + vidas);
        System.out.println("----------------");
    }
    //M_Mostrar tablero
    static void mostrarTablero(char[][] mapa, int filas, int columnas) {
        System.out.println("=================");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("|" + mapa[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("=================");
    }
    //M_Colocar elementos randomizados
    static void colocarElemento(char[][] mapa, int filas, int columnas, char simbolo, int cantidad, Random azar) {
        int colocados = 0;
        while (colocados < cantidad) {
            int f = azar.nextInt(filas);
            int c = azar.nextInt(columnas);
            if (mapa[f][c] == ' ') {
                mapa[f][c] = simbolo;
                colocados++;
            }
        }
    }
    //M_Contar premios faltantes
    static int contarPremios(char[][] mapa, int filas, int columnas) {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (mapa[i][j] == '0' || mapa[i][j] == '$') {
                    contador++;
                }
            }
        }
        return contador;
    }
    //M_Iniciar juego
    static void iniciarJuego(Scanner lector) {
        Random azar = new Random();
        //Pedir nombre
        System.out.print("INGRESE SU NOMBRE: ");
        String nombreUsuario = lector.nextLine().trim();
        //Preparación del tablero
        int filas = 0;
        int columnas = 0;
        int totalEspacios = 0;
        System.out.println("======= CONFIGURAR TABLERO =======");
        System.out.println("POR FAVOR, INGRESE LOS SIGUIENTES VALORES");
        // Tipo de tablero con validación
        String tipoTablero = "";
        while (!tipoTablero.equals("P") && !tipoTablero.equals("G")) {
            System.out.print("TABLERO -> P [PEQUEÑO], G [GRANDE]: ");
            tipoTablero = lector.nextLine().trim().toUpperCase();
            if (!tipoTablero.equals("P") && !tipoTablero.equals("G")) {
                System.out.println("OPCIÓN NO VÁLIDA.");
            }
        }
        //Tamaño del tablero según el tipo
        if (tipoTablero.equals("P")) {
            filas = 5;
            columnas = 6;
        } else {
            filas = 10;
            columnas = 10;
        }
        totalEspacios = filas * columnas;
        //Porcentajes de premios, paredes y trampas
        int maxPremios  = (int)(totalEspacios * 0.40);
        int maxParedes  = (int)(totalEspacios * 0.20);
        int maxTrampas  = (int)(totalEspacios * 0.20);
        //Pedir cantidad de premios con validación
        int noPremios = 0;
        while (noPremios < 1 || noPremios > maxPremios) {
            System.out.print("PREMIOS [1-" + maxPremios + "]: ");
            try {
                noPremios = Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException e) {
                noPremios = 0;
            }
            if (noPremios < 1 || noPremios > maxPremios) {
                System.out.println("ERROR. DEBER SER ENTRE 1 Y " + maxPremios + ".");
            }
        }
        //Pedir cantidad de paredes con validación
        int noParedes = 0;
        while (noParedes < 1 || noParedes > maxParedes) {
            System.out.print("PAREDES [1-" + maxParedes + "]: ");
            try {
                noParedes = Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException e) {
                noParedes = 0;
            }
            if (noParedes < 1 || noParedes > maxParedes) {
                System.out.println("ERROR. DEBE SER ENTRE 1 Y " + maxParedes + ".");
            }
        }
        //Pedir cantidad de trampas con validación
        int noTrampas = 0;
        while (noTrampas < 1 || noTrampas > maxTrampas) {
            System.out.print("TRAMPAS [1-" + maxTrampas + "]: ");
            try {
                noTrampas = Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException e) {
                noTrampas = 0;
            }
            if (noTrampas < 1 || noTrampas > maxTrampas) {
                System.out.println("ERROR. DEBE SER ENTRE 1 Y " + maxTrampas + ".");
            }
        }
        //Construcción del tablero
        char[][] zonaDeJuego = new char[filas][columnas];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                zonaDeJuego[i][j] = ' ';
        //División exacta entre premios simples (0) y especiales ($)
        int premiosSimples  = noPremios / 2;
        int premiosEspeciales = noPremios - premiosSimples;

        colocarElemento(zonaDeJuego, filas, columnas, '0', premiosSimples,   azar);
        colocarElemento(zonaDeJuego, filas, columnas, '$', premiosEspeciales, azar);
        colocarElemento(zonaDeJuego, filas, columnas, 'X', noParedes,         azar);
        colocarElemento(zonaDeJuego, filas, columnas, '@', noTrampas,         azar);

        //Mostrar tablero antes de pedir posición
        System.out.println("Tablero generado:");
        mostrarTablero(zonaDeJuego, filas, columnas);
        // Pedir posición inicial del jugador
        int posFila = -1;
        int posColumna = -1;
        boolean posicionValida = false;
        while (!posicionValida) {
            System.out.println("======= POSICIÓN DEL JUGADOR =======");
            System.out.println("POR FAVOR, INGRESE UNA POSICION INICIAL: ");
            System.out.print("FILA (0-" + (filas - 1) + "): ");
            try {
                posFila = Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException e) {
                posFila = -1;
            }
            System.out.print("COLUMNA (0-" + (columnas - 1) + "): ");
            try {
                posColumna = Integer.parseInt(lector.nextLine().trim());
            } catch (NumberFormatException e) {
                posColumna = -1;
            }
            if (posFila < 0 || posFila >= filas || posColumna < 0 || posColumna >= columnas) {
                System.out.println("POSICIÓN FUERA DEL TABLERO, INTENTE DE NUEVO.");
            } else if (zonaDeJuego[posFila][posColumna] == 'X') {
                System.out.println("POSICIÓN COMPROMETIDA, INTENTE DE NUEVO.");
            } else {
                posicionValida = true;
            }
        }
        //Generación del Pac-Man
        zonaDeJuego[posFila][posColumna] = '<';
        //Variables de partida
        int punteo = 0;
        int vidas  = 3;
        boolean inGame = true;
        //Bucle de la partida
        while (inGame) {
            //Llamar al mét. para mostrar panel del control
            mostrarPanelControl(nombreUsuario, punteo, vidas);
            //Llamar al mét. para mostrar tablero
            mostrarTablero(zonaDeJuego, filas, columnas);
            System.out.println("CONTROLES: W-[↑]; S-[↓]; A-[←]; D-[→]; F[PAUSA]");
            System.out.print("MOVIMIENTO: ");
            String mov = lector.nextLine().trim().toUpperCase();
            //Cuando se da pausa
            if (mov.equalsIgnoreCase("F")) {
                System.out.println("============ PAUSA ============");
                System.out.println("SELECCIONE UNA OPCIÓN");
                System.out.println("1-REGRESAR");
                System.out.println("2-TERMINAR PARTIDA");
                System.out.print("OPCIÓN: ");
                String opPausa = lector.nextLine().trim();
                if (opPausa.equals("2")) {
                    inGame = false;
                    guardarPartida(nombreUsuario, punteo);
                    System.out.println("GAME OVER. PUNTAJE: " + punteo);
                }
                //Si se ingresa cualquier número que no sea 3, se regresa a la partida
                continue;
            }

            //Definir controles para moverse entre filas y columnas
            int nextFila = posFila; int nextColumna = posColumna;
            //Movimiento entre filas y columnas según el control especificado
            switch (mov) {
                case "W": nextFila = posFila - 1; break;
                case "S": nextFila = posFila + 1; break;
                case "A": nextColumna = posColumna - 1; break;
                case "D": nextColumna = posColumna + 1; break;
                default:
                    System.out.println("MOVIMIENTO NO VÁLIDO.");
                    continue;
            }
            //Truco para que las paredes teletransporten
            if (nextFila < 0) nextFila = filas - 1;
            else if (nextFila >= filas) nextFila = 0;
            if (nextColumna < 0) nextColumna = columnas - 1;
            else if (nextColumna >= columnas) nextColumna = 0;
            //Preparativo para colisiones
            char celda = zonaDeJuego[nextFila][nextColumna];
            //Colisiones con obstáculos
            if (celda == 'X') {
                System.out.println("HAY UN OBSTÁCULO, NO SE PUEDE PASAR.");
                continue;
            }
            //Colisiones con fantasmas
            if (celda == '@') {
                vidas--;
                System.out.println("HAS PERDIDO UNA VIDA. VIDAS RESTANTES: " + vidas);
                //La posición anterior se limpia
                zonaDeJuego[posFila][posColumna] = ' ';
                //El fantasma desaparece
                zonaDeJuego[nextFila][nextColumna] = ' ';
                posFila    = nextFila;
                posColumna = nextColumna;
                //Pac-Man reaparece
                zonaDeJuego[posFila][posColumna] = '<';
                //POV: se acaban las vidas
                if (vidas <= 0) {
                    mostrarPanelControl(nombreUsuario, punteo, vidas);
                    mostrarTablero(zonaDeJuego, filas, columnas);
                    System.out.println("GAME OVER. YA NO QUEDAN VIDAS EXTRAS.");
                    guardarPartida(nombreUsuario, punteo);
                    inGame = false;
                }
                continue;
            }
            //Puntos cuando se recoge un premio simple
            if (celda == '0') {
                punteo += 10;
                puntos1 = 10;
                System.out.println("PREMIO SIMPLE! +" + puntos1 +" PTS");
            }
            //Puntos cuando se recoge un premio especial
            if (celda == '$') {
                punteo += 15;
                puntos2 = 15;
                System.out.println("PREMIO ESPECIAL! +" + puntos2 +" PTS");
            }
            //Mover jugador
            zonaDeJuego[posFila][posColumna] = ' ';
            posFila = nextFila; posColumna = nextColumna;
            zonaDeJuego[posFila][posColumna] = '<';

            //Victoria final
            //Si ya no hay más premios
            if (contarPremios(zonaDeJuego, filas, columnas) == 0) {
                //Mostrar panel de control
                mostrarPanelControl(nombreUsuario, punteo, vidas);
                //Mostrar tablero
                mostrarTablero(zonaDeJuego, filas, columnas);
                //Mostrar felicidades
                System.out.println("FELICIDADES " + nombreUsuario + ". GAME OVER.");
                //Guardar partida
                guardarPartida(nombreUsuario, punteo);
                //Terminar juego
                inGame = false;
            }
        }
    }
}