package newVersion.newCode;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * This class shows the reminder when a reminder is selected to be viewed or the when the gps invokes a reminder to be 
 * displayed
 * @author RajatHCI
 *
 */
public class reminderComplete extends Activity {

	TextView title;
	TextView content;
	Button back;
	TextView date;
	ImageView image;
	Context context;
	LocationReminder reminder;
	LinearLayout layout;
	static Vector<String> filenames = new Vector();
	
	
	/**
	 * This function initializes the layout for the reminder.
	 */
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		title = new TextView(this);
		content = new TextView(this);
		back = new Button(this);
		date = new TextView(this);
		image = new ImageView(this);
		layout = new LinearLayout(this);
		MediaPlayer ping = new MediaPlayer();
		try {
			ping.setDataSource("/sdcard/tring.mp3");
			ping.prepare();
			ping.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backButton();
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(title);
		layout.addView(content);
		layout.addView(date);
		layout.addView(image);
		layout.addView(back);
		setContentsOfReminder();
		setContentView(layout);
		
	}
	
	/**
	 * This function shows all the reminders. This function is not used in the code, and was writted to help in
	 * writing the final code of the application.
	 */
	public static void reminders()
	{
		ObjectInputStream file= null;
		try {
			file = new ObjectInputStream(new BufferedInputStream (new FileInputStream("/sdcard/reminderDatabase")));
			while(true)
				filenames.add(((LocationReminder)file.readObject()).getTitle().toString());
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(EOFException e)
		{
			
			try {
				//file.reset();
				file.close();
				System.out.println("all data has been read");
				for (int counter=0; counter < filenames.size(); counter = counter + 1)
				{
					System.out.println("the value of counter is " + counter);
					System.out.println("title is " + filenames.get(counter));
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * This function is used to add scrolling capability to the title of the reminder if the text doesnot fit the 
	 * box of the reminder title box.
	 */
	public void textBoxes()
	{
		title.setScroller(new Scroller(this));
		title.setMaxLines(3);
		title.setVerticalScrollBarEnabled(true);
		title.setMovementMethod(new ScrollingMovementMethod());
	}
	/**
	 * This function gets the data from the database for the reminder to be displayed. The data includes the title,
	 * the body of the reminder, the date and the photo of the reminder.
	 */
	public void setContentsOfReminder()
	{
		System.out.println("reading the function");
		Bundle extras = getIntent().getExtras();
		//FileInputStream file = new FileInputStream();
		//File fileName = new File(context.getExternalFilesDir(null), "reminderDatabase");
		try {
			System.out.println("the file name is " + extras.getString("reminderName"));
			ObjectInputStream file = new ObjectInputStream(new BufferedInputStream (new FileInputStream("/sdcard/reminderdata/" + extras.getString("reminderName"))));
			reminder = (LocationReminder) file.readObject();
			if (reminder == null)
				System.out.println("reminder is null");
			System.out.println("data retrieved from the file after reading from sd card is " + reminder.getTitle());
			
			title.setText(reminder.getTitle());
			content.setText(reminder.getContent().toString());
			//date.setText(reminder.getDate().toString());
			image.setImageBitmap(readTheFileStored(reminder.getTitle()));
			if (reminder.getDate() == null)
				System.out.println("date is null");
			date.setText(reminder.getDate().getDate() + " " + DateUtils.getMonthString(reminder.getDate().getMonth(), DateUtils.LENGTH_LONG) + " " + reminder.getDate().getYear());
			System.out.println("the date text is " + date.getText());
			/*
			Toast showDate = Toast.makeText(this, date.getText(), Toast.LENGTH_LONG);
			showDate.show();
			*/
			file.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This function reads the image stored for the reminder
	 * @param reminderTitle the name of the reminder whose image is to be loaded
	 * @return the bitmap of the image for the reminder
	 */
	public Bitmap readTheFileStored(String reminderTitle)
	{
		System.gc();
		Bitmap bmap=null, obmap;
    	Bitmap cameraPhotoBitmap= null;
    	BufferedInputStream fos= null;
    	try {
			fos = new BufferedInputStream( new FileInputStream("/sdcard/reminderdata/picture"+reminderTitle+"_image"));
			bmap = BitmapFactory.decodeStream(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	int newHeight = 420;
        float scale = ((float)bmap.getWidth())/((float)bmap.getHeight());
        float newWidth = 420*scale;
        float scaleWidth = (newWidth) /bmap.getWidth();
        float scaleHeight = ((float) newHeight) /bmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(90);
        obmap = Bitmap.createBitmap(bmap, 0, 0, bmap.getWidth(), bmap.getHeight(), matrix, true);
        bmap.recycle();
        System.gc();
		return obmap;
	}
	/**
	 * Initialize the back button for the layout and also initialize the listener for this button.
	 */
	public void backButton()
	{
		back.setText("Back");
		back.setHeight(30);
		back.setWidth(50);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
}
