package model;

public abstract class IdentifiedObjectClass {

    //---- Atributos ----
    private int id;

    //---- Constructor ----
    public IdentifiedObjectClass(final int id) {
        this.id = id;
    }

    //---- Metodos ----
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
