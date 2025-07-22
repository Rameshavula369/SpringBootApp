package com.effort.manager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.effort.context.AppContext;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.SyncDao;
import com.effort.entity.Employee;
import com.effort.entity.Media;
import com.effort.entity.MediaResponse;
import com.effort.entity.SmsActivationTemplate;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.util.Api;
import com.effort.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.springframework.stereotype.Service;


@Service
public class WebExtensionManager {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private Constants constants;
	
	@Autowired
	private SyncDao syncDao;
	
	@Autowired
	private MediaManager mediaManager;
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}
	
	public String checkEmployeeExpiryOnSync(String empId, long companyId) {
		  String message = null;
		  if(checkExpiredEmployeeByCompanyLevelCheck(companyId,empId)) {
			  Employee emp = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId);
			  Employee  managerDetails = employeeDao.getManagerBasicDetails(emp.getManagerId()+"" , emp.getCompanyId()+"");
			  message = ConstantsExtra.EXPIRED_EMPLOYEE_ANDROID_MESSAGE;
			  if(managerDetails != null) {
				  message = message.replace("<Manager_Name>", managerDetails.getEmpName());
	  				}
	  		  else {
	  			  message = message.replace("or <Manager_Name>", "");	
	  					}
			  }
		  else {
			  message = null;
		  }
		  return message;
	}
	
	public boolean checkExpiredEmployeeByCompanyLevelCheck(long companyId, String empId) {
		String enableEmployeeExpiryKey = "false";
		try{
			enableEmployeeExpiryKey = extraDao.getCompanySetting(companyId,
					constantsExtra.getEnableEmployeeExpiryKey());
		}catch(Exception e){
			enableEmployeeExpiryKey = "false";
		}	

		  if (!Api.isEmptyString(enableEmployeeExpiryKey) && enableEmployeeExpiryKey.equals("true")) {
			    Employee  isExpired = employeeDao.getExpiredEmployee(empId , companyId+"");
				int checkIsExpire = isExpired.getIsExpired();
				if(checkIsExpire == 1)
				{
					return true;
				}
				else {
					return false;
				}
		  }
		  else {
			  return false;
		  }
   }
	
	

	public String getQRCodeForMobileAppLogin(String activationCode,Employee employee) {
		if(Api.isEmptyString(activationCode)) {
			return null;
		}
		String qrCodeText = "{\"phoneNo\":\""+employee.getEmpPhone()+"\",\"activationCode\":\""+activationCode+"\"}";
		Log.info(this.getClass(), "qrCodeText: "+qrCodeText);
		String directoryRelativePath = File.separator+employee.getCompanyId()+File.separator+employee.getEmpId();
		String localPath = constants.getReportStoragePath()+directoryRelativePath;
		File destinationDir = new File(localPath);
		if (!destinationDir.exists()) {
			destinationDir.mkdirs();
		}
		String fileName = "AccessQRCode";
		String fileRelativePath = File.separator +fileName+".png";
		File qrFile = new File(destinationDir, fileRelativePath);
		int size = 175;
		String fileType = "png";
		String qrCodeLink = "";
		try {
			Long qrMediaId = createQRImageWithGivenString(qrFile,qrCodeText,size,fileType,employee.getEmpId()+"");
			qrCodeLink = constants.getDomain()+"/media/template/media/"+qrMediaId+"?clientPlatform=web";
			
		} catch (Exception e) {
			Log.info(this.getClass(), "Exception: "+e);
		}
		Log.info(this.getClass(), "Qr code File: "+qrFile+"qrCodeText: "+qrCodeText +"qrCodeLink:"+qrCodeLink);
		return qrCodeLink;
	}
	
	public Long createQRImageWithGivenString(File qrFile, String qrCodeText, int size, String fileType,String empId) throws WriterException,  IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Log.info(this.getClass(), "Qr code File inside: "+qrFile+ "qrCodeText: "+qrCodeText);
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Log.info(this.getClass(), "Qr code File inside: "+qrFile+"Image: "+image);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);
		Log.info(this.getClass(), "Qr code File inside: "+qrFile+"Matrix width "+matrixWidth);
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
		Log.info(this.getClass(), "Qr code File inside: "+qrFile);
		String path = qrFile.getPath();
		Log.info(this.getClass(), "path: "+path);
		File f = new File(path);
		FileItem fileItem = new DiskFileItem("file", "image/png", false, f.getName(), (int) f.length() , f.getParentFile());
	    InputStream input =  new FileInputStream(f);
	    OutputStream os = fileItem.getOutputStream();
	    int ret = input.read();
	    while ( ret != -1 ){
	        os.write(ret);
	        ret = input.read();
	    }
	    os.flush();
	    MediaResponse mediaResponse = new MediaResponse();
	    int config = Media.CONFIG_FOR_MEDIA_EXISTS_FOR_SPECIFIC_DURATION;
	    try {
	    	mediaResponse = mediaManager.saveMedia(empId, null,fileItem,config);
	    	f.delete();
	    	String[] entries = f.list();
			for (String s : entries) {
				File currentFile = new File(f.getPath(), s);
				currentFile.delete();
			}
			f.delete();
	   
	    }catch(Exception e) {
	    	
	    }
	    return mediaResponse.getMediaId();
	}
	
	
	public SmsActivationTemplate getSmsActivationTemplateForCompany(String companyId) {
		return syncDao.getSmsActivationTemplate(companyId);
	}
}
