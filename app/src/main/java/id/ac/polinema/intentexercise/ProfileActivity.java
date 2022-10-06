package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView imageprofile;
    private TextView labelabout, labelfullname, labelemail, labelhomepage;
    private Button buttonhomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageprofile = findViewById(R.id.image_profile);
        labelabout = findViewById(R.id.label_about);
        labelfullname = findViewById(R.id.label_fullname);
        labelemail = findViewById(R.id.label_email);
        labelhomepage = findViewById(R.id.label_homepage);
        buttonhomepage = findViewById(R.id.button_homepage);

        String about = getIntent().getExtras().getString("about");
        String fullname = getIntent().getExtras().getString("fullname");
        String email = getIntent().getExtras().getString("email");
        final String homepage = getIntent().getExtras().getString("homepage");

        Uri uri = Uri.parse(getIntent().getExtras().getString("image"));
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageprofile.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "Failed load images", Toast.LENGTH_SHORT).show();
        }

        labelabout.setText(about);
        labelfullname.setText(fullname);
        labelemail.setText(email);
        labelhomepage.setText(homepage);

        buttonhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(homepage));
                startActivity(webIntent);
            }
        });

    }
}
