package com.effort.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.effort.context.AppContext;
import com.effort.entity.Employee;

import com.effort.entity.JmsMessage;
import com.effort.entity.WebUser;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.sqls.Sqls;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactory.Feature;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;


public class Api {
	
	
	public static final int RSA_KEY_SIZE = 1024;
	public static final int RSA_DECODE_LENGTH = RSA_KEY_SIZE / 8;
	
	public static enum DateConversionType{STADARD_TO_XSD,XSD_TO_STADARD,
		DATETIME_TO_UTC_DATETIME_WITH_TZO,
		UTC_DATETIME_TO_DATETIME_WITH_TZO,
		UTC_DATETIME_TO_DATETIME_DISPLAY_FORMAT,
		UTC_DATETIME_TO_IST
		};
		public static enum DateType{YEAR,MOTH,DAY,HOUR,MINUTE,SECOND,DAY_OF_MONTH,DAY_OF_WEEK_IN_MONTH};

		public static String getDateTimeInTz24(Calendar calendar, String offset) {
			calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			return format.format(calendar.getTime());
		}
		public static boolean isEqualString(String string1, String string2,
				boolean makeNullIfEmpty) {
			if (makeNullIfEmpty) {
				return isEqualString(makeNullIfEmpty(string1),
						makeNullIfEmpty(string2));
			} else {
				return isEqualString(string1, string2);
			}
		}
		public static String removeZero(String str) 
	    { 
			try {
				int i = 0; 
		        while (i < str.length() && str.charAt(i) == '0') 
		            i++; 
		  
		        StringBuffer sb = new StringBuffer(str); 
		        sb.replace(0, i, ""); 
		        return sb.toString();
			}catch(Exception e) {
				return null;
			}
	    }
		 public static String getExtensionByMimeType(String mimeType) {
			  
			 Map<String, String> fileExtensionMap =  new HashMap<String, String>();
			 fileExtensionMap.put("image/jpeg", ".jpg");
			 fileExtensionMap.put("image/pjpeg", ".pjpeg");
			 fileExtensionMap.put("image/png", ".png");
			 String extension = fileExtensionMap.get(mimeType);
			 return extension;
		 }
		public static String encodeNewLinesAndQuotes(String string) {
			string = string.replace("\n", "&-nl-n;");
			string = string.replace("\r", "&-nl-r;");
			string = string.replace("'", "&-sq;");
			string = string.replace("\"", "&-dq;");
			return string;
		}
		 public static int getDayOfWeek(String dateString) throws ParseException {

		    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    	    Date dateOfMonth = format.parse(dateString);     
		    	    Calendar c = Calendar.getInstance();
		    	    c.setTime(dateOfMonth); 
		    	    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		    	    
		    	    return dayOfWeek;
		    	}
		 
		 public static String escapChar(String value, char... chars) {
				if (value != null && value.length() > 0) {
					if (chars != null) {
						for (char c : chars) {
							value = value.replace(c + "", "\\" + c);
						}
					}
				}

				return value;
			}
		 
