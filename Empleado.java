//clase hecha por Mykhaylo Freyishyn Novychenko

package ProgramaEligeTuCesta;

import java.io.Serializable;

public class Empleado implements Serializable{
	// definimos los tipos de datos que manejaremos en la práctica
			private String idEmpleado;
			private String nombreEmpleado;
			private String apellido1Empleado;
			private String apellido2Empleado;
			private String edadEmpleado;
			private String sexoEmpleado;
			private String telefonoEmpleado;
			private String salarioEmpleado;
			private String dniEmpleado;
			
			// definimos getters y setters
			public String getIdEmpleado() {
				return idEmpleado;
			}
			public void setIdEmpleado(String idEmpleado) {
				this.idEmpleado = idEmpleado;
			}
			public String getNombreEmpleado() {
				return nombreEmpleado;
			}
			public void setNombreEmpleado(String nombreEmpleado) {
				this.nombreEmpleado = nombreEmpleado;
			}
			public String getApellido1Empleado() {
				return apellido1Empleado;
			}
			public void setApellido1Empleado(String apellido1Empleado) {
				this.apellido1Empleado = apellido1Empleado;
			}
			public String getApellido2Empleado() {
				return apellido2Empleado;
			}
			public void setApellido2Empleado(String apellido2Empleado) {
				this.apellido2Empleado = apellido2Empleado;
			}
			public String getEdadEmpleado() {
				return edadEmpleado;
			}
			public void setEdadEmpleado(String edadEmpleado) {
				this.edadEmpleado = edadEmpleado;
			}
			public String getSexoEmpleado() {
				return sexoEmpleado;
			}
			public void setSexoEmpleado(String sexoEmpleado) {
				this.sexoEmpleado = sexoEmpleado;
			}
			public String getTelefonoEmpleado() {
				return telefonoEmpleado;
			}
			public void setTelefonoEmpleado(String telefonoEmpleado) {
				this.telefonoEmpleado = telefonoEmpleado;
			}
			public String getSalarioEmpleado() {
				return salarioEmpleado;
			}
			public void setSalarioEmpleado(String salarioEmpleado) {
				this.salarioEmpleado = salarioEmpleado;
			}
			public String getDniEmpleado() {
				return dniEmpleado;
			}
			public void setDniEmpleado(String dniEmpleado) {
				this.dniEmpleado = dniEmpleado;
			};
			
			// definimos el constructor de la clase
			public Empleado(String id, String nombre, String apellido1, String apellido2, String edad, String sexo, String telefono, String salario, String dni) {
				idEmpleado = id;
				nombreEmpleado = nombre;
				apellido1Empleado = apellido1;
				apellido2Empleado = apellido2;
				edadEmpleado = edad;
				sexoEmpleado = sexo;
				telefonoEmpleado = telefono;
				salarioEmpleado = salario;
				dniEmpleado = dni;
			} // contructor Empleado
} // clase Empleado
