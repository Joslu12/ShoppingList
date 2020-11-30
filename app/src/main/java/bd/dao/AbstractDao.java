package bd.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import model.IdentifiedObjectClass;

public abstract class AbstractDao<T extends IdentifiedObjectClass> {

    //---- Atributos ----
    private final SQLiteDatabase bdConnection;

    //---- Constructor ----
    public AbstractDao(final SQLiteDatabase connection) {
        bdConnection = connection;
    }

    //---- Metodos ----

    /**
     * @return conexion a la bd
     */
    public SQLiteDatabase getBDConnection() {
        return bdConnection;
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
     * El propio metodo actualiza el estado de elem, editando su id
     * @param elem
     * @return el id generado del elemento anadido o -1 si ocurre algun error
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
