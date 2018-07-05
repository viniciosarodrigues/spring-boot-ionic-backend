package com.viniciosrodrigues.cursomc.resource.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Long> decodeLongList(String s) {
		List<Long> idsEmLong = new ArrayList<>();

		if (s == null || s.isEmpty())
			return idsEmLong;
		String[] idsEmString = s.split(",");

		for (String id : idsEmString) {
			idsEmLong.add(Long.parseLong(id));
		}
		return idsEmLong;
	}

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}