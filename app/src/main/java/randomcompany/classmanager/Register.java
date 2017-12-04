package randomcompany.classmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Patrick Kelly - x14533687 on 26/11/2017.
 */

public class Register extends AppCompatActivity {

    EditText emailtf, passwordtf;
    Button registerBtn;
    FirebaseAuth fAuth;
    String email, password, userType;
    DatabaseReference fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        fAuth = FirebaseAuth.getInstance();
        emailtf = findViewById(R.id.emailTf);
        passwordtf = findViewById(R.id.passwordTf);
        registerBtn = findViewById(R.id.registerBtn);
        fDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    setUpUser();
            }
        });

    }

    private void setUpUser(){


        email = emailtf.getText().toString();
        password = passwordtf.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailtf.setError("Email field is empty");
        }
        if(TextUtils.isEmpty(password)){
            emailtf.setError("Password field is empty");
        }
            else {
            final ProgressDialog progressD;
            progressD = new ProgressDialog(this);
            progressD.setMessage("Setting up new user...");
            progressD.show();

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        final String UserId = fAuth.getCurrentUser().getUid();
                        DatabaseReference currentUser = fDatabase.child(UserId);

                        currentUser.child("Email").setValue(email);
                        currentUser.child("Password").setValue(password);

                        Toast.makeText(Register.this, "User created! ", Toast.LENGTH_LONG).show();

                        progressD.dismiss();

                        fDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String email = dataSnapshot.child(UserId).child("Email").getValue().toString();

                                if(email.equals("@admin")){
                                    Intent intentResident = new Intent(Register.this, AdminHome.class);
                                    startActivity(intentResident);
                                    finish();
                                }else if(email.equals("@faculty")){
                                    Intent intentMain = new Intent(Register.this, FacultyHome.class);
                                    startActivity(intentMain);
                                    finish();
                                }else{
                                    Intent intentMain = new Intent(Register.this, FacultyHome.class);
                                    startActivity(intentMain);
                                    finish();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        /*if(fAuth.getCurrentUser().getEmail().contains("@admin")){
                            startActivity(new Intent(Register.this, AdminHome.class));
                        }
                        if(fAuth.getCurrentUser().getEmail().contains("@faculty")){
                            startActivity(new Intent(Register.this, FacultyHome.class));
                        }
                        else{
                            startActivity(new Intent(Register.this, ParentHome.class));
                        }
                        Toast.makeText(Register.this, "User created! ", Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(Register.this, n.class));
                        progressD.dismiss();*/

                    }


                }
            });

        }

    }

}
