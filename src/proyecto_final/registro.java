package proyecto_final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class registro extends JFrame {

    private JTextField txtCodigo, txtDescripcion, txtPrecio, txtStock, txtBuscar;
    private JButton btnNuevo, btnGrabar, btnActualizar, btnCancelar, btnSalir, btnMostrarTodo;
    private JTable tableProductos;
    private DefaultTableModel tableModel;

    public registro() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel Detalle de Producto
        JPanel panelDetalle = new JPanel();
        panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de Producto"));
        panelDetalle.setBounds(10, 10, 300, 150);
        panelDetalle.setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(10, 25, 80, 20);
        panelDetalle.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 25, 150, 20);
        panelDetalle.add(txtCodigo);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 55, 80, 20);
        panelDetalle.add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(100, 55, 150, 20);
        panelDetalle.add(txtDescripcion);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 85, 80, 20);
        panelDetalle.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 85, 150, 20);
        panelDetalle.add(txtPrecio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(10, 115, 80, 20);
        panelDetalle.add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(100, 115, 150, 20);
        panelDetalle.add(txtStock);

        add(panelDetalle);

        // Botones de acciones
        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(320, 20, 100, 25);
        add(btnNuevo);

        btnGrabar = new JButton("Grabar");
        btnGrabar.setBounds(320, 55, 100, 25);
        add(btnGrabar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(320, 90, 100, 25);
        add(btnActualizar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(320, 125, 100, 25);
        add(btnCancelar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(320, 160, 100, 25);
        add(btnSalir);

        // Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(10, 170, 80, 20);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(60, 170, 150, 20);
        add(txtBuscar);

        btnMostrarTodo = new JButton("Mostrar Todo");
        btnMostrarTodo.setBounds(220, 170, 120, 20);
        add(btnMostrarTodo);

        // Tabla de productos
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Código", "Descripción", "Precio", "Stock"});
        tableProductos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProductos);
        scrollPane.setBounds(10, 200, 460, 150);
        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new registro().setVisible(true);
        });
    }
}
