package aps.unip.front;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import javax.swing.ImageIcon;

/*
 * Essa classe contem alguns metodos para formatar as imagens.
 */
public class FrameUltils {
	
	/*
	 * Redimenciona um icone.
	 * @param xComponete, x do componente.
	 * @param yComponente, y do componente.
	 * @param endereco, caminho da imagem.
	 */
	public static ImageIcon redimensionarIcon(int xComponete, int yComponete, String endereco)
	{
		if (endereco == null) {
			return null;
		}
		ImageIcon icon = new ImageIcon(endereco);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(xComponete, yComponete,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		return icon;
	}
	
	/*
	 * Redimenciona um icone.
	 * @param xComponete, x do componente.
	 * @param yComponente, y do componente.
	 * @param file, imagem em File.
	 */
	public static ImageIcon redimensionarIcon(int xComponete, int yComponete, File file)
	{
		if (file == null) {
			return null;
		}
		ImageIcon icon = new ImageIcon(imageToBytes(file, "jpg"));
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(xComponete, yComponete,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		return icon;
	}
	
	/*
	 * Redimenciona um icone.
	 * @param xComponete, x do componente.
	 * @param yComponente, y do componente.
	 * @param file, Imagem em array de bytes.
	 */
	public static ImageIcon redimensionarIcon(int xComponete, int yComponete, byte[] file)
	{
		if (file == null) {
			return null;
		}
		ImageIcon icon = new ImageIcon(file);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(xComponete, yComponete,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		return icon;
	}
	
	/*
	 * Converte um file em array de bytes.
	 * @param file, imagem em File.
	 * @param tipo, tipo da imagem .png, .jpg ...
	 */
	public static byte[] imageToBytes(File file,String tipo) {
		try {
			byte[] retorno = Files.readAllBytes(file.toPath());
			return retorno;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
