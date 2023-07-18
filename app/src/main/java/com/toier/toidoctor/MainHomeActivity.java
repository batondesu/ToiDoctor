package com.toier.toidoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class Product {
    String name;
    String major;
    double rate;
    int review;
    int doctor_ID;

    public Product(String name, String major, int review, double rate, int doctor_ID) {
        this.name = name;
        this.rate = rate;
        this.major = major;
        this.review = review;
        this.doctor_ID = doctor_ID;
    }

}

public class MainHomeActivity extends AppCompatActivity {

    private Button button;
    private ConstraintLayout booking;
    private ConstraintLayout medicalRecord;
    ArrayList<Product> listProduct;
    MainHomeActivity.ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        button = (Button)findViewById(R.id.allButton_pt);
        booking = (ConstraintLayout) findViewById(R.id.bookingClinic1);
        medicalRecord = (ConstraintLayout) findViewById(R.id.medical_record);

        listProduct = new ArrayList<>();
        listProduct.add(new Product("Dr. Đỗ Duy Chiến", "Khoa nhi", 44, 4.8 , 1));
        listProduct.add(new Product("Dr. Trần Bá Toản", "Mắt", 44, 4.8 , 2));
        listProduct.add(new Product("Dr. Lê Hải Đăng", "Tim", 44, 4.8 , 3));
        listProduct.add(new Product("Dr. Tiến Đăm Săn", "Nha khoa", 44, 4.8 , 4));
        listProduct.add(new Product("Dr. Tiến Đăm Săn", "Nha khoa", 44, 4.8 , 5));

        productListViewAdapter = new ProductListViewAdapter(listProduct);

        listViewProduct = findViewById(R.id.listdoctor);
        listViewProduct.setAdapter(productListViewAdapter);


        //Lắng nghe bắt sự kiện một phần tử danh sách được chọn
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) productListViewAdapter.getItem(position);
                //Làm gì đó khi chọn sản phẩm (ví dụ tạo một Activity hiện thị chi tiết, biên tập ..)
                Toast.makeText(MainHomeActivity.this, product.name, Toast.LENGTH_LONG).show();
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHomeActivity.this, ListDoctorActivity.class);
                startActivity(intent);
            }
        });

        medicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHomeActivity.this, PatientProfileActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHomeActivity.this, ListDoctorActivity.class);
                startActivity(intent);
            }
        });
    }
    class ProductListViewAdapter extends BaseAdapter {

        //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
        final ArrayList<Product> listProduct;

        ProductListViewAdapter(ArrayList<Product> listProduct) {
            this.listProduct = listProduct;
        }

        @Override
        public int getCount() {
            //Trả về tổng số phần tử, nó được gọi bởi ListView
            return listProduct.size();
        }

        @Override
        public Object getItem(int position) {
            //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
            //có chỉ số position trong listProduct
            return listProduct.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
            //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
            //Nếu null cần tạo mới

            View viewProduct;
            if (convertView == null) {
                viewProduct = View.inflate(parent.getContext(), R.layout.doctor, null);
            } else viewProduct = convertView;

            //Bind sữ liệu phần tử vào View
            Product product = (Product) getItem(position);
            ((TextView) viewProduct.findViewById(R.id.doctor)).setText(String.format(product.name));
            ((TextView) viewProduct.findViewById(R.id.majors)).setText(String.format(product.major));
            ((TextView) viewProduct.findViewById(R.id.review)).setText(String.format("(%d Đánh giá)", product.review));
            ((TextView) viewProduct.findViewById(R.id.rate)).setText(String.format(String.valueOf(product.rate)));


            return viewProduct;
        }
    }
}