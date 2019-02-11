package com;

import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MenuReservas {

	public static void main(String[] args) {

		try {
			String resource = "ConfiguracionIBatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

			menu(sqlMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void menu(SqlMapClient sqlMap) throws SQLException {
		String eleccion = "5";
		do {
			eleccion = (String) JOptionPane.showInputDialog(null,
					"---Menu---\n1. CRUD Campo\n2. CRUD Tipo de Deporte\n3. Crear Reserva\n4. Consultar Reservas\n0. Salir");
			if (eleccion == null) {
				eleccion = "0";
			}
			switch (eleccion) {
			case "1":
				menuCampo(sqlMap);
				break;
			case "2":
				menuTipoDeporte(sqlMap);
				break;
			case "3":
				crearReserva(sqlMap);
				break;
			case "4":
				consultarReserva(sqlMap);
				break;
			case "0":
				break;
			default:
				eleccion = "5";
				JOptionPane.showMessageDialog(null, "Esa opción no es correcta");
				break;
			}
		} while (!eleccion.equals("0"));

	}

	private static void consultarReserva(SqlMapClient sqlMap) throws SQLException {
		List<Reserva> reservas = sqlMap.queryForList("getReservas", null);
		String aux = "";
		for (Reserva c : reservas) {
			TipoDeporte tipo = (TipoDeporte) sqlMap.queryForObject("getTipo", c.getIdTipoDeporte());
			Campo campo = (Campo) sqlMap.queryForObject("getCampo", c.getIdCampo());

			aux += "Reserva [id=" + c.getId() + ", dia=" + c.getDia() + ", nombre campo=" + campo.getNombre() + "("
					+ campo.getId() + "), nombre tipo deporte=" + tipo.getNombre() + "(" + tipo.getId() + ")\n";
		}
		JOptionPane.showMessageDialog(null, aux);
	}

	private static void crearReserva(SqlMapClient sqlMap) throws SQLException {
		String auxTipo = consultarTipos2(sqlMap);
		String idTipoDeporte = (String) JOptionPane.showInputDialog(null,
				auxTipo + "Seleccione el tipo de deporte deseado");

		String auxCampo = consultarCampos2(sqlMap);

		String idCampo = (String) JOptionPane.showInputDialog(null, auxCampo + "Seleccione el campo deseado");

		String inicio = (String) JOptionPane.showInputDialog(null, "Introduzca la hora de inicio");
		String fin = (String) JOptionPane.showInputDialog(null, "Introduzca la hora de fin");
		String dia = (String) JOptionPane.showInputDialog(null, "Introduzca el día");

		Reserva reserva = new Reserva();
		try {
			reserva.setHoraInicio(inicio);
			reserva.setHoraFin(fin);
			reserva.setDia(dia);
			reserva.setIdCampo(Integer.parseInt(idCampo));
			reserva.setIdTipoDeporte(Integer.parseInt(idTipoDeporte));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error datos introducinos no válidos");
			menu(sqlMap);
		}
		sqlMap.insert("insertReserva", reserva);
	}

	private static void menuTipoDeporte(SqlMapClient sqlMap) throws SQLException {
		String eleccion = "5";
		do {
			eleccion = (String) JOptionPane.showInputDialog(null,
					"---Menu---\n1. Insertar Tipo de Deporte\n2. Actualizar Tipo de Deporte\n3. Borrar Tipo de Deporte\n4. Consultar Tipos de Deporte\n0. Salir");
			if (eleccion == null) {
				eleccion = "0";
			}
			switch (eleccion) {
			case "1":
				insertarTipo(sqlMap);
				break;
			case "2":
				String claveCampo = (String) JOptionPane.showInputDialog(null,
						"Introduzca el id del tipo de deporte a modificar");
				modificarTipo(sqlMap, Integer.parseInt(claveCampo));
				break;
			case "3":
				String claveCampoBorrar = (String) JOptionPane.showInputDialog(null,
						"Introduzca el id del tipo de deporte a eliminar");
				eliminarTipo(sqlMap, Integer.parseInt(claveCampoBorrar));
				break;
			case "4":
				consultarTipos(sqlMap);
				break;
			case "0":
				break;
			default:
				eleccion = "5";
				JOptionPane.showMessageDialog(null, "Esa opción no es correcta");
				break;
			}
		} while (!eleccion.equals("0"));
	}

	private static void insertarTipo(SqlMapClient sqlMap) throws SQLException {
		TipoDeporte tipo = new TipoDeporte();
		String nombre = (String) JOptionPane.showInputDialog(null, "Introduzca el nombre del tipo de deporte");
		tipo.setNombre(nombre);
		sqlMap.insert("insertTipo", tipo);
	}

	private static void modificarTipo(SqlMapClient sqlMap, Integer claveCampo) throws SQLException {
		TipoDeporte tipo = (TipoDeporte) sqlMap.queryForObject("getTipo", claveCampo);

		String nombre = (String) JOptionPane.showInputDialog(null, "Introduzca el nuevo nombre del tipo de deporte");
		tipo.setNombre(nombre);

		sqlMap.update("updateTipo", tipo);
	}

	private static void eliminarTipo(SqlMapClient sqlMap, Integer id) throws SQLException {
		Reserva reserva = (Reserva) sqlMap.queryForObject("getReservaTipo", id);

		if (reserva == null) {
			sqlMap.delete("removeTipo", id);
		} else {
			JOptionPane.showMessageDialog(null, "El tipo de deporte está siendo utilizado en una reserva");
		}

	}

	private static void consultarTipos(SqlMapClient sqlMap) throws SQLException {
		List<TipoDeporte> tipos = sqlMap.queryForList("getTipos", null);
		String aux = "";
		for (TipoDeporte c : tipos) {
			aux += c.toString() + "\n";
		}
		JOptionPane.showMessageDialog(null, aux);
	}

	private static String consultarTipos2(SqlMapClient sqlMap) throws SQLException {
		List<TipoDeporte> tipos = sqlMap.queryForList("getTipos", null);
		String aux = "";
		for (TipoDeporte c : tipos) {
			aux += c.toString() + "\n";
		}

		return aux;
	}

	private static void menuCampo(SqlMapClient sqlMap) throws SQLException {
		String eleccion = "5";
		do {
			eleccion = (String) JOptionPane.showInputDialog(null,
					"---Menu---\n1. Insertar Campo\n2. Actualizar Campo\n3. Borrar Campo\n4. Consultar Campos\n0. Salir");
			if (eleccion == null) {
				eleccion = "0";
			}
			switch (eleccion) {
			case "1":
				insertarCampo(sqlMap);
				break;
			case "2":
				String claveCampo = (String) JOptionPane.showInputDialog(null,
						"Introduzca el id del campo a modificar");
				modificarCampo(sqlMap, Integer.parseInt(claveCampo));
				break;
			case "3":
				String claveCampoBorrar = (String) JOptionPane.showInputDialog(null,
						"Introduzca el id del campo a eliminar");
				eliminarCampo(sqlMap, Integer.parseInt(claveCampoBorrar));
				break;
			case "4":
				consultarCampos(sqlMap);
				break;
			case "0":
				break;
			default:
				eleccion = "5";
				JOptionPane.showMessageDialog(null, "Esa opción no es correcta");
				break;
			}
		} while (!eleccion.equals("0"));
	}

	private static void insertarCampo(SqlMapClient sqlMap) throws SQLException {
		// INSERTAR no ponemos id porque lo incluye automatico
		Campo campo = new Campo();
		String nombre = (String) JOptionPane.showInputDialog(null, "Introduzca el nombre del campo");
		campo.setNombre(nombre);
		sqlMap.insert("insertCampo", campo);
	}

	private static void modificarCampo(SqlMapClient sqlMap, Integer claveCampo) throws SQLException {
		Campo campoModif = (Campo) sqlMap.queryForObject("getCampo", claveCampo);

		String nombre = (String) JOptionPane.showInputDialog(null, "Introduzca el nuevo nombre del campo");
		campoModif.setNombre(nombre);

		sqlMap.update("updateCampo", campoModif);
	}

	private static void eliminarCampo(SqlMapClient sqlMap, Integer id) throws SQLException {
		Reserva reserva = (Reserva) sqlMap.queryForObject("getReservaCampo", id);

		if (reserva == null) {
			sqlMap.delete("removeCampo", id);
		} else {
			JOptionPane.showMessageDialog(null, "El campo está siendo utilizado en una reserva");
		}
	}

	private static void consultarCampos(SqlMapClient sqlMap) throws SQLException {
		List<Campo> campos = sqlMap.queryForList("getCampos", null);
		String aux = "";
		for (Campo c : campos) {
			aux += c.toString() + "\n";
		}
		JOptionPane.showMessageDialog(null, aux);
	}

	private static String consultarCampos2(SqlMapClient sqlMap) throws SQLException {
		List<Campo> campos = sqlMap.queryForList("getCampos", null);
		String aux = "";
		for (Campo c : campos) {
			aux += c.toString() + "\n";
		}

		return aux;
	}
}
