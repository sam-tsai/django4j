package org.django4j.api.string;

import java.io.UnsupportedEncodingException;

public class AbstractString {

	private StringBuilder sb = new StringBuilder();

	private final String charset;

	public AbstractString(String charset) {
		this.charset = charset;
	}

	public AbstractString(String charset, CharSequence cs) {
		this.charset = charset;
		sb.append(cs);
	}

	public AbstractString(String charset, AbstractString astr) {
		this.charset = charset;
		sb.append(astr.sb);
	}

	public AbstractString() {
		this.charset = null;
	}

	public AbstractString(CharSequence cs) {
		this.charset = null;
		sb.append(cs);
	}

	public AbstractString(AbstractString astr) {
		this.charset = null;
		sb.append(astr.sb);
	}

	public AbstractString append(Object obj) {
		return append(String.valueOf(obj));
	}

	public AbstractString append(String str) {
		sb.append(str);
		return this;
	}

	public AbstractString append(AbstractString astr) {
		sb.append(astr.sb);
		return this;
	}

	public AbstractString append(CharSequence s) {
		sb.append(s);
		return this;
	}

	public AbstractString append(CharSequence s, int start, int end) {
		sb.append(s, start, end);
		return this;
	}

	public AbstractString append(char str[]) {
		sb.append(str);
		return this;
	}

	public AbstractString append(char str[], int offset, int len) {
		sb.append(str, offset, len);
		return this;
	}

	public AbstractString append(boolean b) {
		sb.append(b);
		return this;
	}

	public AbstractString append(char c) {
		sb.append(c);
		return this;
	}

	public AbstractString append(int i) {
		sb.append(i);
		return this;
	}

	public AbstractString append(long lng) {
		sb.append(lng);
		return this;
	}

	public AbstractString append(float f) {
		sb.append(f);
		return this;
	}

	public AbstractString append(double d) {
		sb.append(d);
		return this;
	}

	public byte[] toBytes() {
		try {
			return sb.toString().getBytes(charset == null ? "utf-8" : charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("wrong charset");
		}
	}

	public static boolean isJsonString(String str) {
		if (str == null)
			return false;
		if (str.startsWith("{") && str.endsWith("}"))
			return true;
		if (str.startsWith("[") && str.endsWith("]"))
			return true;
		return false;
	}

	public static boolean isXmlString(String str) {
		if (str == null)
			return false;
		if (str.startsWith("<?xml") && str.endsWith(">"))
			return true;
		return false;
	}

	public static boolean isHtmlString(String str) {
		if (str == null)
			return false;
		if (str.startsWith("<!DOCTYPE") && str.endsWith(">"))
			return true;
		if (str.startsWith("<html") && str.endsWith(">"))
			return true;
		return false;
	}

}
