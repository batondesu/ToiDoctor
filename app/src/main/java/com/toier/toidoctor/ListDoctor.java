package com.toier.toidoctor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ListDoctor extends AppCompatActivity {

    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_doctors);

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
                Toast.makeText(ListDoctor.this, product.name, Toast.LENGTH_LONG).show();
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
