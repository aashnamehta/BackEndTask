import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/* Assumptions
 * Q1-----number of unique users refers to the number of unique userIDs in the CSV file  
   Q2-----total sale refers to the sum of prices of all the items 
   Q3-----total sales per itemID refers to the sum of prices of items displayed according to their itemID
   Q4-----number of purchases per hour refers to the number of(count of) items purchased per hour 
   */

/* Logic used:
 * Initially the CSV file was read
 * While reading the the userID and itemID were stored in no_dup_item_id and no_dup_user_id HashSet respectively .
 * As the HashSet only includes unique elements(no repetition) ,
 * Q1---is calculated  by taking out the size of no_dup_user_id HashSet would give the unique records in the file
 * Also,The no_dup_item_id would include all the unique itemID which would help in
 * Q3---is calculated by the summing up the  prices per itemID which is calculated by iterating each element of no_dup_item_id HashSet 
 * and matching the itemID from the ArrayList and adding the prices of items whose item id matches
 * Q2---is calculated by summing up prices of all the items while reading from the file 
 * Q4---- matching the substring eg:" 00:00" ," 01;00" etc. from  date-time in ArrayList using .contains() function  
 * Kindly Note:  As hour has a space before the digit inside the CSV file and the ArrayList
 * Hence, While matching the hour " 00:00" the number will not mix up with minutes or seconds value due to the space before hour
   eg: "22/12/2016 00:20:45" has a space before the hour*/


public class readACsvFileAndCompute {
  /*HashSet only takes in unique elements and automatically removes the repeating ones 
  * Insertion and Retrieval Time complexity is O(1)
  */
     public static HashSet noDuplicateUserId= new HashSet(); /*To count total no of unique record-----Q1*/  
     public static HashSet noDuplicateItemId= new HashSet(); /*To find the unique itemID------Q3 */
     public static int totalSales;/*To find total sales i.e. the sum of all the items purchased*/
     public static int count;/*To count the total number of records*/
   
     public static List<dailyRecord> purchaseItemList= new ArrayList<>();/*An array list that contains all the records
                                                                       Each record is an object of class Daily_Record*/ 
     public static void main(String[] args) 
         {
	         readFile(); /*reading from a CSV file*/
	         System.out.println("Number of unique users: " + noDuplicateUserId.size());
	         System.out.println("Total sales: "+ totalSales);
	         totalSalesPerItem();/*calculating the total sales per item */
	         totalSalesPerHour();/*calculating total number of purchases per hour*/	
         }  
   
