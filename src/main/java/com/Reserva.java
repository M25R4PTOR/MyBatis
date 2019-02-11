package com;

public class Reserva {

	private Integer id;

	private String horaInicio;

	private String horaFin;

	private String dia;

	private Integer idCampo;

	private Integer idTipoDeporte;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Integer getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(Integer idCampo) {
		this.idCampo = idCampo;
	}

	public Integer getIdTipoDeporte() {
		return idTipoDeporte;
	}

	public void setIdTipoDeporte(Integer idTipoDeporte) {
		this.idTipoDeporte = idTipoDeporte;
	}

	public Reserva(Integer id, String horaInicio, String horaFin, String dia, Integer idCampo, Integer idTipoDeporte) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.dia = dia;
		this.idCampo = idCampo;
		this.idTipoDeporte = idTipoDeporte;
	}

	public Reserva() {
		super();
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", dia=" + dia
				+ ", idCampo=" + idCampo + ", idTipoDeporte=" + idTipoDeporte + "]";
	}

}
