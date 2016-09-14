package tests;

// Deprecated, it work like a bridge between Calendar and DateFormat
import java.util.Date;

// Manipulated dates and times. Add or roll time interval
import java.util.Calendar;

// Formatting date for strings and locales
import java.text.DateFormat;

// Formatting numbers and currencies for locales
import java.text.NumberFormat;
import java.text.ParseException;

// Convert dates, numbers and currencies for specific locales.
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class C8_Formatting {

	public static void main(String[] args) {
		// Section 1
		testDate();
		testCalendar();
		testDateFormat();
		testLocale();
		testNumberFormat();
		
		// Section 2
		testRegExp();
		testTokenizing();
		testFormatter();
		
		// Section 3
		testResourceBundles();
	}
	
	// Test Date class
	// Method: setTime(miliseconds)
	// Method: getTime()
	public static void testDate() {
		System.out.println("Test Date Class");
		
		// Create date with miliseconds
		Date d1 = new Date(1_000_000_000_000L);
		System.out.println("1st date " + d1.toString());
		
		// Add 1 hour to a date
		d1.setTime(d1.getTime() + 3_600_000);
		System.out.println("2do date " + d1.toString());	
	}
	
	// Test Calendar Class (Abstract)
	// Constructor: getInstance()
	// Method: setTime(Date d)
	// Method: getTime()
	// Method: getFirstDayOfWeek()
	// Method: set(int anno, int month, int day)
	// Method: get(attr a)
	// Method: add(attr a, int i)
	// Method: roll(attr a, int i)
	public static void testCalendar() {
		System.out.println("\n\nTest Calendar Class");
		
		Date d1 = new Date(1_000_000_000_000L);
		
		// Get a instance
		Calendar c1 = Calendar.getInstance();
		
		// Set a date
		c1.set(2010, 11, 14);
		
		c1.setTime(d1);
		
		// Get the first day of the week
		if (Calendar.MONDAY == c1.getFirstDayOfWeek()) {
			System.out.println("Monday first day of the week");
		}
		else {
			System.out.println("first day of the week " + c1.getFirstDayOfWeek());
		}
		
		// Get the day of the week
		System.out.println("date is " + c1.get(Calendar.DAY_OF_WEEK));
		
		// Add a month to the date
		c1.add(Calendar.MONTH, 1);
		
		// Convert calendar to date
		Date d2 = c1.getTime();
		System.out.println("new date " + d2.toString());
		
		// Add 9 month to the date but without changes the year
		c1.roll(Calendar.MONTH, 9);
		Date d3 = c1.getTime();
		System.out.println("new date " + d3.toString());
	}
	
	// Test DateFormat class (Abstract)
	// Constructor: getIntance()
	// Constructor: getDateIntance()
	// Constructor: getDateIntance(attr a)
	// Constructor: getDateIntance(attr a, Locale l)
	// Method: format(Date d)
	// Method: parse(String s) => ParseException
	public static void testDateFormat() {
		System.out.println("\n\nTest DateFormat Class");
		
		Date d1 = new Date(1_000_000_000_000L);
		
		DateFormat[] dfa = new DateFormat[6];
		
		// Instances different object
		dfa[0] = DateFormat.getInstance();
		dfa[1] = DateFormat.getDateInstance();
		dfa[2] = DateFormat.getDateInstance(DateFormat.SHORT);
		dfa[3] = DateFormat.getDateInstance(DateFormat.MEDIUM);
		dfa[4] = DateFormat.getDateInstance(DateFormat.LONG);
		dfa[5] = DateFormat.getDateInstance(DateFormat.FULL);
		
		for (DateFormat df : dfa) {
			System.out.println(df.format(d1));
		}
		
		// Format a date
		String sd1 = dfa[2].format(d1);
		System.out.println("date " + sd1);
		
		try {
			// Parse a string to a date
			Date d2 = dfa[2].parse(sd1);
			System.out.println("parsed " + d2.toString());
		}
		catch (ParseException e) {
			System.out.println("parse exception");
		}
		
	}
	
	// Test Locale Class
	// Constructor: Locale(String language)
	// Constructor: Locale(String language, String country)
	// Method: getDisplayCountry()
	// Method: getDisplayCountry(Locale l)
	// Method: getDisplayLanguage()
	// Method: getDisplayLanguage(Locale l)
	// Method: getDefault()
	// Method: setDefault(Locale l)
	public static void testLocale() {
		System.out.println("\n\nTest Locale Class");
		
		// Create 3 locales to different languages and countries
		Locale locIT = new Locale("it", "IT");
		Locale locCL = new Locale("es", "CL");
		Locale locBR = new Locale("pt", "BR");
		
		// Print the country and language of Chile
		System.out.println(locCL.getDisplayCountry());
		System.out.println(locCL.getDisplayLanguage());

		// Print the country and language of Chile in Italian
		System.out.println(locCL.getDisplayCountry(locIT));
		System.out.println(locCL.getDisplayLanguage(locIT));
		
		// Create Calendar and Date
		Calendar c1 = Calendar.getInstance();
		c1.set(2010, 11, 25);
		
		Date d1 = c1.getTime();
		
		// Create DateFormat with locales
		DateFormat df1 = DateFormat.getDateInstance(DateFormat.FULL);
		DateFormat dfIT = DateFormat.getDateInstance(DateFormat.FULL, locIT);
		DateFormat dfCH = DateFormat.getDateInstance(DateFormat.FULL, locCL);
		
		System.out.println(df1.format(d1));
		System.out.println(dfIT.format(d1));
		System.out.println(dfCH.format(d1));
		
		// Get default locale
		Locale initial = Locale.getDefault();
		System.out.println(initial);
		
		// Set default locale
		Locale.setDefault(Locale.GERMANY);
		System.out.println(Locale.getDefault());
		
		Locale.setDefault(initial);
		System.out.println(Locale.getDefault());
	}
	
	// Test NumberFormat Class
	// Method: getInstance()
	// Method: getInstance(locale l)
	// Method: getCurrencyInstance()
	// Method: getCurrencyInstance(locale l)
	// Method: getMaximumFractionDigits()
	// Method: format(number n)
	// Method: parse(string s) => ParseException
	// Method: setParseIntegerOnly(boolean b)
	public static void testNumberFormat() {
		System.out.println("\n\nTest NumberFormat Class");
		
		float f1 = 123.45678f;
		
		// Create locales
		Locale locFR = new Locale("fr");
		Locale locUS = new Locale("en", "US");
		
		// Create array with number formats
		NumberFormat[] nfa = new NumberFormat[6];
		nfa[0] = NumberFormat.getInstance();
		nfa[1] = NumberFormat.getInstance(locFR);
		nfa[2] = NumberFormat.getInstance(locUS);
		nfa[3] = NumberFormat.getCurrencyInstance();
		nfa[4] = NumberFormat.getCurrencyInstance(locFR);
		nfa[5] = NumberFormat.getCurrencyInstance(locUS);
		
		for (NumberFormat nf: nfa) {
			System.out.println(nf.format(f1));
		}
		
		System.out.println(nfa[0].getMaximumFractionDigits() + " " + nfa[0].format(f1));
		
		// Change number of fraction digits
		nfa[0].setMaximumFractionDigits(5);
		System.out.println(nfa[0].getMaximumFractionDigits() + " " + nfa[0].format(f1));
		
		nfa[0].setMaximumFractionDigits(10);
		System.out.println(nfa[0].getMaximumFractionDigits() + " " + nfa[0].format(f1));
		
		try {
			// Use only integer part
			nfa[0].setParseIntegerOnly(true);
			System.out.println(nfa[0].parse("123,456"));
			
			// Use entire number
			nfa[0].setParseIntegerOnly(false);
			System.out.println(nfa[0].parse("123,456"));
		}
		catch (ParseException e) {
			System.out.println("parse exception");
		}
		
	}
	
	// Rule: A regex search runs from left to right and once a character has been used ina match, it cannot be reused
	// Rule: To use metachar like common char we need to add \\ before (ej: \\. or \\w)
	// Rule: You can have a zero-length match when you use ? or *, it can occurs at the start of the string when non match or zero-length source, between chars after a match, at the end of the string
	// Rule: A zero-length match start and end in the same position
	// Metachar \d \D: A digit (0-9), Non a digit
	// Metachar \s \S: A whitespace (space, \t, \n, \f, \r), Non a whitespace
	// Metachar \w \W: A word character (a-z, A-Z, digits, "_"), Non a word
	// Metachar \b \B: A word boundary (word vs non-word in any order), Non a boundary. Each end of the string are non-word, mark the second element match, not return group of chars.
	// Metachar [abc]: Search sets of chars (a, b or c)
	// Metachar [a-d]: Search range of chars (a, b, c or d)
	// Metachar ^: negation
	// Metachar .: any char
	// Quantifier greedy + reluctant +?: one o more occurrences (greedy: using entire string, reluctant: using part of string)
	// Quantifier greedy * reluctant *?: zero o more occurrences (greedy: using entire string, reluctant: using part of string)
	// Quantifier greedy ? reluctant ??: zero o one occurrences (greedy: using entire string, reluctant: using part of string)
	public static void testRegExp() {
		System.out.println("\n\nTest RegExp");
		
		matchRegExp("ab", "abaaaba");
		matchRegExp("\\d", "a12c3e456f");
		matchRegExp("\\w", "a 1 56 _Z");
		matchRegExp("\\S", "w1w w$ &#w1");
		matchRegExp("\\b", "w2w w$ &#w2");
		matchRegExp("\\B", "#ab de#");
		matchRegExp("\\d+", "1 a12 234b");
		matchRegExp("0[xX]([0-9a-fA-F])+", "12 0x 0x12 0Xf 0xg");
		matchRegExp(".*xx", "yyxxxyxx");
		matchRegExp(".*?xx", "yyxxxyxx");
		matchRegExp("a?", "aba");
		matchRegExp("a?", "bab");
		scanRegExp("\\d+", "1 a12 234b");
	}
	
	// Test Patter Class
	// Method: compile(String pattern)
	// Method: matcher(String source)
	
	// Test Matcher Class
	// Method: pattern()
	// Method: find()
	// Method: start()
	// Method: end()
	// Method: group()
	public static void matchRegExp(String pattern, String source) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		
		System.out.println("source: " + source);
		System.out.println("index:  01234567890123456");
		System.out.println("pattern: " + m.pattern());
		
		while (m.find()) {
			System.out.println(m.start() + "-" + m.end() + ": " + m.group());
		}
	}

	// Test Scanner Class
	// Contructor: Scanner(String source)
	// Method: findInLine(String patter)
	public static void scanRegExp(String pattern, String source) {
		System.out.println("source: " + source);
		System.out.println("index:  01234567890123456");
		System.out.println("pattern: " + pattern);
		
		Scanner s = new Scanner(source);
		String token;
		do {
			token = s.findInLine(pattern);
			System.out.println("found " + token);
		} while (token != null);
	}
	
	// Rule: When you tokenizing continues delimiter you get a void string.
	public static void testTokenizing() {
		System.out.println("\n\nTest Tokenizing");
		
		stringTokenizing("\\d", "ab5 ccc 45 @");
		scanTokenizing("", "1 true 34 hi");
		scanTokenizing("_", "1_true_34_hi");
		stringTokenizerTokenizing("", "a bc d e");
		stringTokenizerTokenizing("c", "a bc cd e");
	}
	
	// Test String Class
	// Method: split(String delimiter)
	public static void stringTokenizing(String delimiter, String source) {
		String[] tokens = source.split(delimiter);
		for (String token: tokens) {
			System.out.println(">" + token + "<");
		}
	}
	
	// Test Scanner Class
	// Can be contructed using files, streams or strings as source.
	// Convert token to their appropiateprimite type.
	// Constructor: Scanner(String source)
	// Method: useDelimiter(String delimiter)
	// Method: hasNext() and next()
	// Method: hasNextInt() and nextInt()
	// Method: hasNextBoolean() and nextBoolean()
	// Method: hasNextLong() and nextLong()
	public static void scanTokenizing(String delimiter, String source) {
		String str1, str2;
		int i;
		boolean b;
		
		Scanner s1 = new Scanner(source);
		Scanner s2 = new Scanner(source);
		
		if (delimiter != "") {
			s1.useDelimiter(delimiter);
			s2.useDelimiter(delimiter);
		}
		
		while (s1.hasNext()) {
			str1 = s1.next();
			System.out.print("s:" + str1 + "||");
		}
		System.out.println();
		
		while (s2.hasNext()) {
			if (s2.hasNextInt()) {
				i = s2.nextInt();
				System.out.print("i:" + i + "||");
			}
			else if (s2.hasNextBoolean()) {
				b = s2.nextBoolean();
				System.out.print("b:" + b + "||");
			}
			else {
				str2 = s2.next();
				System.out.print("s:" + str2 + "||");
			}
		}
		System.out.println();
	} 
	
	// Test StringTokenizer Class
	// Constructor: StringTokenizar(String source)
	// Constructor: StringTokenizar(String source, String delimiter)
	// Method: countTokens()
	// Method: hasMoreTokens()
	// Method: nextToken()
	public static void stringTokenizerTokenizing(String delimiter, String source) {
		StringTokenizer st1;
		if (delimiter != "") {
			st1 = new StringTokenizer(source, delimiter);
		}
		else {
			st1 = new StringTokenizer(source);
		}
		System.out.println(st1.countTokens());
		while (st1.hasMoreTokens()) {
			System.out.println(">" + st1.nextToken() + "<");
		}
	}
	
	// Test Formatter Class
	// %[index$][flags][width][.precision]conversion
	// Flags: left justify (-), sign (+), pad with zeroes (0), thousands separation (,), enclose negative (()
	// Conversion: boolean (b), char (c), integer (d), floating point (f), string (s)
	// IllegalFormatFlagsException (Runtime): (-) and (0) not can be used together
	// IllegalFormatConversionException (Runtime): [.precision] can be used only in float type or conversion are wrong (except boolean)
	// MissingFormatWidthException (Runtime): if (-) or (0) are used, [width] must be present
	
	// Method: printf(String sources, String[] vars)
	// Method: format(String sources, String[] vars)
	public static void testFormatter() {
		System.out.println("\n\nTest Formatter");
		
		int i1 = -123;
		int i2 = 12345;
		float f1 = 123.456f;
		
		System.out.printf("> %1$d <\n", i1);
		System.out.printf("> %1$(7d <\n", i1);
		System.out.printf("> %0,7d <\n", i2);
		System.out.format("> %+-7d <\n", i2);
		System.out.format("> %2$b + %1$5d <\n", i1, false);
		System.out.format("> %1$0,10d <\n", i2);
		System.out.format("> %1$0,10.2f <\n", f1);
		System.out.format("> %0+20d <\n", i1);
		System.out.format("> %b <\n", f1);
	}
	
	// Test ResourceBundle Class
	// Rule (search): First use ListResourceBundles class and if not find it use properties file (first java file and then properties files)
	// Rule (search): Looking for properties in this order: full locale, only language, full default locale, only default language, default bundle
	// Rule (search): If not find resource bundle throws a MissingResourceException 
	// Rule (inherit): Inherit can be used in bundle, it start with more especific to more general bundle (RB_fr_CA.properties, RB_fr.properties, RB.properties)
	// Rule (inherit): First look for a resource bundle and then use that resource bundle hierarchy.
	
	// Constructor: getBundle(String filename)
	// Constructor: getBundle(String filename, Locale l)
	// Method: getString(String attr) => used only with file properties
	// Method: getObject(String attr)
	public static void testResourceBundles() {
		System.out.println("\n\nTest ResourceBundle");
		
		Locale l1 = new Locale("en");
		Locale l2 = new Locale("fr");
		Locale l3 = new Locale("en", "CA");
		Locale l4 = new Locale("en", "US");
		
		// Use default locale
		// ResourceBundle rbdf = ResourceBundle.getBundle("properties.Labels");
		
		ResourceBundle rb1 = ResourceBundle.getBundle("properties.Labels", l1);
		ResourceBundle rb2 = ResourceBundle.getBundle("properties.Labels", l2);
		ResourceBundle rb3 = ResourceBundle.getBundle("properties.Labels", l3);
		ResourceBundle rb4 = ResourceBundle.getBundle("properties.Labels", l4);
		
		System.out.println(rb1.getString("hello"));
		System.out.println(rb2.getString("hello"));
		System.out.println(rb3.getObject("hello"));
		
		System.out.println(rb1.getString("message1"));
		System.out.println(rb1.getObject("message2"));
		
		System.out.println(rb3.getString("ride.in") + " " + rb3.getObject("elevator"));
		
		System.out.println(rb4.getString("ride.in") + " " + rb4.getObject("elevator"));
	}
	
}

