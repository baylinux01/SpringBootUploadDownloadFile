package baylinux01.fileUploadAndDownloadProject.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import baylinux01.fileUploadAndDownloadProject.services.FileTransferService;

@CrossOrigin
@RequestMapping("/files")
@RestController
public class FileController {
	
	FileTransferService fileTransferService;
	
	
	@Autowired
	public FileController(FileTransferService fileTransferService) {
		super();
		this.fileTransferService = fileTransferService;
	}



	@PostMapping("/uploadFile")
	void uploadFile(@RequestParam("multipartFileToBeUploaded") MultipartFile multipartFileToBeUploaded) throws IOException
	{
		
		 fileTransferService.handleUpload(multipartFileToBeUploaded);
		
	}
	
	@GetMapping("/downloadFileSlow")
	ResponseEntity<Resource> downloadFileSlow(@RequestParam("fileName") String fileName) throws IOException
	{
		try {
			File fileToBeDownloaded=fileTransferService.getFileToBeDownloaded(fileName);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
					.contentLength(fileToBeDownloaded.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(Files.newInputStream(fileToBeDownloaded.toPath())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/firstPreviewThenDownloadFileSlow")
	ResponseEntity<Resource> firstPreviewThenDownloadFileSlow(@RequestParam("fileName") String fileName) throws IOException
	{
		try {
			File fileToBeDownloaded=fileTransferService.getFileToBeDownloaded(fileName);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
					.contentLength(fileToBeDownloaded.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(Files.newInputStream(fileToBeDownloaded.toPath())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/downloadFileFaster")
	ResponseEntity<Resource> downloadFileFaster(@RequestParam("fileName") String fileName) 
	{
		try {
			File fileToBeDownloaded=fileTransferService.getFileToBeDownloaded(fileName);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
					.contentLength(fileToBeDownloaded.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(fileToBeDownloaded));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/firstPreviewThenDownloadFileFaster")
	ResponseEntity<Resource> firstPreviewThenDownloadFileFaster(@RequestParam("fileName") String fileName) 
	{
		try {
			File fileToBeDownloaded=fileTransferService.getFileToBeDownloaded(fileName);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
					.contentLength(fileToBeDownloaded.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(fileToBeDownloaded));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
	}
	
	

}
