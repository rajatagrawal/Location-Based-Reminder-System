package newVersion.newCode;

import java.io.Serializable;
import java.util.Date;

import android.widget.ImageView;

/**
 * This class stores the data for the reminder. It contains all the data types to store the reminder data.
 * @author RajatHCI
 *
 */
public class LocationReminder implements Serializable {
	
	int longitude;
	int latitude;
	String title;
	String content;
	String ReminderPhoto;
	Date ReminderDate;
	/**
	 * this is the constructor to initialize the values of the reminder variables
	 * @param paramLongitude the longitude of the location of the reminder
	 * @param paramLatitude the latitude of the location of the reminder
	 * @param paramTitle the title of the reminder
	 * @param paramContent the contents of the reminder
	 * @param parameterImageView the image of the reminder
	 * @param parameterDate the date for the reminder
	 */
	public LocationReminder(int paramLongitude, int paramLatitude, String paramTitle, String paramContent, String parameterImageView, Date parameterDate)
	{
		this.longitude = paramLongitude;
		this.latitude = paramLatitude;
		this.title = paramTitle;
		this.content = paramContent;
		this.ReminderPhoto = parameterImageView;
		this.ReminderDate = parameterDate;
	}
	/**
	 * This is the set method to set the image url for the image of the reminder.
	 * @param parameterImageView the url of the image of the reminder
	 */
	public void setImageView(String parameterImageView)
	{
		this.ReminderPhoto = parameterImageView;
	}
	/**
	 * this is the get method to get the url of the image of the reminder
	 * @return the url of the image of the reminder.
	 */
	public String getImageView()
	{
		return this.ReminderPhoto;
	}
	/**
	 * The set method to set the date of the reminder.
	 * @param parameterDate the date to be set for the reminder
	 */
	public void setDate(Date parameterDate)
	{
		this.ReminderDate = parameterDate;
	}
	/**
	 * The method to return the date of the reminder
	 * @return the date of the reminder.
	 */
	public Date getDate()
	{
		return this.ReminderDate;
	}
	/**
	 * The get method to get the latitude for the reminder
	 * @return the latitude of the reminder
	 */
	public int getLatitude()
	{
		return this.latitude;
	}
	
	/**
	 * the get method to get the longitude of the reminder
	 * 
	 * @return the longitude of the reminder
	 */
	public int getLongitude()
	{
		return this.longitude;
	}
	/**
	 * The get method to get the title of the reminder.
	 * @return the title of the reminder
	 */
	public String getTitle()
	{
		return this.title;
	}
	
	/**
	 * The get method to get the body of the reminder
	 * @return the body string variable of the reminder.
	 */
	public String getContent()
	{
		return this.content;
	}
	
	/**
	 * The function to set the longitude for the reminder
	 * @param newLongitudeValue the value to set for the longitude of the reminder.
	 */
	public void setLongitude(int newLongitudeValue)
	{
		this.longitude = newLongitudeValue;
	}
	
	/**
	 * The set function to set the latitude of the reminder
	 * @param newLatitudeValue the value of the latitude to set for the reminder.
	 */
	public void setLatitude(int newLatitudeValue)
	{
		this.latitude = newLatitudeValue;
	}
	
	/**
	 * The set function to set the title of the reminder.
	 * @param newTitleValue the title to set for the reminder.
	 */
	public void setTitle(String newTitleValue)
	{
		this.title = newTitleValue;
	}
	
	/**
	 * The set function to set the body of the reminder
	 * @param newContentValue the string to set for the body of the reminder.
	 */
	public void setContent(String newContentValue)
	{
		this.content = newContentValue;
	}
}
