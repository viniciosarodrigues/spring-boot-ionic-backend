package com.viniciosrodrigues.cursomc.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			return uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename(),
					multipartFile.getContentType());
		} catch (IOException ioe) {
			throw new RuntimeException("Erro de IO: " + ioe.getMessage());
		}

	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata obj = new ObjectMetadata();
			obj.setContentType(contentType);
			LOG.info("Iniciando upload...");
			s3client.putObject(bucketName, fileName, is, obj);
			LOG.info("Upload realizado com sucesso!");
			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException urie) {
			throw new RuntimeException("Erro na conversão de URL para URI");
		}
	}
}
