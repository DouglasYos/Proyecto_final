package proyecto_final;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import proyecto_final.Producto;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar el formulario de inicio de sesión
        sesionForm sesion = new sesionForm();
        sesion.setVisible(true);
        
        // Lógica de inicio de sesión
        sesion.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = sesion.jTextField2.getText();
                String password = new String(sesion.jPasswordField1.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(sesion, "Por favor, ingrese el usuario y la contraseña.");
                } else {
                    // Intentar iniciar sesión
                    conexion_bd conexionBD = new conexion_bd();
                    boolean inicioExitoso = conexionBD.iniciarSesion(username, password);

                    if (inicioExitoso) {
                        JOptionPane.showMessageDialog(sesion, "Presione aceptar para abrir pantalla principal.");
                        // Iniciar el registro de productos
                        sesion.setVisible(false);  // Ocultar la ventana de sesión
                        mostrarRegistroProducto();  // Mostrar la ventana de registro de productos
                    } else {
                        JOptionPane.showMessageDialog(sesion, "el usuario y la contraseña son incorrectos");
                    }
                }
            }
        });

        // Lógica de registro de usuario
        sesion.jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = sesion.jTextField2.getText();
                String password = new String(sesion.jPasswordField1.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(sesion, "Por favor, ingrese un nombre de usuario y una contraseña.");
                } else {
                    conexion_bd conexionBD = new conexion_bd();
                    boolean registroExitoso = conexionBD.registrarUsuario(username, password);

                    if (registroExitoso) {
                        JOptionPane.showMessageDialog(sesion, "Usuario registrado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(sesion, "El usuario ya existe o el registro falló.");
                    }
                }
            }
        });
    }

    // Método que muestra la interfaz para el registro de productos
    private static void mostrarRegistroProducto() {
        // Crear y mostrar la ventana de registro de productos
        registro_producto registro = new registro_producto();

        // Añadir los listeners a los botones de la ventana de registro de productos
        registro.jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarProducto(registro);
            }
        });

        registro.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarProducto(registro);
            }
        });

        registro.jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto(registro);
            }
        });

        registro.jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto(registro);
            }
        });

        registro.jButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos(registro);
            }
        });

        registro.setVisible(true);
    }

    // Métodos para manejar los productos (refactorizados)
    private static void insertarProducto(registro_producto registro) {
        realizarAccionProducto(registro, "insertar");
    }

    private static void consultarProducto(registro_producto registro) {
        realizarAccionProducto(registro, "consultar");
    }

    private static void actualizarProducto(registro_producto registro) {
        realizarAccionProducto(registro, "actualizar");
    }

    private static void eliminarProducto(registro_producto registro) {
        realizarAccionProducto(registro, "eliminar");
    }

    private static void realizarAccionProducto(registro_producto registro, String accion) {
        String codigo = registro.jTextField1.getText();
        String descripcion = registro.jTextField2.getText();
        String precio = registro.jTextField3.getText();
        String stock = registro.jTextField4.getText();

        // Validación de campos
        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
            JOptionPane.showMessageDialog(registro, "Todos los campos deben estar llenos.");
            return;
        }

        conexion_bd conexionBD = new conexion_bd();
        boolean exito = false;

        switch (accion) {
            case "insertar":
                exito = conexionBD.insertarProducto(codigo, descripcion, precio, stock);
                break;
            case "consultar":
                Productos productos = conexionBD.consultarProductos(codigo);
                if (productos != null) {
                    registro.jTextField1.setText(productos.getCodigo());
                    registro.jTextField2.setText(productos.getDescripcion());
                    registro.jTextField3.setText(productos.getPrecio());
                    registro.jTextField4.setText(productos.getStock());
                } else {
                    JOptionPane.showMessageDialog(registro, "Producto no encontrado.");
                }
                return;  // No continuar después de consultar
            case "actualizar":
                exito = conexionBD.actualizarProducto(codigo, descripcion, precio, stock);
                break;
            case "eliminar":
                exito = conexionBD.eliminarProducto(codigo);
                break;
        }

        if (exito) {
            JOptionPane.showMessageDialog(registro, "Producto " + accion + " correctamente.");
            mostrarProductos(registro);
        } else {
            JOptionPane.showMessageDialog(registro, "Error al " + accion + " el producto.");
        }
    }

    private static void mostrarProductos(registro_producto registro) {
        conexion_bd conexionBD = new conexion_bd();
        List<Producto> productos = conexionBD.obtenerTodosLosProductos();

        DefaultTableModel model = (DefaultTableModel) registro.jTable2.getModel();
        model.setRowCount(0);  // Limpiar la tabla

        for (Producto producto : productos) {
            model.addRow(new Object[]{producto.getCodigo(), producto.getDescripcion(), producto.getPrecio(), producto.getStock()});
        }
    }
}