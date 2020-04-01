package exercici3;

import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author bazag
 */
public class Exercici3 {

    public static void main(String[] args) throws XMLDBException {
        
        // Se lee un dato de tipo cadena
        System.out.print("Escribe un departamento: ");
        String s = null;
        
        try {
            
            BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
            s = in.readLine();
            
        } catch (IOException e) {
            
            System.out.println("Error al leer");  
        }
        
        int dep = Integer.parseInt(s);
        
        // Driver para eXist
        String driver = "org.exist.xmldb.DatabaseImpl";
        
        // Colección
        Collection col = null;
        
        // URI colección
        String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Proves";
        
        // Usuario y contraseña
        String user = "admin";
        String password = "admin";
        
        try {
            
            // Cargamos el driver
            Class cl = Class.forName(driver);
            
            // Instanciamos la db
            Database db = (Database) cl.newInstance();
            
            // Registro del driver
            DatabaseManager.registerDatabase(db);
            
        } catch (Exception e) {
            
            System.out.println("Error al inicializar la BD de eXist");
        }
        
        col = DatabaseManager.getCollection(URI, user, password);
        
        if (col == null) {
            
            System.out.println("LA COLECCIÓN NO EXISTE");
        }
        
        XPathQueryService servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        ResourceSet result = servicio.query("for $em in /EMPLEADOS/EMP_ROW[DEPT_NO="+dep+"] return $em");
        
        // Recorremos los datos del recurso
        ResourceIterator i = result.getIterator();
        
        if (!i.hasMoreResources()) {
            
            System.out.println("LA CONSULTA NO DEVUELVE NADA");
        }
        
        while (i.hasMoreResources()) {
            
            Resource r = i.nextResource();
            System.out.println((String)r.getContent());
        }
        
        // Se borra
        col.close();
    }
}
