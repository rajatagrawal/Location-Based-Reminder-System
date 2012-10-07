package newVersion.newCode;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.view.View;


/**
 * This class is used to store the image captured by the camera. It implements the picture callback interface that is 
 * called when the camera finishes clicking the picture.
 * @author RajatHCI
 *
 */
public class HandlePictureStorage implements PictureCallback {

	
	
	Context activityArgument;
	StartCamera localCopy;
	public HandlePictureStorage(Context parameterActivity, StartCamera startCameraCopyForChangingText)
	{
		//super();
		activityArgument = parameterActivity;
		localCopy = startCameraCopyForChangingText;
		System.out.println("the new handle picture storage object");
	}
	
	
	/**
	 * This function is called when the camera finishes taking the picture. This function stores i.e writes this image
	 * raw data to the sd card memory of the phone.
	 */
	@Override
	public void onPictureTaken(byte[] picture, Camera camera) {
		// TODO Auto-generated method stub
		
		System.out.println("picture has been taken");
		String Filename="picture";
		
		try {
			System.out.println("in the file saving try statement");
			DataOutputStream fos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("/sdcard/reminderdata/picture")));
			fos.write(picture);
			System.out.println("picture is being written to the memory card");
			fos.close();
			//localCopy.cameraScreenLayout.addView(localCopy.photoTaken, localCopy.cameraCaptureButton.getId());
			localCopy.cameraScreenLayout.removeView(localCopy.cameraCaptureButton);
			localCopy.photoTaken.setVisibility(View.VISIBLE);
			localCopy.goBackButton.setText("Photo has Been Taken. Press to Go Back");
			//activityArgument.readTheFileStored();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("there is a try catch error");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("another try catch error");
			
		}

	}
	

}
