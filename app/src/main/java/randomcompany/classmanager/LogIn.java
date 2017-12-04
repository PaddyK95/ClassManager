package randomcompany.classmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LogIn extends AppCompatActivity {

    EditText emailtf, passwordtf;
    Button loginBtn, registerBtn;
    FirebaseAuth fAuth;
    DatabaseReference fDatabase;
    String email, password;
    DatabaseReference LoginDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //final String userId = fAuth.getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
        emailtf = findViewById(R.id.emailTf);
        passwordtf = findViewById(R.id.passwordTf);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        //LoginDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        //String userId = fAuth.getCurrentUser().getUid();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailtf.getText().toString();
                password = passwordtf.getText().toString();

                if(TextUtils.isEmpty(email)){
                    emailtf.setError("Email field is empty");
                }
                if(TextUtils.isEmpty(password)){
                    emailtf.setError("Password field is empty");
                }
                else {

                    final ProgressDialog pd = new ProgressDialog(LogIn.this);
                    pd.setMessage("Logging in...");
                    pd.show();

                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userID = currentUser.getUid();

                                LoginDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                                LoginDb.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String email = dataSnapshot.child("Email").getValue().toString();
                                            if(email.equals("@admin")){
                                                Intent intentResident = new Intent(LogIn.this, AdminHome.class);
                                                startActivity(intentResident);
                                                finish();
                                            }else if(email.equals("@faculty")){
                                                Intent intentMain = new Intent(LogIn.this, FacultyHome.class);
                                                startActivity(intentMain);
                                                finish();
                                            }else{
                                                Intent intentMain = new Intent(LogIn.this, FacultyHome.class);
                                                startActivity(intentMain);
                                                finish();
                                            }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            else{
                                Toast.makeText(LogIn.this, "Email or password is incorrect ", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        }
                    });


                }














            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

    }


    private void userLogin(){


                }


}
