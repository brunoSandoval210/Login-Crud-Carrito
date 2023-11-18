package Modelo;

public class Producto {
    private String id;
    private String des;
    private double pre;
    private int sto;
    
    public Producto() {
    }

    public Producto(String id, String des, double pre, int sto) {
        this.id = id;
        this.des = des;
        this.pre = pre;
        this.sto = sto;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPre() {
        return pre;
    }

    public void setPre(double pre) {
        this.pre = pre;
    }

    public int getSto() {
        return sto;
    }

    public void setSto(int sto) {
        this.sto = sto;
    }

    
}
