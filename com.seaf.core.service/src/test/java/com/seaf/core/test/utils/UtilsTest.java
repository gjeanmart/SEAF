package com.seaf.core.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class UtilsTest {
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static final int 	PAGE_SIZE 			= 10;
	public static final int		PAGE_NUMBER 		= 1;
	
	public static final String GROUP1_NAME			= "COMPTA";
	public static final String GROUP1_DESCRIPTION	= "Service de compta";
	public static final String GROUP1_DESCRIPTION2	= "Service de comptabilité";
	public static final String GROUP2_NAME			= "MAKRTING";
	public static final String GROUP2_DESCRIPTION	= "Service de marketing";	
	
	public static final String 	USER1_FIRSTNAME		= "Gregoire";
	public static final String 	USER1_LASTNAME		= "JEANMART";
	public static final String 	USER1_EMAIL			= "gjeanmart@gmail.com";
	public static final String 	USER1_BIRTH			= "21/12/1987";
	
	public static final String 	USER2_FIRSTNAME		= "Isabelle";
	public static final String 	USER2_FIRSTNAME2	= "Isa";
	public static final String 	USER2_LASTNAME		= "LEGAY";
	public static final String 	USER2_EMAIL			= "ilegay87@gmail.com";
	public static final String 	USER2_BIRTH			= "18/06/1987";

	public static final String 	USER3_FIRSTNAME		= "Frederic";
	public static final String 	USER3_LASTNAME		= "JEANMART";
	public static final String 	USER3_EMAIL			= "fff@gmail.com";
	public static final String 	USER3_BIRTH			= "11/03/1960";

	public static final String 	USER4_FIRSTNAME		= "Marie";
	public static final String 	USER4_LASTNAME		= "JEANMART";
	public static final String 	USER4_EMAIL			= "mmm@gmail.com";
	public static final String 	USER4_BIRTH			= "15/01/1958";

	public static final String 	USER5_FIRSTNAME		= "Damien";
	public static final String 	USER5_LASTNAME		= "JEANMART";
	public static final String 	USER5_EMAIL			= "damien@gmail.com";
	public static final String 	USER5_BIRTH			= "17/03/1990";

	public static Date getDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
