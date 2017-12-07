package otrack.util;

import java.sql.ResultSet;
import java.util.ArrayList;

import otrack.core.AppException;
import otrack.dao.GenericDAO;
import otrack.core.AppObject;
/**
 * @author smcafee
 * @objectDescription This Object contains methods for manipulating name strings.
 * 
 * @objectType Utility object
 * 
 * @Created 2/13/2010
 * @artf 1560
 * @version 1.0
 * 
 */
public class NameUtil {
	/**
	 * @Description Specifies allowed name order types.
	 * @order LFM = Last name, First name Middle name
	 * @order FML = First name Middle name Last name 
	 */
	public enum NameOrderTypes {LFM, FML}

	/*
	 * Specifies known name suffixes.
	 */
	private static ArrayList<String> getNameSuffixes(){
		ArrayList<String> alNameSuffixes = new ArrayList<String>();
		alNameSuffixes.add("JR");
		alNameSuffixes.add("SR");
		alNameSuffixes.add("II");
		alNameSuffixes.add("III");
		alNameSuffixes.add("IV");
		alNameSuffixes.add("V");
		alNameSuffixes.add("PHD");
		return alNameSuffixes;
	}
	
	/**
	 * @param String name - The original name
	 * @return String
	 * 
	 * This method returns a properly capitalized name string.  
	 * Input String = "LAST, FIRST MIDDLE"
	 * Input String = "Last, First Middle"
	 * 
	 * This method assumes you want secondary capitals in last names. 
	 * This method assumes you sent the name in "Last, First Middle" order 
	 *   and want the return in "Last, First Middle" order.
	 * If the method throws an exception it simply returns the original input.
	 * 
	 */
	public static synchronized String getCapitalizedName(String name){
		return getCapitalizedName(name,NameOrderTypes.LFM, NameOrderTypes.LFM, true);
	}
	/**
	 * @param String name - The original name
	 * @param boolean secCaps
	 * 		Send true if you want to check the name for secondary capitalization
	 * 		Used for various Scot or Irish Names 
	 * @return String
	 * 
	 * This method returns a properly capitalized name string.  
	 * Input String = "LAST, FIRST MIDDLE"
	 * Input String = "Last, First Middle"
	 * 
	 * This method assumes you sent the name in "Last, First Middle" order 
	 *   and want the return in "Last, First Middle" order.
	 * If the method throws an exception it simply returns the original input.
	 * 
	 */

	public static synchronized String getCapitalizedName(String name, boolean secCaps){
		return getCapitalizedName(name,NameOrderTypes.LFM, NameOrderTypes.LFM, secCaps);
	}
	/**
	 * @param String name - The original name
	 * @param NameOrderTypes inNameOrderType - 
	 * 		Send "NameUtil.NameOrderTypes.LFM" if you are sending the name as Last, First Middle 
	 * 		Send "NameUtil.NameOrderTypes.FML" if you are sending the name as First Middle Last.
	 * @param NameOrderTypes outNameOrderType - 
	 * 		Send "NameUtil.NameOrderTypes.LFM" if you want the return as Last, First Middle 
	 * 		Send "NameUtil.NameOrderTypes.FML" if you want the return as First Middle Last.
	 * @return String
	 * 
	 * This method returns a properly capitalized name string.  
	 * Input String = "LAST, FIRST MIDDLE"
	 * Input String = "Last, First Middle"
	 * 
	 * This method assumes you want secondary capitals in last names. 
	 * If the method throws an exception it simply returns the original input.
	 * 
	 */

