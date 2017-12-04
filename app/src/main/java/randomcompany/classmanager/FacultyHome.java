package randomcompany.classmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Patrick Kelly - x14533687 on 25/11/2017.
 */

public class FacultyHome extends AppCompatActivity{


    Button logoutBtn, manageBtn, searchBtn;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener fListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_home);

        manageBtn = findViewById(R.id.manageBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        fAuth = FirebaseAuth.getInstance();

        fListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth fireAuth) {

                if (fireAuth.getCurrentUser() == null) {

                    Toast.makeText(FacultyHome.this, "Please login or Register ", Toast.LENGTH_LONG).show();
                    Intent i;
                    i = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(i);
                }
            }
        };




        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fAuth.signOut();

            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ManageClass.class);
                startActivity(i);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchStudent.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fListener);
    }


}
