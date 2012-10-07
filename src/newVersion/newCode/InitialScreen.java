package newVersion.newCode;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * This class displays the first screen of the application. The menu options displayed in the first screen are
 * "Create a new reminder"
 * "Show all Reminders"
 * @author RajatHCI
 *
 */
public class InitialScreen extends Activity {
	
	
	LinearLayout buttonOrganization;
	Intent versionNewSSUIFinalProjectActivity;
	TextView newReminder;
	TextView deleteReminder;
	TextView showReminder;
	TextView allReminders;
	
	LocationManager managerLocation;
	MyLocationListener listenerLocation;
	Geocoder geoCoder;
	ScrollView scrollView;
	LinearLayout layout, allReminderLayout;
	Button backButton;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		scrollViewFunction();
		

        managerLocation = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        listenerLocation = new MyLocationListener(this, managerLocation);
        geoCoder = new Geocoder(this,Locale.getDefault());
        managerLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,listenerLocation);
        
        // setup the layout for the initial screen
		buttonOrganization = new LinearLayout(this);
		buttonOrganization.setOrientation(LinearLayout.VERTICAL);
		
		// initialize creating a new reminder option menu
		createNewReminderMenuOption();
		
		// initialize showing all reminders option menu
		allReminders();
		setContentView(buttonOrganization);
	}
	/**
	 * This function creates the layout to show the "Create a new reminder" menu option
	 */
	public void createNewReminderMenuOption()
	{
		
		// set layout for new reminder option menu
		newReminder = new TextView(this);
		newReminder.setText("Create a new Reminder");
		newReminder.setWidth(100);
		newReminder.setHeight(100);
		newReminder.setClickable(true);
		newReminder.setTextSize(28);
		buttonOrganization.addView(newReminder);
		listenerForNewReminder();
	}
	/**
	 * This function creates the scroll view for showing all the reminders when "show all reminders" menu option is 
	 * selected
	 */
	public void scrollViewFunction()
	{
		// initialize a scroll view for showing all the reminders saved in the system
		allReminderLayout = new LinearLayout(this);
		allReminderLayout.setOrientation(LinearLayout.VERTICAL);
		
		backButton();
		scrollView = new ScrollView(this);
		scrollView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 650));
		
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(layout);
		allReminderLayout.addView(scrollView);
		allReminderLayout.addView(backButton);
		
	}
	/**
	 * This function initializes the back button to come back from viewing all the reminders to the menu screen
	 */
	public void backButton()
	{
		
		// initialize the back button to go to initial screen
		backButton = new Button(this);
		backButton.setText("Back");
		backButton.setWidth(50);
		backButton.setHeight(30);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout.removeAllViews();
				setContentView(buttonOrganization);
			}
		});
	}
	/**
	 * This function is the listener for when the new reminder menu option is selected by the user.
	 */
	public void listenerForNewReminder()
	{
		
		// listener for creating new reminder menu option
		newReminder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intentInitialization();
				InitialScreen.this.startActivity(versionNewSSUIFinalProjectActivity);
			}
		});
	}
	/**
	 * This function sets to display a reminder name that needs to be displayed while showing all reminders
	 * @param text the name of the reminder that needs to be displayed while showing all the reminders
	 * @return void
	 */
	public void createNewReminderName(String text)
	{
		
		final TextView textView = new TextView(this);
		textView.setText(text);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
		textView.setClickable(true);
		layout.addView(textView);
		
		
		// the listener code for the text
		textView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InitialScreen.this,reminderComplete.class);
				String name = (String) textView.getText().toString();
				System.out.println("the name passed is " + name);
				intent.putExtra("reminderName", name);
				InitialScreen.this.startActivity(intent);
				
			}
		});
	}
	/**
	 * this function is a listener for showing all reminders menu option. This function reads all the reminders saved
	 * in the application and creates a new scroll view which displays these clickable reminder names
	 * @return void
	 */
	public void listenerForAllReminders()
	{
		
		// listener to show all the reminders saved in the application
		allReminders.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				
				//reminderComplete.reminders();
				File file = new File("/sdcard/reminderdata/reminderNames");
				RandomAccessFile raf;
				try {
					 raf = new RandomAccessFile(file,"r");
					
					//String fileName = raf.readLine();
					
					while(true)
					{
						String fileName = "";
						char takeCharacter = raf.readChar();
						while(takeCharacter!= '\n')
						{
							fileName = fileName + takeCharacter;
							System.out.println("the file name is " + fileName);
							takeCharacter = raf.readChar();
						}
						createNewReminderName(fileName);
					}
						
				}
				catch(EOFException e)
				{
					System.out.println("eof file reached");
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setContentView(allReminderLayout);
				
			}
		});
	}
	
	/**
	 * This function sets layout for displaying menu option "All Reminders" in the initial screen
	 */
	public void allReminders()
	{
		allReminders = new TextView(this);
		allReminders.setText("Show all reminders");
		allReminders.setWidth(100);
		allReminders.setHeight(50);
		allReminders.setTextSize(28);
		buttonOrganization.addView(allReminders);
		listenerForAllReminders();
	}
	/**
	 * This function is to start a new intent to select a location for setting a reminder
	 * @return void
	 */
	public void intentInitialization()
	{
		versionNewSSUIFinalProjectActivity = new Intent(this,MapScreen.class);
	}
	
	/**
	 * This function is used to show a single reminder. This function is not used in the code. It was developed to debug 
	 * and help developing the code.
	 * @return void
	 */
	public void listenerForShowReminder()
	{
		showReminder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//callReminderComplete();
				Intent intent = new Intent(InitialScreen.this,reminderComplete.class);
				InitialScreen.this.startActivity(intent);
				
			}
		});
	}
}
