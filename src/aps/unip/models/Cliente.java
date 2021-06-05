package aps.unip.models;
/*
 * Classe que representa o cliente.
 */
public class Cliente {
	private static String nome;
	private static Integer id;
	private static byte[] foto;
	
	
	public static String getNome() {
		return nome;
	}
	public static void setNome(String nome) {
		Cliente.nome = nome;
	}
	public static Integer getId() {
		return id;
	}
	public static void setId(Integer id) {
		Cliente.id = id;
	}
	public static byte[] getFoto() {
		return foto;
	}
	public static void setFoto(byte[] foto) {
		Cliente.foto = foto;
	}
	
	
	
	
}
