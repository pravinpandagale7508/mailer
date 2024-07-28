package com.utcl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import jakarta.persistence.AttributeConverter;

public class ListConverter implements AttributeConverter<List<Object>, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(List<Object> arg0) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(arg0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteOut.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> convertToEntityAttribute(byte[] arg0) {
		List<Object> data2 = null;
		if (arg0 != null) {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(arg0);
			ObjectInputStream in;

			try {
				in = new ObjectInputStream(byteIn);
				data2 = (List<Object>) in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data2;
	}

}
