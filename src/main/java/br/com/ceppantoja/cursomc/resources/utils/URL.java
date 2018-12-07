package br.com.ceppantoja.cursomc.resources.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL implements Serializable {
    private static final long serialVersionUID = 1L;

    public static List<Integer> decodeIntList(String s) {
        String[] vet = s.split(",");
        return Arrays.stream(vet).map(string -> Integer.parseInt(string)).collect(Collectors.toList());
    }

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}