package fau.edu.shankar.thefinalcompleteottercamera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView cameraView;
    ImageView otterBorder;
    Button cameraButton;
    Button previewButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (ImageView) findViewById(R.id.cameraView);
        otterBorder = (ImageView) findViewById(R.id.otterBorder);
        cameraButton = (Button) findViewById(R.id.camerabutton);
        previewButton = (Button) findViewById(R.id.previewbutton);

        if (!hasCamera()) {
            cameraButton.setEnabled(false);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void setPreview(View v){
        CharSequence[] options = new CharSequence[] {"OK", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Take a Screenshot!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    cameraButton.setVisibility(View.INVISIBLE);
                    previewButton.setVisibility(View.INVISIBLE);
                    new CountDownTimer(5000, 5000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            cameraButton.setVisibility(View.VISIBLE);
                            previewButton.setVisibility(View.VISIBLE);
                        }
                    }.start();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cameraView.setImageBitmap(photo);
        }
    }
}