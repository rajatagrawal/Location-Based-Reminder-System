package newVersion.newCode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


/**
 * This is the map activity file.
 * It initializes the map, the geocoder (for translating string address to latitudes and longitudes), initializing
 * set a reminder button and their actions
 * @author RajatHCI
 *
 */
public class MapScreen extends MapActivity {
	
    MapView mapView;
    List<Overlay> mapOverlays;
    Button setAReminderButton;
    Vector<LocationReminder> reminderData;
    Drawable drawable;
    TextView photoHasBeenTaken;
    CalendarView calendarView;
    CameraSurfaceView camera;
    EditText titleEditText;
    EditText actualReminderText;
    Button saveButton;
    Button cancelButton;
    Button cameraButton;
    Button cameraCaptureButton;
    static Bitmap oldCameraPhotoBitmap;
    static ImageView photoViewFromCameraImage;
    Button setupDate;
    LinearLayout cameraScreenLayout;
    HelloItemizedOverlay itemizedOverlay;
    Button locationSearchButton;
    OverlayItem pointSearchOverlay;
    Geocoder geoCoder;
    List <Address> address;
    Intent cameraActivityIntent;
    LocationManager managerLocation;
    MyLocationListener listenerLocation;
    EditText locationSearchingBox;
    Date date;
    RelativeLayout relativeLayout;
    GeoPoint pointSearch;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.setContentView(R.layout.mainapp);
    	camera = new CameraSurfaceView(getApplicationContext());
    	
        reminderData = new Vector();        		

        // set the layout for map screen
        referenceXMLElements();
        
        geoCoder = new Geocoder(this,Locale.getDefault());
        
