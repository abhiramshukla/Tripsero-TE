package com.tripsero.app.view.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.tripsero.app.R;

import java.util.HashMap;

public class SignUp3 extends AppCompatActivity {

    TextInputLayout bio_of_user;
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    private ImageView ivProfile;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference fileStorage;
    private Uri localFileUri, serverFileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_3);

        bio_of_user =findViewById(R.id.signup_bio);
        scrollView=findViewById(R.id.signup3scrollview);
        countryCodePicker=findViewById(R.id.country_code_picker);
        phoneNumber=findViewById(R.id.signup_phone_number);
        ivProfile = findViewById(R.id.profile_image);
        fileStorage = FirebaseStorage.getInstance().getReference();

    }

    public void done_btn(View view) {

        String _email =getIntent().getStringExtra("email").trim();
        String _password =getIntent().getStringExtra("password").trim();

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //Remove first zero if entered!
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        //Complete phone number
        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode()  + _getUserEnteredPhoneNumber;

        if (validatePhoneNumber()) {
            return;
        }

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();

                            if(localFileUri!=null)
                                updateNameAndPhoto();
                            else
                                updateOnlyName();

                        } else {
                            Toast.makeText(SignUp3.this,
                                    getString(R.string.signup_failed, task.getException()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void pickImage(View view) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},102);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {

                localFileUri = data.getData();
                ivProfile.setImageURI(localFileUri);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==102)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
            else
            {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
            }

        }
    }


    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private void updateOnlyName() {

        String _fullName=getIntent().getStringExtra("name").trim();

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //Remove first zero if entered!
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        //Complete phone number
        final String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;


        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(_fullName)
                .build();

        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    String userID = firebaseUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(NodeNames.USERS);

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(NodeNames.NAME, getIntent().getStringExtra("name"));
                    hashMap.put(NodeNames.USERNAME, getIntent().getStringExtra("username"));
                    hashMap.put(NodeNames.EMAIL, getIntent().getStringExtra("email").trim());
                    hashMap.put(NodeNames.ONLINE, "true");
                    hashMap.put(NodeNames.PHOTO, "");
                    hashMap.put(NodeNames.BIO,  bio_of_user.getEditText().getText().toString());
                    hashMap.put(NodeNames.PASSWORD,getIntent().getStringExtra("password"));
                    hashMap.put(NodeNames.GENDER,getIntent().getStringExtra("gender"));
                    hashMap.put(NodeNames.DOB,getIntent().getStringExtra("dob"));
                    hashMap.put(NodeNames.PHONENUMBER,_phoneNo);

                    databaseReference.child(userID).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(SignUp3.this, R.string.user_created_successfully, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp3.this, LoginPage.class));
                        }
                    });


                } else {
                    Toast.makeText(SignUp3.this,
                            getString(R.string.failed_to_update_profile, task.getException()), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateNameAndPhoto()
    {
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        //Remove first zero if entered!
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        //Complete phone number
        final String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

        String strFileName= firebaseUser.getUid() + ".jpg";
        final  StorageReference fileRef = fileStorage.child("images/"+ strFileName);


        fileRef.putFile(localFileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful())
                {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            serverFileUri = uri;

                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(getIntent().getStringExtra("name"))
                                    .setPhotoUri(serverFileUri)
                                    .build();

                            firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        String userID = firebaseUser.getUid();
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child(NodeNames.USERS);

                                        HashMap<String, String> hashMap = new HashMap<>();

                                        hashMap.put(NodeNames.NAME, getIntent().getStringExtra("name"));
                                        hashMap.put(NodeNames.USERNAME, getIntent().getStringExtra("username"));
                                        hashMap.put(NodeNames.ONLINE, "true");
                                        hashMap.put(NodeNames.BIO,  bio_of_user.getEditText().getText().toString());
                                        hashMap.put(NodeNames.PASSWORD,getIntent().getStringExtra("password"));
                                        hashMap.put(NodeNames.GENDER,getIntent().getStringExtra("gender"));
                                        hashMap.put(NodeNames.DOB,getIntent().getStringExtra("dob"));
                                        hashMap.put(NodeNames.PHONENUMBER, _phoneNo );
                                        hashMap.put(NodeNames.EMAIL, getIntent().getStringExtra("email").trim());
                                        hashMap.put(NodeNames.PHOTO, serverFileUri.getPath());

                                        databaseReference.child(userID).setValue(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        Toast.makeText(SignUp3.this, R.string.user_created_successfully, Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUp3.this, LoginPage.class));
                                                    }
                                                });


                                    } else {
                                        Toast.makeText(SignUp3.this,
                                                getString(R.string.failed_to_update_profile, task.getException()), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    });
                }}});

    }

}
