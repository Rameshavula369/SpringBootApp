package com.effort.manager;

import java.io.File;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.effort.dao.EmployeeDao;
import com.effort.dao.MediaDao;
import com.effort.entity.Employee;
import com.effort.entity.Media;
import com.effort.entity.MediaResponse;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.FileName;
import com.effort.util.ImageDocumentSanitizer;
import com.effort.util.Log;

import jakarta.activation.MimetypesFileTypeMap;
@Service
public class MediaManager {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private Constants constants; 
	
	@Autowired
	private MediaDao mediaDao; 
	
	private MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap(); 
	
	public MediaResponse saveMedia(String empId, String finalChecksum, FileItem fileItem,int config) throws Exception{
		Employee employee = null;
		String serverSidechecksum = null;
		
		if(fileItem == null){
			throw new EffortError(4008, HttpStatus.PRECONDITION_FAILED);
		}
		
		try {
			employee = employeeDao.getEmployeeBasicDetailsByEmpId(empId);
		} catch (Exception e) {
			Log.ignore(this.getClass(), e);
			Log.info(this.getClass(), empId+" Track failed");
			throw new EffortError(4004, HttpStatus.PRECONDITION_FAILED);
		}
		
		if(employee != null){
			String directoryRelativePath = "";
			FileName fileName = new FileName(fileItem.getName(),
					File.separatorChar, '.');
			directoryRelativePath = File.separator 
					+ employee.getCompanyId() 
					+ File.separator
					+ employee.getEmpId();

			String localPath = constants.getMediaStoragePath() + directoryRelativePath;
			File destinationDir = new File(localPath);
			if (!destinationDir.exists()) {
				destinationDir.mkdirs();
			}

			String fileRelativePath = File.separator
					+ System.currentTimeMillis() + "."
					+ fileName.extension();
			
			String fileRelativePathTemp = File.separator
					+ System.currentTimeMillis() + "-temp."
					+ fileName.extension();
			
			long fileWriteTime = System.currentTimeMillis();
			
			File fileTemp = new File(destinationDir, fileRelativePathTemp);
			try{
				fileItem.write(fileTemp);
				fileItem.delete();
				Log.info(getClass(), "FileUpload : Time taken for writing and deleting from fileItem"+(System.currentTimeMillis()-fileWriteTime));
				serverSidechecksum = Api.getCheckSum(fileTemp);
				if (!Api.isEmptyString(finalChecksum) && !serverSidechecksum.equals(finalChecksum)) {
					fileTemp.delete();

					Log.info(this.getClass(), empId+" upload failed");
					throw new EffortError(4036, HttpStatus.PRECONDITION_FAILED);
				}
			} catch (EffortError e) {
				throw e;
			} catch (Exception e) {
				Log.ignore(this.getClass(), e);
				throw new EffortError(4010, HttpStatus.METHOD_FAILURE);
			}
			
			String fileExtension = fileName.extension();
			if(!Api.isEmptyString(fileExtension))
			{
				String supportedImgExtns = constants.getSupportingImgExtn();
				if(Arrays.asList(supportedImgExtns.split(",")).contains(fileExtension)) 
				{
					if(!ImageDocumentSanitizer.madeSafe(fileTemp))
					{
						fileTemp.delete();
						throw new IOException("Unable to secure media. Please contact support");
					}
				}
					
			}
			/*else if("pdf".equalsIgnoreCase(fileExtension))
			{
				if(!PdfDocumentMalwareDetector.isSafe(fileTemp))
				{
					fileTemp.delete();
					throw new IOException("Malicious Pdf file found.");
				}
			}*/
			
			serverSidechecksum = Api.getCheckSum(fileTemp);
			moveFile(destinationDir, fileRelativePath, fileTemp);
			
			Media media = new Media();
			media.setCompanyId(employee.getCompanyId());
			media.setEmpId(employee.getEmpId());
			media.setMimeType((fileItem.getContentType()==null ? mimeTypeMap.getContentType(fileItem.getName()) : fileItem.getContentType()));
			media.setLocalPath(directoryRelativePath+File.separator+fileRelativePath);
			media.setFileName((fileName.filename().length()>300 ? fileName.filename().substring(0, 300): fileName.filename())+"."+fileName.extension());
			media.setConfig(config);
			
			mediaDao.saveMedia(media);
			
			mediaDao.saveMediaChecksum(media, serverSidechecksum);
			
			MediaResponse mediaResponse = new MediaResponse();
			mediaResponse.setMediaId(media.getId());
			mediaResponse.setMediaUrl(constants.getMediaBaseUrl()+"/"+media.getId());
			mediaResponse.setMimeType(media.getMimeType());
			return mediaResponse;
		}
		
		return null;
	}

	public void moveFile(File destinationDir, String fileRelativePath,
			File fileTemp) {
		File file = new File(destinationDir, fileRelativePath);
		if (file.exists()) {
			file.delete();
		}	
		
		fileTemp.renameTo(file);
	}

	
}