	public static synchronized String getCapitalizedName(String name, NameOrderTypes inNameOrderType, NameOrderTypes outNameOrderType){
		return getCapitalizedName(name,inNameOrderType, outNameOrderType, true);
	}
	/**
	 * @param String name - The original name
	 * @param NameOrderTypes inNameOrderType - 
	 * 		Send "NameUtil.NameOrderTypes.LFM" if you are sending the name as Last, First Middle 
	 * 		Send "NameUtil.NameOrderTypes.FML" if you are sending the name as First Middle Last.
	 * @param NameOrderTypes outNameOrderType - 
	 * 		Send "NameUtil.NameOrderTypes.LFM" if you want the return as Last, First Middle 
	 * 		Send "NameUtil.NameOrderTypes.FML" if you want the return as First Middle Last.
	 * @param boolean secCaps
	 * 		Send true if you want to check the name for secondary capitalization
	 * 		Used for various Scot or Irish Names 
	 * @return String
	 * 
	 * This method returns a properly capitalized name string.  
	 * Input String = "LAST, FIRST MIDDLE"
	 * Input String = "Last, First Middle"
	 * 
	 * If the method throws an exception it simply returns the original input.
	 * 
	 */
	public static synchronized String getCapitalizedName(String name, NameOrderTypes inNameOrderType, NameOrderTypes outNameOrderType, boolean secCaps){
		String newName = null;
		try{
			String[] arrRaw = expName(name);
			String rawName = arrRaw[0].trim();
			String suffix = arrRaw[1];
			if(inNameOrderType.equals(NameOrderTypes.LFM) && outNameOrderType.equals(NameOrderTypes.LFM)){
				newName = formatNameLastToLast(rawName, secCaps);
			}else if(inNameOrderType.equals(NameOrderTypes.LFM) && outNameOrderType.equals(NameOrderTypes.FML)){
				newName = formatNameLastToFirst(rawName, secCaps);
			}else if(inNameOrderType.equals(NameOrderTypes.FML) && outNameOrderType.equals(NameOrderTypes.LFM)){
				newName = formatNameFirstToLast(rawName, secCaps);
			}else if(inNameOrderType.equals(NameOrderTypes.FML) && outNameOrderType.equals(NameOrderTypes.FML)){
				newName = formatNameFirstToFirst(rawName, secCaps);
			}
			newName = newName + " " + suffix;
		}catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		return newName;
	}
	
	/*
	 * This method takes in the whole name string and checks to see if there is a suffix on the end
	 * if so, it send the suffix back as the second element of an array.
	 * 
	 * It checks if the suffix is in the NameSuffixes emun.
	 * 
	 * Modified smcafee, artf 1371, atch 1864, item 7
	 */
	private static String[] expName(String name){
		String[] arrReturn = new String[2];
		String rawName = name;
		String suffix = "";
		String formattedName = null;
		try{
			//Added smcafee, 2/8/2010, artf 1371, atch 1869, item 4
			int commaLoc = -1;
			commaLoc = rawName.indexOf(",");
			if(commaLoc > -1){
				if(!rawName.substring(commaLoc, commaLoc+2).equals(", ")){
					formattedName = rawName.replace(",", ", ");
				} else {
					formattedName = rawName;
				}
			}
			//End add
			String[] arrNames = formattedName.split(" ", 4);
			
			ArrayList suffixes = getNameSuffixes();
	    	if(suffixes.contains(arrNames[arrNames.length-1].trim().toUpperCase())){
	    			formattedName = formattedName.substring(0, formattedName.indexOf(arrNames[arrNames.length-1]));
					suffix = arrNames[arrNames.length-1];
			    }
		    arrReturn[0] = formattedName;
		    arrReturn[1] = suffix;
		}catch(Exception e){
		}
	    return arrReturn;
	}

