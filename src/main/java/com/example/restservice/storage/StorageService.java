package com.example.restservice.storage;
 
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException; 

public interface StorageService {
	void store(MultipartFile file, String folder) throws JSchException, SftpException, IOException;

}