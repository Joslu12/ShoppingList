package bd.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import model.IdentifiedObjectClass;

public abstract class AbstractDao<T extends IdentifiedObjectClass> {

    //---- Attributes ----
    private final SQLiteDatabase bdConnection;

    //---- Constructor ----
    public AbstractDao(final SQLiteDatabase connection) {
        bdConnection = connection;
    }

    //---- Methods ----

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
    public int insert(T elem) {
        if(elem == null) {
            return -1;
        } else {
            return doInsert(elem);
        }
    }

    protected abstract int doInsert(T elem);

    /**
     * @param elem
     * @return numero de filas actualizadas o -1 si el elem recibido es null
     */
    public long update(T elem) {
        if(elem == null) {
            return -1;
        } else {
            return doUpdate(elem);
        }
    }

    protected abstract long doUpdate(T elem);

    /**
     *
     * @param elem
     * @return numero de filas eliminadas o -1 si el elem recibido es null
     */
    public long remove(T elem) {
        if(elem == null) {
            return -1;
        } else {
            return doRemove(elem);
        }
    }

    protected abstract long doRemove(T elem);

}
