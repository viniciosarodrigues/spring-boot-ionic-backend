package com.viniciosrodrigues.cursomc.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.viniciosrodrigues.cursomc.exception.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext))
			throw new FileException("Somente imagens PNG ou JPG são permitidos");

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(ext))
				img = pngToJpg(img);

			return img;

		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage cropSquare(BufferedImage img) {
		int min = (img.getHeight() <= img.getWidth()) ? img.getHeight() : img.getWidth();

		return Scalr.crop(img, (img.getWidth() / 2) - (min / 2), (img.getHeight() / 2) - (min / 2), min, min);
	}

	public BufferedImage resize(BufferedImage img, int size) {
		return Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, size);
	}
}
