package model;

public abstract class IdentifiedObjectClass {

    //---- Atributos ----
    private final int id;

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
}
