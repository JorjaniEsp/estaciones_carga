import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    // ── Paleta UCR ────────────────────────────────────────────────────────────
    private final Color AZUL         = new Color(0, 51, 153);
    private final Color CELESTE      = new Color(51, 153, 255);
    private final Color VERDE_LIMON  = new Color(153, 204, 51);
    private final Color AMARILLO     = new Color(255, 204, 0);
    private final Color FONDO        = new Color(245, 247, 250);
    private final Color CARD_BG      = Color.WHITE;
    private final Color TEXTO_GRIS   = new Color(90, 90, 90);

    // ── Navegación ────────────────────────────────────────────────────────────
    private CardLayout cardLayout;
    private JPanel     panelContenedor;

    // ── Datos globales ────────────────────────────────────────────────────────
    private ListaUsuarios      catalogoUsuarios;
    private ListaVehiculos     catalogoVehiculos;
    private GrafoMulticriterio mapaEstaciones;

    // ── Sesión ────────────────────────────────────────────────────────────────
    private Usuario usuarioActual;
    private String  conectorSeleccionado;
    private int     estacionGanadoraIdx = -1;   // índice en el grafo
    private int     vehiculoActualIdx   = 0;    // índice en la lista circular visual

    // ── Referencias a widgets que se actualizan dinámicamente ─────────────────
    private JLabel     lblBienvenida;
    private JPanel     panelTarjetaVehiculo;    // contenedor del carro visible (P2)
    private JLabel     lblResultadoDijkstra;    // panel resultado (P3)
    private JLabel     lblEstacionAgenda;       // nombre estación (P4)
    private JLabel     lblColaEspera;           // "Vehículos en espera: X" (P4)
    private JLabel     lblCronometro;           // MM:SS (P4)
    private JButton    btnYaLlegue;
    private Timer      timerGracia;
    private int        segundosRestantes;
    private DefaultTableModel modeloTablaHistorial; // (P5)

    // ── Constructor ───────────────────────────────────────────────────────────
    public VentanaPrincipal(ListaUsuarios usuarios, ListaVehiculos vehiculos, GrafoMulticriterio mapa) {
        this.catalogoUsuarios  = usuarios;
        this.catalogoVehiculos = vehiculos;
        this.mapaEstaciones    = mapa;

        setTitle("ChargeCR – Movilidad Eléctrica");
        setSize(860, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout       = new CardLayout();
        panelContenedor  = new JPanel(cardLayout);
        panelContenedor.setBackground(FONDO);

        panelContenedor.add(crearPantallaLogin(),        "LOGIN");
        panelContenedor.add(crearPantallaGaraje(),       "GARAJE");
        panelContenedor.add(crearPantallaBusqueda(),     "BUSQUEDA");
        panelContenedor.add(crearPantallaAgendamiento(), "AGENDA");
        panelContenedor.add(crearPantallaHistorial(),    "HISTORIAL");

        add(panelContenedor);
    }

    // =========================================================
    // UTILIDADES DE UI
    // =========================================================

    /** Header azul reutilizable con título y botón opcional derecha. */
    private JPanel crearHeader(String titulo, String labelBotonDer, ActionListener accionDer) {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(AZUL);
        h.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setForeground(Color.WHITE);
        h.add(lbl, BorderLayout.WEST);

        if (labelBotonDer != null) {
            JButton btn = boton(labelBotonDer, AMARILLO, AZUL, 13);
            btn.addActionListener(accionDer);
            h.add(btn, BorderLayout.EAST);
        }
        return h;
    }

    /** Footer de navegación con flechas para las pantallas internas. */
    private JPanel crearFooter(String labelIzq, ActionListener accionIzq,
                                String labelDer, ActionListener accionDer) {
        JPanel f = new JPanel(new BorderLayout());
        f.setBackground(FONDO);
        f.setBorder(BorderFactory.createEmptyBorder(10, 24, 16, 24));

        if (labelIzq != null) {
            JButton b = boton("← " + labelIzq, CELESTE, Color.WHITE, 13);
            b.addActionListener(accionIzq);
            f.add(b, BorderLayout.WEST);
        }
        if (labelDer != null) {
            JButton b = boton(labelDer + " →", VERDE_LIMON, Color.WHITE, 13);
            b.addActionListener(accionDer);
            f.add(b, BorderLayout.EAST);
        }
        return f;
    }

    /** Fábrica de botones estilizados. */
    private JButton boton(String texto, Color bg, Color fg, int fontSize) {
        JButton b = new JButton(texto);
        b.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // Hover ligero
        b.addMouseListener(new MouseAdapter() {
            Color original = bg;
            public void mouseEntered(MouseEvent e) { b.setBackground(original.darker()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(original); }
        });
        return b;
    }

    /** Tarjeta blanca con borde redondeado simulado. */
    private JPanel card() {
        JPanel c = new JPanel();
        c.setBackground(CARD_BG);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));
        return c;
    }

    /** Campo de texto estilizado. */
    private JTextField campo(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CELESTE, 2),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }

    /** Etiqueta con estilo. */
    private JLabel etiqueta(String texto, int estilo, int tam, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", estilo, tam));
        l.setForeground(color);
        return l;
    }

    // =========================================================
    // PANTALLA 1 – LOGIN
    // =========================================================
    private JPanel crearPantallaLogin() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(FONDO);

        JPanel card = card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(400, 320));

        JLabel logo = etiqueta("⚡ ChargeCR", Font.BOLD, 32, AZUL);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = etiqueta("Plataforma de Recarga Eléctrica", Font.PLAIN, 14, TEXTO_GRIS);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCedula = etiqueta("Número de Cédula", Font.BOLD, 13, AZUL);
        lblCedula.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtCedula = campo(20);
        txtCedula.setMaximumSize(new Dimension(320, 40));
        txtCedula.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnIngresar = boton("Ingresar al Sistema", VERDE_LIMON, Color.WHITE, 15);
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(320, 45));

        btnIngresar.addActionListener(e -> validarLogin(txtCedula.getText().trim()));
        // Enter también funciona
        txtCedula.addActionListener(e -> validarLogin(txtCedula.getText().trim()));

        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(logo);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(sub);
        card.add(Box.createRigidArea(new Dimension(0, 24)));
        card.add(lblCedula);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(txtCedula);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(btnIngresar);

        outer.add(card);
        return outer;
    }

    // =========================================================
    // PANTALLA 2 – GARAJE (Lista Circular, un vehículo visible)
    // =========================================================
    private JPanel crearPantallaGaraje() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);

        // Header con nombre del usuario (se actualiza en cargarGaraje)
        lblBienvenida = etiqueta("Hola, Conductor", Font.BOLD, 22, Color.WHITE);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL);
        header.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        header.add(lblBienvenida, BorderLayout.WEST);
        JButton btnLogout = boton("Cerrar Sesión", AMARILLO, AZUL, 13);
        btnLogout.addActionListener(e -> cerrarSesion());
        header.add(btnLogout, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        // Body central
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(30, 60, 20, 60));

        JLabel titulo = etiqueta("🚗  Mi Garaje", Font.BOLD, 20, AZUL);
        body.add(titulo, BorderLayout.NORTH);

        // Tarjeta central del vehículo
        panelTarjetaVehiculo = new JPanel(new BorderLayout());
        panelTarjetaVehiculo.setBackground(FONDO);
        body.add(panelTarjetaVehiculo, BorderLayout.CENTER);

        // Fila de navegación < Anterior | Siguiente >
        JPanel navRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navRow.setBackground(FONDO);
        JButton btnAnterior = boton("◀  Anterior", CELESTE, Color.WHITE, 13);
        JButton btnSiguiente = boton("Siguiente  ▶", CELESTE, Color.WHITE, 13);
        btnAnterior.addActionListener(e -> navegarGaraje(-1));
        btnSiguiente.addActionListener(e -> navegarGaraje(+1));
        navRow.add(btnAnterior);
        navRow.add(btnSiguiente);
        body.add(navRow, BorderLayout.SOUTH);

        panel.add(body, BorderLayout.CENTER);

        // Footer: ir a historial
        JPanel footer = crearFooter("Historial", e -> mostrarHistorial(), null, null);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // PANTALLA 3 – BÚSQUEDA (Dijkstra)
    // =========================================================
    private JPanel crearPantallaBusqueda() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);

        panel.add(crearHeader("🗺  Buscar Estación de Carga", "Cerrar Sesión", e -> cerrarSesion()), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(24, 40, 10, 40));

        // ── Formulario ──────────────────────────────────────────
        JPanel form = card();
        form.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0;
        form.add(etiqueta("¿Desde dónde sales?", Font.BOLD, 13, AZUL), g);

        JComboBox<String> cbOrigen = new JComboBox<>();
        for (int i = 0; i < mapaEstaciones.getCantidadNodos(); i++)
            cbOrigen.addItem(mapaEstaciones.getEstacion(i).getUbicacion());
        cbOrigen.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.gridx = 1;
        form.add(cbOrigen, g);

        g.gridx = 0; g.gridy = 1;
        form.add(etiqueta("Prioridad de ruta:", Font.BOLD, 13, AZUL), g);

        JComboBox<String> cbCriterio = new JComboBox<>(new String[]{"KM – Más corta", "TIEMPO – Más rápida", "PRECIO – Más económica"});
        cbCriterio.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.gridx = 1;
        form.add(cbCriterio, g);

        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        JButton btnCalc = boton("⚡  Calcular Ruta Óptima", VERDE_LIMON, Color.WHITE, 15);
        form.add(btnCalc, g);

        body.add(form, BorderLayout.NORTH);

        // ── Panel de resultados ─────────────────────────────────
        JPanel cardResult = card();
        cardResult.setLayout(new BorderLayout());
        cardResult.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE_LIMON, 2),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        lblResultadoDijkstra = new JLabel("<html><center><i>El resultado aparecerá aquí...</i></center></html>");
        lblResultadoDijkstra.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblResultadoDijkstra.setForeground(TEXTO_GRIS);
        lblResultadoDijkstra.setHorizontalAlignment(SwingConstants.CENTER);
        cardResult.add(lblResultadoDijkstra, BorderLayout.CENTER);

        // Botón agendar (inicia deshabilitado)
        JButton btnAgendar = boton("Confirmar y Agendar →", AMARILLO, AZUL, 14);
        btnAgendar.setEnabled(false);
        cardResult.add(btnAgendar, BorderLayout.SOUTH);

        body.add(cardResult, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        // ── Acción calcular ─────────────────────────────────────
        btnCalc.addActionListener(e -> {
            int origen    = cbOrigen.getSelectedIndex();
            String crit   = cbCriterio.getSelectedItem().toString().split(" ")[0]; // "KM", "TIEMPO", "PRECIO"
            String conect = (conectorSeleccionado != null) ? conectorSeleccionado : "UNIVERSAL";

            int ganador = mapaEstaciones.calcularRutaOptima(origen, crit, conect);
            estacionGanadoraIdx = ganador;

            if (ganador == -1) {
                lblResultadoDijkstra.setText(
                    "<html><center><b style='color:red'>Sin resultado:</b><br>" +
                    "No hay estaciones con conector <b>" + conect + "</b> accesibles.</center></html>");
                btnAgendar.setEnabled(false);
            } else {
                Estacion est = mapaEstaciones.getEstacion(ganador);
                lblResultadoDijkstra.setText(
                    "<html><center>" +
                    "<b style='font-size:15px'>✅  " + est.getUbicacion() + "</b><br><br>" +
                    "🔌 Conector compatible: <b>" + conect + "</b><br>" +
                    "💡 Tarifa: <b>₡" + est.getTarifaPorKwh() + "/kWh</b><br>" +
                    "📍 Criterio usado: <b>" + crit + "</b>" +
                    "</center></html>");
                btnAgendar.setEnabled(true);
            }
        });

        btnAgendar.addActionListener(e -> {
            if (estacionGanadoraIdx != -1) {
                prepararAgendamiento();
                cardLayout.show(panelContenedor, "AGENDA");
            }
        });

        panel.add(crearFooter("Mi Garaje", e -> cardLayout.show(panelContenedor, "GARAJE"), null, null), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // PANTALLA 4 – AGENDAMIENTO (Cola + Timer)
    // =========================================================
    private JPanel crearPantallaAgendamiento() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);
        panel.add(crearHeader("📋  Agendamiento de Carga", "Cerrar Sesión", e -> cerrarSesion()), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(30, 80, 20, 80));

        // Nombre de estación
        lblEstacionAgenda = etiqueta("Estación: —", Font.BOLD, 20, AZUL);
        lblEstacionAgenda.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cola en espera
        lblColaEspera = etiqueta("Vehículos en espera: —", Font.PLAIN, 15, TEXTO_GRIS);
        lblColaEspera.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(600, 2));
        sep.setForeground(CELESTE);

        // Cronómetro
        JPanel cardTimer = card();
        cardTimer.setLayout(new BoxLayout(cardTimer, BoxLayout.Y_AXIS));
        cardTimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardTimer.setMaximumSize(new Dimension(500, 160));

        JLabel lblTitTimer = etiqueta("⏱  Tiempo de gracia para llegar:", Font.BOLD, 14, TEXTO_GRIS);
        lblTitTimer.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblCronometro = etiqueta("10:00", Font.BOLD, 52, VERDE_LIMON);
        lblCronometro.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAviso = etiqueta("Si el tiempo llega a 0:00, se aplicará una multa.", Font.ITALIC, 12, TEXTO_GRIS);
        lblAviso.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardTimer.add(Box.createRigidArea(new Dimension(0, 8)));
        cardTimer.add(lblTitTimer);
        cardTimer.add(Box.createRigidArea(new Dimension(0, 8)));
        cardTimer.add(lblCronometro);
        cardTimer.add(Box.createRigidArea(new Dimension(0, 6)));
        cardTimer.add(lblAviso);
        cardTimer.add(Box.createRigidArea(new Dimension(0, 8)));

        // Botón confirmar reserva
        JButton btnConfirmar = boton("✅  Confirmar Reserva", VERDE_LIMON, Color.WHITE, 15);
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.setMaximumSize(new Dimension(340, 50));

        // Botón ya llegué (inicia deshabilitado)
        btnYaLlegue = boton("🚗  ¡Ya llegué! Iniciar Carga", CELESTE, Color.WHITE, 15);
        btnYaLlegue.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnYaLlegue.setMaximumSize(new Dimension(340, 50));
        btnYaLlegue.setEnabled(false);

        btnConfirmar.addActionListener(e -> {
            agendarEnCola();
            iniciarCronometro();
            btnConfirmar.setEnabled(false);
            btnYaLlegue.setEnabled(true);
        });

        btnYaLlegue.addActionListener(e -> {
            detenerCronometro();
            registrarCargaExitosa();
            cardLayout.show(panelContenedor, "HISTORIAL");
            actualizarTablaHistorial();
        });

        body.add(lblEstacionAgenda);
        body.add(Box.createRigidArea(new Dimension(0, 8)));
        body.add(lblColaEspera);
        body.add(Box.createRigidArea(new Dimension(0, 16)));
        body.add(sep);
        body.add(Box.createRigidArea(new Dimension(0, 20)));
        body.add(cardTimer);
        body.add(Box.createRigidArea(new Dimension(0, 20)));
        body.add(btnConfirmar);
        body.add(Box.createRigidArea(new Dimension(0, 12)));
        body.add(btnYaLlegue);

        panel.add(body, BorderLayout.CENTER);
        panel.add(crearFooter("Buscar otra ruta", e -> {
            detenerCronometro();
            cardLayout.show(panelContenedor, "BUSQUEDA");
        }, null, null), BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // PANTALLA 5 – HISTORIAL (Pila)
    // =========================================================
    private JPanel crearPantallaHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);
        panel.add(crearHeader("📜  Historial de Transacciones", "Cerrar Sesión", e -> cerrarSesion()), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        String[] columnas = {"Fecha / Hora", "Estación", "Minutos", "Monto (₡)", "Tipo"};
        modeloTablaHistorial = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(modeloTablaHistorial);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(AZUL);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(CELESTE);
        tabla.setGridColor(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        body.add(scroll, BorderLayout.CENTER);

        panel.add(body, BorderLayout.CENTER);
        panel.add(crearFooter("Mi Garaje", e -> cardLayout.show(panelContenedor, "GARAJE"), null, null), BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================
    // LÓGICA DE NEGOCIO
    // =========================================================

    private void validarLogin(String cedula) {
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Usuario encontrado = catalogoUsuarios.consultarUsuarioXCedula(cedula);
        if (encontrado != null) {
            usuarioActual       = encontrado;
            vehiculoActualIdx   = 0;
            cargarGaraje();
            cardLayout.show(panelContenedor, "GARAJE");
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cerrarSesion() {
        detenerCronometro();
        usuarioActual = null;
        cardLayout.show(panelContenedor, "LOGIN");
    }

    // ── Garaje ────────────────────────────────────────────────

    /** Reconstruye el array de índices de vehículos desde la ListaSimpleC. */
    private int[] obtenerIndicesVehiculos() {
        ListaSimpleC garaje = usuarioActual.getMisVehiculosIds();
        int[] info  = garaje.getINFO();
        int[] apunt = garaje.getApunt();
        int ptr     = garaje.getPTR();

        if (ptr == 0) return new int[0];

        // Contamos cuántos nodos hay en la lista circular
        int count = 0;
        int cur   = ptr;
        do { count++; cur = apunt[cur]; } while (cur != ptr);

        int[] ids = new int[count];
        cur = ptr;
        for (int i = 0; i < count; i++) {
            ids[i] = info[cur];
            cur = apunt[cur];
        }
        return ids;
    }

    private void cargarGaraje() {
        lblBienvenida.setText("Hola, " + usuarioActual.getNombreCompleto());
        mostrarVehiculoActual();
    }

    private void navegarGaraje(int delta) {
        int[] ids = obtenerIndicesVehiculos();
        if (ids.length == 0) return;
        vehiculoActualIdx = ((vehiculoActualIdx + delta) % ids.length + ids.length) % ids.length;
        mostrarVehiculoActual();
    }

    private void mostrarVehiculoActual() {
        panelTarjetaVehiculo.removeAll();

        int[] ids = obtenerIndicesVehiculos();
        if (ids.length == 0) {
            panelTarjetaVehiculo.add(etiqueta("No tienes vehículos en tu garaje.", Font.ITALIC, 14, TEXTO_GRIS));
            panelTarjetaVehiculo.revalidate();
            panelTarjetaVehiculo.repaint();
            return;
        }

        Vehiculo v = catalogoVehiculos.getVehiculo(ids[vehiculoActualIdx]);
        if (v == null) return;

        JPanel card = card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE_LIMON, 2),
            BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));

        JLabel icono = etiqueta("🚗", Font.PLAIN, 48, AZUL);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nombreV = etiqueta(v.getMarcaModelo(), Font.BOLD, 22, AZUL);
        nombreV.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel placa = etiqueta("Placa: " + v.getPlaca(), Font.PLAIN, 15, TEXTO_GRIS);
        placa.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel conector = etiqueta("Conector: " + v.getTipoConector(), Font.BOLD, 15, CELESTE);
        conector.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Indicador de posición (1 / N)
        JLabel pos = etiqueta((vehiculoActualIdx + 1) + " / " + ids.length, Font.PLAIN, 12, TEXTO_GRIS);
        pos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnBuscar = boton("⚡  Buscar Carga para este Vehículo", VERDE_LIMON, Color.WHITE, 15);
        btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBuscar.setMaximumSize(new Dimension(380, 50));
        final String conectorCaptura = v.getTipoConector();
        btnBuscar.addActionListener(e -> {
            conectorSeleccionado = conectorCaptura;
            cardLayout.show(panelContenedor, "BUSQUEDA");
        });

        card.add(icono);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(nombreV);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(placa);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(conector);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(pos);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(btnBuscar);

        panelTarjetaVehiculo.add(card, BorderLayout.CENTER);
        panelTarjetaVehiculo.revalidate();
        panelTarjetaVehiculo.repaint();
    }

    // ── Dijkstra → Agendamiento ───────────────────────────────

    private void prepararAgendamiento() {
        Estacion est = mapaEstaciones.getEstacion(estacionGanadoraIdx);
        lblEstacionAgenda.setText("📍  " + est.getUbicacion());

        // Contamos cuántos hay en cola rápida + lenta (aproximación visible)
        int enEspera = 0; // Las colas no exponen tamaño directamente; lo dejamos en 0 por defecto
        lblColaEspera.setText("Vehículos en espera: " + enEspera);

        // Resetear UI del timer
        lblCronometro.setText("10:00");
        lblCronometro.setForeground(VERDE_LIMON);
        btnYaLlegue.setEnabled(false);

        // Reactivar botón confirmar (por si volvieron atrás)
        // Lo buscamos en el árbol de componentes de AGENDA — más limpio: usar campo de instancia
    }

    private void agendarEnCola() {
        if (usuarioActual == null || estacionGanadoraIdx == -1) return;
        Estacion est = mapaEstaciones.getEstacion(estacionGanadoraIdx);

        // Creamos un turno provisional (tiempo 0, monto 0 hasta que confirme llegada)
        TurnoFactura turno = new TurnoFactura(
            java.time.LocalDateTime.now().toString(),
            est.getIdEstacion(),
            0,
            0.0
        );
        // Encolamos como carga rápida por defecto
        est.agendarVehiculo(turno, true);
    }

    private void iniciarCronometro() {
        segundosRestantes = 10 * 60; // 10 minutos

        timerGracia = new Timer(1000, null);
        timerGracia.addActionListener(e -> {
            segundosRestantes--;
            int mm = segundosRestantes / 60;
            int ss = segundosRestantes % 60;
            lblCronometro.setText(String.format("%02d:%02d", mm, ss));

            // Cambia a rojo en los últimos 2 minutos
            if (segundosRestantes <= 120) lblCronometro.setForeground(Color.RED);

            if (segundosRestantes <= 0) {
                detenerCronometro();
                aplicarMulta();
            }
        });
        timerGracia.start();
    }

    private void detenerCronometro() {
        if (timerGracia != null && timerGracia.isRunning()) {
            timerGracia.stop();
        }
    }

    private void aplicarMulta() {
        if (usuarioActual == null || estacionGanadoraIdx == -1) return;

        Estacion est = mapaEstaciones.getEstacion(estacionGanadoraIdx);
        // Desencolar forzado
        est.atenderSiguienteVehiculo(true);

        // Crear factura de multa
        TurnoFactura multa = new TurnoFactura(
            java.time.LocalDateTime.now().toString(),
            est.getIdEstacion() + " [MULTA]",
            0,
            5000.0   // multa fija ₡5,000
        );
        usuarioActual.registrarFactura(multa);

        JOptionPane.showMessageDialog(this,
            "⚠️  Tiempo de gracia agotado.\nSe aplicó una multa de ₡5,000.",
            "Multa Aplicada", JOptionPane.WARNING_MESSAGE);

        actualizarTablaHistorial();
        cardLayout.show(panelContenedor, "HISTORIAL");
    }

    private void registrarCargaExitosa() {
        if (usuarioActual == null || estacionGanadoraIdx == -1) return;

        Estacion est = mapaEstaciones.getEstacion(estacionGanadoraIdx);
        int minutosUsados = (10 * 60 - segundosRestantes) / 60 + 1;
        double monto = minutosUsados * est.getTarifaPorKwh();

        TurnoFactura factura = new TurnoFactura(
            java.time.LocalDateTime.now().toString(),
            est.getIdEstacion(),
            minutosUsados,
            monto
        );
        usuarioActual.registrarFactura(factura);
        // Desencolar al usuario que ya fue atendido
        est.atenderSiguienteVehiculo(true);

        JOptionPane.showMessageDialog(this,
            "✅  ¡Carga iniciada!\nMonto estimado: ₡" + String.format("%.2f", monto),
            "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Historial ─────────────────────────────────────────────

    private void mostrarHistorial() {
        actualizarTablaHistorial();
        cardLayout.show(panelContenedor, "HISTORIAL");
    }

    private void actualizarTablaHistorial() {
        modeloTablaHistorial.setRowCount(0); // limpiar
        if (usuarioActual == null) return;

        Pila pila = usuarioActual.getHistorialFacturas();

        // Leemos desde el tope sin destruir la pila — creamos una pila auxiliar
        Pila aux = new Pila(pila.getTop());
        // Vaciamos la original hacia la auxiliar (invierte), luego la restauramos
        // Pasada 1: original → aux (queda invertida)
        while (!pila.pilaVacia()) {
            aux.ingresarElemento(pila.eliminarElemento());
        }
        // Pasada 2: aux → original, y al mismo tiempo llenamos la tabla (orden correcto: tope primero)
        // aux tiene el fondo de la original en su tope, así que necesitamos otra pasada
        // Más simple: usamos un arreglo temporal de tamaño conocido
        // Rehacemos con conteo
        // Restauramos primero
        while (!aux.pilaVacia()) {
            pila.ingresarElemento(aux.eliminarElemento());
        }

        // Ahora leemos desde el tope con una copia real
        // La Pila tiene getTop() → sabemos el máximo.
        // Usamos dos aux para mostrar sin perder datos:
        Pila copia = new Pila(pila.getTop());
        Pila temp  = new Pila(pila.getTop());

        // original → temp (invierte)
        while (!pila.pilaVacia()) {
            temp.ingresarElemento(pila.eliminarElemento());
        }
        // temp → copia (restaura orden original) y llena tabla
        while (!temp.pilaVacia()) {
            TurnoFactura tf = temp.eliminarElemento();
            copia.ingresarElemento(tf);          // reconstruimos la original en copia
            String tipo = tf.getIdEstacion().contains("MULTA") ? "⚠️ Multa" : "✅ Carga";
            modeloTablaHistorial.addRow(new Object[]{
                tf.getFechaHora().substring(0, 19).replace("T", "  "),
                tf.getIdEstacion().replace(" [MULTA]", ""),
                tf.getTiempoConsumidoMinutos(),
                String.format("%.2f", tf.getMontoCobrado()),
                tipo
            });
        }
        // Restaurar la pila original desde copia
        Pila temp2 = new Pila(pila.getTop());
        while (!copia.pilaVacia()) {
            temp2.ingresarElemento(copia.eliminarElemento());
        }
        while (!temp2.pilaVacia()) {
            pila.ingresarElemento(temp2.eliminarElemento());
        }
    }
}