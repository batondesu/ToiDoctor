package com.toier.toidoctor;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);

        run();
    }

    private void run() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctorRef = db.collection("Doctors");

        List<Doctor> doctorList = new ArrayList<>();
        List<Doctor> filterList = new ArrayList<>();

        doctorRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Doctor doctor = document.toObject(Doctor.class);
                        doctorList.add(doctor);
                    }
                }
            }

            DoctorAdapter adapter = new DoctorAdapter(this, doctorList);
            ListView listView1 = findViewById(R.id.listViewfilterlayout);
            listView1.setAdapter(adapter);
            //adapter.notifyDataSetChanged();

            String str = String.valueOf(doctorList.size());
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

            SearchView searchView = findViewById(R.id.searchViewfilter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });

        });
    }
}