        // listener for the search location button
        locationSearchButton.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				System.out.println("the button is being clicked");
				// TODO Auto-generated method stub
				try
		        {
					
		        	System.out.println("started to look for location");
		        	
		        	if (locationSearchingBox.getText().toString().equals("") != true )
		        	{
		        		System.out.println("the search input string is and the entered string is not null " + locationSearchingBox.getText().toString());
		        		address = geoCoder.getFromLocationName(locationSearchingBox.getText().toString(), 1);
		        		
		        		if (address.isEmpty())
		        		{
		        			Toast noResultsFound = Toast.makeText(getApplicationContext(), "Sorry no results were found. Please search again!", Toast.LENGTH_LONG);
		        			noResultsFound.show();
		        			return;
		        		}
		        	}
		        	else
		        	{
		        		System.out.println("the search input string is " + locationSearchingBox.getText().toString());
		        		CharSequence errorMessage = "Sorry you entered an empty address";
		        		Toast errorToast = Toast.makeText(getApplicationContext(),errorMessage, Toast.LENGTH_SHORT);
		        		errorToast.show();
		        		return;
		        	}
		        	System.out.println("the size of returned address is " + address.size());
			        
		        	
		        		System.out.println("address is not null");
				        pointSearch = new GeoPoint((int)(address.get(0).getLatitude()*1000000),(int) (address.get(0).getLongitude()*1000000));
				        System.out.println("the locality of search is " + address.get(0).getLongitude() + " " + address.get(0).getLatitude() + " " + address.get(0).getLocality() + " " + address.get(0).getFeatureName());
				        pointSearchOverlay = new OverlayItem(pointSearch,address.get(0).getLocality(),address.get(0).getFeatureName());
				        itemizedOverlay.addOverlay(pointSearchOverlay);
				        MapController mapController = mapView.getController();
				        mapController.animateTo(pointSearch);
		        	
		        }
		        catch (IOException e)
		        {
		        	System.out.println("Impossible to connect to geo coder " + e);
		        
		        }
		        catch (Exception e) {
		            Log.e("MapError", "Something went wrong: ", e);
		        }	
			}
		});
        
        
        //initialize the map view
        mapView = (MapView)this.findViewById(R.id.mapview);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.pin);
        itemizedOverlay = new HelloItemizedOverlay(drawable);
        mapOverlays.add(itemizedOverlay);
        setAReminderButton.setVisibility(View.VISIBLE);
        
        
        
        
    }
    
    // called when another started activity returns result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	// called when the user finishes selecting the date
        if(resultCode==RESULT_OK) {
            int year = data.getIntExtra("year", 0);   // get number of year
            int month = data.getIntExtra("month", 0); // get number of month 0..11
            int day = data.getIntExtra("day", 0);     // get number of day 0..31

            // format date and display on screen
            final Calendar dat = Calendar.getInstance();
            dat.set(Calendar.YEAR, year);
            dat.set(Calendar.MONTH, month);
            dat.set(Calendar.DAY_OF_MONTH, day);
            
            // show result
            SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd");
            Toast.makeText(MapScreen.this, format.format(dat.getTime()), Toast.LENGTH_LONG).show();
            date = new Date();
            date.setDate(day);
            date.setMonth(month);
            date.setYear(year);
                    
        }
        
        //called when the camera finishes clicking a photograph
        else if (resultCode == RESULT_CANCELED)
        {
        	System.out.println("the camera activity has been finished");
        	readTheFileStored();
        }
    }
    @Override
    protected boolean isRouteDisplayed()
    {
    	return false;
    }
    
    public void invokeReminderLayout()
    {
    	// make reminder layout visible
    	titleEditText.setVisibility(View.VISIBLE);
    	actualReminderText.setVisibility(View.VISIBLE);
    	saveButton.setVisibility(View.VISIBLE);
    	cancelButton.setVisibility(View.VISIBLE);
    	cameraButton.setVisibility(View.VISIBLE);
    	//photoViewFromCameraImage.setVisibility(View.VISIBLE);
    	
    	//photoViewFromCameraImage.setVisibility(View.VISIBLE);
    	setupDate.setVisibility(View.VISIBLE);
    	
    	//make the previous layout invisible
    	mapView.setVisibility(View.INVISIBLE);
    	locationSearchingBox.setVisibility(View.INVISIBLE);
    	locationSearchButton.setVisibility(View.INVISIBLE);
    	setAReminderButton.setVisibility(View.INVISIBLE);
    }
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
    public void saveButtonListener()
    {
    	if (saveButton == null)
    		Log.d("Error", "save button is null");
    	saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (reminderData == null)
					System.out.println("reminder Data is null");
				if (address == null)
					System.out.println("address is null");
				/*
				if (readTheFileStored()==null)
				{
					Toast photoIsNull = Toast.makeText(getApplicationContext(), "Please take a photo", Toast.LENGTH_LONG);
					photoIsNull.show();
					return;
				}
				else
				{
					Toast photoIsNull = Toast.makeText(getApplicationContext(), "the photo taken is not null", Toast.LENGTH_LONG);
					photoIsNull.show();
				}
				*/
				
				//reminderData.add( new LocationReminder((int)address.get(0).getLongitude(),(int)address.get(0).getLatitude(),titleEditText.getText().toString(), actualReminderText.getText().toString(),readTheFileStored(), new Date()));
				databaseReading(new LocationReminder((int)address.get(0).getLongitude(),(int)address.get(0).getLatitude(),titleEditText.getText().toString(), actualReminderText.getText().toString(),"/sdcard/picture", date));
				setContentsOfReminder();
				Toast setReminderDone = Toast.makeText(getApplicationContext(), "Your reminder has been set", Toast.LENGTH_LONG);
		    	setReminderDone.show();
		    	//cameraPhotoBitmap.recycle();
		    	//cameraPhotoBitmap=null;
		    	finish();
		    	//invokeMapLayout();
				//System.out.println(reminderData.get(0).getTitle() + " " + reminderData.get(0).getContent());
			}
		});
    	
    }
    public void setContentsOfReminder()
	{
    	LocationReminder reminder;
		//FileInputStream file = new FileInputStream();
		//File fileName = new File(context.getExternalFilesDir(null), "reminderDatabase");
		try {
			ObjectInputStream file = new ObjectInputStream(new BufferedInputStream (new FileInputStream("/sdcard/reminderDatabase")));
			reminder = (LocationReminder) file.readObject();
			System.out.println("data retrieved from the file after reading from sd card is " + reminder.getTitle());
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
    public void listenerReminderSetButton()
    {
    	System.out.println("set a reminder button pressed");
    	setAReminderButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mapView.setVisibility(View.INVISIBLE);
				
				if (address == null)
				{
					Toast noLocationChosen = Toast.makeText(getApplicationContext(),"Sorry you didn't search for an address. Please choose a location", Toast.LENGTH_SHORT);
					noLocationChosen.show();
					return;
				}
				else
				{
					Intent intent = new Intent(MapScreen.this,ReminderGUI.class);
					intent.putExtra("longitude", (pointSearch.getLongitudeE6()));
					intent.putExtra("latitude", (pointSearch.getLatitudeE6()));
					MapScreen.this.startActivity(intent);
					finish();
				}
			}
		});
    }
    public void listenerSearchBox()
    {
    	locationSearchingBox.setClickable(true);
    	locationSearchingBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				locationSearchingBox.setText("");
				
			}
		});
    }
    
    public void cameraCaptureButtonListener()
    {
    	cameraCaptureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    /*
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
    	Intent parentActivityIntent = getIntent();
    	
    	if (parentActivityIntent.getBooleanExtra("photoTaken", (Boolean) null) == true)
    	{
    		System.out.println("the application has gained focus");
    		photoHasBeenTaken.setVisibility(View.VISIBLE);
    	}
    }*/
    public void cameraButtonListener()
    {
    	cameraButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
				/*if (cameraPhotoBitmap!= null)
				cameraPhotoBitmap.recycle();*/
				//setContentView(cameraScreenLayout);
				cameraActivityIntent = new Intent(MapScreen.this,StartCamera.class);
				MapScreen.this.startActivityForResult(cameraActivityIntent, 300);
				//VersionNewSSUIFinalProjectActivity.this.start
				
				//photoHasBeenTaken.setVisibility(View.VISIBLE);
				//photoHasBeenTaken.setL
				//invokeReminderLayout();
			}
		});
    }
    public void databaseReading(LocationReminder reminder)
    {
    	try {
			ObjectOutputStream outputStream = new ObjectOutputStream (new BufferedOutputStream( new FileOutputStream("/sdcard/reminderDatabase")));
			outputStream.writeObject(reminder);
			outputStream.flush();
			outputStream.reset();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	//cameraPhotoBitmap = null;
    	System.gc();
    }

    public void readTheFileStored()
	{
    	System.out.println("calling the read file function");
    	BufferedInputStream fos= null;
    	
    	try {
			fos = new BufferedInputStream( new FileInputStream("/sdcard/picture"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	//photoViewFromCameraImage = (ImageView) findViewById(R.id.photoFromCameraImage);
    	Matrix matrix = new Matrix();
    	matrix.postRotate(90);
    	
    	Bitmap cameraPhotoBitmap = BitmapFactory.decodeStream(fos);
    	//Bitmap tempBitmap = Bitmap.createBitmap(cameraPhotoBitmap,0,0,cameraPhotoBitmap.getWidth(), cameraPhotoBitmap.getHeight(), matrix, true);
    	//photoViewFromCameraImage.setImageBitmap(cameraPhotoBitmap);
    	//photoViewFromCameraImage.setI
    	/*
    	Canvas canvas = new Canvas(cameraPhotoBitmap);
    	canvas.rotate(90);
    	*/
    	View view = new View(this);
    	//photoViewFromCameraImage = canvas;
    	//photoViewFromCameraImage.setImageMatrix(matrix);
    	//cameraPhotoBitmap.recycle();
    	//tempBitmap=null;
    	
    	//cameraPhotoBitmap.recycle();
    	cameraPhotoBitmap=null;
    	
    	System.gc();
    	
    	//RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    	//params.addRule(RelativeLayout.BELOW,R.id.actualReminderText);
    	//relativeLayout.addView(photoViewFromCameraImage);
    	
    	
    	//relativeLayout.invalidate();
    	try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void invokeMapLayout()
    {
    	System.out.println("map layout is called");
    	titleEditText.setVisibility(View.INVISIBLE);
    	actualReminderText.setVisibility(View.INVISIBLE);
    	saveButton.setVisibility(View.INVISIBLE);
    	cancelButton.setVisibility(View.INVISIBLE);
    	photoViewFromCameraImage.setVisibility(View.INVISIBLE);
    	setupDate.setVisibility(View.INVISIBLE);
    	
    	//make the previous layout invisible
    	mapView.setVisibility(View.VISIBLE);
    	locationSearchingBox.setVisibility(View.VISIBLE);
    	locationSearchButton.setVisibility(View.VISIBLE);
    	setAReminderButton.setVisibility(View.VISIBLE);
    }
    public void cancelButtonListener()
    {
    	cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// make reminder layout visible
		    	titleEditText.setVisibility(View.INVISIBLE);
		    	actualReminderText.setVisibility(View.INVISIBLE);
		    	saveButton.setVisibility(View.INVISIBLE);
		    	cancelButton.setVisibility(View.INVISIBLE);
		    	
		    	//make the previous layout invisible
		    	mapView.setVisibility(View.VISIBLE);
		    	locationSearchingBox.setVisibility(View.VISIBLE);
		    	locationSearchButton.setVisibility(View.VISIBLE);
		    	setAReminderButton.setVisibility(View.VISIBLE);
				
			}
		});
    	
    }
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
    public void referenceXMLElements()
    {
    	//relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);
    	relativeLayout = new RelativeLayout(this);
    	//final CameraSurfaceView camera = new CameraSurfaceView(getApplicationContext());
    	//setContentView(camera);
    	
    	
    	locationSearchingBox = (EditText) findViewById(R.id.searchTextBox);
    	locationSearchingBox.setText("Carnegie Mellon University");
    	listenerSearchBox();
    	
        locationSearchButton = (Button) findViewById(R.id.locationSearch);
        
        setAReminderButton = (Button)findViewById(R.id.setAReminderButton);
        listenerReminderSetButton();
        
    }
}