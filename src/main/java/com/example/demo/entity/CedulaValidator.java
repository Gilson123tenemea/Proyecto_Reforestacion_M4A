package com.example.demo.entity;

public class CedulaValidator {
	
	public static boolean isValidCedula(String cedula) {
        if (cedula == null || cedula.length() != 10 || !cedula.matches("[0-9]+")) {
            return false; 
        }

        int provinceCode = Integer.parseInt(cedula.substring(0, 2));
        if (provinceCode < 1 || provinceCode > 24) {
            return false; 
        }

        int total = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) { 
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            total += digit;
        }

        int lastDigit = Character.getNumericValue(cedula.charAt(9));
        int calculatedDigit = (10 - (total % 10)) % 10; 

        return lastDigit == calculatedDigit;
    }
}
