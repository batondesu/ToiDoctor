package com.toier.toidoctor.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.enums.Role;
import com.toier.toidoctor.enums.Sex;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    private final static String TAG = "UserController";
    private String phoneNumber;
    private String password;
    private String name;
    private String email;
    private Role role;
    private Sex sex;

    private static UserController instance;

    private FirebaseFirestore db;

    private UserController() {
        db = FirebaseFirestore.getInstance();
    }

    public static UserController getInstance() {
        return instance = (instance == null ? new UserController() : instance);
    }

    public void addPhoneAndPassword(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public void createUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("phoneNumber", phoneNumber);
        user.put("password", password);
        user.put("email", email);
        user.put("role", role.toString());
        user.put("sex", sex.toString());

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void addInfo(String name, String email, Sex sex) {
        this.name = name;
        this.email = email;
        this.role = Role.PATIENT;
        this.sex = sex;
    }

    public void checkVerificationUser(String phone, String password, FirestoreCallback firestoreCallback) {
        db.collection("Users")
                .whereEqualTo("phoneNumber", phone)
                .whereEqualTo("password", password)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                                Log.d(TAG, String.valueOf(task.getResult().isEmpty()));
                                firestoreCallback.checkExistUser(!task.getResult().isEmpty());
                            }
                        }
                    }
                });
    }

    public void checkExistUser(String phone, FirestoreCallback firestoreCallback) {
        db.collection("Users")
                .whereEqualTo("phoneNumber", phone)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                                Log.d(TAG, String.valueOf(task.getResult().isEmpty()));
                                firestoreCallback.checkExistUser(!task.getResult().isEmpty());
                            }
                        }
                    }
                });
    }

    public void getCurrentUserFromDB(String phone) {
        db.collection("Users")
                .whereEqualTo("phoneNumber", phone)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    phoneNumber = document.getString("phoneNumber");
                                    password = document.getString("password");
                                    email = document.getString("email");
                                    role = Role.getRole(document.getString("role"));
                                    sex = Sex.getSex(document.getString("sex"));
                                    Log.d(TAG, phoneNumber + " + " + password + " + " + email + " + " + role + " + " + sex);
                                }
                            }
                        }
                    }
                });
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public interface FirestoreCallback {
        void checkExistUser(boolean ok);
    }
}
