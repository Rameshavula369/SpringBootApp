package com.effort.util;



import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageParser;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.bmp.BmpImageParser;
import org.apache.commons.imaging.formats.gif.GifImageParser;
import org.apache.commons.imaging.formats.pcx.PcxImageParser;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.tiff.TiffImageParser;
import org.apache.commons.imaging.formats.wbmp.WbmpImageParser;
import org.apache.commons.imaging.formats.xbm.XbmImageParser;
import org.apache.commons.imaging.formats.xpm.XpmImageParser;
import org.apache.commons.io.FilenameUtils;

public class ImageDocumentSanitizer {

    public static  boolean madeSafe(File f) {
        boolean safeState = false;
        boolean fallbackOnApacheCommonsImaging = false;
        String[] allowedFormats = {"TIFF", "PCX", "DCX", "BMP", "GIF", "PNG", "WBMP", "XBM", "XPM","WAV","AIF","MP3","MID","MP4","FLV","MKV","3GP","DOC","PDF","DOCX"};
        try {
            if ((f != null) && f.exists() && f.canRead() && f.canWrite()) {
                //Get the image format
                String formatName = null;
                try {
                	ImageInputStream iis = ImageIO.createImageInputStream(f);
                    Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReaders(iis);
                    //If there not ImageReader instance found so it's means that the current format is not supported by the Java built-in API
                    if (!imageReaderIterator.hasNext()) {
                        ImageInfo imageInfo = Imaging.getImageInfo(f);
                        if (imageInfo != null && imageInfo.getFormat() != null && imageInfo.getFormat().getName() != null) {
                            formatName = imageInfo.getFormat().getName();
                            fallbackOnApacheCommonsImaging = true;
                        } else {
                            throw new IOException("Format of the original image is not supported for read operation !");
                        }
                    } else {
                        
                        String fileExtension = FilenameUtils.getExtension(f.getName());
                        Log.info(ImageDocumentSanitizer.class, "madeSafe() //inside else fileExtension = "+fileExtension);
	                        if(fileExtension.equals("jpg") || fileExtension.equals("jpeg")) {
	                        imageReaderIterator.next();
	                       	formatName = "JPEG";
	                       	}else {
	                       	ImageReader reader = imageReaderIterator.next();
	                       	formatName = reader.getFormatName();
	                       	}
                        Log.info(ImageDocumentSanitizer.class, "madeSafe() //fileExtension = "+fileExtension+" formatName = "+formatName);
                        fallbackOnApacheCommonsImaging = false;
                    }
                }
                catch(Exception ex) {
                	Log.info(ImageDocumentSanitizer.class, "madeSafe() // 1 Error occured : "+ex.getMessage(), ex);
                }
                // Load the image
                BufferedImage originalImage;
                if (!fallbackOnApacheCommonsImaging) {
                    originalImage = ImageIO.read(f);
                } else {
                    originalImage = Imaging.getBufferedImage(f);
                }

                // Check that image has been successfully loaded
                if (originalImage == null) {
                    throw new IOException("Cannot load the original image !");
                }

                // Get current Width and Height of the image
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);


                // Resize the image by removing 1px on Width and Height
                Image resizedImage = originalImage.getScaledInstance(originalWidth - 1, originalHeight - 1, Image.SCALE_SMOOTH);

                // Resize the resized image by adding 1px on Width and Height - In fact set image to is initial size
                Image initialSizedImage = resizedImage.getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);

                // Save image by overwriting the provided source file content
                BufferedImage sanitizedImage = new BufferedImage(initialSizedImage.getWidth(null), initialSizedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics bg = sanitizedImage.getGraphics();
                bg.drawImage(initialSizedImage, 0, 0, null);
                bg.dispose();
                try {
                	OutputStream fos = Files.newOutputStream(f.toPath(), StandardOpenOption.WRITE);
                    if (!fallbackOnApacheCommonsImaging) {
                        ImageIO.write(sanitizedImage, formatName, fos);
                    } else {
                        ImageParser imageParser;
                        //Handle only formats for which Apache Commons Imaging can successfully write (YES in Write column of the reference link) the image format
                        //See reference link in the class header
                       int formatNo =0;
                        if(!Api.isEmptyString(formatName)) {
                        	for(int i=0;i< allowedFormats.length; i++) {
                        		if(allowedFormats[i].equalsIgnoreCase(formatName)) {
                        			formatNo = i+1;
                        			break;
                        		}
                        	}
                        }
                        switch (formatNo) {
                           /* case "TIFF": {*/
                        	case 1: {
                                imageParser = new TiffImageParser();
                                break;
                            }
                            /*case "PCX": {*/
                        	case 2: {
                                imageParser = new PcxImageParser();
                                break;
                            }
                            /*case "DCX": {*/
                        	case 3: {
                                imageParser = new PcxImageParser();
                                break;
                            }
                            /*case "BMP": {*/
                        	case 4: {
                                imageParser = new BmpImageParser();
                                break;
                            }
                            /*case "GIF": {*/
                        	case 5: {
                                imageParser = new GifImageParser();
                                break;
                            }
                            /*case "PNG": {*/
                        	case 6: {
                                imageParser = new PngImageParser();
                                break;
                            }
                            /*case "WBMP": {*/
                        	case 7: {
                                imageParser = new WbmpImageParser();
                                break;
                            }
                            /*case "XBM": {*/
                        	case 8: {
                                imageParser = new XbmImageParser();
                                break;
                            }
                            /*case "XPM": {*/
                        	case 9: {
                                imageParser = new XpmImageParser();
                                break;
                            }
                            default: {
                                throw new IOException("Format of the original image is not supported for write operation !");
                            }

                        }

                        imageParser.writeImage(sanitizedImage, fos, null);
                        //fos.close();
                    }

                }
                catch(Exception ex) {
                	Log.info(ImageDocumentSanitizer.class, "madeSafe() // 2 Error occured : "+ex.getMessage(), ex);
                }

                // Set state flag
                safeState = true;
            }
        } catch (Exception ex) {
            safeState = false;
            Log.info(ImageDocumentSanitizer.class, "madeSafe() // 3 Error occured : "+ex.getMessage(), ex);
        }

        return safeState;
    }

	
}
