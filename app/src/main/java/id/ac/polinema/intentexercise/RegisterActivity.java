package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout layoutfullname, layoutemail, layoutpassword, layoutconfirmpassword, layouthomepage, layoutabout;
    private TextInputEditText textfullname, textemail, textpassword,textconfirmpassword, texthomepage, textabout;
    private Button buttonOk;
    private ImageView imageView;
    private boolean change_img = false;
    private Uri imgUri = null;
    private Bitmap bitmap;
    private CircleImageView Avatar, changeAvatar;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        layoutfullname = findViewById(R.id.layout_fullname);
        layoutemail = findViewById(R.id.layout_email);
        layoutpassword = findViewById(R.id.layout_password);
        layoutconfirmpassword = findViewById(R.id.layout_confirm_password);
        layouthomepage = findViewById(R.id.layout_homepage);
        layoutabout = findViewById(R.id.layout_about);

        textfullname = findViewById(R.id.text_fullname);
        textemail = findViewById(R.id.text_email);
        textpassword = findViewById(R.id.text_password);
        textconfirmpassword = findViewById(R.id.text_confirm_password);
        texthomepage = findViewById(R.id.text_homepage);
        textabout = findViewById(R.id.text_about);

        buttonOk = findViewById(R.id.button_ok);
        imageView = findViewById(R.id.imageView);

        Avatar = findViewById(R.id.image_profile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = textfullname.getText().toString();
                String email = textemail.getText().toString();
                String password = textpassword.getText().toString();
                String confirmpassword = textconfirmpassword.getText().toString();
                String homepage = texthomepage.getText().toString();
                String about = textabout.getText().toString();

                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);

                if(!change_img){
                    Toast.makeText(RegisterActivity.this, "Image must be change", Toast.LENGTH_LONG).show();
                }else if(fullname.isEmpty()){
                    textfullname.setError("Fullname Required!");
                }else if(email.isEmpty()){
                    textemail.setError("Email Required!");
                }else if(password.isEmpty()){
                    textpassword.setError("Password Required!");
                }else if(confirmpassword.isEmpty()) {
                    textconfirmpassword.setError("Confirm Password Required!");
                }else if(homepage.isEmpty()) {
                    texthomepage.setError("Homepage Required!");
                }else if(about.isEmpty()) {
                    textabout.setError("About Required!");
                }else if (!password.equals(confirmpassword)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    pindah.putExtra("image", image);
                    pindah.putExtra("fullname", fullname);
                    pindah.putExtra("email", email);
                    pindah.putExtra("password", password);
                    pindah.putExtra("con_password", confirmpassword);
                    pindah.putExtra("homepage", homepage);
                    pindah.putExtra("about", about);
                    startActivity(pindah);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (requestCode == GALLERY_REQUEST_CODE){
                if (data != null){
                    try{
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        Avatar.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

}