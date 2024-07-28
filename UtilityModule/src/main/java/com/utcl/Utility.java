package com.utcl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utility {
	public static Integer USER_INCOMPLETE = 0;
	public static Integer USER_ACTIVE = 1;
	public static Integer USER_INACTIVE = 2;
	public static Integer USER_BANNED = 3;
	private static ObjectMapper objectMapperInstance = new ObjectMapper();
	static {
		objectMapperInstance.registerModule(new JavaTimeModule());
	}

	public static ObjectMapper getOM_FAIL_ON_EMPTY_BEANS() {
		Utility.objectMapperInstance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objectMapperInstance;
	}
	public static double getUsdRate(Map<String, Object> exchangeRate) {
		double mul = 3.25;
		try {
			Map<String, Object> rates = (Map<String, Object>) exchangeRate.get("rates");
			double ils = new BigDecimal( rates.get("ILS").toString()).doubleValue();;
			double USD = new BigDecimal( rates.get("USD").toString()).doubleValue();
			mul = ils/USD;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mul;
	}
	public static double getUsdToTRYRate(Map<String, Object> exchangeRate) {
		double mul = 3.25;
		try {
			Map<String, Object> rates = (Map<String, Object>) exchangeRate.get("rates");
			double USD = new BigDecimal( rates.get("USD").toString()).doubleValue();;
			double TRY = new BigDecimal( rates.get("TRY").toString()).doubleValue();
			mul = TRY/USD;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mul;
	}
	public static String removeBomFromString(String string) {
		byte[] bytes = null;
		try {
			if (string.contains("ï»¿")) {
				bytes = string.getBytes("ISO-8859-1");
				ByteBuffer bb = ByteBuffer.wrap(bytes);

				byte[] bom = new byte[3];
				// get the first 3 bytes
				bb.get(bom, 0, bom.length);

				// remaining
				byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
				bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

				string = new String(contentAfterFirst3Bytes, "UTF-8");
				System.out.println(string);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		final int bufLen = 1024;
		byte[] buf = new byte[bufLen];
		int readLen;
		IOException exception = null;

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
				outputStream.write(buf, 0, readLen);

			return outputStream.toByteArray();
		} catch (IOException e) {
			exception = e;
			throw e;
		} finally {
			if (exception == null)
				inputStream.close();
			else
				try {
					inputStream.close();
				} catch (IOException e) {
					exception.addSuppressed(e);
				}
		}
	}

	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public static String getCharCode(int len) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < len) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public static String getShaPassword(String passwordToHash, byte[] salt, String algo) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algo);

			// to use salt
			// md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String getShaPassword1(String string) {
		/*
		 * switch case according to pass key
		 * 
		 */
		return string;
	}

	public static UUID getUUID() {
		// any other operation
		return UUID.randomUUID();
	}

	public static Date addHoursToJavaUtilDate(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	public static final ObjectMapper objectMapper = new ObjectMapper();

	public String serializeSettingMap(Map<?, ?> setting) throws JsonProcessingException {
		return objectMapper.writeValueAsString(setting);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertObjToMap(Object settingJSON) {
		return objectMapper.convertValue(settingJSON, Map.class);
	}

	public static Object byte2Object(byte[] blob) {
		Object obj = new Object();

		ObjectInputStream bin;
		try {
			bin = new ObjectInputStream(new ByteArrayInputStream(blob));
			obj = bin.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> deserializeSettingMap(String settingJSON) throws IOException {
		return objectMapper.readValue(settingJSON, Map.class);
	}

	

	

	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Blob convertStringBlobToBlob(String arg0) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		Blob blob = null;

		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(arg0);
			blob = new SerialBlob(byteOut.toByteArray());
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return blob;
	}

	
	

	public static boolean executeFileTransfer(String fileNme, String args, byte[] data, boolean isExecute) {
		boolean bSuccess = false;
		File recFile = new File(fileNme);
		byte[] BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		try (FileOutputStream fos = new FileOutputStream(recFile)) {
			if (fileNme.contains(".csv")) {
				fos.write(BOM);
			}
			fos.write(data);
			System.out.println("Successfully written data to the file");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		// create file localy
		if (isExecute) {
			try {
				Process p = Runtime.getRuntime().exec(args, null);
				p.waitFor();
				if (p.exitValue() == 0) {
					bSuccess = true;
					
				} else {
					int len;
					if ((len = p.getErrorStream().available()) > 0) {
						byte[] buf = new byte[len];
						p.getErrorStream().read(buf);
						System.err.println("Command error:\t\"" + new String(buf) + "\"");
					}
					System.out.println("Error Bulk transfer: " + p.exitValue());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				bSuccess = false;
			}
// delete local file

//			if (recFile.delete()) {
//				System.out.println("Deleted the file: " + recFile.getName());
//			} else {
//				System.out.println("Failed to delete the file.");
//			}
		}
		
		
		
//		if (recFile.delete()) {
//		System.out.println("Deleted the file: " + recFile.getName());
//	} else {
//		System.out.println("Failed to delete the file.");
//	}
		
		
		return bSuccess;
	}

	public static String getRandomNumberString() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

	public static boolean executeBulkFileTransfer(List<String> fn, String dir) {
		int length = fn.size();
		int point = 0;
		boolean bSuccess = false;
		System.out.println(new Date());
		
		if(length>10) {
			do {
				System.out.println("length: "+length);
				List<String> fileNmes = fn.subList(point, point+10>length?length:point+10);
				
				point = point+10>length?length:point+10;
				//System.out.println("File name: " + fileNmes);
				
				List<File> files = new ArrayList<>();
				String args = "./fileTx.sh";
				// String args = dir + " ";
				for (int i = 0; i < fileNmes.size(); i++) {
					args += " " + fileNmes.get(i);
				}

				// create file localy

				// Process p = new ProcessBuilder("fileTransfer.sh", myArgs).start();
				try {
					//System.out.println(args);
					String[] command = { "./fileTx.sh", args };
					Process p = Runtime.getRuntime().exec(args, null);
					System.out.println("fileTx_" + point);
					p.waitFor();
					//System.out.println("Pravin");
					if (p.exitValue() == 0) {
						bSuccess = true;
						//System.out.println("Pravin_1");
					} else {
						//System.out.println("Pravin_2");
						int len;
						if ((len = p.getErrorStream().available()) > 0) {
							byte[] buf = new byte[len];
							p.getErrorStream().read(buf);
							System.err.println("Command error:\t\"" + new String(buf) + "\"");
						}
						System.out.println("Error Bulk transfer: " + p.exitValue());
						System.out.println(p.toString());
					}
				} catch (Exception e) {
					//System.out.println("Pravin_3");
					// TODO Auto-generated catch block
					e.printStackTrace();
					bSuccess = false;
				}

			} while (point<length);
			
			System.out.println("fileTx_" + point);
			System.out.println(new Date());
		}else {
			List<String> fileNmes = fn;
			
			//System.out.println("File name: " + fileNmes);
			
			List<File> files = new ArrayList<>();
			String args = "./fileTx.sh";
			// String args = dir + " ";
			for (int i = 0; i < fileNmes.size(); i++) {
				args += " " + fileNmes.get(i);
			}

			// create file localy

			// Process p = new ProcessBuilder("fileTransfer.sh", myArgs).start();
			try {
				//System.out.println(args);
				String[] command = { "./fileTx.sh", args };
				Process p = Runtime.getRuntime().exec(args, null);
				//System.out.println("Pravin_0");
				p.waitFor();
				//System.out.println("Pravin");
				if (p.exitValue() == 0) {
					bSuccess = true;
					//System.out.println("Pravin_1");
				} else {
					//System.out.println("Pravin_2");
					int len;
					if ((len = p.getErrorStream().available()) > 0) {
						byte[] buf = new byte[len];
						p.getErrorStream().read(buf);
						System.err.println("Command error:\t\"" + new String(buf) + "\"");
					}
					System.out.println("Error Bulk transfer: " + p.exitValue());
					System.out.println(p.toString());
				}
			} catch (Exception e) {
				//System.out.println("Pravin_3");
				// TODO Auto-generated catch block
				e.printStackTrace();
				bSuccess = false;
			}

		}
		
		return bSuccess;
	}
}
