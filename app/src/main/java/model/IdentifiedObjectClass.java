package model;

import java.io.Serializable;

public abstract class IdentifiedObjectClass implements Serializable {

    //---- Attributes ----
    private int id;

    //---- Constructor ----
    public IdentifiedObjectClass(final int id) {
        this.id = id;
    }

    //---- Methods ----
    /**
     * @return el identificador del objeto
     */
    public int getID() {
        return id;
    }

    /**
     * @param id nuevo identificador del objeto
     */
    public void setID(final int id) {
        this.id = id;
    }
}
