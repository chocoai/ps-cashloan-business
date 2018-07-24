package com.adpanshi.cashloan.business.cl.util;

import java.io.*;

public class SerializeUtil {

	public static byte[] serialize(Object o) {
		if (o == null) {
			throw new NullPointerException("Can't serialize null");
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream outo = null;
		try {
			outo = new ObjectOutputStream(out);
			outo.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				outo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
	}

	public static Object deserialize(byte[] b) {
		ObjectInputStream oin = null;
		try {
			oin = new ObjectInputStream(new ByteArrayInputStream(b));
			try {
				return oin.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
