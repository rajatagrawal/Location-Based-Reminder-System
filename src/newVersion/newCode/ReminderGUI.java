package newVersion.newCode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the layout class for setting the details of the reminder. It also contains the action function for the 
 * layout elements in the layout.
 * @author RajatHCI
 *
 */
public class ReminderGUI extends Activity {

	Button saveButton,cameraButton,cancelButton,setupDate;
	TextView actualReminderText,titleEditText;
	RelativeLayout relativeLayout;
	Date date;
	//ImageViewExtension image;
	ImageView image;
	TextView dateText;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		date = new Date();
		setupGUI();
		setContentView(relativeLayout);
		
	}
	
	/**
	 * This function setups the layout of all the GUI elements that are present in the layout
	 */
	public void setupGUI()
	{
		//relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);
    	relativeLayout = new RelativeLayout(this);
    	//final CameraSurfaceView camera = new CameraSurfaceView(getApplicationContext());
    	//setContentView(camera);
    	

    	saveButton = new Button(this);
    	saveButton.setText("Save");
    	saveButton.setId(6);
    	saveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    	saveButtonListener();
    	
    	//image = new ImageViewExtension(this,returnABitmap());
    	image = new ImageView(this);
    	image.setId(3);
       	cameraButton = new Button(this);
    	cameraButton.setText("Take a photo");
    	cameraButton.setId(4);
    	cameraButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    	cameraButtonListener();
    	
    	dateText = new TextView(this);
    	dateText.setText("No Date Selected");
    	dateText.setId(8);
    	
    	
    	//cameraCaptureButton = (Button ) findViewById(R.id.cameraCaptureButton);
    	//cameraCaptureButtonListener();
    	
    	//cancelButton = (Button) findViewById(R.id.cancel);
    	cancelButton = new Button(this);
    	cancelButton.setText("Cancel");
    	cancelButton.setId(7);
    	cancelButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    	cancelButtonListener();
    	
        titleEditText = new EditText(this);
        titleEditText.setText("Enter your reminder Title Here");
        titleEditText.setScroller(new Scroller(this));
        titleEditText.setMaxLines(1);
        titleEditText.setVerticalScrollBarEnabled(true);
        titleEditText.setMovementMethod(new ScrollingMovementMethod());
        titleEditText.setId(1);
        titleEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listenerForReminderTitleText();
        
        //actualReminderText = (EditText) findViewById(R.id.actualReminderText);
        
        actualReminderText = new EditText(this);
        actualReminderText.setText("Enter your reminder body here");
        actualReminderText.setScroller(new Scroller(this));
        actualReminderText.setMaxLines(3);
        actualReminderText.setVerticalScrollBarEnabled(true);
        actualReminderText.setMovementMethod(new ScrollingMovementMethod());
        actualReminderText.setId(2);
        listenerForReminderBodyText();
        
        //setupDate = (Button) findViewById(R.id.setDate);
        setupDate = new Button(this);
        setupDate.setText("Select Date");
        setupDate.setId(5);
        setupDate.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setupDateFunction();
        
        relativeLayout.addView(titleEditText);
        
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,180);
        layoutParams.addRule(RelativeLayout.BELOW, titleEditText.getId());
        relativeLayout.addView(actualReminderText, layoutParams);
        layoutParams = null;
        
        
    	layoutParams = new RelativeLayout.LayoutParams(300, 300);
    	layoutParams.addRule(RelativeLayout.BELOW, actualReminderText.getId());
    	relativeLayout.addView(image,layoutParams);
    	//image.getBitmap();
    	layoutParams = null;
    	
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, image.getId());
        relativeLayout.addView(dateText, layoutParams);
        
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, dateText.getId());
        relativeLayout.addView(cameraButton, layoutParams);
        layoutParams = null;
        
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, cameraButton.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_TOP, cameraButton.getId());
        relativeLayout.addView(setupDate, layoutParams);
        layoutParams = null;
        

        
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, cameraButton.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_LEFT, cameraButton.getId());
        relativeLayout.addView(saveButton, layoutParams);
        layoutParams = null;
        
        
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.RIGHT_OF, saveButton.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_TOP, saveButton.getId());
        relativeLayout.addView(cancelButton, layoutParams);
    	layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    	layoutParams = null;
    	
    	
    	
    	
		
	}
	/**
	 * This function rotates the image recevied from the camera by 90 degrees and rescales it to fit inside the layout
	 * @return void
	 */
	public void iconImage()
	{
		Bitmap bmap= null;
		Bitmap obmap;
		try {
			BufferedInputStream file = new BufferedInputStream(new FileInputStream("/sdcard/reminderdata/picture"));
			bmap = BitmapFactory.decodeStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int newHeight = 100;
        float scale = ((float)bmap.getWidth())/((float)bmap.getHeight());
        float newWidth = 100*scale;
        float scaleWidth = (newWidth) /bmap.getWidth();
        float scaleHeight = ((float) newHeight) /bmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(90);
        obmap = Bitmap.createBitmap(bmap, 0, 0, bmap.getWidth(), bmap.getHeight(), matrix, true);
        bmap.recycle();
        image.setImageBitmap(obmap);
	
	}
	/**
	 * This function is the click listener for the save button in the layout
	 * @return void
	 */
	public void saveButtonListener()
    {
    	
    	saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (date == null)
				{
					Toast noDate = Toast.makeText(getApplicationContext(), "Please select a date.", Toast.LENGTH_LONG);
					noDate.show();
					return;
				}
				
				Bundle extras = getIntent().getExtras();
				System.out.println(" the date saved is " + date.toString() + " " + date.getDate() + " " + date.getMonth());
				databaseWriting(new LocationReminder(extras.getInt("longitude"),extras.getInt("latitude"),titleEditText.getText().toString(), actualReminderText.getText().toString(),"/sdcard/picture", date));
				
				Toast setReminderDone = Toast.makeText(getApplicationContext(), "Your reminder has been set", Toast.LENGTH_LONG);
		    	setReminderDone.show();
		    	finish();
			}
		});
    	
    }
	/**
	 * This button is the click listener for taking photographs using the button present in the layout
	 * @return void
	 */
	public void cameraButtonListener()
    {
    	cameraButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent cameraActivityIntent = new Intent(ReminderGUI.this,StartCamera.class);
				ReminderGUI.this.startActivityForResult(cameraActivityIntent, 300);
				
			}
		});
    }
	
	/**
	 * This function is called when the user finishes to select a date or finishes to take a photo graph for the
	 * reminder
	 */
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) 
        {
        	System.out.println("Returning result from activity");
        	int year = data.getIntExtra("year", 0);   // get number of year
            int month = data.getIntExtra("month", 0); // get number of month 0..11
            int day = data.getIntExtra("day", 0);     // get number of day 0..31
            
            date.setDate(day);
            date.setMonth(month);
            date.setYear(year);
            System.out.println("the date received is " + date.getDate() + " " + date.getMonth() + " " + date.getYear());
            dateText.setText(DateUtils.getMonthString(month, DateUtils.LENGTH_LONG) + " " + day + ", " +year);
        }
        else if (resultCode == RESULT_CANCELED)
        {
        	readImage();
        }
        }
	
	/**
	 * This function is the click listener for the cancel button present in the layout
	 */
	public void cancelButtonListener()
    {
    	cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
    	
    }
	/**
	 * This function is the click listener for the Title EditText View in the layout. This function sets the text of this 
	 * edit text to empty string on clicking it.
	 */
	public void listenerForReminderTitleText()
    {
    		titleEditText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				titleEditText.setText("");
			}
		});
    }
	/**
	 * This function is listener for clicking the body edit text in the layout. This function sets the text of the body
	 * text view to an empty string on clicking it.
	 */
	public void listenerForReminderBodyText()
    {
    	actualReminderText.setClickable(true);
        actualReminderText.setOnClickListener(new View.OnClickListener() {
        	
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("the actual reminder text box is being clicked");
				actualReminderText.setText("");
				
			}
		});
    }
	/**
	 * This function is the click listener for the date button present in the layout. This function initializes the date
	 * elements layout and also calls the intent for selecting the date for the reminder.
	 */
	public void setupDateFunction()
    {
    	setupDate.setClickable(true);
    	setupDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Intent.ACTION_PICK).setDataAndType(null, CalendarActivity.MIME_TYPE), 100);
			}
		});
    	
    }
	public void databaseWriting(LocationReminder reminder)
    {
    	try {
    		File file = new File("/sdcard/reminderdata/reminderNames");
    		RandomAccessFile raf = new RandomAccessFile(file,"rw");
    		raf.seek(file.length());
    		System.out.println("the file name written to the file is " + reminder.getTitle().toString() + " date is " + reminder.getDate().getDate() + " " + reminder.getDate().getMonth());
    		raf.writeChars(reminder.getTitle().toString()+"\n");
    		raf.close();
    		
    		ObjectOutputStream reminderContent = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/sdcard/reminderdata/" + reminder.getTitle())));
    		reminderContent.writeObject(reminder);
    		reminderContent.close();
    		
    		File imageHandle = new File("/sdcard/reminderdata/picture");
    		File newImageHandle = new File("/sdcard/reminderdata/picture" + reminder.getTitle()+"_image");
    		imageHandle.renameTo(newImageHandle);
    		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	public void readImage()
	{
		/*
		try {
			BufferedInputStream file = new BufferedInputStream(new FileInputStream("/sdcard/picture"));
			image.setBitmap(BitmapFactory.decodeStream(file));
			image.invalidate();
			relativeLayout.invalidate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		iconImage();
	}
	
	public Bitmap returnABitmap()
	{
		BufferedInputStream file;
		try {
			file = new BufferedInputStream(new FileInputStream("/sdcard/picture"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return BitmapFactory.decodeStream(file);
	}
}
