package bd.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import model.IdentifiedObjectClass;

public abstract class AbstractDao<T extends IdentifiedObjectClass> {

    //---- Atributos ----
    private final SQLiteDatabase conexionBD;

    //---- Constructor ----
    public AbstractDao(final SQLiteDatabase conexion) {
        conexionBD = conexion;
    }

    //---- Metodos ----

    /**
     * @return conexion a la bd
     */
    public SQLiteDatabase getConexionBD() {
        return conexionBD;
    }

    /**
     *
     * @param id
     * @return
     */
    public abstract T findById(int id);

    /**
     *
     * @return
     */
    public abstract List<T> findAll();

    /**
     * @param elem
     * @return el id generado del elemento añadido o -1 si ocurre algún error
     */
    public abstract int insert(T elem);

    /**
     * @param elem
     * @return numero de filas actualizadas
     */
    public abstract long update(T elem);

    /**
     *
     * @param elem
     * @return numero de filas eliminadas
     */
    public abstract long remove(T elem);

}
