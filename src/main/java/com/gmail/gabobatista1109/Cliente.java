package com.gmail.gabobatista1109;

public class Cliente {
    private String idCliente;
    private String nombreCompania;
    private String nombreContacto;
    private int antiguedad;
    private double facturacion;
    private String cargoContacto;
    private String direccion;
    private String ciudad;
    private String region;
    private String codPostal;
    private String pais;
    private String telefono;
    private String fax;

    public Cliente(String idCliente, String nombreCompania, String nombreContacto, int antiguedad, double facturacion, String cargoContacto, String direccion, String ciudad, String region, String codPostal, String pais, String telefono, String fax) {
        this.idCliente = idCliente;
        this.nombreCompania = nombreCompania;
        this.nombreContacto = nombreContacto;
        this.antiguedad = antiguedad;
        this.facturacion = facturacion;
        this.cargoContacto = cargoContacto;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.region = region;
        this.codPostal = codPostal;
        this.pais = pais;
        this.telefono = telefono;
        this.fax = fax;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNombreCompania() {
        return nombreCompania;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public double getFacturacion() {
        return facturacion;
    }

    public String getCargoContacto() {
        return cargoContacto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getRegion() {
        return region;
    }

    public String getPais() {
        return pais;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setFacturacion(double facturacion) {
        this.facturacion = facturacion;
    }

    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
