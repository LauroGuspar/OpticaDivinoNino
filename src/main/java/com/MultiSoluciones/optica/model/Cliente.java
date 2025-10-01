package com.MultiSoluciones.optica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente", nullable=false)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name="cli_nombre",nullable = false)
    private String nombre;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
    @Column(name="cli_nombreuser",nullable = false, unique = true, length = 50)
    private String usuario;

    @NotBlank(message="El Apellido Paterno es obligatorio")
    @Size(min=5, max=100, message="El Apellido Paterno debe de tener mínimos 5 y maximo 100 caracteres")
    @Column(name="cli_apellido_paterno",nullable=false)
    private String apellidoPaterno;

    @NotBlank(message="El Apellido Materno es obligatorio")
    @Size(min=5, max=100, message="El Apellido Materno debe de tener mínimos 5 y maximo 100 caracteres")
    @Column(name="cli_apellido_materno", nullable=false)
    private String apellidoMaterno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(name="cli_correo", nullable = false, unique = true, length=60)
    private String correo;

    @Size(min = 6, max=150, message = "La clave debe tener al menos 6 caracteres")
    @Column(name="cli_contrasena",nullable = false)
    private String clave;

    @NotBlank(message="El Telefono es obligatorio")
    @Size(min=9, max=9, message="El Número telefonico debe de tener mínimos 9 y maximo 9 caracteres")
    @Column(name="cli_telefono", nullable=false, unique = true)
    private String telefono;

    @NotBlank(message="La dirección es obligatorio")
    @Size(min=10, max=100, message="La Dirección debe de tener mínimos 20 y maximo 100 caracteres")
    @Column(name="cli_direccion",nullable=false)
    private String direccion;

    @Column(name="cli_estado", nullable = false)
    private Integer estado = 1;

    @NotBlank(message="El Número de Documento es obligatorio")
    @Size(min=8, max=20, message="El Número De Documento debe de tener mínimos 8 Caracteres")
    @Column(name="cli_ndocumento",nullable=false, unique = true)
    private String ndocumento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipodocumento")
    private TipoDocumento tipodocumento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @NotBlank(message = "El nombre de empresa es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de empresa debe tener entre 2 y 100 caracteres")
    @Column(name="cli_nombre_empresa",nullable = false)
    private String nombreEmpresa;

    @NotBlank(message = "La direccion de empresa es obligatoria")
    @Size(min = 2, max = 100, message = "La direccion de empresa debe tener entre 2 y 100 caracteres")
    @Column(name="cli_direccion_empresa",nullable = false)
    private String direccionEmpresa;

    public Cliente() {
    }

    public Cliente(String nombre, String usuario, String apellidoPaterno, String apellidoMaterno, String correo, String clave, String telefono, String direccion, String ndocumento, TipoDocumento tipodocumento, Perfil perfil, String nombreEmpresa, String direccionEmpresa) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.clave = clave;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = 1;
        this.ndocumento = ndocumento;
        this.tipodocumento = tipodocumento;
        this.perfil = perfil;
        this.nombreEmpresa = nombreEmpresa;
        this.direccionEmpresa = direccionEmpresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El usuario es obligatorio") @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres") String getUsuario() {
        return usuario;
    }

    public void setUsuario(@NotBlank(message = "El usuario es obligatorio") @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres") String usuario) {
        this.usuario = usuario;
    }

    public @NotBlank(message = "El Apellido Paterno es obligatorio") @Size(min = 5, max = 100, message = "El Apellido Paterno debe de tener mínimos 5 y maximo 100 caracteres") String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(@NotBlank(message = "El Apellido Paterno es obligatorio") @Size(min = 5, max = 100, message = "El Apellido Paterno debe de tener mínimos 5 y maximo 100 caracteres") String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public @NotBlank(message = "El Apellido Materno es obligatorio") @Size(min = 5, max = 100, message = "El Apellido Materno debe de tener mínimos 5 y maximo 100 caracteres") String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(@NotBlank(message = "El Apellido Materno es obligatorio") @Size(min = 5, max = 100, message = "El Apellido Materno debe de tener mínimos 5 y maximo 100 caracteres") String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String getCorreo() {
        return correo;
    }

    public void setCorreo(@NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe tener un formato válido") String correo) {
        this.correo = correo;
    }

    public @Size(min = 6, max = 150, message = "La clave debe tener al menos 6 caracteres") String getClave() {
        return clave;
    }

    public void setClave(@Size(min = 6, max = 150, message = "La clave debe tener al menos 6 caracteres") String clave) {
        this.clave = clave;
    }

    public @NotBlank(message = "El Telefono es obligatorio") @Size(min = 9, max = 9, message = "El Número telefonico debe de tener mínimos 9 y maximo 9 caracteres") String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotBlank(message = "El Telefono es obligatorio") @Size(min = 9, max = 9, message = "El Número telefonico debe de tener mínimos 9 y maximo 9 caracteres") String telefono) {
        this.telefono = telefono;
    }

    public @NotBlank(message = "La dirección es obligatorio") @Size(min = 10, max = 100, message = "La Dirección debe de tener mínimos 20 y maximo 100 caracteres") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotBlank(message = "La dirección es obligatorio") @Size(min = 10, max = 100, message = "La Dirección debe de tener mínimos 20 y maximo 100 caracteres") String direccion) {
        this.direccion = direccion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public @NotBlank(message = "El Número de Documento es obligatorio") @Size(min = 8, max = 20, message = "El Número De Documento debe de tener mínimos 8 Caracteres") String getNdocumento() {
        return ndocumento;
    }

    public void setNdocumento(@NotBlank(message = "El Número de Documento es obligatorio") @Size(min = 8, max = 20, message = "El Número De Documento debe de tener mínimos 8 Caracteres") String ndocumento) {
        this.ndocumento = ndocumento;
    }

    public TipoDocumento getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(TipoDocumento tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public @NotBlank(message = "El nombre de empresa es obligatorio") @Size(min = 2, max = 100, message = "El nombre de empresa debe tener entre 2 y 100 caracteres") String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(@NotBlank(message = "El nombre de empresa es obligatorio") @Size(min = 2, max = 100, message = "El nombre de empresa debe tener entre 2 y 100 caracteres") String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public @NotBlank(message = "La direccion de empresa es obligatoria") @Size(min = 2, max = 100, message = "La direccion de empresa debe tener entre 2 y 100 caracteres") String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(@NotBlank(message = "La direccion de empresa es obligatoria") @Size(min = 2, max = 100, message = "La direccion de empresa debe tener entre 2 y 100 caracteres") String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }
}