	/*
	 * This method takes in a LFM formatted name and returns a LFM name.
	 */
	private static String formatNameLastToLast(String name, boolean secCaps){
		String newName = null;
		try{
			String[] arrNames = name.split(" ", 3);
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < arrNames.length; i++){
	        	if(secCaps){
		        	if(i == 0 && arrNames[i].length() > 2){
		        		int secCapPlace = getSecondaryCapitalIndex(arrNames[i]);
		        		
			        	if(secCapPlace > 0){
			                sb.append(formatNameSec(arrNames[i].toLowerCase(), secCapPlace));
			        	} else {
			                sb.append(formatName(arrNames[i].toLowerCase()));
			        	}
		        	} else {
		                sb.append(formatName(arrNames[i].toLowerCase()));
		        	}
		        } else {
	                sb.append(formatName(arrNames[i].toLowerCase()));
	        	}
	        }
			newName = sb.toString().trim();
		}catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		return newName;
	}
	
	/*
	 * This method takes in a LFM formatted name and returns a FML name.
	 */
	private static String formatNameLastToFirst(String name, boolean secCaps){
		String newName = null;
		try{
			String[] arrNames = name.split(" ", 3);
			String lastHolder = null;
			StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < arrNames.length; i++){
	        	if(i == 0 && arrNames[i].length() > 2 && arrNames[i].substring(0, 2).toUpperCase().equals("MC")){
	        		lastHolder = formatNameSec(arrNames[i].toLowerCase(), 3);
	        	} else if(i == 0){
	        		lastHolder = formatName(arrNames[i].toLowerCase());
	        	} else {
	        		sb.append(formatName(arrNames[i].toLowerCase()));
	        	}
	        }
	        sb.append(lastHolder.replace(",", ""));
			newName = sb.toString().trim();
		}catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		return newName;
	}

	/*
	 * This method takes in a FML formatted name and returns a LFM name.
	 */
	private static String formatNameFirstToLast(String name, boolean secCaps){
		String newName = null;
		StringBuffer working = new StringBuffer();
		try{
			String[] arrNames = name.split(" ", 3);
			int arrLen = arrNames.length;
			if(arrLen >= 3){
				String lastHolder = arrNames[2].replaceAll(",", "");
				arrNames[2] = arrNames[1];
				arrNames[1] = arrNames[0];
				arrNames[0] = lastHolder + ",";
			} else if(arrLen == 2){
				String lastHolder = arrNames[1].replaceAll(",", "");
				arrNames[1] = arrNames[0];
				arrNames[0] = lastHolder + ",";
			}
	        for(int j = 0; j < arrNames.length; j++){
	        	working.append(arrNames[j].trim() + " ");
	        }
			newName = formatNameLastToLast(working.toString(), secCaps);
		}catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		return newName;
	}
	
	/*
	 * This method takes in a FML formatted name and returns a FML name.
	 */
	private static String formatNameFirstToFirst(String name, boolean secCaps){
		String newName = null;
		try{
			String[] arrNames = name.split(" ", 3);
			StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < arrNames.length; i++){
	        	if(i != 0 && arrNames[i].length() > 2 && arrNames[i].substring(0, 2).toUpperCase().equals("MC")){
	        		sb.append(formatNameSec(arrNames[i].toLowerCase(), 3));
	        	} else {
	        		sb.append(formatName(arrNames[i].toLowerCase()));
	        	}
	        }
			newName = sb.toString().trim();
		}catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		return newName;
	}
	
	/*
	 * This method capitalizes the first letter of a string.
	 */
	private static String formatName(String name){
		String newName = null;
		try{
			String newStr = name.toLowerCase();
			String firstLetter = newStr.substring(0,1);
			newName = newStr.replaceFirst(firstLetter, firstLetter.toUpperCase()) + " ";
		} catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		
		return newName;
	}

	/*
	 * This method capitalizes the first letter of a string.
	 * It also capitalizes the letter in the place specified by the capPlace integer
	 */
	private static String formatNameSec(String name, int capPlace){
		String newName = null;
		try{
    		String str1 = name.substring(0, capPlace - 1).toLowerCase();
    		String str2 = name.substring(capPlace - 1).toLowerCase();
    		String firstLetter = str1.substring(0,1);
    		String nextLetter = str2.substring(0,1);
    		String newStr = 
    			str1.replaceFirst(firstLetter, firstLetter.toUpperCase()) 
    			+ str2.replaceFirst(nextLetter, nextLetter.toUpperCase());
    		newName = newStr + " ";
		} catch(Exception e){
			e.printStackTrace();
			newName = name;
		}
		
		return newName;
	}

	/*
	 * This method returns the index of the secondary letter to be capitalized
	 */
	private static int getSecondaryCapitalIndex(String name){
		int capPlaceReturn = 0;
		if(name.substring(0, 2).toUpperCase().equals("MC")){
			capPlaceReturn = 3;
//		} else if(name.substring(0, 2).toUpperCase().equals("MAC")){
//			capPlaceReturn = 4;
		} else if(name.substring(0, 2).toUpperCase().equals("O'")){
			capPlaceReturn = 3;
		} else if(name.substring(0, 2).toUpperCase().equals("D'")){
			capPlaceReturn = 3;
//		} else if(name.substring(0, 2).toUpperCase().equals("DE")){
//			capPlaceReturn = 3;
		}
		return capPlaceReturn;
	}

}
