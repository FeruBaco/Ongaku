package com.example.ongaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText regEditTextUser, regEditTextEmail, regEditTextPass;
    private Button regButtonRegister;

    private String user = "", email = "", password = "";
    FirebaseAuth regAuth;
    DatabaseReference regDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regAuth = FirebaseAuth.getInstance();
        regDatabase = FirebaseDatabase.getInstance().getReference();

        regEditTextUser = findViewById(R.id.et_reg_user);
        regEditTextEmail = findViewById(R.id.et_reg_email);
        regEditTextPass = findViewById(R.id.et_reg_password);
        regButtonRegister = findViewById(R.id.btn_reg_reg);

        regButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = regEditTextUser.getText().toString();
                email = regEditTextEmail.getText().toString();
                password = regEditTextPass.getText().toString();

                if (!user.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.length() > 5) {
                        registerUser();
                    } else {
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        regAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = regAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("user", user);
                    map.put("email", email);
                    map.put("password", password);
                    regDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar usuario.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
