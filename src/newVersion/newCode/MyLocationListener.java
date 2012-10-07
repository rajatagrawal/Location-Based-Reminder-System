package newVersion.newCode;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * This is the GPS class that listens for the current location of the user and calls the function when the GPS
 * receives the user location. The function tests if the user location matches any of the location saved in the
 * reminders by the user.
 * @author RajatHCI
 *
 */
public class MyLocationListener implements LocationListener {

	double latitude;
	double longitude;
	Intent intent;
	Context context;
	Vector<LocationReminder> savedLocations;
	File file;
	LocationManager locManager;
	RandomAccessFile raf;
	public MyLocationListener(Context parameterContext, LocationManager locManager)
	{
		context = parameterContext;
		savedLocations = new Vector();
		this.locManager = locManager;
		//savedLocations = parameterSavedLocations;
		
	}
	/**
	 * This function is called whenever the GPS detects the location of the user. This function also checks 
	 * if the user location matches any of the locations of the reminders set by the user. Incase the location matches,
	 * the function invokes the reminder view for that reminder.
	 */
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		System.out.println("the listener function is getting executed");
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		CharSequence locationChangedText = " latitude is " + latitude + " longitude is " + longitude; 
		Toast ToastUpdate = Toast.makeText(context, locationChangedText, Toast.LENGTH_LONG);
		//ToastUpdate.show();
		System.out.println("the value of the mobile latitute and longitude is " + latitude + " " + longitude);
		file = new File("/sdcard/reminderdata/reminderNames");
		try {
			raf = new RandomAccessFile(file,"rw");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Toast t = Toast.makeText(context, "opened the file ", Toast.LENGTH_LONG);
			
			while(true)
			{
				
				String filename = "";
				char c= raf.readChar();
				while(c != '\n')
				{
					filename = filename + c;
					c = raf.readChar();
				}
				t = Toast.makeText(context,"the filename read is " + filename , Toast.LENGTH_LONG);
				t.show();
				System.out.println("the filename read is " + filename);
				ObjectInputStream reminderFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("/sdcard/reminderdata/" + filename)));
				LocationReminder obj = (LocationReminder) reminderFile.readObject();
				savedLocations.add(obj);
				t = Toast.makeText(context, "the location read is " + obj.getTitle(), Toast.LENGTH_LONG);
				System.out.println("the location read is " + obj.getTitle());
			}
		}
		catch(EOFException e)
		{
			try {
				raf.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		System.out.println("the size of saved locations is " + savedLocations.size());
		Toast sizeOfSavedLocation = Toast.makeText(context, "the size of saved locations is " + savedLocations.size(), Toast.LENGTH_LONG);
		sizeOfSavedLocation.show();
		*/
		System.out.println("the size of locations is " + savedLocations.size());
		for (int counter=0; counter < savedLocations.size(); counter = counter + 1)
		{
			System.out.println("the size of counter is " + counter);
			if (savedLocations.get(counter).getLatitude() > (this.latitude - 4) || savedLocations.get(counter).getLatitude() > (this.latitude + 4) )
			{
				System.out.println("inside outer if" + savedLocations.get(counter).getTitle() + " name of the title" );
				Toast toast = Toast.makeText(context, "inside outer loop", Toast.LENGTH_LONG);
				if (savedLocations.get(counter).getLongitude() > (this.longitude - 4) || savedLocations.get(counter).getLongitude() < (this.longitude + 4) )
				{	
					System.out.println("current time is " + new Date().toString() + "");
					System.out.println("location time is " + savedLocations.get(counter).getDate().getDay() + " " + savedLocations.get(counter).getDate().getMonth());
					if (savedLocations.get(counter).getDate().getDate() == new Date().getDate() && savedLocations.get(counter).getDate().getMonth() == new Date().getMonth())
					{
						Toast to = Toast.makeText(context, "inside inner loop", Toast.LENGTH_LONG);
						locManager.removeUpdates(this);
						Toast reachedTheLocation = Toast.makeText(context, "You have reached the location", Toast.LENGTH_LONG);
						intent = new Intent();
						intent.setClass(context, reminderComplete.class);
						intent.putExtra("reminderName", savedLocations.get(counter).getTitle());
						context.startActivity(intent);
					}
					
					
				}
			}
		}
	}

	/**
	 * This function is called whenever the GPS is disabled
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
		CharSequence message = " gps has been turned off";
		System.out.println("gps has been turned off");
		Toast disabledToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		disabledToast.show();

	}

	/**
	 * This function is called whenever the GPS is enabled.
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