   static void readFile()
        {    
	       BufferedReader reader = null;
		  
		   int dontIncludeBeginningOfRecord = 0;//To avoid including the first record which includes the title i.e. User_ID,date,item_ID and price
		   try {
			       reader = new BufferedReader(new FileReader("purchases-2018-01-01.csv"));//Reading elements from a given CSV file 
		        } 
		   catch (FileNotFoundException e)
		      {
			    // TODO Auto-generated catch block
			        e.printStackTrace();
		      }

		// Reading from a file line by line
		   String line = null;
		   Scanner scanner = null;
		   int index = 0;
		   String id="";
		   try {
			      while ((line = reader.readLine()) != null) //reading one line from a CSV file*/
			         {
				           dailyRecord rec = new dailyRecord();
				           scanner = new Scanner(line);
				           scanner.useDelimiter(",");//the columns are separated by a comma in a CSV file */
				           while (scanner.hasNext()) {
					              String data=scanner.next();//takes in the first element of the line read by reader.readline()
					              if (index == 0)
						             rec.setUserId(data);//reads userID and stores it in the userID of ArrayList
					              else if (index == 1)
						             rec.setDate(data);//reads Date and stores it in the Date of the ArrayList
					              else if (index == 2)
						             rec.setItemID(data);//reads itemID and stores it in the ArrayList
					              else if (index == 3)
						             rec.setPrice(data);//reads price and stores it in the ArrayList
				                  else
						              System.out.println("Invalid Data");
					              index++;
					         }
                           index = 0;
				    	if(dontIncludeBeginningOfRecord ==1) 
		 		              {
				    		      count++;
		 	                      purchaseItemList.add(rec); 
		 		                   try {
		 		                            String s1= trimDoubleQuotes(rec.getPrice());//string stored in the array list has additional 
		 		                                                                        //  quotation marks so it has been removed by this funtion
					                        totalSales = totalSales+Integer.parseInt(s1);
				                       } 
		 		                   catch (Exception e) {
				                        	// TODO Auto-generated catch block
					                        e.printStackTrace();
				                        }
		 		                   noDuplicateUserId.add(rec.getUserId());//All the unique usedID's are stored in a HashSet  
		 	                   	   noDuplicateItemId.add(rec.getItemID());//All the unique itemID's are stored in a HashSet
		 	                   }
			                       dontIncludeBeginningOfRecord = 1;
			                       //avoids including the first record which is the title
				              
			         
			                   }
			      
	    } catch (NumberFormatException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//close reader
		try {
			       reader.close();
		    } 
		  catch (IOException e)
		      {
			     // TODO Auto-generated catch block
			      e.printStackTrace();
	          }
		}
   
   static void totalSalesPerItem()
   {
	   String itemId="";
       String itemIdCopy="";
       Iterator it = noDuplicateItemId.iterator();
       int salesPerItem=0;
	   System.out.print("Total sales per itemID: ");
	   int commaOrNot=0;
	   while(it.hasNext())//traversing the HashSet for the unique itemID's 
	     {  itemId = (String) it.next();
		    itemIdCopy = itemId;//creating a copy of the String for altering and traversing it
       		for(int scanArrList=0;scanArrList<count;scanArrList++) 
		         { 
			        if(itemIdCopy.equals(purchaseItemList.get(scanArrList).getItemID())) 
			            { 
				          
				            String altered_string= trimDoubleQuotes(purchaseItemList.get(scanArrList).getPrice());//quotation marks in the string are removed
				            salesPerItem=salesPerItem+Integer.parseInt(altered_string);
				        }	
			
		         }
            commaOrNot++;//The output should have comma after the initial and intermediate records but not the last record
       
           if(commaOrNot==noDuplicateItemId.size()) 
              {
       	          System.out.print(itemIdCopy+": "+salesPerItem);
               }
           else
              {
       	          System.out.print(itemIdCopy+": "+salesPerItem+",");
              }
	  
	   
	    }
	 
    }
   
   static void totalSalesPerHour() 
      {    
	       int countNumberofRecords = 0;//counting the number of records to be displayed at the end
           int timeIterator = 0;//iterating from 00:00 till 23:00
           System.out.println();
           int  commaOrNot = 0;
           System.out.print("Number of purchases per hour:");
           while(timeIterator<24)
              { 
        	     String convertToString="";//used for converting the time iterator into string
                 if(timeIterator<10) 
                       { 
                	       convertToString=" 0"+timeIterator+":";//for time from 00:00  to 09:00
                	   }
                 else
                       {
                	       convertToString=" "+timeIterator+":";//for time from 10:00 to 24:00
                       }
                 for(int scanArrList=0;scanArrList<count;scanArrList++) 
                       {
                             
                	         if(purchaseItemList.get(scanArrList).getDate().contains(convertToString))
                                {
                                     countNumberofRecords++;//calculating the number of purchases per hour
                                	
                                 }
                       }
                 timeIterator++;
                 commaOrNot++;
                 if(commaOrNot==24) 
                       {
	                        System.out.print(convertToString+"00: "+countNumberofRecords);//if it is the last record do not insert comma 
                       }
                 else
                       {
                	         System.out.print(convertToString+"00: "+countNumberofRecords+", ");//else insert comma for beginning nad end records
                	   }
                 countNumberofRecords=0;
                }
	   
       }
       /*This function is used for trimming the strings by removing the quotation marks from the string*/
       public static String trimDoubleQuotes(String text) 
           {
	            int textLength = text.length();
	            if (textLength >= 2 && text.charAt(0) == '"' && text.charAt(textLength - 1) == '"') 
	            {
	                  return text.substring(1, textLength - 1);
	            }
                return text;
	      }
     
}
 