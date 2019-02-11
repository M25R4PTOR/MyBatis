package com;

import java.io.Reader;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.io.Resources;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class Test {

	public static void main(String[] args) {

		try {
			String resource = "ConfiguracionIBatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);

			// insertarCoche(sqlMap);
			// modificarCoche(sqlMap, 10);
			selectCoche(sqlMap, 10);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void insertarCoche(SqlMapClient sqlMap) throws SQLException {
		// INSERTAR no ponemos id porque lo incluye automatico
		Coche coche = new Coche();
		coche.setMarca("Marca");
		coche.setMatricula("0000XXX");
		coche.setFechaMatricula(new Date());
		sqlMap.insert("insertCoche", coche);

		// Ahora insertamos con un Hashtable.
		Hashtable hashCoche = new Hashtable();
		hashCoche.put("marca", "la marca");
		hashCoche.put("matricula", "x-2222-z");
		hashCoche.put("fechaMatricula", new Date());
		sqlMap.insert("insertCoche", hashCoche);
	}

	private static void modificarCoche(SqlMapClient sqlMap, Integer claveCoche) throws SQLException {
		// MODIFICAR
		// Obtenemos un coche.
		Coche cocheModif = (Coche) sqlMap.queryForObject("getCoche", claveCoche);

		// modificar un coche
		cocheModif.setMarca("cambiada");
		sqlMap.update("updateCoche", cocheModif);
	}

	private static void selectCoche(SqlMapClient sqlMap, Integer claveCoche) throws SQLException {
		// SELECT
		Coche coche2 = (Coche) sqlMap.queryForObject("getCoche", claveCoche);

		List<Coche> coches = sqlMap.queryForList("getCoches", null);
		for (Coche c : coches) {
			System.out.println(c);
		}

		// Map hashCoche2 = (Map)sqlMap.queryForObject("getHashCoche",3);
	}

	private static void eliminarCoche(SqlMapClient sqlMap) throws SQLException {
		// ELIMINAR
		sqlMap.delete("removeCoche", 4);
	}
}
