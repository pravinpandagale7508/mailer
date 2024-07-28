package com.utcl.auth.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import jakarta.persistence.AttributeConverter;


public class UserInfoConverter implements AttributeConverter<UserInfo, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(UserInfo arg0) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(arg0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return byteOut.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserInfo convertToEntityAttribute(byte[] arg0) {
		UserInfo data2 = null;
		if (arg0 != null) {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(arg0);
			ObjectInputStream in;

			try {
				in = new ObjectInputStream(byteIn);
				data2 = (UserInfo) in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data2;
	}

}
