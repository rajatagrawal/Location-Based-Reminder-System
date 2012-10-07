package newVersion.newCode;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class is an activity that starts the camera. The camera is used to take photo reminders for the reminder.
 * @author RajatHCI
 *
 */
public class StartCamera extends Activity{
	
	LinearLayout cameraScreenLayout;
	Button cameraCaptureButton;
	Button goBackButton;
	CameraSurfaceView camera;
	StartCamera duplicateCopy;
	TextView photoTaken;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		camera = new CameraSurfaceView(this);
		duplicateCopy = this;
		
		// set the layout of the screen
		cameraScreenLayout = new LinearLayout(getApplicationContext());
		cameraScreenLayout.setOrientation(LinearLayout.VERTICAL);
		photoTaken = new TextView(getApplicationContext());
		photoTaken.setText("Photo has been Taken. Press the Button below to go back");
		photoTaken.setWidth(100);
		photoTaken.setHeight(30);
		photoTaken.setVisibility(View.INVISIBLE);
		goBackButton = new Button(getApplicationContext());
		goBackButton.setText("Go Back");
		goBackButton.setHeight(30);
		goBackButton.setWidth(100);
		
		// setup the listener for go back button
		listenerForGoBackButton();
		
		// initialize the button to capture photos using the camera
		cameraCaptureButton = new Button(getApplicationContext());
		
		// set the layout for the button to take photos using the camera
		cameraCaptureButton.setText("Capture a photo button");
		makeListenerForCapturingPhotos();
		cameraCaptureButton.setHeight(30);
		cameraCaptureButton.setWidth(100);
		
		// set the layout for the camera screen
		cameraScreenLayout.addView(cameraCaptureButton);
		cameraScreenLayout.addView(goBackButton);
		cameraScreenLayout.addView(photoTaken);
		cameraScreenLayout.addView(camera);
		
		setContentView(cameraScreenLayout);
			
	}
	
	/**
	 * This function is click listener for the button used to take photos using the camera of the application
	 */
	public void makeListenerForCapturingPhotos()
	{
		cameraCaptureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("taking a picture");
				Camera cameraObject = camera.getCamera();
				cameraObject.takePicture(null, null, new HandlePictureStorage(getApplicationContext(), duplicateCopy));
				
			}
		});
	}
	/**
	 * This function is the click listener for the back button in the layout of the screen for taking photos.
	 */
	public void listenerForGoBackButton()
	{
		goBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
	

}
