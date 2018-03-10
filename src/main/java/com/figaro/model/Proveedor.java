package com.figaro.model;

public class Proveedor {

	private int id;
	private String nombre;
	private String email;
	private String telefono;
	private String dirCiudad;
	private String dirCalle;
	private Integer dirNumeroCalle;
	private Integer dirPiso;
	private String dirDpto;
	private String notas;
	
	

	public void update(Proveedor proveedor) {
		this.setNombre(proveedor.getNombre());
		this.setEmail(proveedor.getEmail());
		this.setTelefono(proveedor.getTelefono());
		this.setDirCiudad(proveedor.getDirCiudad());
		this.setDirCalle(proveedor.getDirCalle());
		this.setDirNumeroCalle(proveedor.getDirNumeroCalle());
		this.setDirPiso(proveedor.getDirPiso());
		this.setDirDpto(proveedor.getDirDpto());
		this.setNotas(proveedor.getNotas());
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDirCiudad() {
		return dirCiudad;
	}
	public void setDirCiudad(String dirCiudad) {
		this.dirCiudad = dirCiudad;
	}
	public String getDirCalle() {
		return dirCalle;
	}
	public void setDirCalle(String dirCalle) {
		this.dirCalle = dirCalle;
	}
	public Integer getDirNumeroCalle() {
		return dirNumeroCalle;
	}
	public void setDirNumeroCalle(Integer dirNumeroCalle) {
		this.dirNumeroCalle = dirNumeroCalle;
	}
	public Integer getDirPiso() {
		return dirPiso;
	}
	public void setDirPiso(Integer dirPiso) {
		this.dirPiso = dirPiso;
	}
	public String getDirDpto() {
		return dirDpto;
	}
	public void setDirDpto(String dirDpto) {
		this.dirDpto = dirDpto;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	

	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
				+ ", dirCiudad=" + dirCiudad + ", dirCalle=" + dirCalle + ", dirNumeroCalle=" + dirNumeroCalle
				+ ", dirPiso=" + dirPiso + ", dirDpto=" + dirDpto + ", notas=" + notas + "]";
	}



	
}
