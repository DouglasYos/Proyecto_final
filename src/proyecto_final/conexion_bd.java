package proyecto_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class conexion_bd {
    private static final String URL = "jdbc:mysql://localhost:3306/almacen";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    
    // Método para conectar a la base de datos
    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
    
    // Método para registrar un usuario en la base de datos
    public boolean registrarUsuario(String username, String password) {
        if (usuarioExiste(username)) {
            System.out.println("El usuario ya está registrado.");
            return false;
        }
        
        Connection conexion = conectar();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int filasInsertadas = stmt.executeUpdate();
            
            if (filasInsertadas > 0) {
                System.out.println("Usuario registrado con éxito");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }
    
    // Método para validar el inicio de sesión
    public boolean iniciarSesion(String username, String password) {
        Connection conexion = conectar();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultado = stmt.executeQuery();
            
            if (resultado.next()) {
                System.out.println("Inicio de sesión exitoso");
                return true;
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }

    // Método para verificar si un usuario ya está registrado
    public boolean usuarioExiste(String username) {
        Connection conexion = conectar();
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet resultado = stmt.executeQuery();
            
            if (resultado.next()) {
                System.out.println("El usuario ya existe en la base de datos.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si el usuario existe: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }
    
    // Método para insertar un producto en la base de datos
    public boolean insertarProducto(String codigo, String descripcion, String precio, String stock) {
        Connection conexion = conectar();
        String sql = "INSERT INTO productos (codigo, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.setString(2, descripcion);
            stmt.setString(3, precio);
            stmt.setString(4, stock);
            int filasInsertadas = stmt.executeUpdate();
            
            if (filasInsertadas > 0) {
                System.out.println("Producto insertado con éxito");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return false;
    }
    
    // Método para consultar un producto en la base de datos por su código
    public Productos consultarProducto(String codigo) {
    Connection conexion = conectar();
    String sql = "SELECT descripcion, precio, stock FROM productos WHERE codigo = ?";
    
    try {
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet resultado = stmt.executeQuery();
        
        if (resultado.next()) {
            String descripcion = resultado.getString("descripcion");
            String precio = resultado.getString("precio");
            String stock = resultado.getString("stock");
            return new Productos(codigo, descripcion, precio, stock);  // Devuelve un objeto Producto
        } else {
            System.out.println("Producto no encontrado");
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar producto: " + e.getMessage());
    } finally {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return null;  // Devuelve null si no encuentra el producto
    }

    Productos consultarProductos(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean actualizarProductos(String codigo, String descripcion, String precio, String stock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean eliminarProductos(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<Productos> obtenerTodosLosProductos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Producto> obtenerTodosLosProductos() {
    List<Producto> productos = new ArrayList<>();
    
    try {
        // Conexión a la base de datos
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "usuario", "contraseña");
        String query = "SELECT * FROM productos";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        // Llenar la lista de productos con los resultados
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setCodigo(rs.getString("codigo"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getString("precio"));
            producto.setStock(rs.getString("stock"));
            productos.add(producto);
        }
        
        rs.close();
        stmt.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return productos;
}
public List<Producto> obtenerTodosLosProductos() {
    List<Producto> productos = new ArrayList<>();
    
    try {
        Connection conexion = conectar();
        String query = "SELECT * FROM productos";
        PreparedStatement stmt = conexion.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Producto producto = new Producto(
                rs.getString("codigo"), 
                rs.getString("descripcion"), 
                rs.getString("precio"), 
                rs.getString("stock")
            );
            productos.add(producto);
        }
        
        rs.close();
        stmt.close();
        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return productos;
}

}