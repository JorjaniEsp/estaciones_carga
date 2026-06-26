
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private String conectorSeleccionado; // Variable para almacenar el tipo de conector del carro

    // Paleta de colores institucional
    private final Color AZUL = new Color(0, 51, 153);
    private final Color CELESTE = new Color(51, 153, 255);
    private final Color VERDE_LIMON = new Color(153, 204, 51);
    private final Color AMARILLO = new Color(255, 204, 0);
    private final Color BLANCO_FONDO = new Color(245, 245, 245);

    // Gestor de pantallas
    private CardLayout cardLayout;
    private JPanel panelContenedor;

    // Memoria global
    private ListaUsuarios catalogoUsuarios;
    private ListaVehiculos catalogoVehiculos;
    private GrafoMulticriterio mapaEstaciones;

    // Sesión
    private Usuario usuarioActual;

    public VentanaPrincipal(ListaUsuarios usuarios, ListaVehiculos vehiculos, GrafoMulticriterio mapa) {
        this.catalogoUsuarios = usuarios;
        this.catalogoVehiculos = vehiculos;
        this.mapaEstaciones = mapa;

        // Configuración básica del JFrame
        setTitle("ChargeCR - Movilidad Eléctrica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);

        // Inicializar CardLayout para navegación fluida
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Crear e inyectar las pantallas
        // Dentro del constructor VentanaPrincipal
        panelContenedor.add(crearPantallaLogin(), "LOGIN");
        panelContenedor.add(crearPantallaDashboard(), "DASHBOARD");
        panelContenedor.add(crearPantallaBusqueda(), "BUSQUEDA"); // <-- AGREGAR ESTO

        add(panelContenedor);
    }

    // =========================================================
    // PANTALLA 1: LOGIN
    // =========================================================
    private JPanel crearPantallaLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido a ChargeCR");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(AZUL);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSub = new JLabel("Ingrese su número de cédula para continuar");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(Color.DARK_GRAY);
        gbc.gridy = 1;
        panel.add(lblSub, gbc);

        // Campo de texto para Cédula
        JTextField txtCedula = new JTextField(15);
        txtCedula.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtCedula.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CELESTE, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridy = 2;
        panel.add(txtCedula, gbc);

        // Botón Ingresar
        JButton btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIngresar.setBackground(VERDE_LIMON);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnIngresar.addActionListener(e -> {
            String cedulaInput = txtCedula.getText().trim();
            validarLogin(cedulaInput);
        });

        gbc.gridy = 3;
        panel.add(btnIngresar, gbc);

        return panel;
    }

    // =========================================================
    // PANTALLA 2: DASHBOARD Y GARAJE
    // =========================================================
    private JPanel panelDashboard; // Lo hacemos global a la clase para actualizarlo
    private JLabel lblBienvenida;
    private JPanel panelGaraje;

    private JPanel crearPantallaDashboard() {
        panelDashboard = new JPanel(new BorderLayout());
        panelDashboard.setBackground(BLANCO_FONDO);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL);
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblBienvenida = new JLabel("Hola, Conductor");
        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblBienvenida.setForeground(Color.WHITE);
        header.add(lblBienvenida, BorderLayout.WEST);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(AMARILLO);
        btnCerrarSesion.setForeground(AZUL);
        btnCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(e -> {
            usuarioActual = null;
            cardLayout.show(panelContenedor, "LOGIN");
        });
        header.add(btnCerrarSesion, BorderLayout.EAST);

        panelDashboard.add(header, BorderLayout.NORTH);

        // --- BODY (Garaje y Acciones) ---
        JPanel body = new JPanel(new BorderLayout());
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        body.setBackground(BLANCO_FONDO);

        // Título del garaje
        JLabel lblGarajeTitulo = new JLabel("Mi Garaje");
        lblGarajeTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblGarajeTitulo.setForeground(AZUL);
        body.add(lblGarajeTitulo, BorderLayout.NORTH);

        // Contenedor dinámico de vehículos
        panelGaraje = new JPanel();
        panelGaraje.setLayout(new BoxLayout(panelGaraje, BoxLayout.Y_AXIS));
        panelGaraje.setBackground(BLANCO_FONDO);
        JScrollPane scrollGaraje = new JScrollPane(panelGaraje);
        scrollGaraje.setBorder(BorderFactory.createEmptyBorder());
        body.add(scrollGaraje, BorderLayout.CENTER);

        // Botón Buscar Carga (Navega a Pantalla 3)
        JButton btnBuscarCarga = new JButton("Buscar Carga en Mapa");
        btnBuscarCarga.setBackground(CELESTE);
        btnBuscarCarga.setForeground(Color.WHITE);
        btnBuscarCarga.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnBuscarCarga.setPreferredSize(new Dimension(200, 50));
        btnBuscarCarga.setFocusPainted(false);
        // Aquí conectaremos la acción hacia la pantalla del Grafo luego
        btnBuscarCarga.addActionListener(e -> JOptionPane.showMessageDialog(this, "Próxima pantalla: Grafo y Dijkstra"));
        
        body.add(btnBuscarCarga, BorderLayout.SOUTH);
        panelDashboard.add(body, BorderLayout.CENTER);

        return panelDashboard;
    }

    // =========================================================
    // LÓGICA DE NAVEGACIÓN Y CARGA DE DATOS
    // =========================================================
    private void validarLogin(String cedula) {
        if(cedula.isEmpty()){
            JOptionPane.showMessageDialog(this, "Debe ingresar una cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario encontrado = null;
        
        // CORRECCIÓN: Ahora usamos getAUsuario() que es el método real de tu clase ListaUsuarios
        for (int i = 0; i < catalogoUsuarios.getCantidadUsuarios(); i++) { 
            Usuario u = catalogoUsuarios.getUsuario(i);
            if (u != null && u.getCedula().equals(cedula)) {
                encontrado = u;
                break;
            }
        }

        if (encontrado != null) {
            this.usuarioActual = encontrado;
            cargarDatosDashboard();
            cardLayout.show(panelContenedor, "DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarDatosDashboard() {
        lblBienvenida.setText("Hola, " + usuarioActual.getNombreCompleto());
        
        // Limpiamos el garaje visual
        panelGaraje.removeAll();
        
        // Extraemos la estructura dinámica del usuario
        ListaSimpleC miGaraje = usuarioActual.getMisVehiculosIds();
        int ptrActual = miGaraje.getPTR();
        
        if (ptrActual == 0) {
            // El garaje está vacío
            JLabel lblVacio = new JLabel("No tienes vehículos registrados en tu garaje.");
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 14));
            panelGaraje.add(lblVacio);
        } else {
            // Extracción de datos directos de la memoria
            int[] info = miGaraje.getINFO();
            int[] apunt = miGaraje.getApunt();
            int inicio = ptrActual;
            
            // Recorremos la Lista Circular
            do {
                int idVehiculo = info[ptrActual]; // Sacamos el ID/Índice
                Vehiculo carro = catalogoVehiculos.getVehiculo(idVehiculo); // Buscamos el carro real
                
                if (carro != null) {
                    panelGaraje.add(crearTarjetaVehiculo(carro));
                    panelGaraje.add(Box.createRigidArea(new Dimension(0, 10))); // Margen entre tarjetas
                }
                
                // Saltamos al siguiente nodo de la lista enlazada
                ptrActual = apunt[ptrActual]; 
                
            } while (ptrActual != inicio && ptrActual != 0); // Condición de ciclo circular
        }
        
        // Refrescar la pantalla
        panelGaraje.revalidate();
        panelGaraje.repaint();
    }

    // Nuevo método para diseñar tarjetas bonitas y funcionales
    private JPanel crearTarjetaVehiculo(Vehiculo v) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(BLANCO_FONDO); // Usamos un fondo más limpio
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_LIMON, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        tarjeta.setMaximumSize(new Dimension(800, 70));

        // Información del carro
        String texto = String.format("🚗 %s (Placa: %s)  |  Conector: %s", 
                                      v.getMarcaModelo(), v.getPlaca(), v.getTipoConector());
        JLabel infoCarro = new JLabel(texto);
        infoCarro.setFont(new Font("SansSerif", Font.BOLD, 15));
        infoCarro.setForeground(AZUL);
        tarjeta.add(infoCarro, BorderLayout.CENTER);
        
        // Botón para iniciar el flujo de Dijkstra
        JButton btnUsar = new JButton("Buscar Carga");
        btnUsar.setBackground(AMARILLO);
        btnUsar.setForeground(AZUL);
        btnUsar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnUsar.setFocusPainted(false);
        btnUsar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Acción temporal: Confirmar que lee el conector correcto
        btnUsar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Iniciando búsqueda para conector: " + v.getTipoConector(), 
                "Grafo Multicriterio", 
                JOptionPane.INFORMATION_MESSAGE);
            // Aquí conectaremos la Pantalla 3 (El Grafo) en el siguiente paso
        });
        
        btnUsar.addActionListener(e -> {
            this.conectorSeleccionado = v.getTipoConector(); // Guardamos el conector
            cardLayout.show(panelContenedor, "BUSQUEDA");   // Navegamos al Grafo
        });
        
        tarjeta.add(btnUsar, BorderLayout.EAST);

        return tarjeta;
    }
    
    private JPanel crearPantallaBusqueda() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BLANCO_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Selección de Ruta (Dijkstra)");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(AZUL);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(new JLabel("¿Desde qué ubicación sales?"));
        JComboBox<String> cbOrigen = new JComboBox<>();
        for (int i = 0; i < mapaEstaciones.getCantidadNodos(); i++) {
            cbOrigen.addItem(mapaEstaciones.getEstacion(i).getUbicacion());
        }
        panel.add(cbOrigen);

        panel.add(new JLabel("Prioridad de cálculo:"));
        JComboBox<String> cbCriterio = new JComboBox<>(new String[]{"KM", "TIEMPO", "PRECIO"});
        panel.add(cbCriterio);

        JButton btnCalcular = new JButton("Calcular Ruta");
        btnCalcular.setBackground(VERDE_LIMON);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.addActionListener(e -> {
            int origen = cbOrigen.getSelectedIndex();
            String criterio = (String) cbCriterio.getSelectedItem();
            
            // Aquí consumimos la variable puente
            int ganador = mapaEstaciones.calcularRutaOptima(origen, criterio, this.conectorSeleccionado);
            
            if (ganador != -1) {
                Estacion est = mapaEstaciones.getEstacion(ganador);
                JOptionPane.showMessageDialog(this, 
                    "¡Ruta encontrada!\nEstación: " + est.getUbicacion() + 
                    "\nCriterio: " + criterio + 
                    "\nConector compatible: " + this.conectorSeleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "No hay estaciones con " + this.conectorSeleccionado + " accesibles desde aquí.");
            }
        });
        panel.add(btnCalcular);
        
        return panel;
    }
}