		 public static void populateLogText(WebUser webUser,String ignoreMethodName) {
			   try {
				    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
				    List<String> pathList = new ArrayList<String>();
				    String requiredPart = null;
				    String className = null;
				    String methodName = null;
					for (StackTraceElement stackTraceElement : stackTraceElements) {
						//methodCallList.add(stackTraceElement);
						//System.out.println("populateLogText() // stackTraceElement = "+stackTraceElement);
						className = stackTraceElement.getClassName();
						if(Api.isEmptyString(className)) {
							continue;
						}
						if(!className.startsWith("com.ayansys.effort")) {
							continue;
						}
						methodName = stackTraceElement.getMethodName();
						if(methodName.equals("populateLogText") || methodName.equals("invoke")) {
							continue;
						}
						if(!Api.isEmptyString(ignoreMethodName) && methodName.equals(ignoreMethodName)) {
							continue;
						}
						String fileName = stackTraceElement.getFileName();
						if(fileName.indexOf("<generated>") > -1) {
							continue;
						}
						requiredPart = methodName+"("+fileName+":"+stackTraceElement.getLineNumber()+")";
						pathList.add(requiredPart);
						if(pathList.size() > 200) {
							break;
						}
					}
					Collections.reverse(pathList);  
					StringBuffer completePath = new StringBuffer();
					int i = 0;
					for (String path : pathList) {
						
						if(i>0) {
							completePath.append("==>");
						}
						i++;
						completePath.append(path);
					}
					//System.out.println("completePath = "+completePath);
					String resolvedCompletePath = null;
					
					if(RequestContextHolder.getRequestAttributes() != null) {
						HttpServletRequest request = 
						        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
						                .getRequest();
						if(request != null && request.getRequestURI() != null) {
							String queryString = request.getRequestURI()+"?"+request.getQueryString();
							resolvedCompletePath = completePath.toString() +" && url : "+queryString;
							webUser.setLogText(Thread.currentThread().getName()+" : "+resolvedCompletePath);
						}
					}else {
						resolvedCompletePath = completePath.toString() +" && this is schedularCall";
						webUser.setLogText(Thread.currentThread().getName()+" : "+resolvedCompletePath);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		      }

		 
		public static boolean compareTwoDateBefore(String date1, String date2){
	    	String dateStart = date1;
	    	String dateStop = date2;
	    	boolean flag = false;
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	    	Date d1 = null;
	    	Date d2 = null;

	    	try 
	    	{
	    	    d1 = format.parse(dateStart);
	    	    d2 = format.parse(dateStop);

	    	    flag = d1.before(d2);
	    	}
	    	catch (Exception e) 
	    	{
	    	    // TODO: handle exception
	    		e.printStackTrace();
	    	} 
	    	return flag;
	    }
		
		
		public static Integer getGivenFieldFromDateAndTime(String dateAndTime, DateType type) {
			   Date date;
			  try {
			      date = DateUtils.parseDate(dateAndTime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss.s","yyyy-MM-dd"});
			  } catch (ParseException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			      return null;
			  }
			    Calendar calendar = Calendar.getInstance();
			    calendar.setTime(date);
			    switch(type){
			    case YEAR:
			    return calendar.get(Calendar.DAY_OF_YEAR);
			    case MOTH:
			    return calendar.get(Calendar.DAY_OF_MONTH);
			    case DAY:
			    return calendar.get(Calendar.DAY_OF_WEEK);
			    case HOUR:
			    return  calendar.get(Calendar.HOUR_OF_DAY);
			    case MINUTE:
			    return   calendar.get(Calendar.MINUTE);
			    case SECOND:
			    return calendar.get(Calendar.SECOND);
			    case DAY_OF_MONTH:
				return calendar.get(Calendar.DAY_OF_MONTH);
			    case DAY_OF_WEEK_IN_MONTH:
			    return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
			    	
			    
			    }
			    
			    return null;
			}

		
		public static String makeNullIfEmpty(String value) {
			if (value != null && value.trim().length() == 0) {
				return null;
			} else {
				return value;
			}
		}
		
		 public static String removeTrailingCommaFormString(String value) {
				
				if(value == null || value.trim().length() == 0){
					return value;
				}
				int valueLength = value.trim().length();
				
				if(",".equalsIgnoreCase(value.trim()))
				{
					return "";
				}
				if(!value.trim().endsWith(","))
					return value;
				String finalValue = value.trim().substring(0, valueLength-1);
				return finalValue;
			}
		  
		 
		 public static String getMD5EncodedValue(String data){

	            MessageDigest md = null;
	            
	            try {
	                if(data != null){
	                md = MessageDigest.getInstance("MD5");

	                md.update(data.getBytes());

	                byte byteData[] = md.digest();

	                StringBuffer hexString = new StringBuffer();
	                for (int i=0;i<byteData.length;i++) {
	                    String hex=Integer.toHexString(0xff & byteData[i]);
	                    if(hex.length()==1) hexString.append('0');
	                    hexString.append(hex);
	                }


	                //logger.info("Digest(in hex format):: " + hexString.toString());
	               // Log.info(getClass(), "md5sum for "+data+" "+hexString.toString());
	                return hexString.toString();
	                }

	            } catch (NoSuchAlgorithmException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            return null;

	        }
	    	
		 
	public static Object getFieldValue(Object obj, String fieldName)
			throws IllegalArgumentException, IllegalAccessException {
		if (fieldName != null && fieldName.trim().length() >= 0) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				if (name.equalsIgnoreCase(fieldName.trim())) {
					field.setAccessible(true);
					return field.get(obj);
				}
			}
		}

		return null;
	}
	
	
	public static String toCSV(Object[] strings) {
		if (strings != null) {
			StringBuilder csv = new StringBuilder("");
			for (Object string : strings) {
				if (string != null) {
					if (csv.length() > 0) {
						csv.append(", ");
					}

					csv.append(string.toString());
				}
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	
	public static boolean islistContainsGivenValue(List<?> list, String requiredPropertyName,String giveValue) {
		 try{
			if(requiredPropertyName!=null){
			for (Object object : list) {
			String value =BeanUtils.getProperty(object, requiredPropertyName);
			if(value!=null&&value.equalsIgnoreCase(giveValue)){
				return true;
			}
		}
	 }	
			}catch(Exception ex){
			 
		 }		
		 return false;
		}
	
	public static Map<Object,Object> getMapFromList(List<?> list, String requiredFieldKeyName,String requiredFieldValueName) {
		Map<Object,Object> map=new HashMap<Object,Object>();
		try{
			
			
			if(requiredFieldKeyName!=null){
			for (Object object : list) {
			//String valueKey =BeanUtils.getProperty(object, requiredFieldKeyName);
				
			Object valueKey=PropertyUtils.getProperty(object, requiredFieldKeyName);
			
			Object requiredValue=object;
			if(requiredFieldValueName!=null){
				requiredValue=PropertyUtils.getProperty(object, requiredFieldValueName);
			}
			/*System.out.println("valueKey = "+valueKey+" requiredValue = "+requiredValue);*/
			map.put(valueKey, requiredValue);
		}
	 }	
			}catch(Exception ex){
			 
		 }		
		 return map;
		}
	
	  public  static void convertDateTimesToGivenTypeList(List<?> objects,DateConversionType dateConversionType,String... propertyNames){
	    	if(objects!=null && !objects.isEmpty()){
	    	  for (Object object : objects) {
	    		  convertDateTimesToGivenType(object, dateConversionType, propertyNames);
			 }
	    	}	  
	    	  
	      }
	  
		public static List<Integer> csvToIntegerList(String csv) {
			ArrayList<Integer> list = new ArrayList<Integer>();

			if (!Api.isEmptyString(csv)) {
				String[] parts = csv.split(",");
				for (String part : parts) {
					list.add(Integer.parseInt(part.trim()));
				}
			}

			return list;
		}
	  
		
		public static String displayQuery(String sql,Object[] objArray) {
			  int placeHolderCount = charCount(sql,'?');
			  if(placeHolderCount != objArray.length) {
				  return "PlaceHolder count not matching with objArray length = "+objArray.length+" placeHolderCount = "+placeHolderCount;
			  }
			  Object value = null;
			  for(int i = 0;i<objArray.length;i++) {
				  value = (objArray[i] != null)?objArray[i].toString().replace("'", "\\\\'"):objArray[i];
				  sql = sql.replaceFirst("\\?", "'"+value+"'");
		      }
		      return sql;
		  }
			
		public static int charCount(String string,Character charToSerach) {
			  
			  int counter = 0;
			  for( int i=0; i<string.length(); i++ ) {
			      if( string.charAt(i) == charToSerach ) {
			          counter++;
			      } 
			  }
			  return counter;
		  }
		
	public  static void convertDateTimesToGivenType(Object object,DateConversionType dateConversionType,String... propertyNames){
		   
		 if(object != null){
		 for (String propertyName : propertyNames) {
			 Object value;
			try {
				value = PropertyUtils.getProperty(object, propertyName);
				
				if(value!=null && !Api.isEmptyString(value.toString())){
				
					if(dateConversionType==DateConversionType.STADARD_TO_XSD){
						value=toUtcXsd(value.toString());
				    	
				    }else if(dateConversionType==DateConversionType.XSD_TO_STADARD){
				    	value=getDateTimeFromXsd(value.toString());
				    }
					
					PropertyUtils.setProperty(object, propertyName, value);
		    	  }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
	}
	    	  
    }
	public static String toCSV(List<?> list, String fieldName)
			throws IllegalArgumentException, IllegalAccessException {
		if (list != null) {
			StringBuilder csv =  new StringBuilder("");
			for (Object obj : list) {
				if (csv.length() > 0) {
					csv.append(",");
				}

				if (fieldName == null || fieldName.trim().length() == 0) {
					csv.append(obj.toString());
				} else {
					Object val = getFieldValue(obj, fieldName);
					csv.append(val == null ? "" : val.toString());
				}
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	

	public static String getErrorMesg(List<ObjectError> errors){
		if(errors != null && errors.size() > 0){
			String error = "";
			for (ObjectError objectError : errors) {
				if(!Api.isEmptyString(error)){
					error +=  ", ";
				}
				error += objectError.getDefaultMessage();
			}
			return error;
		} else {
			return null;
		}
	}
	
	 public static String generateRandomGuId() {
			return UUID.randomUUID().toString();
		}
	public static List<Long> listToLongList(List<?> list, String fieldName) {
		
		String csv = "";
		try {
			csv = toCSV(list, fieldName);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Long> resultStringList = new ArrayList<Long>();
		
		if(!isEmptyString(csv)){
			resultStringList = csvToLongList(csv);
			return resultStringList;
		}
		
		//return null;
		return new ArrayList<Long>();
	}
	public static List<Long> csvToLongList(String csv) {
		ArrayList<Long> list = new ArrayList<Long>();

		if (!Api.isEmptyString(csv)) {
			String[] parts = csv.split(",");
			for (String part : parts) {
				if(!Api.isEmptyString(part)) {
					list.add(Long.parseLong(part.trim()));
				}
			}
		}

		return list;
	}

		
	
	
	 public static String getDateTimeFromXsd(String datetimeXsd) {
	        String syncDateFormat = Api.getConvertDateTimesToGivenType(datetimeXsd, DateConversionType.STADARD_TO_XSD, "");
	        Instant instant = Instant.parse(syncDateFormat);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(instant.toEpochMilli());
	        String datetime = Api.getDateTimeInUTC(calendar);
	        datetime = datetime.substring(0, 19);

	        return datetime;
	    }
	
	public  static String getConvertDateTimesToGivenType(String value,DateConversionType dateConversionType,String tzo){
		try {
			
			if(value!=null && !Api.isEmptyString(value.toString())){
			
				if(dateConversionType==DateConversionType.STADARD_TO_XSD){
					value=toUtcXsd(value.toString());
			    	
			    }else if(dateConversionType==DateConversionType.XSD_TO_STADARD){
			    	value=getDateTimeFromXsd(value.toString());
			    }else if(dateConversionType==DateConversionType.DATETIME_TO_UTC_DATETIME_WITH_TZO){
			    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
			    	//value=getDateTimeInTzToUtc(calendar, tzo) ;
			    }else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_DATETIME_WITH_TZO){
			    	value =getDateTimeInTz24(value.toString(), tzo);
			    }
			    else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_DATETIME_DISPLAY_FORMAT){
			    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
			    	value =getDateTimeInTz(calendar, tzo, 1);
			    }
			    else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_IST){
			    	Calendar calendar = Calendar.getInstance();
			    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
			    	//value =getDateTimeInTzIst(calendar, tzo);
			    }
	    	  }
			  
			} catch (Exception e) {
				e.printStackTrace();
			}
		  return value;
		}
	public static String toCSVListOfEmployeeIds(List<Employee> list) {
		StringBuilder sb = new StringBuilder("");
		if (list != null) {

			for (Employee employee : list) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(employee.getEmpId());

			}

			return sb.toString();
		} else {
			return null;
		}
	}
	public static String toCSV(List<?> list,CsvOptions csvOption) {
		if (list != null) {
			StringBuilder csv = new StringBuilder("");
			for (Object obj : list) {
				if(obj != null)
				{
					if(CsvOptions.FILTER_NULL_OR_EMPTY==csvOption && Api.isEmptyString(obj.toString())){
						continue;
					}
					
					if (csv.length() > 0) {
						csv.append(", ");
					}
	
					csv.append(obj.toString());
				}
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	public static String getDateSpanDisplayValue(String timeInMillis) {

		try {
			String dateSpan = "";
			if(Api.isNumber(timeInMillis)) {
				
				long difference_In_Time = Long.parseLong(timeInMillis);
				
				long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

				long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

				if (difference_In_Years > 1) {
					if(difference_In_Days > 1){
						dateSpan = difference_In_Years + " years, " + difference_In_Days + " days ";
					}else{
						dateSpan = difference_In_Years + " years, " + difference_In_Days + " day ";
					}
				}else if (difference_In_Years == 1) {
					if(difference_In_Days > 1){
						dateSpan = difference_In_Years + " year, " + difference_In_Days + " days ";
					}else{
						dateSpan = difference_In_Years + " year, " + difference_In_Days + " day ";
					}
				}else{
					if(difference_In_Days > 1){
						dateSpan = difference_In_Days + " days ";
					}else{
						dateSpan = difference_In_Days + " day ";
					}
				}
			}else
			{
				dateSpan = timeInMillis;
			}
			
			return dateSpan+"";
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCheckSum(File checkSumFIle) {
		String checkSum = "";
		InputStream is = null;
		try {
			// System.out.println("in getCheckSum method ");
			MessageDigest md = MessageDigest.getInstance("MD5");
			is = new FileInputStream(checkSumFIle);

			int length = 512;
			byte bt[] = new byte[length];
			int read = 0;

			while ((read = is.read(bt, 0, length)) != -1) {
				md.update(bt, 0, read);
			}
			checkSum = new BigInteger(1, md.digest()).toString(16);
			if (checkSum.length() < 32) {
				int l = 32 - checkSum.length();
				for (int i = 0; i < l; i++) {
					checkSum = "0" + checkSum;
				}
			}
			// System.out.println("check sum is " + checkSum);
		} catch (Exception e) {
			// System.out.println("Error " + e.toString());
			Log.info(Api.class, "Error: " + e.toString(), e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				// System.out.println("Error " + e.toString());
				Log.info(Api.class, "Error: " + e.toString(), e);
			}
		}
		return checkSum;
	}

	public static String getDateTimeInTz(Calendar calendar, String offset, int type) {
		calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));
		SimpleDateFormat format = null;
		if(type == 1)
			format =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		else if(type == 2)
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(calendar.getTime());
	}
	
	
	 public static String processStringValuesList1(List<String> givenvalues) {
 		if (givenvalues != null) {
 			List<String> tempList = new ArrayList<String>();
 			for (String string : givenvalues) {
 				if(string!=null){
 				string = string.replaceAll("'", "");
 				string = "'" + string + "'";
 				tempList.add(string);
  				}
 			}
 			return Api.toCSV(tempList);
 		}
 		return null;
 	}
	 
	 public static boolean isEmpty(String object) {
			if (object != null && !object.equalsIgnoreCase("null")) {
				return false;
			} else {
				return true;
			}
		}
	 public static String getDateTimeInUTC(Calendar calendar) {
			Date date = calendar.getTime();
			return getDateTimeInUTC(date);
		}
	 
	 
	public static String processStringValuesList(List<String> givenvalues) {
		//List<String> givenvalues = Api.csvToList(value);
		if (givenvalues != null) {
			List<String> tempList = new ArrayList<String>();
			for (String string : givenvalues) {
				if(!Api.isEmptyString(string))
				{
					tempList.add(new StringBuilder("'").append(string.replaceAll("'", "")).append("'").toString());
				}
			}

			return Api.toCSV(tempList);
		}
		return null;
	}
	
	public static boolean isNonZeroPositiveLong(Long long1) {
		if (long1 == null || long1.longValue() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	public static String getDateTimeSpanDisplayValue(String timeInMillis) 
    {

        try {

            String dateTimeSpan = "";
            if(Api.isNumber(timeInMillis)) {
                long difference_In_Time = Long.parseLong(timeInMillis);


                long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

                long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

                long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

                long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

                if (difference_In_Years > 1) {
                    if(difference_In_Days > 1){
                        dateTimeSpan = difference_In_Years + " years, " +difference_In_Days + " days, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }else{
                        dateTimeSpan = difference_In_Years + " years, " +difference_In_Days + " day, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }
                }else if (difference_In_Years == 1) {
                    if(difference_In_Days > 1){
                        dateTimeSpan = difference_In_Years + " year, " +difference_In_Days + " days, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }else{
                        dateTimeSpan = difference_In_Years + " year, " +difference_In_Days + " day, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }
                }else{
                    if(difference_In_Days > 1){
                        dateTimeSpan = difference_In_Days + " days, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }else{
                        dateTimeSpan = difference_In_Days + " day, " + difference_In_Hours + " hours, "
                        + difference_In_Minutes + " minutes ";
                    }
                }
            }else
            {
                dateTimeSpan = timeInMillis;
            }
            return dateTimeSpan+"";
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	
	public static String getDateTimeInUTC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(date);
	}
	public static JmsMessage getJmsMessageForBulkApproval(int type, long id, long companyId, long empId,
			int changeType, long entityId, Serializable extra, Boolean byClient,String empIds,boolean ignoreFirebasePushNotification,boolean ignoreFirebasePushNotificationForNotBulkApproval) {
		
		JmsMessage jmsMessage = new JmsMessage(type, id, companyId,
				empId, changeType, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), entityId, extra, byClient, 0);
		  jmsMessage.setEmpIds(empIds);
		  jmsMessage.setIgnoreFirebasePushNotification(ignoreFirebasePushNotification);
		  jmsMessage.setIgnoreFirebasePushNotificationForNotBulkApproval(ignoreFirebasePushNotificationForNotBulkApproval);
		  return jmsMessage;
	}
	
	public static String makeEmptyIfNull(String value) {
		if (value == null) {
			return "";
		} 
		else if (value.equals("null") || value.equals("Null")
				|| value.equals("NULL")) {
			return "";
		}
		else {
			return value;
		}
	}
	
	public static String getDateTimeInTzToXsd(Calendar calendar, String offset) {
		calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(calendar.getTime());
	}
	public static String getDateTimeInTzToXsd(String dateTime, String offset) {
		try {
			Calendar calendar = getCalendar(getDateTimeInUTC(dateTime));
			return getDateTimeInTzToXsd(calendar, offset);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<Object,List<Object>> getGroupedMapFromList(List<?> list, String requiredFieldKeyName,String requiredFiedValueName) {
		Map<Object,List<Object>> map=new HashMap<Object,List<Object>>();
		try{
			if(requiredFieldKeyName!=null){
			for (Object object : list) {
			//String valueKey =BeanUtils.getProperty(object, requiredFieldKeyName);
			Object valueKey=PropertyUtils.getProperty(object, requiredFieldKeyName);
			List<Object> groupedObjectList=map.get(valueKey);
			if(groupedObjectList==null){
			 groupedObjectList=new ArrayList<Object>();
				map.put(valueKey, groupedObjectList);
			}
				if(Api.isEmptyString(requiredFiedValueName)){
					groupedObjectList.add(object);
				}else{
				  Object value=PropertyUtils.getProperty(object, requiredFiedValueName);
				  groupedObjectList.add(value);
				}
		}
	 }	
			}catch(Exception ex){
			 
		 }		
		 return map;
		}
	
	public static boolean compareTwoDateTimesBefore(String dateTime1, String dateTime2){
    	String dateStart = dateTime1;
    	String dateStop = dateTime2;
    	boolean flag = false;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	Date d1 = null;
    	Date d2 = null;

    	try 
    	{
    	    d1 = format.parse(dateStart);
    	    d2 = format.parse(dateStop);

    	    flag = d1.before(d2);
    	}
    	catch (Exception e) 
    	{
    	    // TODO: handle exception
    		e.printStackTrace();
    	} 
    	return flag;
    }

	public static JmsMessage getJmsMessage(int type, long id, long companyId, long empId,
			int changeType, long entityId, Serializable extra, Boolean byClient,String empIds,boolean ignoreFirebasePushNotification) {
		
		JmsMessage jmsMessage = new JmsMessage(type, id, companyId,
				empId, changeType, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())), entityId, extra, byClient, 0);
		  jmsMessage.setEmpIds(empIds);
		  jmsMessage.setIgnoreFirebasePushNotification(ignoreFirebasePushNotification);
		  return jmsMessage;
	}
	
		public static enum CsvOptions{FILTER_NULL_OR_EMPTY,NONE};
	
		
		public static Map<Object,List<Object>> getResolvedMapFromList(List<?> list, String requiredFieldKeyName) 
		 {
			Map<Object, List<Object>> map = new HashMap<Object, List<Object>>();
			try 
			{
				if (requiredFieldKeyName != null) 
				{
					for (Object object : list) 
					{
						List<Object> objects = new ArrayList<Object>();
						Object valueKey = PropertyUtils.getProperty(object,
								requiredFieldKeyName);
						if(map.containsKey(valueKey))
						{
							objects = map.get(valueKey);
						}
						objects.add(object);
						map.put(valueKey, objects);
					}
				}
			} 
			catch (Exception ex) 
			{
		
			}
			return map;
		}
		
		public static List<String> csvToList(String csv) {
			ArrayList<String> list = new ArrayList<String>();

			if (!Api.isEmptyString(csv)) {
				String[] parts = csv.split(",");
				for (String part : parts) {
					list.add(part.trim());
				}
			}

			return list;
		}
		public static String toCSV(List<?> list, String fieldName,CsvOptions csvOption){
			try{
				if (list != null) {
					StringBuilder csv = new StringBuilder("");
					for (Object obj : list) {
						String value=BeanUtils.getProperty(obj, fieldName);
							
						if(CsvOptions.FILTER_NULL_OR_EMPTY==csvOption && Api.isEmptyString(value)){
							continue;
						}
						
						if (csv.length() > 0) {
							csv .append(",");
						}
						
							csv .append(value == null ? "" : value);
						}
					return csv.toString();
				}
				 else {
					return null;
				}
			}catch(Exception ex){
				Log.debug(Api.class, "in toCSV", ex);
				return null;
			}
		}
			
		public static Date getDateTimeInUTC(String date) throws ParseException {
			try
			{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format.setTimeZone(TimeZone.getTimeZone("GMT"));
				return format.parse(date);
			}catch(Exception e){
				String value = date.replace("T", " ");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				format.setTimeZone(TimeZone.getTimeZone("GMT"));
				return format.parse(value);
			}
		}
		
		public static String toCSVWithSpacesBetween(List<?> list, String fieldName,CsvOptions csvOption){
			try{
				if (list != null) {
					StringBuilder csv = new StringBuilder("");
					for (Object obj : list) {
						String value=BeanUtils.getProperty(obj, fieldName);
							
						if(CsvOptions.FILTER_NULL_OR_EMPTY==csvOption && Api.isEmptyString(value)){
							continue;
						}
						
						if (csv.length() > 0) {
							csv .append(", ");
						}
						
							csv .append(value == null ? "" : value);
						}
					return csv.toString();
				}
				 else {
					return null;
				}
			}catch(Exception ex){
				Log.debug(Api.class, "in toCSV", ex);
				return null;
			}
		}
		public static Calendar toCalendar(Date date) {
			Calendar calendar = Calendar.getInstance();
			// calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
			calendar.setTime(date);
			return calendar;
		}
		
		public static String toUtcXsd(String date) throws ParseException {
	        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	        try {
	            calendar.setTimeInMillis(getDateTimeInUTC(date).getTime());
	        } catch (ParseException e) {
	            try {
	                Instant instant = Instant.parse(date); // Expecting ISO-8601 format
	                ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
	                calendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
	            } catch (Exception ee) {
	                throw e;
	            }
	        }

	        // Convert Calendar to ISO-8601 DateTime string
	        ZonedDateTime zonedDateTime = calendar.toInstant().atZone(ZoneId.of("UTC"));
	        return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    }
		
		
		 
	public static long getCurrentTimeInUTCLong() {
		long millis = TimeZone.getDefault().getRawOffset();
		long time = System.currentTimeMillis() - millis;
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		// calendar.setTimeInMillis(time);
		return time;
	}
	
	public static Calendar getCalendarNowInUtc() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar;
	}

	private static Constants getConstants(){
		Constants constants = AppContext.getApplicationContext().getBean("constants",Constants.class);
		return constants;
	}
	
	public static String getDateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static Date getDateNowInUtc() {
		Calendar calendar = getCalendarNowInUtc();
		long time = calendar.getTimeInMillis();
		time -= TimeZone.getDefault().getRawOffset();
		Date date = new Date(time);
		return date;
	}

	 public static String getCurrentLocalTime(String tzo,String format){
		    
		    Date currentUTC = Api.getDateNowInUtc();
			String currentUTCTime = Api.getDateToString(currentUTC);
			
			String localTime = Api.addTzoAndGetDateTime(currentUTCTime, tzo, format);
			return localTime;
	 }
	 
	 public static String getDateTimeInTzToUtc(Calendar calendar, String offset) {
			calendar.add(Calendar.MINUTE, Integer.parseInt(offset));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			return format.format(calendar.getTime());
		}
		
	 public static boolean isEqualLong(Long long1, Long long2) {
			if (long1 == null) {
				if (long2 == null) {
					return true;
				} else {
					return false;
				}
			} else {
				if (long2 == null) {
					return false;
				} else {
					return long1.longValue() == long2.longValue();
				}
			}
		}

	 
	 public static String getCommaSeperatedStringWithOutFormat(String number) {
			try {

				if (!Api.isEmptyString(number)) {
					BigDecimal payment = new BigDecimal(number);
					NumberFormat n = NumberFormat.getCurrencyInstance();
					double doublePayment = payment.doubleValue();
					String s = n.format(doublePayment);
					return s.substring(s.indexOf('.') + 1, s.length() - 3);
				} else {
					return number;
				}
			} catch (Exception e) {
				return number;
			}
			

		}
	 
	 public static String getDateTimeUTCtoTzo(String dateTime, String tzo){
			try {
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    	calendar.setTimeInMillis(getDateTimeInUTC(dateTime).getTime());
	    	return getDateTimeInTz(calendar, tzo, 1);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	 
	 public static String getTimeZoneDates(String date, String myTimeZone,
				String employeeTimeZone) {

			try {
				if ((employeeTimeZone != null && !employeeTimeZone.equalsIgnoreCase("null")) && (myTimeZone != null && !myTimeZone.equalsIgnoreCase("null") )) {

					String myTimeZoneDate = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(date), myTimeZone, 1);
					/*String myTimeZoneDate = Api.getDateTimeInTz(
							Api.getCalendar(Api.getDateTimeInUTC(date)), myTimeZone,1);*/
							/*
							Api.getDateTimeInTz(
							Api.getCalendar(Api.getDateTimeInUTC(date)), myTimeZone);*/
					
					if(!myTimeZone.equalsIgnoreCase(employeeTimeZone)) {
						String employeeTimeZoneDate =Api.getDateTimeInTz(DatatypeConverter.parseDateTime(date), employeeTimeZone, 1);
								/*Api.getDateTimeInTz(
								Api.getCalendar(Api.getDateTimeInUTC(date)),
								employeeTimeZone);*/
						myTimeZoneDate = myTimeZoneDate + " (" + employeeTimeZoneDate + ")";
					}
					date = myTimeZoneDate;

				} else if (myTimeZone != null) {
					/*String myTimeZoneDate = Api.getDateTimeInTz(
							Api.getCalendar(Api.getDateTimeInUTC(date)), myTimeZone);*/
					String myTimeZoneDate = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(date), myTimeZone, 1);
					date = myTimeZoneDate;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return date;
		}
	 
	 
	 public static String getBooleanString(boolean content) {
			
			String booleanString = (content) ? "true" : "false";
			return booleanString;
		}
	 public static Long maxValue(int length){
			
			switch(length){
				case 4: return 9999l;
				case 5: return 99999l;
				case 6: return 999999l;
				case 7: return 9999999l;
				case 8: return 99999999l;
				case 9: return 999999999l;
				case 10:return 9999999999l;
				case 11:return 99999999999l;
				case 12:return 999999999999l;
				case 13:return 9999999999999l;
				case 14:return 99999999999999l;
				case 15:return 999999999999999l;
				case 16:return 9999999999999999l;
				case 17:return 99999999999999999l;
				case 18:return 999999999999999999l;
				//case 19:return 9999999999999999999l;
				//case 20:return 99999999999999999999l;
				default :     return 9999l; 
			}
		}
	 public static List<Long> removeDuplicates(List<Long> list){
		    
		    List<Long> uniqueList = new ArrayList<Long>();
			// add elements to al, including duplicates
			Set<Long> hs = new HashSet<Long>();
			hs.addAll(list);
			uniqueList.addAll(hs);
			return uniqueList;
	  }
	  
	 
	 public static String getDateTimeInTzToUtc(String dateTime, String offset) {
			try {
				Calendar calendar = getCalendar(getDateTimeInUTC(dateTime));
				return getDateTimeInTzToUtc(calendar, offset);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
	 
	 
	 public static List<String> longListToStringList(List<Long> longList){
			
			if(longList == null || longList.isEmpty()) {
				return new ArrayList<String>();
			}
			List<String> stringList = new ArrayList<String>(longList.size());
			for (Long myInt : longList) { 
				stringList.add(String.valueOf(myInt)); 
			}
			return stringList;
		}
	 
	 
	 public static List<String> longSetListToStringList(Set<Long> longList){
			
			if(longList == null || longList.isEmpty()) {
				return new ArrayList<String>();
			}
			List<String> stringList = new ArrayList<String>(longList.size());
			for (Long myInt : longList) { 
				stringList.add(String.valueOf(myInt)); 
			}
			return stringList;
		}
	 public static String getLogTextWithNewLineSeparator() {
		   
		 String logText = "";
		 try {
			    String ignoreMethodName = "getLogText";
			    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			    List<String> pathList = new ArrayList<String>();
			    String requiredPart = null;
			    String className = null;
			    String methodName = null;
				for (StackTraceElement stackTraceElement : stackTraceElements) {
					//methodCallList.add(stackTraceElement);
					//System.out.println("populateLogText() // stackTraceElement = "+stackTraceElement);
					className = stackTraceElement.getClassName();
					if(Api.isEmptyString(className)) {
						continue;
					}
					if(!className.startsWith("com.ayansys.effort")) {
						continue;
					}
					methodName = stackTraceElement.getMethodName();
					if(methodName.equals("populateLogText") || methodName.equals("invoke")) {
						continue;
					}
					if(!Api.isEmptyString(ignoreMethodName) && methodName.equals(ignoreMethodName)) {
						continue;
					}
					String fileName = stackTraceElement.getFileName();
					if(fileName.indexOf("<generated>") > -1) {
						continue;
					}
					requiredPart = methodName+"("+fileName+":"+stackTraceElement.getLineNumber()+")";
					pathList.add(requiredPart);
					if(pathList.size() > 200) {
						break;
					}
				}
				Collections.reverse(pathList);  
				StringBuffer completePath = new StringBuffer();
				int i = 0;
				for (String path : pathList) {
					
					if(i>0) {
						completePath.append("\n");
					}
					i++;
					completePath.append(path);
				}
				//System.out.println("completePath = "+completePath);
				String resolvedCompletePath = null;
				
				if(RequestContextHolder.getRequestAttributes() != null) {
					HttpServletRequest request = 
					        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
					                .getRequest();
					if(request != null && request.getRequestURI() != null) {
						String queryString = request.getRequestURI()+"?"+request.getQueryString();
						resolvedCompletePath = completePath.toString() +" \n url : "+queryString;
						logText = logText + (Thread.currentThread().getName()+" : "+resolvedCompletePath);
					}
				}else {
					resolvedCompletePath = completePath.toString() +" && this is schedularCall";
					logText = logText + (Thread.currentThread().getName()+" : "+resolvedCompletePath);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		   return logText;
	    }
	 
	 public static String addTimeToDateTime(String startDateTime,
				long visitduration) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm");
			try {
				Date oldDate = format.parse(startDateTime);
				Calendar cal = Calendar.getInstance();
				cal.setTime(oldDate);
				cal.add(Calendar.MINUTE, (int) visitduration);
				Date newdate = cal.getTime();
				return format.format(newdate);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	 
	 
	 public static Date addHoursToJavaUtilDate(Date date, int hours) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    calendar.add(Calendar.HOUR_OF_DAY, hours);
		    return calendar.getTime();
		}
	 
	 public static Date increaseDate(Date date, int days)
     {
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         cal.add(Calendar.DATE, days); //minus number would decrement the days
         return cal.getTime();
     }
	 public static String getStringFromDate(Date date) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 		String dateString = sdf.format(date.getTime());
	 		return dateString;
		}
	 
	 public static boolean isEqualString(String string1, String string2) {
			if (string1 == null) {
				if (string2 == null || Api.isEmptyString(string2)) {
					return true;
				} else {
					return false;
				}
			} else {
				return string1.equals(string2);
			}
		}
	 

		public static Date increaseMonth(String dateString) 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			Calendar c = Calendar.getInstance();
			
			try {
				c.setTime(sdf.parse(dateString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.add(Calendar.MONTH, 1);
			return c.getTime();
			
		}
		
		public static Long getDateTimeInMillies(String datetime){
		    if(!isEmptyString(datetime)){
			    try {
				   Date  date = DateUtils.parseDate(datetime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss.s"});
				   
				   return date.getTime();
				  } catch (ParseException e) {
				      // TODO Auto-generated catch block
				      e.printStackTrace();
				      
				  }
			    }
				//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            return null;
		}
		
		public static String getOnlyDateInStandardFormat(String dateString) {
    	    if(!isEmptyString(dateString)){
    	    try {
    		   Date  date = DateUtils.parseDate(dateString, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss.s"});
    		   
    		  return  DateFormatUtils.format(date, "yyyy-MM-dd");
    		  } catch (ParseException e) {
    		      // TODO Auto-generated catch block
    		      e.printStackTrace();
    		      return null;
    		  }
    	    }
    		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		return dateString;
    	}
		 public static String getTimeInTz24(String time, String offset)
					throws ParseException {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				Date date = format.parse(time);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));

				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);

				return (hour > 9 ? hour : "0" + hour) + ":"
						+ (minute > 9 ? minute : "0" + minute) + ":"
						+ (second > 9 ? second : "0" + second);
			}
		 
		public static String getTimeInTzToUtc(String time, String offset)
				throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date date = format.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, Integer.parseInt(offset));

			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);

			return (hour > 9 ? hour : "0" + hour) + ":"
					+ (minute > 9 ? minute : "0" + minute) + ":"
					+ (second > 9 ? second : "0" + second);
		}
		
		
	 public static String getDateToStringForTransactionId(Date date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			return format.format(date);
		}
	 public static String getDateToStringForId(Date date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format.format(date);
		}
	   
	 
	  public static String getCurrentTimeTz24(){
//    	return  DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
  	  	 Calendar cal = Calendar.getInstance();
  	  	 cal.add(Calendar.MINUTE, 15);
  	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  	     
  	     return sdf.format(cal.getTime());
    }
	  
	 public static String toCSV(List<?> list, String fieldName,String valuInCaseNull)
				throws IllegalArgumentException, IllegalAccessException {
			if (list != null) {
				StringBuilder csv = new StringBuilder("");
				for (Object obj : list) {
					if (csv.length() > 0) {
						csv .append(",");
					}

					if (fieldName == null || fieldName.trim().length() == 0) {
						csv.append(obj.toString());
					} else {
						Object val = getFieldValue(obj, fieldName);
						csv.append(val == null ? valuInCaseNull : val.toString());
					}
				}

				return csv.toString();
			} else {
				return null;
			}
		}
		
		
		public static String toCSVFromList(List<?> list, String fieldName,String  valueIncaseNull){
			try{
				return toCSV(list, fieldName,valueIncaseNull);
			}catch(Exception ex){
				
			}
			
			return null;
		}
		
		
		
	 public static String trimComma(String string)
 	{
 		if (!Api.isEmptyString(string) && string.endsWith(",")) 
			{
 			string = string.substring(0, string.length() - 1);
			}
			if (!Api.isEmptyString(string) && string.startsWith(",")) 
			{
				string = string.substring(1, string.length());
			}
			return string;
 	}

	 public static String getDateTimeInTzIst(Calendar calendar, String offset) {
			calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
			format.setTimeZone(TimeZone.getTimeZone("IST"));
			return format.format(calendar.getTime());
		}
		
	 
	 public static String replaceAllNewLineChar(String input) {
			String output = replaceAllSkipNull(input, "\n", " ");
			output = replaceAllSkipNull(output, "\r", " ");
			return output;
		}
	 public static String replaceAllSkipNull(String input, String regex,
				String replacement) {
			if (input != null) {
				return input.replaceAll(regex, replacement);
			} else {
				return null;
			}
		}
	 
 public  static void convertDateTimesToGivenType(Object object, String tzo,DateConversionType dateConversionType,String... propertyNames){
	   
		 
		 for (String propertyName : propertyNames) {
			 Object value;
			try {
				value = PropertyUtils.getProperty(object, propertyName);
				
				if(value!=null && !Api.isEmptyString(value.toString())){
				
					if(dateConversionType==DateConversionType.STADARD_TO_XSD){
						value=toUtcXsd(value.toString());
				    	
				    }else if(dateConversionType==DateConversionType.XSD_TO_STADARD){
				    	value=getDateTimeFromXsd(value.toString());
				    }else if(dateConversionType==DateConversionType.DATETIME_TO_UTC_DATETIME_WITH_TZO){
				    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
				    	value=getDateTimeInTzToUtc(calendar, tzo) ;
				    }else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_DATETIME_WITH_TZO){
				    	value =getDateTimeInTz24(value.toString(), tzo);
				    }
				    else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_DATETIME_DISPLAY_FORMAT){
				    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
				    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
				    	value =getDateTimeInTz(calendar, tzo, 1);
				    }
				    else if(dateConversionType==DateConversionType.UTC_DATETIME_TO_IST){
				    	Calendar calendar = Calendar.getInstance();
				    	calendar.setTimeInMillis(getDateTimeInUTC(value.toString()).getTime());
				    	value =getDateTimeInTzIst(calendar, tzo);
				    }
					PropertyUtils.setProperty(object, propertyName, value);
		    	  }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
	    	  
     }
	 
 
 
 
 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object fromJson(String json, Class class1)
			throws JsonParseException, JsonMappingException, IOException 
	{
		String disableInternFieldNames = getConstants().getDisableInternFieldNamesForJsonObjectMapper();
		

		if ("true".equalsIgnoreCase(String.valueOf(disableInternFieldNames).trim())) 
		{
			JsonFactory f = new JsonFactory();
	        f.disable(Feature.INTERN_FIELD_NAMES);
	        ObjectMapper mapper = new ObjectMapper(f);
	        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        return mapper.readValue(json, class1);
		}
        else
        {
        	ObjectMapper mapper = new ObjectMapper();
        	return mapper.readValue(json, class1);
        }
	}
	
public static Pattern compilePattern(String expr) {
		
		try
		{
			return Pattern.compile(expr);
		}
		catch(Throwable t)
		{
			Log.info(Api.class, "compilePattern() // Invalid Reg Expr. : "+expr, t); 
		}
		
		return null;
	}

public static String getToday() {
	String date = "";
	String month = "";
	String year = "";

	Calendar currentTime = Calendar.getInstance(TimeZone.getDefault());

	year = ""
			+ ((currentTime.get(Calendar.YEAR) + "").length() > 1 ? currentTime
					.get(Calendar.YEAR) : "0"
					+ currentTime.get(Calendar.YEAR));

	month = ""
			+ (((currentTime.get(Calendar.MONTH) + 1) + "").length() > 1 ? (currentTime
					.get(Calendar.MONTH) + 1) : "0"
					+ (currentTime.get(Calendar.MONTH) + 1));

	date = ""
			+ ((currentTime.get(Calendar.DAY_OF_MONTH) + "").length() > 1 ? currentTime
					.get(Calendar.DAY_OF_MONTH) : "0"
					+ currentTime.get(Calendar.DAY_OF_MONTH));

	return (year + "-" + month + "-" + date);
}

public static String getOnlyDateToString(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	return format.format(date);
}

public static boolean compareTwoDates(String sourceDate , String compareDate )
{
	 Date date1 = Api.getDateFromString(sourceDate);
	 Date date2 = Api.getDateFromString(compareDate);
	 
	 if(date1.after(date2) || date1.equals(date2))
	 {
		 return true;
	 }
	 return false;
}

public static boolean evaluateDateRestriction(String dateOfBirth, Integer minAge, Integer maxAge) {

	String today = Api.getToday();
	boolean restrictDate = true;
	
	String formatt = "yyyy-MM-dd";
	Date date1=DateValidator.getInstance().validate(dateOfBirth, "yyyy-MM-dd");
	Date date2=DateValidator.getInstance().validate(dateOfBirth, "dd-MM-yyyy");
	Date date3=DateValidator.getInstance().validate(dateOfBirth, "MM/dd/yyyy");
	Date date4=DateValidator.getInstance().validate(dateOfBirth, "dd/MM/yyyy");
	if(date1 != null) {
		formatt = "yyyy-MM-dd";
	}else if(date2 != null) {
		formatt = "dd-MM-yyyy";
	}else if(date3 != null) {
		formatt = "MM/dd/yyyy";
	}else if(date4 != null) {
		formatt = "dd/MM/yyyy";
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat(formatt);
	
	

	try {

		Date d1 = sdf.parse(dateOfBirth);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dob = format.format(d1);
		
		if(minAge != null && minAge > 0 && maxAge != null && maxAge > 0) {
			Calendar calendar = Api.getCalendar(d1);
			calendar.add(Calendar.YEAR, minAge);
			Date dateNew = calendar.getTime();
			String minAgeDate = Api.getOnlyDateToString(dateNew);
			
			Calendar maxcalendar = Api.getCalendar(d1);
			maxcalendar.add(Calendar.YEAR, maxAge+1);
			Date maxcalendarDate = maxcalendar.getTime();
			String maxAgeDate = Api.getOnlyDateToString(maxcalendarDate);
			
			
			if(Api.compareTwoDates(today,minAgeDate) && Api.compareTwoDates(maxAgeDate,today) && !maxAgeDate.equals(today)) {
				restrictDate = false;
			}
		}else if(minAge != null && minAge > 0) {
			Calendar calendar = Api.getCalendar(d1);
			calendar.add(Calendar.YEAR, minAge);
			Date dateNew = calendar.getTime();
			String minAgeDate = Api.getOnlyDateToString(dateNew);
			
			if(Api.compareTwoDates(today,minAgeDate)) {
				restrictDate = false;
			}
		}else if(maxAge != null && maxAge > 0) {
			Calendar maxcalendar = Api.getCalendar(d1);
			maxcalendar.add(Calendar.YEAR, maxAge+1);
			Date maxcalendarDate = maxcalendar.getTime();
			String maxAgeDate = Api.getOnlyDateToString(maxcalendarDate);
			if(Api.compareTwoDates(dob,today) && !dob.equals(today)) {
				restrictDate = true;
			}else if(Api.compareTwoDates(maxAgeDate,today) && !maxAgeDate.equals(today)) {
				restrictDate = false;
			}
		}
		
		return restrictDate;
	} catch (ParseException e) {
		e.printStackTrace();
		return false;
	}
}
	
	public static boolean isValueMatchForRegEx(String value,
			String regExpr) {
		try
		{
			Pattern pattern = Api.compilePattern(regExpr);
			Matcher matcher = pattern.matcher(value);
			if(matcher.matches())
			{
				Log.info(Api.class, "isValueMatchForRegEx() // value is matched. value : "+value+" & regExpr : "+regExpr);
				return true;
			}
		}
		catch(Throwable t)
		{
			Log.info(Api.class, "isValueMatchForRegEx() // Error Occured for value : "+value+" & regExpr : "+regExpr, t); 
		}
		Log.info(Api.class, "isValueMatchForRegEx() // value is not matched. value : "+value+" & regExpr : "+regExpr);
		return false;
	}
	
	
	public static String getTimeZoneDatesInLTZ(String date, String myTimeZone,
			String employeeTimeZone) {

		try {
			if ((employeeTimeZone != null && !employeeTimeZone.equalsIgnoreCase("null")) && (myTimeZone != null && !myTimeZone.equalsIgnoreCase("null") )) {

				String myTimeZoneDate = Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(date)), myTimeZone, 1);

				
				if(!myTimeZone.equalsIgnoreCase(employeeTimeZone)) {
					String employeeTimeZoneDate =Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(date)), employeeTimeZone, 1);
							
					myTimeZoneDate = myTimeZoneDate + " (" + employeeTimeZoneDate + ")";
				}
				date = myTimeZoneDate;

			} else if (myTimeZone != null) {
				String myTimeZoneDate = Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(date)), myTimeZone, 1);
				date = myTimeZoneDate;

			}else if (employeeTimeZone != null && !employeeTimeZone.equalsIgnoreCase("null")){
				String myTimeZoneDate = Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(date)), employeeTimeZone, 1);
				date = myTimeZoneDate;
			}
		} catch (Exception e) {
		//	e.printStackTrace();
		}

		return date;
	}
	public static String toCSV(List<?> list) {
		if (list != null) {
			StringBuilder csv = new StringBuilder("");
			for (Object obj : list) {
				if(obj != null)
				{
					if (csv.length() > 0) {
						csv.append(", ");
					}
	
					csv.append(obj.toString());
				}
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	
	public static String decryptRSAEncryptedString(String encodedString, String rsaPrivatekey) throws Exception {

		byte[] encode = com.effort.util.Base64.decode(encodedString, 0);
	    byte [] buffers = new byte[]{};
	    for (int i = 0; i < encode.length; i += RSA_DECODE_LENGTH) {
	        byte[] subarray = ArrayUtils.subarray(encode, i, i + RSA_DECODE_LENGTH);
	        byte[] doFinal = decryptByPrivateKey(subarray, rsaPrivatekey);
	        buffers = ArrayUtils.addAll(buffers, doFinal);
	    }
	    return new String(buffers);
	}
	
	public static byte[] decryptByPrivateKey(byte[] data, String rsaPrivatekey) throws Exception {

	    Key privateKey = loadPrivateKey(rsaPrivatekey);

	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);

	    return cipher.doFinal(data);
	}
	
	public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
	    try {
	        byte[] buffer = com.effort.util.Base64.decode(privateKeyStr, 0);
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(keySpec);
	    } catch (NoSuchAlgorithmException e) {
	        throw new Exception("NoSuchAlgorithmException");
	    } catch (InvalidKeySpecException e) {
	        throw new Exception("InvalidKeySpecException");
	    } catch (NullPointerException e) {
	        throw new Exception("NullPointerException");
	    }
	}
	
	public static String toJson(Object object) throws JsonGenerationException,
	JsonMappingException, IOException 
			{
			
			String disableInternFieldNames = getConstants().getDisableInternFieldNamesForJsonObjectMapper();
			if("true".equalsIgnoreCase(disableInternFieldNames))
			{
				JsonFactory f = new JsonFactory();
			    f.disable(Feature.INTERN_FIELD_NAMES);
			    ObjectMapper mapper = new ObjectMapper(f);
			    mapper.registerModule(new JavaTimeModule());
			    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				Writer strWriter = new StringWriter();
				mapper.writeValue(strWriter, object);
				
				return strWriter.toString();
			}
			else
			{
				ObjectMapper mapper = new ObjectMapper();
				//mapper.findAndRegisterModules();
				mapper.registerModule(new JavaTimeModule());
			    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			    
				Writer strWriter = new StringWriter();
				mapper.writeValue(strWriter, object);
				
				return strWriter.toString();
			}
			
			}
	
	public static boolean isEmptyString(String string1) {
		 return string1 == null || string1.trim().isEmpty() || string1.equalsIgnoreCase("null");
	}
	
	public static String toCSV(Set<?> list) {
		if (list != null) {
			StringBuilder csv = new StringBuilder("");
			for (Object obj : list) {
				if(obj != null)
				{
					if (csv.length() > 0) {
						csv.append(", ");
					}
	
					csv.append(obj.toString());
				}
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	
	public static Map<Object,Object> getMapFromList(List<?> list, String requiredFieldKeyName) {
		Map<Object,Object> map=new HashMap<Object,Object>();
		try{
			if (requiredFieldKeyName != null) {
				for (Object object : list) {
					// String valueKey =BeanUtils.getProperty(object, requiredFieldKeyName);
					Object valueKey = PropertyUtils.getProperty(object, requiredFieldKeyName);
					map.put(valueKey, object);
				}
			}
			}catch(Exception ex){
			 
		 }		
		 return map;
		}
			
	public static String toCSVFromList(List<?> list, String fieldName){
		try{
			return toCSV(list, fieldName);
		}catch(Exception ex){
			
		}
		
		return null;
	}
	
	
	public static boolean isEmptyObj(Object object) {
		if (object != null && !object.equals("null") && !object.equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String toCSV1(List<?> list) {
		if (list != null) {
			StringBuilder csv = new StringBuilder("");
			for (Object obj : list) {
				if (csv.length() > 0) {
					csv.append(",");
				}

				csv.append(obj.toString());
			}

			return csv.toString();
		} else {
			return null;
		}
	}
	
	 public static String getLogText() {
		   
		 String logText = "";
		 try {
			    String ignoreMethodName = "getLogText";
			    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			    List<String> pathList = new ArrayList<String>();
			    String requiredPart = null;
			    String className = null;
			    String methodName = null;
				for (StackTraceElement stackTraceElement : stackTraceElements) {
					//methodCallList.add(stackTraceElement);
					//System.out.println("populateLogText() // stackTraceElement = "+stackTraceElement);
					className = stackTraceElement.getClassName();
					if(Api.isEmptyString(className)) {
						continue;
					}
					if(!className.startsWith("com.ayansys.effort")) {
						continue;
					}
					methodName = stackTraceElement.getMethodName();
					if(methodName.equals("populateLogText") || methodName.equals("invoke")) {
						continue;
					}
					if(!Api.isEmptyString(ignoreMethodName) && methodName.equals(ignoreMethodName)) {
						continue;
					}
					String fileName = stackTraceElement.getFileName();
					if(fileName.indexOf("<generated>") > -1) {
						continue;
					}
					requiredPart = methodName+"("+fileName+":"+stackTraceElement.getLineNumber()+")";
					pathList.add(requiredPart);
					if(pathList.size() > 200) {
						break;
					}
				}
				Collections.reverse(pathList);  
				StringBuffer completePath = new StringBuffer();
				int i = 0;
				for (String path : pathList) {
					
					if(i>0) {
						completePath.append("==>");
					}
					i++;
					completePath.append(path);
				}
				//System.out.println("completePath = "+completePath);
				String resolvedCompletePath = null;
				
				if(RequestContextHolder.getRequestAttributes() != null) {
					HttpServletRequest request = 
					        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
					                .getRequest();
					if(request != null && request.getRequestURI() != null) {
						String queryString = request.getRequestURI()+"?"+request.getQueryString();
						resolvedCompletePath = completePath.toString() +" && url : "+queryString;
						logText = logText + (Thread.currentThread().getName()+" : "+resolvedCompletePath);
					}
				}else {
					resolvedCompletePath = completePath.toString() +" && this is schedularCall";
					logText = logText + (Thread.currentThread().getName()+" : "+resolvedCompletePath);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		   return logText;
	    }
	
	 
	 public static List<String> listToList(List<?> list, String fieldName) {
	 		
	 		String csv = "";
				try {
					csv = toCSV(list, fieldName);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		
	 		List<String> resultStringList = new ArrayList<String>();
	 		
	 		if(!isEmptyString(csv)){
	 			resultStringList = csvToList(csv);
	 			return resultStringList;
	 		}
	 		
	 		return null;
	 	}
	 
	 public static boolean isNumber(String number){
			try {
				Double.parseDouble(number);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		public static boolean isNumber(Object number){
			try {
				Double.parseDouble(number.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		public static double round(double unrounded, int precision) {
			return round(unrounded, precision, BigDecimal.ROUND_HALF_DOWN);
		}
		public static double round(double unrounded, int precision, int roundingMode) {
			BigDecimal bd = new BigDecimal(unrounded);
			BigDecimal roundedBig = bd.setScale(precision, roundingMode);
			double rounded = roundedBig.doubleValue();
			return rounded;
		}
	
	 public static String roundToString(double unrounded, int precision) {
			return roundToString(unrounded, precision, BigDecimal.ROUND_HALF_DOWN);
		}
	 public static String roundToString(double unrounded, int precision, int roundingMode) {
			BigDecimal bd = new BigDecimal(unrounded);
			BigDecimal roundedBig = bd.setScale(precision, roundingMode);
			String rounded = roundedBig.toString();
			return rounded;
		}
	 public static String secondsToDuration(String secondsInString) 
 	{
 	//	String startTime = "00:00";
			if(isEmptyObj(secondsInString)) {
				return "";
			}
			double s = Double.parseDouble(secondsInString);
 		String s1 = String.valueOf(s);
 		Long seconds = Math.abs(Long.parseLong((s1.substring(0, s1.indexOf(".")))));
 		
 		long h = TimeUnit.SECONDS.toHours(seconds);
 		long m = TimeUnit.SECONDS.toMinutes(seconds)%60;
 		long hAndMInSec = (h*60*60)+(m*60);
 		long sec = seconds-hAndMInSec;
 		
 		String hour = h+"";
 		if(hour.length()==1)
 		{
 			hour=0+hour;
 		}
 		String minute = m+"";
 		if(minute.length()==1)
 		{
 			minute=0+minute;
 		}
 		String second = sec+"";
 		if(second.length()==1)
 		{
 			second=0+second;
 		}
 		String duration = hour+":"+minute+":"+second;
 		if(s < 0) {
 			duration  = "-"+duration;
 		}
 		return duration;
 	}
	 public static String secondsToTime(Double s) 
 	{
 	//	String startTime = "00:00";
 		String s1 = String.valueOf(s);
 		Long seconds = Math.abs(Long.parseLong((s1.substring(0, s1.indexOf(".")))));
 		
 		long h = TimeUnit.SECONDS.toHours(seconds);
 		long m = TimeUnit.SECONDS.toMinutes(seconds)%60;
 		
 		String hour = h+"";
 		if(hour.length()==1)
 		{
 			hour=0+hour;
 		}
 		String minute = m+"";
 		if(minute.length()==1)
 		{
 			minute=0+minute;
 		}
 		return hour+":"+minute;
 	}
	 

		public static String addTzoAndGetDateTime(String dateTime,String tzo,String format){
			
			  String resultNew = Api.getDateTimeInTz24(dateTime, tzo);
			  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		      SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		      String requiredFormat = null;
		      try {
		  		Calendar cal = Calendar.getInstance();
		  		Date oldDate = sdf1.parse(resultNew);
		  		cal.setTime(oldDate);
		  		requiredFormat = sdf2.format(cal.getTime());
		  		System.out.println("requiredFormat = "+requiredFormat);
		  	} catch (ParseException e) {
		  		e.printStackTrace();
		  	}
		      return requiredFormat;
		}
		public static String getDateTimeInTz24(String dateTime, String offset) {
			String dateTime1 = removeTrailingZeroFromDateTime(dateTime);
			Date date =getDateFromString(dateTime1);
			Calendar calendar= getCalendar(date);
			return getDateTimeWithGivenTime(calendar, offset);
		}
		 public static String removeTrailingZeroFromDateTime(String datetimeXsd) {
				
				if(datetimeXsd == null || datetimeXsd.trim().length() == 0){
					return datetimeXsd;
				}
				if(datetimeXsd.trim().length() <= 19){
					return datetimeXsd;
				}
				if(!datetimeXsd.trim().endsWith(".0"))
					return datetimeXsd;
				String datetime = datetimeXsd.substring(0, 19);
				return datetime;
			}
		  public static Date getDateFromString(String dateTime) {
			  Date date;
			  try {
			      date = DateUtils.parseDate(dateTime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd hh:mm:ss a","yyyy-MM-dd HH:mm:ss.s","yyyy-MM-dd","dd-MM-yyyy","yyyy-MM-dd hh:mm a","yyyy-MM-dd hh:mm","MM/dd/yyyy","dd/MM/yyyy h:mm:ss a","MM/dd/yyyy HH:mm:ss","MM/dd/yyyy","yyyy/MM/dd HH:mm:ss.SSS","EEE MMM dd HH:mm:ss zzz yyyy"});
			      return date;
			  } catch (ParseException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			      return null;
			  }
		        }
		  public static Calendar getCalendar(Date date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
				calendar.setTime(date);
				return calendar;
			}
		  public static String getDateTimeWithGivenTime(Calendar calendar, String offset) {
				calendar.add(Calendar.MINUTE, -Integer.parseInt(offset));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return format.format(calendar.getTime());
			}
		  
		  public static boolean isStringOperationFormula(String formula){
				
				if(formula != null && formula.indexOf("<comma>") > -1)
					return true;
				if(formula != null && formula.indexOf("<space>") > -1)
					return true;
				
				return false;
			}
		  public static void filterDisableEmployees(List<Employee> employees) {
				if (employees != null && employees.size() > 0) {
					Iterator<Employee> emloyeeIterator = employees.iterator();
					while (emloyeeIterator.hasNext()) {
						Employee employee = emloyeeIterator.next();
						if (employee.getStatus() == 0) {
							emloyeeIterator.remove();
						}
					}
				}
			}
		  
		  public static int timeToSeconds(String s)
	    	{
	    	    String[] hourMin = s.split(":");
	    	    int hour = Integer.parseInt(hourMin[0]);
	    	    int mins = Integer.parseInt(hourMin[1]);
	    	    int hoursInMins = hour * 60;
	    	    return (hoursInMins + mins)*60;
	    	}
		  
		  public static String getSeparator(List<String> operatorsList,int operandCount ){
				
				String separator = " ";
				if(operandCount == 0)
					return "";
				if(operatorsList.size() >= operandCount)
					separator = operatorsList.get(operandCount-1);
				
				if(separator.equalsIgnoreCase("<space>"))
					return " ";
				if(separator.equalsIgnoreCase("<comma>"))
					return ",";
				
				return separator;
			}
		  
		  public static List<String> getOperatorsList(String formula){
				
			  String onlyOperators = formula.replaceAll("[^<space><comma>.]","");
		      onlyOperators = onlyOperators.replaceAll("[\\d.]", "");
		      System.out.println("onlyOperators = "+onlyOperators);
		      Pattern regex = Pattern.compile("[<{\\[].*?[\\]}>]");
		      List<String> operatorsList = new ArrayList<String>();
		      Matcher regexMatcher = regex.matcher(formula);

	        while (regexMatcher.find()){
	      	  operatorsList.add(regexMatcher.group());
	        } 
	        System.out.println(operatorsList.size());
	        System.out.println(operatorsList);
	        return operatorsList;
		}
		  
		  public static Double durationToSeconds(String sec)
			{
				if(Api.isEmptyString(sec)) {
					return 0.0;
				}
			    String[] hourMin = sec.split(":");
			    int hour = 0;
			    int mins = 0;
			    int secs = 0;
			    
			    if(hourMin.length >= 1) {
			    	hour = (int) Double.parseDouble(hourMin[0]);
			    }
			    if(hourMin.length >= 2) {
			    	mins = (int) Double.parseDouble(hourMin[1]);
			    }
			    if(hourMin.length >= 3 ) {
			    	secs = (int) Double.parseDouble(hourMin[2]);
			    }
			    int hoursInMins = hour * 60;
			    int hrAndMinInSec = (hoursInMins + mins)*60;
			    int hrAndMinAndSecInSec = hrAndMinInSec + secs;
			    Double hrAndMinAndSecInSecDouble = Double.parseDouble(hrAndMinAndSecInSec+"");
			    return hrAndMinAndSecInSecDouble;
			}
		  public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		        List<T> list = new ArrayList<T>();

		        for (T t : list1) {
		            if(list2.contains(t)) {
		                list.add(t);
		            }
		        }

		        return list;
		    }
		  
			public static int getDaysBetweenTwoDates(String startDate, String endDate) throws ParseException{
				   
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = format.parse(startDate);
				Date date2 = format.parse(endDate);
				long millis = date2.getTime()-date1.getTime();
				return (int)(millis/(1000*60*60*24));
		   }
			
		  public static String getCurrentTimeTz24New(){
			  	 Calendar cal = Calendar.getInstance();
			     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			     
			     return sdf.format(cal.getTime());
		  }
		  
		  public static boolean isSendQRCodeForMobileAppLogin(int createWebUser, boolean generateQrCode,boolean isEnableMultiUserLogin) {
				if((Employee.WEB_USER_ONLY == createWebUser) || 
						(Employee.WEB_USER_AND_CONFIGURATOR == createWebUser) ||
						(Employee.MOBILE_APP_ACCESS_ONLY == createWebUser && generateQrCode) || isEnableMultiUserLogin) {
					return true;
				}
				return false;
			}
		  
			public static String getRandomNumber(int length) {
				String chars = "0123456789";
				Random r = new Random();

				char[] buf = new char[length];

				for (int i = 0; i < buf.length; i++) {
					buf[i] = chars.charAt(r.nextInt(chars.length()));
				}

				return new String(buf);
			}
}

